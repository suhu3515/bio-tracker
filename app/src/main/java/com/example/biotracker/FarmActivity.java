package com.example.biotracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class FarmActivity extends AppCompatActivity {

    FloatingActionButton floatingActionButtonAddFarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farm);

        floatingActionButtonAddFarm = findViewById(R.id.fab_add_farm);
        floatingActionButtonAddFarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent farmAddIntent = new Intent(FarmActivity.this, AddFarmActivity.class);
                startActivity(farmAddIntent);
                finish();

            }
        });

    }
}