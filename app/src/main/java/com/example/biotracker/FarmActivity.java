package com.example.biotracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

public class FarmActivity extends AppCompatActivity {

    FloatingActionButton floatingActionButtonAddFarm;
    RecyclerView recyclerView;
    List<Farm> farmList;
    User user;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farm);

        floatingActionButtonAddFarm = findViewById(R.id.fab_add_farm);
        progressBar = findViewById(R.id.progress_farm);
        floatingActionButtonAddFarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent farmAddIntent = new Intent(FarmActivity.this, AddFarmActivity.class);
                startActivity(farmAddIntent);
                finish();

            }
        });


        user = SharedPrefManager.getInstance(this).getUser();

        recyclerView = findViewById(R.id.recycler_farm);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        farmList = new ArrayList<>();

        Call<JsonArray> viewFarmCall = RetrofitClient.getInstance().getMyApi().viewFarm(String.valueOf(user.getId()));

        viewFarmCall.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {

                if (response.isSuccessful())
                {
                    progressBar.setVisibility(View.GONE);
                    try
                    {
                        JSONArray array = new JSONArray(response.body().toString());
                        for (int i=0;i<array.length();i++)
                        {
                            JSONObject farmObject = array.getJSONObject(i);

                            farmList.add(new Farm(
                                    Integer.parseInt(farmObject.getString("farm_id")),
                                    farmObject.getString("fish_type"),
                                    farmObject.getString("fish_count"),
                                    farmObject.getString("tank_volume"),
                                    farmObject.getString("start_date"),
                                    farmObject.getString("est_time")));
                        }

                        FarmAdapterActivity adapterActivity = new FarmAdapterActivity(FarmActivity.this,farmList);
                        recyclerView.setAdapter(adapterActivity);
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {

                Toast.makeText(FarmActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e( "onFailure: ", t.getMessage() );

            }
        });



    }
}