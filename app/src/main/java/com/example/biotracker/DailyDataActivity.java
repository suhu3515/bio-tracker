package com.example.biotracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DailyDataActivity extends AppCompatActivity {

    EditText editTextAmmonia, editTextPh, editTextOxygen, editTextNitrogen, editTextNitrate, editTextNitrite, editTextMortalCount;
    Button buttonAddData;
    String todayDate;
    Calendar calendar;
    SimpleDateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_data);

        editTextAmmonia = findViewById(R.id.edit_text_data_ammonia);
        editTextPh = findViewById(R.id.edit_text_data_ph);
        editTextOxygen = findViewById(R.id.edit_text_data_oxygen);
        editTextNitrogen = findViewById(R.id.edit_text_data_nitrogen);
        editTextNitrate = findViewById(R.id.edit_text_data_nitrate);
        editTextNitrite = findViewById(R.id.edit_text_data_nitrite);
        editTextMortalCount = findViewById(R.id.edit_text_data_mortality_rate);
        buttonAddData = findViewById(R.id.button_data_add_data);

        int farmId = getIntent().getExtras().getInt("farm_id");

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        todayDate = dateFormat.format(calendar.getTime());

        buttonAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editTextAmmonia.getText().toString().isEmpty())
                {
                    editTextAmmonia.setError("Please enter value of ammonia");
                    editTextAmmonia.requestFocus();
                }
                else if (editTextPh.getText().toString().isEmpty())
                {
                    editTextPh.setError("Please enter value of pH");
                    editTextPh.requestFocus();
                }
                else if (editTextOxygen.getText().toString().isEmpty())
                {
                    editTextOxygen.setError("Please enter value of Oxygen");
                    editTextOxygen.requestFocus();
                }
                else if (editTextNitrogen.getText().toString().isEmpty())
                {
                    editTextNitrogen.setError("Please enter value of Nitrogen");
                    editTextNitrogen.requestFocus();
                }
                else if (editTextNitrate.getText().toString().isEmpty())
                {
                    editTextNitrate.setError("Please enter value of Nitrate");
                    editTextNitrate.requestFocus();
                }
                else if (editTextNitrite.getText().toString().isEmpty())
                {
                    editTextNitrite.setError("Please enter value of Nitrite");
                    editTextNitrite.requestFocus();
                }
                else if (editTextMortalCount.getText().toString().isEmpty())
                {
                    editTextMortalCount.setError("Please enter count of Mortality");
                    editTextMortalCount.requestFocus();
                }
                else
                {
                    Call<JsonObject> addDataCall = RetrofitClient.getInstance().getMyApi().addData(editTextAmmonia.getText().toString().trim(),
                            editTextPh.getText().toString().trim(), editTextOxygen.getText().toString().trim(), editTextNitrogen.getText().toString().trim(),
                            editTextNitrate.getText().toString().trim(), editTextNitrite.getText().toString().trim(), editTextMortalCount.getText().toString().trim(),
                            todayDate, String.valueOf(farmId));

                    addDataCall.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                            try
                            {
                                if (response.isSuccessful())
                                {
                                    JSONObject jsonObject = new JSONObject(response.body().toString());
                                    if (!jsonObject.getBoolean("error"))
                                    {
                                        Toast.makeText(DailyDataActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                    else
                                    {
                                        Toast.makeText(DailyDataActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                            catch (JSONException e)
                            {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {

                            Toast.makeText(DailyDataActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }
        });

    }
}