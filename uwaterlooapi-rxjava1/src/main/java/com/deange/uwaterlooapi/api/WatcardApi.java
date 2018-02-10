package com.deange.uwaterlooapi.api;

import android.support.annotation.RestrictTo;
import com.deange.uwaterlooapi.model.watcard.TransactionDate;
import com.deange.uwaterlooapi.model.watcard.Transactions;
import com.deange.uwaterlooapi.model.watcard.Watcard;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface WatcardApi {

  @RestrictTo(RestrictTo.Scope.LIBRARY)
  @GET("OneWeb/Account/LogOn")
  Observable<ResponseBody> homepage();

  @RestrictTo(RestrictTo.Scope.LIBRARY)
  @FormUrlEncoded
  @POST("OneWeb/Account/LogOn")
  Observable<ResponseBody> login(
      @Field("Account") String studentNumber,
      @Field("Password") String pin,
      @Field("__RequestVerificationToken") String token);

  @GET("OneWeb/Financial/Balances")
  Observable<Watcard> balances();

  @GET("OneWeb/Financial/TransactionsPass")
  Observable<Transactions> transactions(
      @Query("dateFrom") TransactionDate dateFrom,
      @Query("dateTo") TransactionDate dateTo,
      @Query("returnRows") int limit);

}
