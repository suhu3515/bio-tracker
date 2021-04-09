package com.example.biotracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = this.getSharedPreferences("language_settings", Context.MODE_PRIVATE);
        boolean isLanguageSet = sharedPreferences.getBoolean("is_language_set",false);
        if (!isLanguageSet)
        {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    Intent languageIntent = new Intent(MainActivity.this, ChooseLanguageActivity.class);
                    startActivity(languageIntent);
                    finish();
                }
            },3500);
        }
        else
        {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(loginIntent);
                    finish();
                }
            },3500);
        }
    }
}