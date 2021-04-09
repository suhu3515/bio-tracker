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