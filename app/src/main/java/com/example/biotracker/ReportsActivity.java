package com.example.biotracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportsActivity extends AppCompatActivity {

    Spinner dateSpinner;
    Button buttonAnalyse, buttonApply;
    TextView textViewAmmonia, textViewPh, textViewOxygen, textViewNitrogen, textViewNitrate, textViewNitrite, textViewMortality, textViewWarning, textViewReportscaps;
    TableLayout tableReports;
    int dataCount;
    List<String> dates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        dateSpinner = findViewById(R.id.reports_spin_select);
        buttonAnalyse = findViewById(R.id.reports_button_analyse);
        buttonApply = findViewById(R.id.reports_button_apply);
        textViewAmmonia = findViewById(R.id.reports_text_ammonia);
        textViewPh = findViewById(R.id.reports_text_ph);
        textViewOxygen = findViewById(R.id.reports_text_oxygen);
        textViewNitrogen = findViewById(R.id.reports_text_nitrogen);
        textViewNitrate = findViewById(R.id.reports_text_nitrate);
        textViewNitrite = findViewById(R.id.reports_text_nitrite);
        textViewMortality = findViewById(R.id.reports_text_mortality);
        textViewWarning = findViewById(R.id.report_text_view_warning);
        tableReports = findViewById(R.id.table_report);
        textViewReportscaps = findViewById(R.id.reports_text_view_report);

        dates = new ArrayList<>();

        getDates();

        buttonApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String date = dateSpinner.getSelectedItem().toString();
                if (date.equals("Average"))
                {
                    getDataCount();
                    buttonAnalyse.setVisibility(View.VISIBLE);
                }
                else
                {
                    buttonAnalyse.setVisibility(View.GONE);
                    Call<JsonObject> dailyDataCall = RetrofitClient.getInstance().getMyApi().getDailyData(String.valueOf(getIntent().getExtras().getInt("farm_id")),date);
                    dailyDataCall.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                            if (response.isSuccessful())
                            {
                                try
                                {
                                    JSONObject jsonObject = new JSONObject(response.body().toString());
                                    if (!jsonObject.getBoolean("error"))
                                    {
                                        textViewReportscaps.setVisibility(View.VISIBLE);
                                        tableReports.setVisibility(View.VISIBLE);
                                        textViewAmmonia.setText(jsonObject.getString("ammonia"));
                                        textViewPh.setText(jsonObject.getString("ph"));
                                        textViewOxygen.setText(jsonObject.getString("oxygen"));
                                        textViewNitrogen.setText(jsonObject.getString("nitrogen"));
                                        textViewNitrate.setText(jsonObject.getString("nitrate"));
                                        textViewNitrite.setText(jsonObject.getString("nitrite"));
                                        textViewMortality.setText(jsonObject.getString("mortality"));
                                        textViewWarning.setVisibility(View.GONE);
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

        buttonAnalyse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent graphIntent = new Intent(ReportsActivity.this, GraphActivity.class);
                graphIntent.putExtra("ammonia", Double.parseDouble(textViewAmmonia.getText().toString()));
                graphIntent.putExtra("ph", Double.parseDouble(textViewPh.getText().toString()));
                graphIntent.putExtra("oxygen", Double.parseDouble(textViewOxygen.getText().toString()));
                graphIntent.putExtra("nitrogen", Double.parseDouble(textViewNitrogen.getText().toString()));
                graphIntent.putExtra("nitrate", Double.parseDouble(textViewNitrate.getText().toString()));
                graphIntent.putExtra("nitrite", Double.parseDouble(textViewNitrite.getText().toString()));
                graphIntent.putExtra("mortality", Integer.parseInt(textViewMortality.getText().toString()));
                graphIntent.putExtra("fish_count", getIntent().getExtras().getInt("fish_count"));
                startActivity(graphIntent);
                finish();
            }
        });
    }

    private void getDates()
    {
        Call<JsonArray> getDatesCall = RetrofitClient.getInstance().getMyApi().getDates(String.valueOf(getIntent().getExtras().getInt("farm_id")));
        getDatesCall.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {

                if (response.isSuccessful())
                {
                    try
                    {
                        JSONArray jsonArray = new JSONArray(response.body().toString());
                        for (int i=0;i<jsonArray.length();i++)
                        {
                            dates.add(jsonArray.getString(i));
                            ArrayAdapter arrayAdapter = new ArrayAdapter(ReportsActivity.this, android.R.layout.simple_spinner_item, dates);
                            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            dateSpinner.setAdapter(arrayAdapter);
                        }
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {

                Toast.makeText(ReportsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void getDataCount()
    {
        Call<JsonObject> getDataCountCall = RetrofitClient.getInstance().getMyApi().getCount(String.valueOf(getIntent().getExtras().getInt("farm_id")));

        getDataCountCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if (response.isSuccessful())
                {
                    try
                    {
                        JSONObject object = new JSONObject(response.body().toString());
                        if (!object.getBoolean("error"))
                        {
                            dataCount = object.getInt("count");
                            if (dataCount<5)
                            {
                                textViewReportscaps.setVisibility(View.VISIBLE);
                                tableReports.setVisibility(View.VISIBLE);
                                textViewAmmonia.setText("0");
                                textViewPh.setText("0");
                                textViewOxygen.setText("0");
                                textViewNitrogen.setText("0");
                                textViewNitrate.setText("0");
                                textViewNitrite.setText("0");
                                textViewMortality.setText("0");
                                textViewWarning.setVisibility(View.VISIBLE);
                            }
                            else
                            {
                                getAverage();
                            }
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

                Toast.makeText(ReportsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void getAverage()
    {
        Call<JsonObject> getAverageCall = RetrofitClient.getInstance().getMyApi().getAverage(String.valueOf(getIntent().getExtras().getInt("farm_id")));
        getAverageCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if (response.isSuccessful())
                {
                    try
                    {
                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        if (!jsonObject.getBoolean("error"))
                        {
                            textViewReportscaps.setVisibility(View.VISIBLE);
                            tableReports.setVisibility(View.VISIBLE);
                            textViewAmmonia.setText(jsonObject.getString("avg_ammonia"));
                            textViewPh.setText(jsonObject.getString("avg_ph"));
                            textViewOxygen.setText(jsonObject.getString("avg_oxygen"));
                            textViewNitrogen.setText(jsonObject.getString("avg_nitrogen"));
                            textViewNitrate.setText(jsonObject.getString("avg_nitrate"));
                            textViewNitrite.setText(jsonObject.getString("avg_nitrite"));
                            textViewMortality.setText(jsonObject.getString("sum_mortality"));
                            textViewWarning.setVisibility(View.GONE);
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

                Toast.makeText(ReportsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}