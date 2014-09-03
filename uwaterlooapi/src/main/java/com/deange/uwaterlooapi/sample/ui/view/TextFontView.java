package com.deange.uwaterlooapi.sample.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import com.deange.uwaterlooapi.sample.R;


public class TextFontView extends TextView {

    public TextFontView(final Context context, final String defaultFont) {
        super(context);
		init(context, null, 0, defaultFont);
	}

	public TextFontView(final Context context, final AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs, 0, null);
	}

	public TextFontView(final Context context, final AttributeSet attrs, final int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs, defStyle, null);
	}

	private void init(final Context context, final AttributeSet attrs, final int defStyle, final String defaultFont) {

		final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.TextFontView, defStyle, 0);
        String fontName;
        if (a != null) {
			fontName = a.getString(R.styleable.TextFontView_fontName);
            if (!fontName.contains(".")) {
                fontName += ".ttf";
            }

			a.recycle();
		} else {
			fontName = defaultFont;
		}

		if (isInEditMode()) {
			// Fix to view the TextFontView in IDE graphical editor
			return;
		}

		if (!TextUtils.isEmpty(fontName)) {
			setTypeface(Typeface.createFromAsset(getContext().getAssets(), fontName));
		}
	}

}
