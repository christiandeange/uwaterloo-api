package com.deange.uwaterlooapi.sample.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.annotation.IntDef;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.deange.uwaterlooapi.sample.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.TypefaceUtils;


public final class FontUtils {

  public static final int THIN = 0;
  public static final int LIGHT = 1;
  public static final int BOOK = 2;
  public static final int MEDIUM = 3;
  public static final int BOLD = 4;
  public static final int BLACK = 5;
  public static final int ULTRA = 6;
  public static final int THIN_ITALIC = 7;
  public static final int LIGHT_ITALIC = 8;
  public static final int BOOK_ITALIC = 9;
  public static final int MEDIUM_ITALIC = 10;
  public static final int BOLD_ITALIC = 11;
  public static final int BLACK_ITALIC = 12;
  public static final int ULTRA_ITALIC = 13;
  public static final int FANCY = 14;

  public static final int DEFAULT = BOOK;

  private static AssetManager sAssets;
  private static final SparseArray<String> FONTS = new SparseArray<>();

  private FontUtils() {
    throw new AssertionError();
  }

  public static void init(final Context context) {
    sAssets = context.getResources().getAssets();

    FONTS.put(THIN, context.getString(R.string.font_thin));
    FONTS.put(LIGHT, context.getString(R.string.font_light));
    FONTS.put(BOOK, context.getString(R.string.font_book));
    FONTS.put(MEDIUM, context.getString(R.string.font_medium));
    FONTS.put(BOLD, context.getString(R.string.font_bold));
    FONTS.put(BLACK, context.getString(R.string.font_black));
    FONTS.put(ULTRA, context.getString(R.string.font_ultra));
    FONTS.put(THIN_ITALIC, context.getString(R.string.font_thin_italic));
    FONTS.put(LIGHT_ITALIC, context.getString(R.string.font_light_italic));
    FONTS.put(BOOK_ITALIC, context.getString(R.string.font_book_italic));
    FONTS.put(MEDIUM_ITALIC, context.getString(R.string.font_medium_italic));
    FONTS.put(BOLD_ITALIC, context.getString(R.string.font_bold_italic));
    FONTS.put(BLACK_ITALIC, context.getString(R.string.font_black_italic));
    FONTS.put(ULTRA_ITALIC, context.getString(R.string.font_ultra_italic));
    FONTS.put(FANCY, context.getString(R.string.font_fancy));

    CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                                      .setDefaultFontPath(FONTS.get(DEFAULT))
                                      .setFontAttrId(R.attr.fontPath)
                                      .build());
  }

  public static void apply(final View view, final @Font int type) {
    apply(view, getFont(type));
  }

  public static void apply(
      final View view,
      final Typeface typeface) {
    if (view instanceof TextView) {
      ((TextView) view).setTypeface(typeface);

    } else if (view instanceof ViewGroup) {
      final ViewGroup parent = ((ViewGroup) view);
      for (int i = 0; i < parent.getChildCount(); ++i) {
        apply(parent.getChildAt(i), typeface);
      }
    }
  }

  public static Typeface getFont(final @Font int font) {
    final String fontPath = FONTS.get(font);
    return TypefaceUtils.load(sAssets, fontPath);
  }

  @IntDef({
      THIN, LIGHT, BOOK, MEDIUM, BOLD, BLACK, ULTRA,
      THIN_ITALIC, LIGHT_ITALIC, BOOK_ITALIC, MEDIUM_ITALIC, BOLD_ITALIC, BLACK_ITALIC, ULTRA_ITALIC,
      FANCY,})
  public @interface Font {
  }

}
