package com.example.biotracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmOrderActivity extends AppCompatActivity {

    TextView textViewPayment, textViewQty, textViewPrice;
    EditText editTextHName, editTextPlace, editTextPincode, editTextDistrict;
    Button buttonConfirm, buttonCancel;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);

        textViewPayment = findViewById(R.id.confirm_text_view_payment);
        textViewQty = findViewById(R.id.confirm_text_view_qty);
        textViewPrice = findViewById(R.id.confirm_text_view_total);
        buttonConfirm = findViewById(R.id.button_confirm_order);
        buttonCancel = findViewById(R.id.button_cancel_order);
        editTextHName = findViewById(R.id.confirm_et_h_name);
        editTextPlace = findViewById(R.id.confirm_et_place);
        editTextPincode = findViewById(R.id.confirm_et_pincode);
        editTextDistrict = findViewById(R.id.confirm_et_dst);

        user = SharedPrefManager.getInstance(this).getUser();

        textViewPayment.setText(String.format("Payment Mode : %s", getIntent().getExtras().getString("pay_mode")));
        textViewQty.setText(String.format("Order Quantity : %d", getIntent().getExtras().getInt("order_qty")));
        textViewPrice.setText(String.format("Total Price : INR %d", getIntent().getExtras().getInt("order_total")));
        editTextHName.setText(user.getUserHName());
        editTextPlace.setText(user.getUserPlace());
        editTextPincode.setText(user.getUserPincode());
        editTextDistrict.setText(user.getUserDistrict());

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editTextHName.getText().toString().isEmpty())
                {
                    editTextHName.setError("Enter the house name");
                    editTextHName.requestFocus();
                }
                else if (editTextPlace.getText().toString().isEmpty())
                {
                    editTextPlace.setError("Enter the place");
                    editTextPlace.requestFocus();
                }
                else if (editTextPincode.getText().toString().isEmpty())
                {
                    editTextPincode.setError("Enter the pincode");
                    editTextPincode.requestFocus();
                }
                else if (editTextDistrict.getText().toString().isEmpty())
                {
                    editTextDistrict.setError("Enter the district");
                    editTextDistrict.requestFocus();
                }
                else
                {
                    String address = editTextHName.getText().toString() + ",\n" + editTextPlace.getText().toString() + ",\n" + editTextPincode.getText().toString() + ",\n" + editTextDistrict.getText().toString();
                    Call<JsonObject> addOrderCall = RetrofitClient.getInstance().getMyApi().addOrder(String.valueOf(getIntent().getExtras().getInt("pr_id")),String.valueOf(user.getId()),
                            getIntent().getExtras().getString("pay_mode"),String.valueOf(getIntent().getExtras().getInt("order_qty")),String.valueOf(getIntent().getExtras().getInt("order_total")),address);

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
            }
        });
    }
}