package com.deange.uwaterlooapi.sample.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.deange.uwaterlooapi.sample.R;

public class FaqSection extends FrameLayout {

  private final TextView mQuestionView;
  private final TextView mAnswerView;

  public FaqSection(final Context context) {
    this(context, null);
  }

  public FaqSection(final Context context, final AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public FaqSection(final Context context, final AttributeSet attrs, final int defStyleAttr) {
    super(context, attrs, defStyleAttr);

    inflate(getContext(), R.layout.view_faq_section, this);
    mQuestionView = (TextView) findViewById(android.R.id.text1);
    mAnswerView = (TextView) findViewById(android.R.id.text2);

    final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FaqSection, defStyleAttr,
                                                        0);

    String text1 = a.getString(R.styleable.FaqSection_text1);
    String text2 = a.getString(R.styleable.FaqSection_text2);

    if (a.hasValue(R.styleable.FaqSection_android_entries)) {
      final CharSequence[] entries = a.getTextArray(R.styleable.FaqSection_android_entries);
      if (entries != null) {
        if (entries.length > 0) text1 = entries[0].toString();
        if (entries.length > 1) text2 = entries[1].toString();
      }
    }
    a.recycle();

    setQuestion(text1);
    setAnswer(text2);

    mQuestionView.setMovementMethod(LinkMovementMethod.getInstance());
    mAnswerView.setMovementMethod(LinkMovementMethod.getInstance());
  }


  public void setQuestion(final CharSequence text) {
    mQuestionView.setText(Html.fromHtml(String.valueOf(text)));
  }

  public void setAnswer(final CharSequence text) {
    mAnswerView.setText(Html.fromHtml(String.valueOf(text)));
  }

}
