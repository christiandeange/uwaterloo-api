package com.deange.uwaterlooapi.sample.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.utils.Px;
import com.deange.uwaterlooapi.sample.utils.ViewUtils;

public class ModuleListItemView
    extends FrameLayout {

  private static final boolean ALTERNATE_ITEMS = false;

  private final String mTitle;
  private final String mDescription;
  private final Drawable mIcon;

  @BindView(android.R.id.text1) TextView mTitleView;
  @BindView(android.R.id.text2) TextView mDescriptionView;
  @BindView(android.R.id.icon) ImageView mIconView;

  public ModuleListItemView(final Context context) {
    this(context, null);
  }

  public ModuleListItemView(final Context context, final AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public ModuleListItemView(
      final Context context,
      final AttributeSet attrs,
      final int defStyleAttr) {
    super(context, attrs, defStyleAttr);

    final TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                                                                   R.styleable.ModuleListItemView,
                                                                   0, 0);

    mTitle = a.getString(R.styleable.ModuleListItemView_moduleTitle);
    mDescription = a.getString(R.styleable.ModuleListItemView_moduleDescription);
    mIcon = a.getDrawable(R.styleable.ModuleListItemView_moduleIcon);

    a.recycle();
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();

    inflate(getContext(), R.layout.simple_module_item, this);
    ButterKnife.bind(this);

    ViewUtils.setText(mTitleView, mTitle);
    ViewUtils.setText(mDescriptionView, mDescription);
    ViewUtils.setDrawable(mIconView, mIcon);

    if (ALTERNATE_ITEMS) {
      final ViewGroup imageViewParent = (ViewGroup) mIconView.getParent();
      final MarginLayoutParams lp = (MarginLayoutParams) mIconView.getLayoutParams();
      final int margin = Px.fromDp(16);

      post(() -> {
        final int position = ((ViewGroup) getParent()).indexOfChild(ModuleListItemView.this);

        if (position % 2 == 0) {
          mTitleView.setGravity(Gravity.CENTER_VERTICAL | Gravity.START);
          mDescriptionView.setGravity(Gravity.CENTER_VERTICAL | Gravity.START);

          imageViewParent.removeView(mIconView);
          imageViewParent.addView(mIconView, 0);

          lp.setMarginStart(0);
          lp.setMarginEnd(margin);

        } else {
          mTitleView.setGravity(Gravity.CENTER_VERTICAL | Gravity.END);
          mDescriptionView.setGravity(Gravity.CENTER_VERTICAL | Gravity.END);

          imageViewParent.removeView(mIconView);
          imageViewParent.addView(mIconView);

          lp.setMarginStart(margin);
          lp.setMarginEnd(0);
        }
      });
    }
  }

}
