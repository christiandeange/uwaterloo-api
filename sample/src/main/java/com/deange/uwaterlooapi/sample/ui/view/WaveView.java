package com.deange.uwaterlooapi.sample.ui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import com.deange.uwaterlooapi.sample.ui.Colors;

public class WaveView extends View {

  private static final float DEFAULT_AMPLITUDE_RATIO = 0.05f;
  private static final float DEFAULT_WATER_LEVEL_RATIO = 0.5f;
  private static final float DEFAULT_WAVE_LENGTH_RATIO = 1.0f;
  private static final float DEFAULT_WAVE_SHIFT_RATIO = 0.0f;

  // if true, the shader will display the wave
  private boolean mShowWave;
  // shader containing repeated waves
  private BitmapShader mWaveShader;
  // shader matrix
  private Matrix mShaderMatrix;
  // paint to draw wave
  private Paint mViewPaint;

  private float mDefaultAmplitude;
  private float mDefaultWaterLevel;
  private double mDefaultAngularFrequency;

  private int mWaveColor = Colors.BLUE_500;
  private float mAmplitudeRatio = DEFAULT_AMPLITUDE_RATIO;
  private float mWaveLengthRatio = DEFAULT_WAVE_LENGTH_RATIO;
  private float mWaterLevelRatio = DEFAULT_WATER_LEVEL_RATIO;
  private float mWaveShiftRatio = DEFAULT_WAVE_SHIFT_RATIO;

  public WaveView(Context context) {
    super(context);
    init();
  }

  public WaveView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public WaveView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    init();
  }

  private void init() {
    mShaderMatrix = new Matrix();
    mViewPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
  }

  public float getWaveShiftRatio() {
    return mWaveShiftRatio;
  }

  /**
   * Shift the wave horizontally according to <code>waveShiftRatio</code>.
   *
   * @param waveShiftRatio Should be 0 ~ 1. Default to be 0.
   *                       <br/>Result of waveShiftRatio multiples width of WaveView is the length to shift.
   */
  public void setWaveShiftRatio(float waveShiftRatio) {
    if (mWaveShiftRatio != waveShiftRatio) {
      mWaveShiftRatio = waveShiftRatio;
      invalidate();
    }
  }

  public float getWaterLevelRatio() {
    return mWaterLevelRatio;
  }

  /**
   * Set water level according to <code>waterLevelRatio</code>.
   *
   * @param waterLevelRatio Should be 0 ~ 1. Default to be 0.5.
   *                        <br/>Ratio of water level to WaveView height.
   */
  public void setWaterLevelRatio(float waterLevelRatio) {
    if (mWaterLevelRatio != waterLevelRatio) {
      mWaterLevelRatio = waterLevelRatio;
      invalidate();
    }
  }

  public float getAmplitudeRatio() {
    return mAmplitudeRatio;
  }

  /**
   * Set vertical size of wave according to <code>amplitudeRatio</code>
   *
   * @param amplitudeRatio Default to be 0.05. Result of amplitudeRatio + waterLevelRatio should be less than 1.
   *                       <br/>Ratio of amplitude to height of WaveView.
   */
  public void setAmplitudeRatio(float amplitudeRatio) {
    if (mAmplitudeRatio != amplitudeRatio) {
      mAmplitudeRatio = amplitudeRatio;
      invalidate();
    }
  }

  public float getWaveLengthRatio() {
    return mWaveLengthRatio;
  }

  /**
   * Set horizontal size of wave according to <code>waveLengthRatio</code>
   *
   * @param waveLengthRatio Default to be 1.
   *                        <br/>Ratio of wave length to width of WaveView.
   */
  public void setWaveLengthRatio(float waveLengthRatio) {
    mWaveLengthRatio = waveLengthRatio;
  }

  public boolean isShowingWave() {
    return mShowWave;
  }

  public void setShowWave(boolean showWave) {
    mShowWave = showWave;
    invalidate();
  }

  public int getColor() {
    return mWaveColor;
  }

  public void setColor(final int color) {
    mWaveColor = color;
    createShader();

    invalidate();
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);

    createShader();
  }

  /**
   * Create the shader with default waves which repeat horizontally, and clamp vertically
   */
  private void createShader() {
    mDefaultAngularFrequency = 2.0f * Math.PI / DEFAULT_WAVE_LENGTH_RATIO / getWidth();
    mDefaultAmplitude = getHeight() * DEFAULT_AMPLITUDE_RATIO;
    mDefaultWaterLevel = getHeight() * DEFAULT_WATER_LEVEL_RATIO;

    Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(bitmap);

    // Draw default waves into the bitmap
    // y = Asin(ωx+φ)+h
    int waveX1 = 0;
    final int wave2Shift = getWidth() / 4;
    final int endX = getWidth();
    final int endY = getHeight();

    final Paint wavePaint1 = new Paint();
    wavePaint1.setColor(mWaveColor);
    wavePaint1.setAlpha(128);

    final Paint wavePaint2 = new Paint();
    wavePaint2.setColor(mWaveColor);
    wavePaint2.setAlpha(255);

    while (waveX1 < endX) {
      int waveX2 = (waveX1 + wave2Shift) % endX;
      double wx = waveX1 * mDefaultAngularFrequency;

      int startY = (int) (mDefaultWaterLevel + mDefaultAmplitude * Math.sin(wx));

      // draw bottom wave
      canvas.drawLine(waveX1, startY, waveX1, endY, wavePaint1);
      // draw top wave
      canvas.drawLine(waveX2, startY, waveX2, endY, wavePaint2);

      waveX1++;
    }

    // use the bitamp to create the shader
    mWaveShader = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.CLAMP);
    mViewPaint.setShader(mWaveShader);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    // modify paint shader according to mShowWave state
    if (mShowWave && mWaveShader != null) {
      // first call after mShowWave, assign it to our paint
      if (mViewPaint.getShader() == null) {
        mViewPaint.setShader(mWaveShader);
      }

      // scale shader according to mWaveLengthRatio and mAmplitudeRatio
      // this decides the size(mWaveLengthRatio for width, mAmplitudeRatio for height) of waves
      mShaderMatrix.setScale(
          mWaveLengthRatio / DEFAULT_WAVE_LENGTH_RATIO,
          mAmplitudeRatio / DEFAULT_AMPLITUDE_RATIO,
          0,
          mDefaultWaterLevel);

      // translate shader according to mWaveShiftRatio and mWaterLevelRatio
      // this decides the start position(mWaveShiftRatio for x, mWaterLevelRatio for y) of waves
      mShaderMatrix.postTranslate(
          mWaveShiftRatio * getWidth(),
          (DEFAULT_WATER_LEVEL_RATIO - mWaterLevelRatio) * getHeight());

      // assign matrix to invalidate the shader
      mWaveShader.setLocalMatrix(mShaderMatrix);

      float radiusW = getWidth() / 2f;
      float radiusH = getHeight() / 2f;
      float radius = Math.max(radiusW, radiusH);
      canvas.drawCircle(radiusW, radiusH, radius, mViewPaint);

    } else {
      mViewPaint.setShader(null);
    }
  }

}