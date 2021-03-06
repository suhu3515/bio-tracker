package com.example.biotracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
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

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigation;
    public static List<Products> productsList;
    public static List<Posts> postsList;
    public static String fishCount, fishCountTotal, farmCount;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (savedInstanceState == null)
        {
            openFragment(HomeFragment.newInstance("",""));
        }

        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        productsList = new ArrayList<>();
        postsList = new ArrayList<>();

        user = SharedPrefManager.getInstance(this).getUser();

        Call<JsonObject> getHomeDataCall = RetrofitClient.getInstance().getMyApi().getHomeData(String.valueOf(user.getId()));
        getHomeDataCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                try
                {
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    farmCount = jsonObject.getString("farm_count");
                    fishCountTotal = jsonObject.getString("fish_total");
                    int mortal_count = jsonObject.getInt("mortal_count");

                    fishCount = String.valueOf((Integer.parseInt(fishCountTotal) - mortal_count));

                    Log.e( "Details:","farms: " + farmCount + "\n" + "total fish: " + fishCountTotal + "\n" +"remaining : " + fishCount);
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

                Toast.makeText(HomeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        Call<JsonArray> getPostsCall = RetrofitClient.getInstance().getMyApi().getPosts();
        getPostsCall.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {

                if (response.isSuccessful())
                {
                    try
                    {
                        JSONArray array = new JSONArray(response.body().toString());
                        for (int j=0;j<array.length();j++)
                        {
                            JSONObject jsonObject = array.getJSONObject(j);
                            postsList.add(new Posts(Integer.parseInt(jsonObject.getString("post_id")),
                                    jsonObject.getString("post_date"),
                                    jsonObject.getString("post_caption"),
                                    jsonObject.getString("post_image"),
                                    jsonObject.getString("post_likes"),
                                    jsonObject.getString("post_comments"),
                                    jsonObject.getString("user_name")));
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
            }
        });

        Call<JsonArray> getProductsCall = RetrofitClient.getInstance().getMyApi().getProducts();
        getProductsCall.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {

                if (response.isSuccessful())
                {
                    try
                    {
                        JSONArray jsonArray = new JSONArray(response.body().toString());
                        for (int i=0;i<jsonArray.length();i++)
                        {
                            JSONObject object = jsonArray.getJSONObject(i);
                            productsList.add(new Products(Integer.parseInt(object.getString("product_id")),
                                    object.getString("product_name"),
                                    object.getString("product_price"),
                                    object.getString("product_qty"),
                                    object.getString("product_desc"),
                                    object.getString("product_img"),
                                    object.getString("seller_name"),
                                    Integer.parseInt(object.getString("seller_id"))));

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

                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void openFragment(Fragment fragment)
    {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId())
            {
                case R.id.navigation_store:
                    openFragment(StoreFragment.newInstance("",""));
                    return true;

                case R.id.navigation_community:
                    openFragment(CommunityFragment.newInstance("",""));
                    return true;

                case R.id.navigation_home:
                    openFragment(HomeFragment.newInstance("",""));
                    return true;


                case R.id.navigation_more:
                    openFragment(MoreFragment.newInstance("",""));
                    return true;
            }
            return false;
        }
    };
}