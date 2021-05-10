package com.example.biotracker;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api
{

    String BASE_URL = "http://"+URLs.ipAddress+"/biotracker/";

    @POST("Api.php?apicall=user_register")
    @FormUrlEncoded
    Call<JsonObject> regUser(@Field("fullName") String fullName, @Field("dateOfBirth") String userDob, @Field("mobileNumber") String mobileNum,
                             @Field("emailAddress") String emailAddr, @Field("password") String userPass, @Field("houseName") String houseName,
                             @Field("place") String place, @Field("pinCode") String userPinCode, @Field("district") String userDst);

    @POST("Api.php?apicall=add_farm")
    @FormUrlEncoded
    Call<JsonObject> addFarm(@Field("fish_type") String fishType, @Field("fish_count") String fishCount, @Field("tank_vol") String tankVolume,
                             @Field("start_date") String startDate, @Field("est_time") String estTime, @Field("user_id") String userId);

    @POST("Api.php?apicall=view_farm_details")
    @FormUrlEncoded
    Call<JsonArray> viewFarm(@Field("user_id") String userId);

    @POST("Api.php?apicall=add_daily_data")
    @FormUrlEncoded
    Call<JsonObject> addData(@Field("ammonia_val") String ammoniaValue, @Field("ph_val") String pHValue, @Field("oxygen_val") String oxygenValue,
                             @Field("nitrogen_val") String nitrogenValue, @Field("nitrate_val") String nitrateValue,
                             @Field("nitrite_val") String nitriteValue,@Field("mortal_count") String mortalCount,
                             @Field("data_date") String dataDate, @Field("farm_id") String farmId);

    @POST("Api.php?apicall=get_data_count")
    @FormUrlEncoded
    Call<JsonObject> getCount(@Field("farm_id") String farmId);

    @POST("Api.php?apicall=get_average")
    @FormUrlEncoded
    Call<JsonObject> getAverage(@Field("farm_id") String farmId);

    @POST("Api.php?apicall=get_dates")
    @FormUrlEncoded
    Call<JsonArray> getDates(@Field("farm_id") String farmId);

    @POST("Api.php?apicall=get_daily_data")
    @FormUrlEncoded
    Call<JsonObject> getDailyData(@Field("farm_id") String farmId, @Field("data_date") String dataDate);

    @POST("Api.php?apicall=get_products")
    Call<JsonArray> getProducts();

    @POST("Api.php?apicall=get_seller")
    @FormUrlEncoded
    Call<JsonObject> getSeller(@Field("seller_id") String sellerId);

    @POST("Api.php?apicall=add_orders")
    @FormUrlEncoded
    Call<JsonObject> addOrder(@Field("product_id") String productId, @Field("user_id") String userId, @Field("payment_mode") String paymentMode,
                              @Field("product_qty") String orderQty, @Field("order_amount") String orderAmount, @Field("order_address") String orderAddress);

    @POST("Api.php?apicall=get_orders")
    @FormUrlEncoded
    Call<JsonArray> getOrders(@Field("user_id") String userId);

    @POST("Api.php?apicall=cancel_order")
    @FormUrlEncoded
    Call<JsonObject> cancelOrder(@Field("order_id") String orderId);

}
