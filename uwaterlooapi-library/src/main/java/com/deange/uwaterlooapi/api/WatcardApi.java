package com.deange.uwaterlooapi.api;

import android.support.annotation.RestrictTo;

import com.deange.uwaterlooapi.model.watcard.Watcard;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface WatcardApi {

    String URL = "https://watcard.uwaterloo.ca/";

    @RestrictTo(RestrictTo.Scope.LIBRARY)
    @GET("OneWeb/Account/LogOn")
    Call<ResponseBody> homepage();

    @RestrictTo(RestrictTo.Scope.LIBRARY)
    @FormUrlEncoded
    @POST("OneWeb/Account/LogOn")
    Call<ResponseBody> login(@Field("Account") String studentNumber, @Field("Password") String pin, @Field("__RequestVerificationToken") String token);

    @GET("OneWeb/Financial/Balances")
    Call<Watcard> balances();

}
