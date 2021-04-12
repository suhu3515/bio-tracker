package com.example.biotracker;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api
{
    String BASE_URL = "http://192.168.0.106/biotracker/";

    @POST("Api.php?apicall=user_register")
    @FormUrlEncoded
    Call<JsonObject> regUser(@Field("fullName") String fullName, @Field("dateOfBirth") String userDob, @Field("mobileNumber") String mobileNum,
                             @Field("emailAddress") String emailAddr, @Field("password") String userPass, @Field("houseName") String houseName,
                             @Field("place") String place, @Field("pinCode") String userPinCode, @Field("district") String userDst);

    @POST("Api.php?apicall=add_farm")
    @FormUrlEncoded
    Call<JsonObject> addFarm(@Field("fish_type") String fishType, @Field("fish_count") String fishCount, @Field("tank_vol") String tankVolume,
                             @Field("start_date") String startDate, @Field("est_time") String estTime, @Field("user_id") String userId);
}
