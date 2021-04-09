package com.example.biotracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class ChooseLanguageActivity extends AppCompatActivity {

    RadioGroup radioGroup_langauges;
    int selectedId;
    Button btn_continue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_language);

        radioGroup_langauges = findViewById(R.id.language_grp);
        btn_continue = findViewById(R.id.btn_language_continue);

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectedId = radioGroup_langauges.getCheckedRadioButtonId();
                if (selectedId == R.id.radio_eng)
                {
                    setLanguage("English");
                }
                else if (selectedId == R.id.radio_ml)
                {
                    setLanguage("Malayalam");
                }
                Intent loginIntent = new Intent(ChooseLanguageActivity.this,LoginActivity.class);
                startActivity(loginIntent);
                finish();
            }
        });
    }

    private void setLanguage(String language)
    {
        SharedPreferences sharedPreferences = this.getSharedPreferences("language_settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("selected_language", language);
        editor.putBoolean("is_language_set", true);
        editor.apply();
    }
}