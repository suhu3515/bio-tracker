package com.example.biotracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FarmDetailsActivity extends AppCompatActivity {

    TextView textViewFishName, textViewFishCount, textViewStartDate, textViewEstimatedTime, textViewTankVolume;
    Button buttonAddDailyData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farm_details);

        textViewFishName = findViewById(R.id.text_view_details_fish_name);
        textViewFishCount = findViewById(R.id.text_view_details_fish_count);
        textViewEstimatedTime = findViewById(R.id.text_view_details_est_time);
        textViewStartDate = findViewById(R.id.text_view_details_start_date);
        textViewTankVolume = findViewById(R.id.text_view_details_tank_volume);
        buttonAddDailyData = findViewById(R.id.button_details_add_daily_data);

        textViewFishName.setText(getIntent().getExtras().getString("fish_type"));
        textViewFishCount.setText(getIntent().getExtras().getString("fish_count"));
        textViewStartDate.setText(String.format("   %s", getIntent().getExtras().getString("start_date")));
        textViewEstimatedTime.setText(String.format("   %s MONTHS", getIntent().getExtras().getString("est_time")));
        textViewTankVolume.setText(String.format("   %s LITER TANK", getIntent().getExtras().getString("tank_volume")));
        int farm_id = getIntent().getExtras().getInt("farm_id");

        buttonAddDailyData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent dailyDataIntent = new Intent(FarmDetailsActivity.this,DailyDataActivity.class);
                dailyDataIntent.putExtra("farm_id", farm_id);
                startActivity(dailyDataIntent);
                finish();
            }
        });
    }
}