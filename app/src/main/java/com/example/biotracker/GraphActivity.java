package com.example.biotracker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GraphActivity extends AppCompatActivity {

    double ammonia, oxygen, ph, nitrogen, nitrate, nitrite;
    int mortal, fishCount;
    TextView textViewAmmonia, textViewAmmoniaValue, textViewPh, textViewPhValue, textViewOxygen, textViewOxygenValue, textViewNitrogen, textViewNitrogenValue, textViewNitrate, textViewNitrateValue, textViewNitrite, textViewNitriteValue, textViewMortal, textViewMortalValue;
    Button buttonGoHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        ammonia = getIntent().getExtras().getDouble("ammonia");
        ph = getIntent().getExtras().getDouble("ph");
        oxygen = getIntent().getExtras().getDouble("oxygen");
        nitrogen = getIntent().getExtras().getDouble("nitrogen");
        nitrate = getIntent().getExtras().getDouble("nitrate");
        nitrite = getIntent().getExtras().getDouble("nitrite");
        mortal = getIntent().getExtras().getInt("mortality");
        fishCount = getIntent().getExtras().getInt("fish_count");

        textViewAmmonia = findViewById(R.id.graph_ammonia_msg);
        textViewAmmoniaValue = findViewById(R.id.graph_ammonia_action);
        textViewPh = findViewById(R.id.graph_ph_msg);
        textViewPhValue = findViewById(R.id.graph_ph_action);
        textViewOxygen = findViewById(R.id.graph_oxygen_msg);
        textViewOxygenValue = findViewById(R.id.graph_oxygen_action);
        textViewNitrogen = findViewById(R.id.graph_nitrogen_msg);
        textViewNitrogenValue = findViewById(R.id.graph_nitrogen_action);
        textViewNitrate = findViewById(R.id.graph_nitrate_msg);
        textViewNitrateValue = findViewById(R.id.graph_nitrate_action);
        textViewNitrite = findViewById(R.id.graph_nitrite_msg);
        textViewNitriteValue = findViewById(R.id.graph_nitrite_action);
        textViewMortal = findViewById(R.id.graph_mortality_msg);
        textViewMortalValue = findViewById(R.id.graph_mortality_action);
        buttonGoHome = findViewById(R.id.graph_button_home);

        SharedPreferences sharedPreferences = this.getSharedPreferences("language_settings", Context.MODE_PRIVATE);
        String language = sharedPreferences.getString("selected_language",null);

        Log.e( "results: ", "Ammonia : " + ammonia + "\npH : " + ph + "\nOxygen : " + oxygen + "\nNitrogen : " + nitrogen + "\nNitrate : " + nitrate + "\nNitrite : " + nitrite + "\nMortal : " + mortal );

        buttonGoHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent farmIntent = new Intent(GraphActivity.this, HomeActivity.class);
                startActivity(farmIntent);
                finish();
            }
        });

        if (ammonia > 2.5)
        {
            textViewAmmonia.setText("Ammonia level is high");
            textViewAmmoniaValue.setVisibility(View.VISIBLE);
            textViewAmmoniaValue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (language.equals("Malayalam"))
                    {
                        Call<JsonObject> highAmmoniaMLCall = RetrofitClient.getInstance().getMyApi().viewSuggestion(language, "high_ammonia");
                        highAmmoniaMLCall.enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                                try
                                {
                                    JSONObject jsonObject = new JSONObject(response.body().toString());
                                    if (!jsonObject.getBoolean("error"))
                                    {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(GraphActivity.this);
                                        builder.setMessage(jsonObject.getString("message"))
                                                .setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                        dialog.dismiss();
                                                    }
                                                });
                                        AlertDialog alertDialog = builder.create();
                                        alertDialog.show();
                                    }
                                }
                                catch (JSONException e)
                                {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {

                                Toast.makeText(GraphActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                    else
                    {
                        Call<JsonObject> highAmmoniaENGCall = RetrofitClient.getInstance().getMyApi().viewSuggestion("English", "high_ammonia");
                        highAmmoniaENGCall.enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                                try
                                {
                                    JSONObject jsonObject = new JSONObject(response.body().toString());
                                    if (!jsonObject.getBoolean("error"))
                                    {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(GraphActivity.this);
                                        builder.setMessage(jsonObject.getString("message"))
                                                .setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                        dialog.dismiss();
                                                    }
                                                });
                                        AlertDialog alertDialog = builder.create();
                                        alertDialog.show();
                                    }
                                }
                                catch (JSONException e)
                                {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {

                                Toast.makeText(GraphActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                }
            });
        }
        if (ammonia < 1.5)
        {
            textViewAmmonia.setText("Ammonia level is low");
            textViewAmmoniaValue.setVisibility(View.VISIBLE);
            textViewAmmoniaValue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (language.equals("Malayalam"))
                    {
                        Call<JsonObject> lowAmmoniaMLCall = RetrofitClient.getInstance().getMyApi().viewSuggestion(language, "low_ammonia");
                        lowAmmoniaMLCall.enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                                try
                                {
                                    JSONObject jsonObject = new JSONObject(response.body().toString());
                                    if (!jsonObject.getBoolean("error"))
                                    {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(GraphActivity.this);
                                        builder.setMessage(jsonObject.getString("message"))
                                                .setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                        dialog.dismiss();
                                                    }
                                                });
                                        AlertDialog alertDialog = builder.create();
                                        alertDialog.show();
                                    }
                                }
                                catch (JSONException e)
                                {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {

                                Toast.makeText(GraphActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                    else
                    {
                        Call<JsonObject> lowAmmoniaENGCall = RetrofitClient.getInstance().getMyApi().viewSuggestion("English", "low_ammonia");
                        lowAmmoniaENGCall.enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                                try
                                {
                                    JSONObject jsonObject = new JSONObject(response.body().toString());
                                    if (!jsonObject.getBoolean("error"))
                                    {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(GraphActivity.this);
                                        builder.setMessage(jsonObject.getString("message"))
                                                .setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                        dialog.dismiss();
                                                    }
                                                });
                                        AlertDialog alertDialog = builder.create();
                                        alertDialog.show();
                                    }
                                }
                                catch (JSONException e)
                                {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {

                                Toast.makeText(GraphActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                }
            });
        }
        if (ph > 8.25)
        {
            textViewPh.setText("pH level is high");
            textViewPhValue.setVisibility(View.VISIBLE);
            textViewPhValue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (language.equals("Malayalam"))
                    {
                        Call<JsonObject> highPhMLCall = RetrofitClient.getInstance().getMyApi().viewSuggestion(language, "high_ph");
                        highPhMLCall.enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                                try
                                {
                                    JSONObject jsonObject = new JSONObject(response.body().toString());
                                    if (!jsonObject.getBoolean("error"))
                                    {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(GraphActivity.this);
                                        builder.setMessage(jsonObject.getString("message"))
                                                .setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                        dialog.dismiss();
                                                    }
                                                });
                                        AlertDialog alertDialog = builder.create();
                                        alertDialog.show();
                                    }
                                }
                                catch (JSONException e)
                                {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {

                                Toast.makeText(GraphActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                    else
                    {
                        Call<JsonObject> highPhENGCall = RetrofitClient.getInstance().getMyApi().viewSuggestion("English", "high_ph");
                        highPhENGCall.enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                                try
                                {
                                    JSONObject jsonObject = new JSONObject(response.body().toString());
                                    if (!jsonObject.getBoolean("error"))
                                    {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(GraphActivity.this);
                                        builder.setMessage(jsonObject.getString("message"))
                                                .setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                        dialog.dismiss();
                                                    }
                                                });
                                        AlertDialog alertDialog = builder.create();
                                        alertDialog.show();
                                    }
                                }
                                catch (JSONException e)
                                {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {

                                Toast.makeText(GraphActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                }
            });
        }
        if (ph < 6.75)
        {
            textViewPh.setText("pH level is low");
            textViewPhValue.setVisibility(View.VISIBLE);
            textViewPhValue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (language.equals("Malayalam"))
                    {
                        Call<JsonObject> lowPhMLCall = RetrofitClient.getInstance().getMyApi().viewSuggestion(language, "low_ph");
                        lowPhMLCall.enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                                try
                                {
                                    JSONObject jsonObject = new JSONObject(response.body().toString());
                                    if (!jsonObject.getBoolean("error"))
                                    {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(GraphActivity.this);
                                        builder.setMessage(jsonObject.getString("message"))
                                                .setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                        dialog.dismiss();
                                                    }
                                                });
                                        AlertDialog alertDialog = builder.create();
                                        alertDialog.show();
                                    }
                                }
                                catch (JSONException e)
                                {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {

                                Toast.makeText(GraphActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                    else
                    {
                        Call<JsonObject> lowPhMLCall = RetrofitClient.getInstance().getMyApi().viewSuggestion("English", "low_ph");
                        lowPhMLCall.enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                                try
                                {
                                    JSONObject jsonObject = new JSONObject(response.body().toString());
                                    if (!jsonObject.getBoolean("error"))
                                    {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(GraphActivity.this);
                                        builder.setMessage(jsonObject.getString("message"))
                                                .setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                        dialog.dismiss();
                                                    }
                                                });
                                        AlertDialog alertDialog = builder.create();
                                        alertDialog.show();
                                    }
                                }
                                catch (JSONException e)
                                {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {

                                Toast.makeText(GraphActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                }
            });
        }
        if (oxygen > 1.25)
        {
            textViewOxygen.setText("Oxygen level is high");
            textViewOxygenValue.setVisibility(View.VISIBLE);
            textViewOxygenValue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (language.equals("Malayalam"))
                    {
                        Call<JsonObject> highOxygenMLCall = RetrofitClient.getInstance().getMyApi().viewSuggestion(language, "high_oxygen");
                        highOxygenMLCall.enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                                try
                                {
                                    JSONObject jsonObject = new JSONObject(response.body().toString());
                                    if (!jsonObject.getBoolean("error"))
                                    {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(GraphActivity.this);
                                        builder.setMessage(jsonObject.getString("message"))
                                                .setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                        dialog.dismiss();
                                                    }
                                                });
                                        AlertDialog alertDialog = builder.create();
                                        alertDialog.show();
                                    }
                                }
                                catch (JSONException e)
                                {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {

                                Toast.makeText(GraphActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                    else
                    {
                        Call<JsonObject> highOxygenENGCall = RetrofitClient.getInstance().getMyApi().viewSuggestion("English", "high_oxygen");
                        highOxygenENGCall.enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                                try
                                {
                                    JSONObject jsonObject = new JSONObject(response.body().toString());
                                    if (!jsonObject.getBoolean("error"))
                                    {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(GraphActivity.this);
                                        builder.setMessage(jsonObject.getString("message"))
                                                .setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                        dialog.dismiss();
                                                    }
                                                });
                                        AlertDialog alertDialog = builder.create();
                                        alertDialog.show();
                                    }
                                }
                                catch (JSONException e)
                                {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {

                                Toast.makeText(GraphActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                }
            });
        }
        if (oxygen < 1.0)
        {
            textViewOxygen.setText("Oxygen level is low");
            textViewOxygenValue.setVisibility(View.VISIBLE);
            textViewOxygenValue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (language.equals("Malayalam"))
                    {
                        Call<JsonObject> lowOxygenMLCall = RetrofitClient.getInstance().getMyApi().viewSuggestion(language, "low_oxygen");
                        lowOxygenMLCall.enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                                try
                                {
                                    JSONObject jsonObject = new JSONObject(response.body().toString());
                                    if (!jsonObject.getBoolean("error"))
                                    {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(GraphActivity.this);
                                        builder.setMessage(jsonObject.getString("message"))
                                                .setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                        dialog.dismiss();
                                                    }
                                                });
                                        AlertDialog alertDialog = builder.create();
                                        alertDialog.show();
                                    }
                                }
                                catch (JSONException e)
                                {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {

                                Toast.makeText(GraphActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                    else
                    {
                        Call<JsonObject> lowOxygenENGCall = RetrofitClient.getInstance().getMyApi().viewSuggestion("English", "low_oxygen");
                        lowOxygenENGCall.enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                                try
                                {
                                    JSONObject jsonObject = new JSONObject(response.body().toString());
                                    if (!jsonObject.getBoolean("error"))
                                    {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(GraphActivity.this);
                                        builder.setMessage(jsonObject.getString("message"))
                                                .setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                        dialog.dismiss();
                                                    }
                                                });
                                        AlertDialog alertDialog = builder.create();
                                        alertDialog.show();
                                    }
                                }
                                catch (JSONException e)
                                {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {

                                Toast.makeText(GraphActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                }
            });
        }
        if (nitrogen > 1.25)
        {
            textViewNitrogen.setText("Nitrogen level is high");
            textViewNitrogenValue.setVisibility(View.VISIBLE);
            textViewNitrogenValue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (language.equals("Malayalam"))
                    {
                        Call<JsonObject> highNitrogenMLCall = RetrofitClient.getInstance().getMyApi().viewSuggestion(language, "high_nitrogen");
                        highNitrogenMLCall.enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                                try
                                {
                                    JSONObject jsonObject = new JSONObject(response.body().toString());
                                    if (!jsonObject.getBoolean("error"))
                                    {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(GraphActivity.this);
                                        builder.setMessage(jsonObject.getString("message"))
                                                .setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                        dialog.dismiss();
                                                    }
                                                });
                                        AlertDialog alertDialog = builder.create();
                                        alertDialog.show();
                                    }
                                }
                                catch (JSONException e)
                                {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {

                                Toast.makeText(GraphActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                    else
                    {
                        Call<JsonObject> highNitrogenENGCall = RetrofitClient.getInstance().getMyApi().viewSuggestion("English", "high_nitrogen");
                        highNitrogenENGCall.enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                                try
                                {
                                    JSONObject jsonObject = new JSONObject(response.body().toString());
                                    if (!jsonObject.getBoolean("error"))
                                    {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(GraphActivity.this);
                                        builder.setMessage(jsonObject.getString("message"))
                                                .setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                        dialog.dismiss();
                                                    }
                                                });
                                        AlertDialog alertDialog = builder.create();
                                        alertDialog.show();
                                    }
                                }
                                catch (JSONException e)
                                {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {

                                Toast.makeText(GraphActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                }
            });
        }
        if (nitrogen < 0.5)
        {
            textViewNitrogen.setText("Nitrogen level is low");
            textViewNitrogenValue.setVisibility(View.VISIBLE);
            textViewNitrogenValue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (language.equals("Malayalam"))
                    {
                        Call<JsonObject> lowNitrogenMLCall = RetrofitClient.getInstance().getMyApi().viewSuggestion(language, "low_nitrogen");
                        lowNitrogenMLCall.enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                                try
                                {
                                    JSONObject jsonObject = new JSONObject(response.body().toString());
                                    if (!jsonObject.getBoolean("error"))
                                    {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(GraphActivity.this);
                                        builder.setMessage(jsonObject.getString("message"))
                                                .setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                        dialog.dismiss();
                                                    }
                                                });
                                        AlertDialog alertDialog = builder.create();
                                        alertDialog.show();
                                    }
                                }
                                catch (JSONException e)
                                {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {

                                Toast.makeText(GraphActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                    else
                    {
                        Call<JsonObject> lowNitrogenENGCall = RetrofitClient.getInstance().getMyApi().viewSuggestion("English", "low_nitrogen");
                        lowNitrogenENGCall.enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                                try
                                {
                                    JSONObject jsonObject = new JSONObject(response.body().toString());
                                    if (!jsonObject.getBoolean("error"))
                                    {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(GraphActivity.this);
                                        builder.setMessage(jsonObject.getString("message"))
                                                .setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                        dialog.dismiss();
                                                    }
                                                });
                                        AlertDialog alertDialog = builder.create();
                                        alertDialog.show();
                                    }
                                }
                                catch (JSONException e)
                                {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {

                                Toast.makeText(GraphActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                }
            });
        }
        if (nitrate > 1.0)
        {
            textViewNitrate.setText("Nitrate level is high");
            textViewNitrateValue.setVisibility(View.VISIBLE);
            textViewNitrateValue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (language.equals("Malayalam"))
                    {
                        Call<JsonObject> highNitrateMLCall = RetrofitClient.getInstance().getMyApi().viewSuggestion(language, "high_nitrate");
                        highNitrateMLCall.enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                                try
                                {
                                    JSONObject jsonObject = new JSONObject(response.body().toString());
                                    if (!jsonObject.getBoolean("error"))
                                    {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(GraphActivity.this);
                                        builder.setMessage(jsonObject.getString("message"))
                                                .setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                        dialog.dismiss();
                                                    }
                                                });
                                        AlertDialog alertDialog = builder.create();
                                        alertDialog.show();
                                    }
                                }
                                catch (JSONException e)
                                {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {

                                Toast.makeText(GraphActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                    else
                    {
                        Call<JsonObject> highNitrateENGCall = RetrofitClient.getInstance().getMyApi().viewSuggestion("English", "high_nitrate");
                        highNitrateENGCall.enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                                try
                                {
                                    JSONObject jsonObject = new JSONObject(response.body().toString());
                                    if (!jsonObject.getBoolean("error"))
                                    {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(GraphActivity.this);
                                        builder.setMessage(jsonObject.getString("message"))
                                                .setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                        dialog.dismiss();
                                                    }
                                                });
                                        AlertDialog alertDialog = builder.create();
                                        alertDialog.show();
                                    }
                                }
                                catch (JSONException e)
                                {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {

                                Toast.makeText(GraphActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                }
            });
        }
        if (nitrate < 0.5)
        {
            textViewNitrate.setText("Nitrate level is low");
            textViewNitrateValue.setVisibility(View.VISIBLE);
            textViewNitrateValue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (language.equals("Malayalam"))
                    {
                        Call<JsonObject> lowNitrateMLCall = RetrofitClient.getInstance().getMyApi().viewSuggestion(language, "low_nitrate");
                        lowNitrateMLCall.enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                                try
                                {
                                    JSONObject jsonObject = new JSONObject(response.body().toString());
                                    if (!jsonObject.getBoolean("error"))
                                    {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(GraphActivity.this);
                                        builder.setMessage(jsonObject.getString("message"))
                                                .setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                        dialog.dismiss();
                                                    }
                                                });
                                        AlertDialog alertDialog = builder.create();
                                        alertDialog.show();
                                    }
                                }
                                catch (JSONException e)
                                {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {

                                Toast.makeText(GraphActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                    else
                    {
                        Call<JsonObject> lowNitrateENGCall = RetrofitClient.getInstance().getMyApi().viewSuggestion("English", "low_nitrate");
                        lowNitrateENGCall.enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                                try
                                {
                                    JSONObject jsonObject = new JSONObject(response.body().toString());
                                    if (!jsonObject.getBoolean("error"))
                                    {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(GraphActivity.this);
                                        builder.setMessage(jsonObject.getString("message"))
                                                .setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                        dialog.dismiss();
                                                    }
                                                });
                                        AlertDialog alertDialog = builder.create();
                                        alertDialog.show();
                                    }
                                }
                                catch (JSONException e)
                                {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {

                                Toast.makeText(GraphActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                }
            });
        }
        if (nitrite > 1.0)
        {
            textViewNitrite.setText("Nitrite level is high");
            textViewNitriteValue.setVisibility(View.VISIBLE);
            textViewNitriteValue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (language.equals("Malayalam"))
                    {
                        Call<JsonObject> highNitriteMLCall = RetrofitClient.getInstance().getMyApi().viewSuggestion(language, "high_nitrite");
                        highNitriteMLCall.enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                                try
                                {
                                    JSONObject jsonObject = new JSONObject(response.body().toString());
                                    if (!jsonObject.getBoolean("error"))
                                    {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(GraphActivity.this);
                                        builder.setMessage(jsonObject.getString("message"))
                                                .setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                        dialog.dismiss();
                                                    }
                                                });
                                        AlertDialog alertDialog = builder.create();
                                        alertDialog.show();
                                    }
                                }
                                catch (JSONException e)
                                {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {

                                Toast.makeText(GraphActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                    else
                    {
                        Call<JsonObject> highNitriteENGCall = RetrofitClient.getInstance().getMyApi().viewSuggestion("English", "high_nitrite");
                        highNitriteENGCall.enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                                try
                                {
                                    JSONObject jsonObject = new JSONObject(response.body().toString());
                                    if (!jsonObject.getBoolean("error"))
                                    {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(GraphActivity.this);
                                        builder.setMessage(jsonObject.getString("message"))
                                                .setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                        dialog.dismiss();
                                                    }
                                                });
                                        AlertDialog alertDialog = builder.create();
                                        alertDialog.show();
                                    }
                                }
                                catch (JSONException e)
                                {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {

                                Toast.makeText(GraphActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                }
            });
        }
        if (nitrite < 0.5)
        {
            textViewNitrite.setText("Nitrite level is low");
            textViewNitriteValue.setVisibility(View.VISIBLE);
            textViewNitriteValue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (language.equals("Malayalam"))
                    {
                        Call<JsonObject> lowNitriteMLCall = RetrofitClient.getInstance().getMyApi().viewSuggestion(language, "low_nitrite");
                        lowNitriteMLCall.enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                                try
                                {
                                    JSONObject jsonObject = new JSONObject(response.body().toString());
                                    if (!jsonObject.getBoolean("error"))
                                    {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(GraphActivity.this);
                                        builder.setMessage(jsonObject.getString("message"))
                                                .setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                        dialog.dismiss();
                                                    }
                                                });
                                        AlertDialog alertDialog = builder.create();
                                        alertDialog.show();
                                    }
                                }
                                catch (JSONException e)
                                {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {

                                Toast.makeText(GraphActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                    else
                    {
                        Call<JsonObject> lowNitriteENGCall = RetrofitClient.getInstance().getMyApi().viewSuggestion("English", "low_nitrite");
                        lowNitriteENGCall.enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                                try
                                {
                                    JSONObject jsonObject = new JSONObject(response.body().toString());
                                    if (!jsonObject.getBoolean("error"))
                                    {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(GraphActivity.this);
                                        builder.setMessage(jsonObject.getString("message"))
                                                .setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                        dialog.dismiss();
                                                    }
                                                });
                                        AlertDialog alertDialog = builder.create();
                                        alertDialog.show();
                                    }
                                }
                                catch (JSONException e)
                                {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {

                                Toast.makeText(GraphActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                }
            });
        }
        if (mortal > (fishCount / 2))
        {
            textViewMortal.setText("Mortality rate is high");
            textViewMortalValue.setVisibility(View.VISIBLE);
            textViewMortalValue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (language.equals("Malayalam"))
                    {
                        Call<JsonObject> highMortalityMLCall = RetrofitClient.getInstance().getMyApi().viewSuggestion(language, "high_mortality");
                        highMortalityMLCall.enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                                try
                                {
                                    JSONObject jsonObject = new JSONObject(response.body().toString());
                                    if (!jsonObject.getBoolean("error"))
                                    {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(GraphActivity.this);
                                        builder.setMessage(jsonObject.getString("message"))
                                                .setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                        dialog.dismiss();
                                                    }
                                                });
                                        AlertDialog alertDialog = builder.create();
                                        alertDialog.show();
                                    }
                                }
                                catch (JSONException e)
                                {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {

                                Toast.makeText(GraphActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                    else
                    {
                        Call<JsonObject> highMortalityENGCall = RetrofitClient.getInstance().getMyApi().viewSuggestion("English", "high_mortality");
                        highMortalityENGCall.enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                                try
                                {
                                    JSONObject jsonObject = new JSONObject(response.body().toString());
                                    if (!jsonObject.getBoolean("error"))
                                    {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(GraphActivity.this);
                                        builder.setMessage(jsonObject.getString("message"))
                                                .setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                        dialog.dismiss();
                                                    }
                                                });
                                        AlertDialog alertDialog = builder.create();
                                        alertDialog.show();
                                    }
                                }
                                catch (JSONException e)
                                {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {

                                Toast.makeText(GraphActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                }
            });
        }
    }
}