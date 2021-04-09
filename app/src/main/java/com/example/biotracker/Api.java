package com.example.biotracker;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api
{
    String BASE_URL = "http://192.168.0.105/biotracker/";

    @POST("Api.php?apicall=user_register")
    @FormUrlEncoded
    Call<JsonObject> regUser(@Field("fullName") String fullName, @Field("dateOfBirth") String userDob, @Field("mobileNumber") String mobileNum,
                             @Field("emailAddress") String emailAddr, @Field("password") String userPass, @Field("houseName") String houseName,
                             @Field("place") String place, @Field("pinCode") String userPinCode, @Field("district") String userDst);
}
