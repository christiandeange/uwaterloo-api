package com.deange.uwaterlooapi.sample.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.RelativeLayout;

import com.deange.uwaterlooapi.sample.R;

public class ExpandablePanel extends RelativeLayout {

  private final int mHandleId;
  private final int mContentId;

  private View mHandle;
  private View mContent;
  private OnExpandListener mListener;

  private boolean mExpanded = false;
  private int mCollapsedHeight = 0;
  private int mContentHeight = 0;

  public ExpandablePanel(final Context context) {
    this(context, null);
  }

  public ExpandablePanel(final Context context, final AttributeSet attrs) {
    super(context, attrs);

    final TypedArray a = context.obtainStyledAttributes(attrs,
                                                        R.styleable.ExpandablePanel, 0, 0);

    // How high the content should be in "collapsed" state
    mCollapsedHeight = (int) a.getDimension(
        R.styleable.ExpandablePanel_layout_collapsedHeight, 0);

    final int handleId = a.getResourceId(R.styleable.ExpandablePanel_handle, View.NO_ID);
    if (handleId == View.NO_ID) {
      throw new IllegalArgumentException(
          "The handle attribute is required and must refer to a valid child.");
    }

    final int contentId = a.getResourceId(R.styleable.ExpandablePanel_content, View.NO_ID);
    if (contentId == View.NO_ID) {
      throw new IllegalArgumentException(
          "The content attribute is required and must refer to a valid child.");
    }

    mHandleId = handleId;
    mContentId = contentId;

    a.recycle();
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();

    mHandle = findViewById(mHandleId);
    if (mHandle == null) {
      throw new IllegalArgumentException(
          "The handle attribute is must refer to an existing child.");
    }

    mContent = findViewById(mContentId);
    if (mContent == null) {
      throw new IllegalArgumentException(
          "The content attribute is must refer to an existing child.");
    }

    mHandle.setOnClickListener(v -> setExpanded(!mExpanded));
  }

  @Override
  protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {

    if (mContentHeight == 0) {
      setContentHeight(0);
    }

    mContent.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
    mContentHeight = mContent.getMeasuredHeight();

    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
  }

  public void setOnExpandListener(final OnExpandListener listener) {
    mListener = listener;
  }

  public void setExpandedImmediate(final boolean expand) {
    setContentHeight(expand ? mContentHeight : mCollapsedHeight);
    mExpanded = expand;
  }

  public void setExpanded(final boolean expand) {
    if (expand) expand();
    else collapse();
  }

  public void expand() {
    final Animation anim = new ExpandAnimation(mContent.getMeasuredHeight(), mContentHeight);
    if (mListener != null) mListener.onExpand(mHandle, mContent);
    anim.setDuration(500);
    mContent.startAnimation(anim);
    mExpanded = true;
  }

  public void collapse() {
    final Animation anim = new ExpandAnimation(mContent.getMeasuredHeight(), mCollapsedHeight);
    if (mListener != null) mListener.onCollapse(mHandle, mContent);
    anim.setDuration(500);
    mContent.startAnimation(anim);
    mExpanded = false;
  }

  private void setContentHeight(final int height) {
    ViewGroup.LayoutParams lp = mContent.getLayoutParams();
    lp.height = height;
    mContent.setLayoutParams(lp);
  }

  private class ExpandAnimation extends Animation {
    private final int mStartHeight;
    private final int mDeltaHeight;

    public ExpandAnimation(final int startHeight, final int endHeight) {
      mStartHeight = startHeight;
      mDeltaHeight = endHeight - startHeight;
    }

    @Override
    protected void applyTransformation(final float interpolatedTime, final Transformation t) {
      setContentHeight((int) (mStartHeight + mDeltaHeight * interpolatedTime));
    }

    @Override
    public boolean willChangeBounds() {
      return true;
    }
  }

  public interface OnExpandListener {
    public void onExpand(final View handle, final View content);

    public void onCollapse(final View handle, final View content);
  }

}
