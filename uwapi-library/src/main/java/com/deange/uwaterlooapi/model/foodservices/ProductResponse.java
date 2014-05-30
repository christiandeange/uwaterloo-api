package com.deange.uwaterlooapi.model.foodservices;

import com.deange.uwaterlooapi.model.BaseResponse;
import com.google.gson.annotations.SerializedName;

public class ProductResponse extends BaseResponse {

    @SerializedName("data")
    private Product mProduct;

    public Product getProduct() {
        return mProduct;
    }

}
