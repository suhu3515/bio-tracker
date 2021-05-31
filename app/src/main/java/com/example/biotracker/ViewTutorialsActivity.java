package com.example.biotracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.widget.LinearLayout.VERTICAL;

public class ViewTutorialsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Tutorial> tutorialList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tutorials);

        recyclerView = findViewById(R.id.recycler_tutorials);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tutorialList = new ArrayList<>();

        Call<JsonArray> getTutorialsCall = RetrofitClient.getInstance().getMyApi().getTutorials();
        getTutorialsCall.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {

                if (response.isSuccessful())
                {
                    try
                    {
                        JSONArray array = new JSONArray(response.body().toString());
                        for (int i=0;i<array.length();i++)
                        {
                            JSONObject object = array.getJSONObject(i);
                            tutorialList.add(new Tutorial(Integer.parseInt(object.getString("tut_id")),
                                    object.getString("tut_title"),
                                    object.getString("tut_text"),
                                    object.getString("tut_link")));
                        }

                        TutorialAdapterActivity adapterActivity = new TutorialAdapterActivity(ViewTutorialsActivity.this, tutorialList);
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

                Toast.makeText(ViewTutorialsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

}