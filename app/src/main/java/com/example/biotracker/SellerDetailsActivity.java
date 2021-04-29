package com.example.biotracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SellerDetailsActivity extends AppCompatActivity {

    TextView textViewSeller, textViewSellerAddress, textViewSellerPlace, textViewSellerDst, textViewSellerMobile, textViewSellerEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_details);

        textViewSeller = findViewById(R.id.seller_details_text_view_seller);
        textViewSellerAddress = findViewById(R.id.seller_details_text_view_addr);
        textViewSellerPlace = findViewById(R.id.seller_details_text_view_place);
        textViewSellerDst = findViewById(R.id.seller_details_text_view_dst);
        textViewSellerMobile = findViewById(R.id.seller_details_text_view_mob);
        textViewSellerEmail = findViewById(R.id.seller_details_text_view_mail);

        Call<JsonObject> getSellerCall = RetrofitClient.getInstance().getMyApi().getSeller(String.valueOf(getIntent().getExtras().getInt("seller_id")));

        getSellerCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if (response.isSuccessful())
                {
                    try
                    {
                        JSONObject object = new JSONObject(response.body().toString());
                        if (!object.getBoolean("error"))
                        {
                            textViewSeller.setText(object.getString("seller_name"));
                            textViewSellerAddress.setText(object.getString("seller_addr"));
                            textViewSellerPlace.setText(object.getString("seller_place"));
                            textViewSellerDst.setText(object.getString("seller_dst"));
                            textViewSellerMobile.setText(object.getString("seller_phone"));
                            textViewSellerEmail.setText(object.getString("seller_mail"));
                        }
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

                Toast.makeText(SellerDetailsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}