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

import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.utils.ViewUtils;

public class ModuleListItem
        extends FrameLayout {

    private static final boolean ALTERNATE_ITEMS = false;

    private final String mTitle;
    private final String mDescription;
    private final Drawable mIcon;

    public ModuleListItem(final Context context) {
        this(context, null);
    }

    public ModuleListItem(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ModuleListItem(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        final TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.ModuleListItem, 0, 0);

        mTitle = a.getString(R.styleable.ModuleListItem_moduleTitle);
        mDescription = a.getString(R.styleable.ModuleListItem_moduleDescription);
        mIcon = a.getDrawable(R.styleable.ModuleListItem_moduleIcon);

        a.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        inflate(getContext(), R.layout.simple_module_item, this);

        final TextView title = (TextView) findViewById(android.R.id.text1);
        final TextView description = (TextView) findViewById(android.R.id.text2);
        final ImageView imageView = (ImageView) findViewById(android.R.id.icon);

        ViewUtils.setText(title, mTitle);
        ViewUtils.setText(description, mDescription);
        ViewUtils.setDrawable(imageView, mIcon);

        final ViewGroup imageViewParent = (ViewGroup) imageView.getParent();
        final MarginLayoutParams lp = (MarginLayoutParams) imageView.getLayoutParams();
        final int margin = (int) (16 * getResources().getDisplayMetrics().density);

        if (ALTERNATE_ITEMS) {
            post(new Runnable() {
                @Override
                public void run() {
                    if (((ViewGroup) getParent()).indexOfChild(ModuleListItem.this) % 2 == 0) {
                        title.setGravity(Gravity.CENTER_VERTICAL | Gravity.START);
                        description.setGravity(Gravity.CENTER_VERTICAL | Gravity.START);

                        imageViewParent.removeView(imageView);
                        imageViewParent.addView(imageView, 0);

                        lp.setMarginStart(0);
                        lp.setMarginEnd(margin);

                    } else {
                        title.setGravity(Gravity.CENTER_VERTICAL | Gravity.END);
                        description.setGravity(Gravity.CENTER_VERTICAL | Gravity.END);

                        imageViewParent.removeView(imageView);
                        imageViewParent.addView(imageView);

                        lp.setMarginStart(margin);
                        lp.setMarginEnd(0);
                    }
                }
            });
        }
    }

}
