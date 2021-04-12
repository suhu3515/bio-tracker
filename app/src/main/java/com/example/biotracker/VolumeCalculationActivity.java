package com.example.biotracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class VolumeCalculationActivity extends AppCompatActivity {

    String selected_shape;
    EditText editTextRectangleLength, editTextRectangleBreadth, editTextRectangleHeight, editTextCircleHeight, editTextCircleRadius;
    Button buttonAddFarm;
    double volume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volume_calculation);

        editTextRectangleLength = findViewById(R.id.edit_text_rect_length);
        editTextRectangleBreadth = findViewById(R.id.edit_text_rect_breadth);
        editTextRectangleHeight = findViewById(R.id.edit_text_rect_height);
        editTextCircleRadius = findViewById(R.id.edit_text_circle_radius);
        editTextCircleHeight = findViewById(R.id.edit_text_circle_height);
        buttonAddFarm = findViewById(R.id.button_add_farm);

        selected_shape = getIntent().getExtras().getString("shape");
        User user = SharedPrefManager.getInstance(this).getUser();
        if (selected_shape.equals("Rectangle"))
        {
            editTextRectangleLength.setVisibility(View.VISIBLE);
            editTextRectangleBreadth.setVisibility(View.VISIBLE);
            editTextRectangleHeight.setVisibility(View.VISIBLE);
        }

        if (selected_shape.equals("Circle"))
        {
            editTextCircleHeight.setVisibility(View.VISIBLE);
            editTextCircleRadius.setVisibility(View.VISIBLE);
        }

        buttonAddFarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (selected_shape.equals("Circle"))
                {
                    if (editTextCircleRadius.getText().toString().isEmpty())
                    {
                        editTextCircleRadius.setError("Please enter radius in cm");
                        editTextCircleRadius.requestFocus();
                    }
                    else if (editTextCircleHeight.getText().toString().isEmpty())
                    {
                        editTextCircleHeight.setError("Please enter height in cm");
                        editTextCircleHeight.requestFocus();
                    }
                    else
                    {
                        volume = (3.14 * (Double.parseDouble(editTextCircleHeight.getText().toString())) * (Double.parseDouble(editTextCircleRadius.getText().toString())) * (Double.parseDouble(editTextCircleRadius.getText().toString())));
                    }
                }
                if (selected_shape.equals("Rectangle"))
                {
                    if (editTextRectangleLength.getText().toString().isEmpty())
                    {
                        editTextRectangleLength.setError("Please enter length in cm");
                        editTextRectangleLength.requestFocus();
                    }
                    else if (editTextRectangleBreadth.getText().toString().isEmpty())
                    {
                        editTextRectangleBreadth.setError("Please enter breadth in cm");
                        editTextRectangleBreadth.requestFocus();
                    }
                    else if (editTextRectangleHeight.getText().toString().isEmpty())
                    {
                        editTextRectangleHeight.setError("Please enter height in cm");
                        editTextRectangleHeight.requestFocus();
                    }
                    else
                    {
                        volume = (Double.parseDouble(editTextRectangleHeight.getText().toString()) * Double.parseDouble(editTextRectangleBreadth.getText().toString()) * Double.parseDouble(editTextRectangleLength.getText().toString()));
                    }
                }
                Call<JsonObject> addFarmCall = RetrofitClient.getInstance().getMyApi().addFarm(getIntent().getExtras().getString("fish_type"),
                        getIntent().getExtras().getString("fish_count"),
                        String.valueOf(volume),
                        getIntent().getExtras().getString("start_date"),
                        getIntent().getExtras().getString("est_time"),
                        String.valueOf(user.getId()));

                addFarmCall.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                        try
                        {
                            if (response.isSuccessful())
                            {
                                JSONObject object = new JSONObject(response.body().toString());
                                if (!object.getBoolean("error"))
                                {
                                    Toast.makeText(VolumeCalculationActivity.this, object.getString("message"), Toast.LENGTH_SHORT).show();
                                    Intent farmIntent = new Intent(VolumeCalculationActivity.this, FarmActivity.class);
                                    startActivity(farmIntent);
                                    finish();
                                }
                                else
                                {
                                    Toast.makeText(VolumeCalculationActivity.this, object.getString("message"), Toast.LENGTH_SHORT).show();
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

                        Toast.makeText(VolumeCalculationActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
    }
}