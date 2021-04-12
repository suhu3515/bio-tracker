package com.example.biotracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddFarmActivity extends AppCompatActivity {

    EditText editTextFishType, editTextFishCount, editTextStartDate, editTextEstTime;
    Button buttonNextStep;
    TextView textViewBreadth, textViewLength, textViewHeight, textViewCircleHeight, textViewRadius, textViewShape, textViewChangeShape;
    RadioGroup tankShapes;
    RadioButton radioButtonCircle, radioButtonRectangle;
    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_farm);

        editTextFishType = findViewById(R.id.edit_text_fish_name);
        editTextFishCount = findViewById(R.id.edit_text_fish_count);
        editTextStartDate = findViewById(R.id.edit_text_fish_start_date);
        editTextEstTime = findViewById(R.id.edit_text_fish_est_time);
        textViewShape = findViewById(R.id.text_view_tank_shape);
        buttonNextStep = findViewById(R.id.button_next_page);
        tankShapes = findViewById(R.id.tank_shape_grp);
        radioButtonCircle = findViewById(R.id.radio_circle);
        radioButtonRectangle = findViewById(R.id.radio_rect);


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                editTextStartDate.setText(sdf.format(myCalendar.getTime()));
            }
        };

        SharedPreferences sharedPreferences = this.getSharedPreferences("language_settings", Context.MODE_PRIVATE);
        String language = sharedPreferences.getString("selected_language",null);

        editTextStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(AddFarmActivity.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        radioButtonRectangle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                radioButtonCircle.setChecked(false);
                radioButtonRectangle.setChecked(true);

            }
        });

        radioButtonCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                radioButtonRectangle.setChecked(false);
                radioButtonCircle.setChecked(true);

            }
        });

        if (language.equals("Malayalam"))
        {
            editTextFishType.setHint("മത്സ്യത്തിന്റെ തരം");
            editTextFishCount.setHint("മത്സ്യത്തിന്റെ എണ്ണം");
            editTextStartDate.setHint("തുടങ്ങിയ തിയ്യതി");
            editTextEstTime.setHint("ഏകദേശ സമയം (മാസത്തിൽ)");
            textViewShape.setText("ടാങ്കിൻറെ രൂപം");
            radioButtonCircle.setText("വൃത്തം");
            radioButtonRectangle.setText("ദീർഘചതുരം");
        }

        buttonNextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editTextFishType.getText().toString().isEmpty())
                {
                    if (language.equals("Malayalam"))
                    {
                        editTextFishType.setError("മൽസ്യത്തിൻറെ തരം ചേർക്കുക");
                        editTextFishType.requestFocus();
                    }
                    else
                    {
                        editTextFishType.setError("Please enter fish type");
                        editTextFishType.requestFocus();
                    }
                }
                else if (editTextFishCount.getText().toString().isEmpty())
                {
                    if (language.equals("Malayalam"))
                    {
                        editTextFishCount.setError("മൽസ്യത്തിൻറെ എണ്ണം ചേർക്കുക");
                        editTextFishCount.requestFocus();
                    }
                    else
                    {
                        editTextFishCount.setError("Please enter count of fish");
                        editTextFishCount.requestFocus();
                    }
                }
                else if (editTextStartDate.getText().toString().isEmpty())
                {
                    if (language.equals("Malayalam"))
                    {
                        editTextStartDate.setError("തുടങ്ങിയ തിയ്യതി ചേർക്കുക");
                        editTextStartDate.requestFocus();
                    }
                    else
                    {
                        editTextStartDate.setError("Please enter start date");
                        editTextStartDate.requestFocus();
                    }
                }
                else if (editTextEstTime.getText().toString().isEmpty())
                {
                    if (language.equals("Malayalam"))
                    {
                        editTextEstTime.setError("ഏകദേശ വളർച്ച മാസത്തിൽ ചേർക്കുക");
                        editTextEstTime.requestFocus();
                    }
                    else
                    {
                        editTextEstTime.setError("Please enter estimated months");
                        editTextEstTime.requestFocus();
                    }
                }
                else
                {
                    String selected_shape = "";
                    if (tankShapes.getCheckedRadioButtonId() == R.id.radio_circle)
                    {
                        selected_shape = "Circle";
                    }
                    else
                    {
                        selected_shape = "Rectangle";
                    }

                    Intent volumeIntent = new Intent(AddFarmActivity.this, VolumeCalculationActivity.class);
                    volumeIntent.putExtra("fish_type", editTextFishType.getText().toString());
                    volumeIntent.putExtra("fish_count", editTextFishCount.getText().toString());
                    volumeIntent.putExtra("start_date", editTextStartDate.getText().toString());
                    volumeIntent.putExtra("est_time", editTextEstTime.getText().toString());
                    volumeIntent.putExtra("shape", selected_shape);
                    startActivity(volumeIntent);
                    finish();
                }

            }
        });

    }
}