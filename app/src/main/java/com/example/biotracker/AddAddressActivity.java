package com.example.biotracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;


import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAddressActivity extends AppCompatActivity {

    EditText editTextHouseName, editTextPlace, editTextPincode, editTextDistrict;
    Button buttonAddress;
    String fullName, emailAddress, dateOfBirth, mobileNumber, password;
    JsonObject jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        editTextHouseName = findViewById(R.id.address_et_h_name);
        editTextPlace = findViewById(R.id.address_et_place);
        editTextPincode = findViewById(R.id.address_et_pincode);
        editTextDistrict = findViewById(R.id.address_et_dst);
        buttonAddress = findViewById(R.id.address_btn_add);

        fullName = getIntent().getExtras().getString("fullName");
        emailAddress = getIntent().getExtras().getString("mailid");
        dateOfBirth = getIntent().getExtras().getString("dob");
        mobileNumber = getIntent().getExtras().getString("mobileno");
        password = getIntent().getExtras().getString("password");

        SharedPreferences sharedPreferences = this.getSharedPreferences("language_settings", Context.MODE_PRIVATE);
        String language = sharedPreferences.getString("selected_language",null);

        if (language.equals("Malayalam"))
        {
            editTextHouseName.setHint(R.string.house_name_ml);
            editTextPlace.setHint(R.string.place_ml);
            editTextPincode.setHint(R.string.pincode_ml);
            editTextDistrict.setHint(R.string.district_ml);
            buttonAddress.setText(R.string.add_address_ml);
        }



        buttonAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editTextHouseName.getText().toString().isEmpty())
                {
                    if (language.equals("Malayalam"))
                    {
                        editTextHouseName.setError("ദയവായി വീട്ട് പേര് ചേർക്കുക");
                        editTextHouseName.requestFocus();
                    }
                    else
                    {
                        editTextHouseName.setError("Please enter house name");
                        editTextHouseName.requestFocus();
                    }
                }
                else if (editTextPlace.getText().toString().isEmpty())
                {
                    if (language.equals("Malayalam"))
                    {
                        editTextPlace.setError("ദയവായി സ്ഥലം ചേർക്കുക");
                        editTextPlace.requestFocus();
                    }
                    else
                    {
                        editTextPlace.setError("Please enter place");
                        editTextPlace.requestFocus();
                    }
                }
                else if (editTextPincode.getText().toString().isEmpty())
                {
                    if (language.equals("Malayalam"))
                    {
                        editTextPincode.setError("ദയവായി പിൻകോഡ് ചേർക്കുക");
                        editTextPincode.requestFocus();
                    }
                    else
                    {
                        editTextPincode.setError("Please enter pincode");
                        editTextPincode.requestFocus();
                    }
                }
                else if (editTextPincode.getText().length()<6)
                {
                    if (language.equals("Malayalam"))
                    {
                        editTextPincode.setError("പിൻകോഡ് 6 അക്കങ്ങൾ വേണം");
                        editTextPincode.requestFocus();
                    }
                    else
                    {
                        editTextPincode.setError("Pincode should have 6  digits");
                        editTextPincode.requestFocus();
                    }
                }
                else if (editTextDistrict.getText().toString().isEmpty())
                {
                    if (language.equals("Malayalam"))
                    {
                        editTextDistrict.setError("ദയവായി ജില്ല ചേർക്കുക");
                        editTextDistrict.requestFocus();
                    }
                    else
                    {
                        editTextDistrict.setError("Please enter district");
                        editTextDistrict.requestFocus();
                    }
                }
                else
                {
                    Call<JsonObject> userRegCall = RetrofitClient.getInstance().getMyApi().regUser(fullName,dateOfBirth,mobileNumber,emailAddress,password,editTextHouseName.getText().toString().trim(),
                            editTextPlace.getText().toString().trim(),editTextPincode.getText().toString().trim(),editTextDistrict.getText().toString().trim());

                    userRegCall.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                            jsonObject = response.body();
                            if (response.isSuccessful())
                            {
                                try
                                {
                                    JSONObject json = new JSONObject(response.body().toString());
                                    if (!json.getBoolean("error"))
                                    {
                                        Toast.makeText(AddAddressActivity.this, json.getString("message"), Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(AddAddressActivity.this,LoginActivity.class));
                                        finish();
                                    }
                                    else
                                    {
                                        Toast.makeText(AddAddressActivity.this, json.getString("message"), Toast.LENGTH_SHORT).show();
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

                            Log.e("Error", t.getMessage());
                            Toast.makeText(AddAddressActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }
        });
    }
}