package com.example.biotracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewOrdersActivity extends AppCompatActivity {

    List<Orders> ordersList;
    ProgressBar progressBar;
    User user;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_orders);

        ordersList = new ArrayList<>();
        progressBar = findViewById(R.id.progress_orders);
        user = SharedPrefManager.getInstance(this).getUser();
        recyclerView = findViewById(R.id.recycler_orders);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Call<JsonArray> getOrdersCall = RetrofitClient.getInstance().getMyApi().getOrders(String.valueOf(user.getId()));
        getOrdersCall.enqueue(new Callback<JsonArray>() {
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
                            JSONObject object = array.getJSONObject(i);

                            ordersList.add(new Orders(Integer.parseInt(object.getString("order_id")),
                                    object.getString("product_name"),
                                    object.getString("product_img"),
                                    object.getString("order_qty"),
                                    object.getString("order_amount"),
                                    object.getString("payment_mode"),
                                    object.getString("order_address"),
                                    object.getString("pay_status"),
                                    object.getString("order_status"),
                                    object.getString("order_date"),
                                    object.getString("estimated_date")));
                        }

                        OrderAdapter adapter = new OrderAdapter(ViewOrdersActivity.this,ordersList);
                        recyclerView.setAdapter(adapter);
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {

                Toast.makeText(ViewOrdersActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}