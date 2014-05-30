package com.deange.uwaterlooapi.model.foodservices;

import com.deange.uwaterlooapi.model.BaseModel;
import com.google.gson.annotations.SerializedName;

public class Product extends BaseModel {

    @SerializedName("product_id")
    private int mId;

    @SerializedName("product_name")
    private String mName;

    @SerializedName("ingredients")
    private String mIngredients;

    @SerializedName("serving_size")
    private String mServingSize;

    @SerializedName("serving_size_ml")
    private int mServingSizeMl;

    @SerializedName("serving_size_g")
    private int mServingSizeG;

    @SerializedName("calories")
    private int mCalories;

    @SerializedName("total_fat_g")
    private int mTotalFatG;

    @SerializedName("total_fat_percent")
    private int mTotalFatPercent;

    @SerializedName("fat_saturated_g")
    private int mFatSaturatedG;

    @SerializedName("fat_saturated_percent")
    private int mFatSaturatedPercent;

    @SerializedName("fat_trans_g")
    private int mFatTransG;

    @SerializedName("fat_trans_percent")
    private int mFatTransPercent;

    @SerializedName("cholesterol_mg")
    private int mCholesterol;

    @SerializedName("sodium_mg")
    private int mSodiumMg;

    @SerializedName("sodium_percent")
    private int mSodiumPercent;

    @SerializedName("carbo_g")
    private int mCarbohydratesG;

    @SerializedName("carbo_percent")
    private int mCarbohydratesPercent;

    @SerializedName("carbo_fibre_g")
    private int mFibreG;

    @SerializedName("carbo_fibre_percent")
    private int mFibrePercent;

    @SerializedName("carbo_sugars_g")
    private int mSugar;

    @SerializedName("protein_g")
    private int mProtein;

    @SerializedName("vitamin_a_percent")
    private int mVitaminAPercent;

    @SerializedName("vitamin_c_percent")
    private int mVitaminCPercent;

    @SerializedName("calcium_percent")
    private int mCalcium;

    @SerializedName("iron_percent")
    private int mIron;

    @SerializedName("micro_nutrients")
    private String mMicroNutrients;

    @SerializedName("tips")
    private String mTips;

    @SerializedName("diet_id")
    private int mDietId;

    @SerializedName("diet_type")
    private String mDietType;

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getIngredients() {
        return mIngredients;
    }

    public String getServingSize() {
        return mServingSize;
    }

    public int getServingSizeMilliliters() {
        return mServingSizeMl;
    }

    public int getServingSizeGrams() {
        return mServingSizeG;
    }

    public int getCalories() {
        return mCalories;
    }

    public int getTotalFatGrams() {
        return mTotalFatG;
    }

    public int getTotalFatPercent() {
        return mTotalFatPercent;
    }

    public int getFatSaturatedGrams() {
        return mFatSaturatedG;
    }

    public int getFatSaturatedPercent() {
        return mFatSaturatedPercent;
    }

    public int getFatTransGrams() {
        return mFatTransG;
    }

    public int getFatTransPercent() {
        return mFatTransPercent;
    }

    public int getCholesterol() {
        return mCholesterol;
    }

    public int getSodiumMilligrams() {
        return mSodiumMg;
    }

    public int getSodiumPercent() {
        return mSodiumPercent;
    }

    public int getCarbohydratesGrams() {
        return mCarbohydratesG;
    }

    public int getCarbohydratesPercent() {
        return mCarbohydratesPercent;
    }

    public int getFibreGrams() {
        return mFibreG;
    }

    public int getFibrePercent() {
        return mFibrePercent;
    }

    public int getSugarGrams() {
        return mSugar;
    }

    public int getProteinGrams() {
        return mProtein;
    }

    public int getVitaminAPercent() {
        return mVitaminAPercent;
    }

    public int getVitaminCPercent() {
        return mVitaminCPercent;
    }

    public int getCalciumGrams() {
        return mCalcium;
    }

    public int getIronGrams() {
        return mIron;
    }

    public String getMicroNutrients() {
        return mMicroNutrients;
    }

    public String getTips() {
        return mTips;
    }

    public int getDietId() {
        return mDietId;
    }

    public String getDietType() {
        return mDietType;
    }
}
