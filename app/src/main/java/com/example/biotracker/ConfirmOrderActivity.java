package com.example.biotracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmOrderActivity extends AppCompatActivity {

    TextView textViewPayment, textViewQty, textViewPrice, textViewAddress;
    Button buttonConfirm, buttonCancel;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);

        textViewPayment = findViewById(R.id.confirm_text_view_payment);
        textViewQty = findViewById(R.id.confirm_text_view_qty);
        textViewPrice = findViewById(R.id.confirm_text_view_total);
        textViewAddress = findViewById(R.id.confirm_text_view_address_text);
        buttonConfirm = findViewById(R.id.button_confirm_order);
        buttonCancel = findViewById(R.id.button_cancel_order);

        user = SharedPrefManager.getInstance(this).getUser();

        textViewPayment.setText(String.format("Payment Mode : %s", getIntent().getExtras().getString("pay_mode")));
        textViewQty.setText(String.format("Order Quantity : %d", getIntent().getExtras().getInt("order_qty")));
        textViewPrice.setText(String.format("Total Price : INR %d", getIntent().getExtras().getInt("order_total")));
        textViewAddress.setText(String.format("%s\n%s\nPincode: %s\n%s District",user.getUserHName(),user.getUserPlace(),user.getUserPincode(),user.getUserDistrict()));

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Call<JsonObject> addOrderCall = RetrofitClient.getInstance().getMyApi().addOrder(String.valueOf(getIntent().getExtras().getInt("pr_id")),String.valueOf(user.getId()),
                        getIntent().getExtras().getString("pay_mode"),String.valueOf(getIntent().getExtras().getInt("order_qty")),String.valueOf(getIntent().getExtras().getInt("order_total")));

                addOrderCall.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                        if (response.isSuccessful())
                        {
                            try
                            {
                                JSONObject obj = new JSONObject(response.body().toString());
                                if (!obj.getBoolean("error"))
                                {
                                    Toast.makeText(ConfirmOrderActivity.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
                                    Intent homeIntent = new Intent(ConfirmOrderActivity.this, HomeActivity.class);
                                    startActivity(homeIntent);
                                    finish();
                                }
                                else
                                {
                                    Toast.makeText(ConfirmOrderActivity.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
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

                        Toast.makeText(ConfirmOrderActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });
    }
}