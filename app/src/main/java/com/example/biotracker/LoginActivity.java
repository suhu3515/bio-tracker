package com.example.biotracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class LoginActivity extends AppCompatActivity {

    EditText editTextMob, editTextPass;
    Button buttonLogin;
    TextView textViewRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextMob = findViewById(R.id.login_et_mobile);
        editTextPass = findViewById(R.id.login_et_password);
        buttonLogin = findViewById(R.id.login_btn_login);
        textViewRegister = findViewById(R.id.login_tv_register);

        if (SharedPrefManager.getInstance(this).isLoggedIn())
        {
            finish();
            startActivity(new Intent(this, HomeActivity.class));
            return;
        }

        SharedPreferences sharedPreferences = this.getSharedPreferences("language_settings", Context.MODE_PRIVATE);
        String language = sharedPreferences.getString("selected_language",null);

        if (language.equals("Malayalam"))
        {
            editTextMob.setHint(R.string.mobile_number_ml);
            editTextPass.setHint(R.string.password_ml);
            buttonLogin.setText(R.string.login_ml);
            textViewRegister.setText(R.string.create_an_account_ml);
        }

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editTextMob.getText().toString().isEmpty())
                {
                    editTextMob.setError("Please enter mobile number");
                    editTextMob.requestFocus();
                }
                else if (editTextPass.getText().toString().isEmpty())
                {
                    editTextPass.setError("Please enter mobile number");
                    editTextPass.requestFocus();
                }
                else
                {
                    Call<JsonObject> userLoginCall = RetrofitClient.getInstance().getMyApi().userLogin(editTextMob.getText().toString(),editTextPass.getText().toString());
                    userLoginCall.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                            if (response.isSuccessful())
                            {
                                try
                                {
                                    JSONObject jsonObject = new JSONObject(response.body().toString());
                                    if (!jsonObject.getBoolean("error"))
                                    {
                                        Toast.makeText(LoginActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                        JSONObject userJson = jsonObject.getJSONObject("user");
                                        User user = new User(userJson.getInt("user_id"),
                                                userJson.getString("user_name"),
                                                userJson.getString("user_dob"),
                                                userJson.getString("user_hname"),
                                                userJson.getString("user_place"),
                                                userJson.getString("user_pin"),
                                                userJson.getString("user_dst"),
                                                userJson.getString("user_mobile"),
                                                userJson.getString("user_email"));

                                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                                        Intent homeIntent = new Intent(LoginActivity.this, HomeActivity.class);
                                        startActivity(homeIntent);
                                        finish();
                                    }
                                    else
                                    {
                                        Toast.makeText(LoginActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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

                        }
                    });
                }

            }
        });

        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
                finish();
            }
        });

    }
}