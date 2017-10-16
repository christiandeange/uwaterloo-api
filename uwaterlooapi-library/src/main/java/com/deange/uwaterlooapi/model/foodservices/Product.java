package com.deange.uwaterlooapi.model.foodservices;

import android.os.Parcel;
import android.os.Parcelable;

import com.deange.uwaterlooapi.model.BaseModel;
import com.google.gson.annotations.SerializedName;

public class Product
    extends BaseModel
    implements
    Parcelable {

  @SerializedName("product_id")
  int mId;

  @SerializedName("product_name")
  String mName;

  @SerializedName("ingredients")
  String mIngredients;

  @SerializedName("serving_size")
  String mServingSize;

  @SerializedName("serving_size_ml")
  int mServingSizeMl;

  @SerializedName("serving_size_g")
  int mServingSizeG;

  @SerializedName("calories")
  int mCalories;

  @SerializedName("total_fat_g")
  int mTotalFatG;

  @SerializedName("total_fat_percent")
  int mTotalFatPercent;

  @SerializedName("fat_saturated_g")
  int mFatSaturatedG;

  @SerializedName("fat_saturated_percent")
  int mFatSaturatedPercent;

  @SerializedName("fat_trans_g")
  int mFatTransG;

  @SerializedName("fat_trans_percent")
  int mFatTransPercent;

  @SerializedName("cholesterol_mg")
  int mCholesterol;

  @SerializedName("sodium_mg")
  int mSodiumMg;

  @SerializedName("sodium_percent")
  int mSodiumPercent;

  @SerializedName("carbo_g")
  int mCarbohydratesG;

  @SerializedName("carbo_percent")
  int mCarbohydratesPercent;

  @SerializedName("carbo_fibre_g")
  int mFibreG;

  @SerializedName("carbo_fibre_percent")
  int mFibrePercent;

  @SerializedName("carbo_sugars_g")
  int mSugar;

  @SerializedName("protein_g")
  int mProtein;

  @SerializedName("vitamin_a_percent")
  int mVitaminAPercent;

  @SerializedName("vitamin_c_percent")
  int mVitaminCPercent;

  @SerializedName("calcium_percent")
  int mCalcium;

  @SerializedName("iron_percent")
  int mIron;

  @SerializedName("micro_nutrients")
  String mMicroNutrients;

  @SerializedName("tips")
  String mTips;

  @SerializedName("diet_id")
  int mDietId;

  @SerializedName("diet_type")
  String mDietType;

  protected Product(final Parcel in) {
    super(in);
    mId = in.readInt();
    mName = in.readString();
    mIngredients = in.readString();
    mServingSize = in.readString();
    mServingSizeMl = in.readInt();
    mServingSizeG = in.readInt();
    mCalories = in.readInt();
    mTotalFatG = in.readInt();
    mTotalFatPercent = in.readInt();
    mFatSaturatedG = in.readInt();
    mFatSaturatedPercent = in.readInt();
    mFatTransG = in.readInt();
    mFatTransPercent = in.readInt();
    mCholesterol = in.readInt();
    mSodiumMg = in.readInt();
    mSodiumPercent = in.readInt();
    mCarbohydratesG = in.readInt();
    mCarbohydratesPercent = in.readInt();
    mFibreG = in.readInt();
    mFibrePercent = in.readInt();
    mSugar = in.readInt();
    mProtein = in.readInt();
    mVitaminAPercent = in.readInt();
    mVitaminCPercent = in.readInt();
    mCalcium = in.readInt();
    mIron = in.readInt();
    mMicroNutrients = in.readString();
    mTips = in.readString();
    mDietId = in.readInt();
    mDietType = in.readString();
  }

  @Override
  public void writeToParcel(final Parcel dest, final int flags) {
    super.writeToParcel(dest, flags);
    dest.writeInt(mId);
    dest.writeString(mName);
    dest.writeString(mIngredients);
    dest.writeString(mServingSize);
    dest.writeInt(mServingSizeMl);
    dest.writeInt(mServingSizeG);
    dest.writeInt(mCalories);
    dest.writeInt(mTotalFatG);
    dest.writeInt(mTotalFatPercent);
    dest.writeInt(mFatSaturatedG);
    dest.writeInt(mFatSaturatedPercent);
    dest.writeInt(mFatTransG);
    dest.writeInt(mFatTransPercent);
    dest.writeInt(mCholesterol);
    dest.writeInt(mSodiumMg);
    dest.writeInt(mSodiumPercent);
    dest.writeInt(mCarbohydratesG);
    dest.writeInt(mCarbohydratesPercent);
    dest.writeInt(mFibreG);
    dest.writeInt(mFibrePercent);
    dest.writeInt(mSugar);
    dest.writeInt(mProtein);
    dest.writeInt(mVitaminAPercent);
    dest.writeInt(mVitaminCPercent);
    dest.writeInt(mCalcium);
    dest.writeInt(mIron);
    dest.writeString(mMicroNutrients);
    dest.writeString(mTips);
    dest.writeInt(mDietId);
    dest.writeString(mDietType);
  }

  public static final Creator<Product> CREATOR = new Creator<Product>() {
    @Override
    public Product createFromParcel(final Parcel in) {
      return new Product(in);
    }

    @Override
    public Product[] newArray(final int size) {
      return new Product[size];
    }
  };

  /**
   * Food item's numeric id
   */
  public int getId() {
    return mId;
  }

  /**
   * Name of the food item
   */
  public String getName() {
    return mName;
  }

  /**
   * Food ingredients
   */
  public String getIngredients() {
    return mIngredients;
  }

  /**
   * Item's service size (in grams or whole)
   */
  public String getServingSize() {
    return mServingSize;
  }

  /**
   * Serving size in milliliters
   */
  public int getServingSizeMilliliters() {
    return mServingSizeMl;
  }

  /**
   * Serving size in grams
   */
  public int getServingSizeGrams() {
    return mServingSizeG;
  }

  /**
   * Food calorie count
   */
  public int getCalories() {
    return mCalories;
  }

  /**
   * Total fat in grams
   */
  public int getTotalFatGrams() {
    return mTotalFatG;
  }

  /**
   * Total fat in percentage
   */
  public int getTotalFatPercent() {
    return mTotalFatPercent;
  }

  /**
   * Total saturated fat in grams
   */
  public int getFatSaturatedGrams() {
    return mFatSaturatedG;
  }

  /**
   * Total saturated fat in percentage
   */
  public int getFatSaturatedPercent() {
    return mFatSaturatedPercent;
  }

  /**
   * Total trans fat in grams
   */
  public int getFatTransGrams() {
    return mFatTransG;
  }

  /**
   * Total trans fat in percentage
   */
  public int getFatTransPercent() {
    return mFatTransPercent;
  }

  /**
   * Total cholesterol in milligrams
   */
  public int getCholesterol() {
    return mCholesterol;
  }

  /**
   * Sodium in milligrams
   */
  public int getSodiumMilligrams() {
    return mSodiumMg;
  }

  /**
   * Sodium in percentage
   */
  public int getSodiumPercent() {
    return mSodiumPercent;
  }

  /**
   * Total carbohydrates in grams
   */
  public int getCarbohydratesGrams() {
    return mCarbohydratesG;
  }

  /**
   * Carbohydrates as percentage
   */
  public int getCarbohydratesPercent() {
    return mCarbohydratesPercent;
  }

  /**
   * Carbohydrate fibres in grams
   */
  public int getFibreGrams() {
    return mFibreG;
  }

  /**
   * Carbohydrate fibers as percentage
   */
  public int getFibrePercent() {
    return mFibrePercent;
  }

  /**
   * Carbohydrate sugar in grams
   */
  public int getSugarGrams() {
    return mSugar;
  }

  /**
   * Total protein in grams
   */
  public int getProteinGrams() {
    return mProtein;
  }

  /**
   * Total vitamin A percentage
   */
  public int getVitaminAPercent() {
    return mVitaminAPercent;
  }

  /**
   * Total vitamin C percentage
   */
  public int getVitaminCPercent() {
    return mVitaminCPercent;
  }

  /**
   * Total calcium percentage
   */
  public int getCalciumPercentage() {
    return mCalcium;
  }

  /**
   * Total iron percentage
   */
  public int getIronPercentage() {
    return mIron;
  }

  /**
   * Micro nutrients in item
   */
  public String getMicroNutrients() {
    return mMicroNutrients;
  }

  /**
   * Any eating tips for the item
   */
  public String getTips() {
    return mTips;
  }

  /**
   * Foodservices-given diet id
   */
  public int getDietId() {
    return mDietId;
  }

  /**
   * Foodservices-given diet string
   */
  public String getDietType() {
    return mDietType;
  }
}
