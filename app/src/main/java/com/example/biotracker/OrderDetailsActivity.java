package com.example.biotracker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailsActivity extends AppCompatActivity {

    TextView textViewProdName, textViewOrderDateQty, textViewAddress, textViewEstDate, textViewOrderAmount;
    ImageView imageViewProdImage, imageViewOrderStatus;
    Button buttonCancelOrder;
    int orderId;
    String img_location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        orderId = getIntent().getExtras().getInt("order_id");

        textViewProdName = findViewById(R.id.textview_order_details_prod);
        textViewOrderDateQty = findViewById(R.id.textview_order_details_prod_qty);
        textViewAddress = findViewById(R.id.textview_order_details_order_address);
        textViewEstDate = findViewById(R.id.textview_order_details_est_date);
        textViewOrderAmount = findViewById(R.id.textview_order_details_payment);
        imageViewProdImage = findViewById(R.id.imageView_order_prod);
        imageViewOrderStatus = findViewById(R.id.image_order_details_status);
        buttonCancelOrder = findViewById(R.id.button_order_details_cancel);

        textViewProdName.setText(getIntent().getExtras().getString("prod_name"));
        textViewOrderDateQty.setText(String.format("Ordered %s pcs on %s",getIntent().getExtras().getString("order_qty"),getIntent().getExtras().getString("order_date")));
        textViewAddress.setText(getIntent().getExtras().getString("order_address"));
        if (getIntent().getExtras().getString("estimated_time").equalsIgnoreCase("null"))
        {
            textViewEstDate.setVisibility(View.GONE);
        }
        else
        {
            textViewEstDate.setText(String.format("Estimated delivery date: %s",getIntent().getExtras().getString("estimated_time")));
        }
        textViewOrderAmount.setText(String.format("Order Amount: INR %s ( %s )",getIntent().getExtras().getString("order_amount"),getIntent().getExtras().getString("pay_mode")));
        img_location = String.format("http://"+URLs.ipAddress+"/biotracker/seller/pages/%s",getIntent().getExtras().getString("prod_img"));
        try
        {
            URL url = new URL(img_location);
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            imageViewProdImage.setImageBitmap(bmp);
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        if (getIntent().getExtras().getString("order_status").equalsIgnoreCase("Confirmed"))
        {
            imageViewOrderStatus.setBackgroundResource(R.drawable.confirmed);
        }
        else if (getIntent().getExtras().getString("order_status").equalsIgnoreCase("Packed"))
        {
            imageViewOrderStatus.setBackgroundResource(R.drawable.packed);
        }
        else if (getIntent().getExtras().getString("order_status").equalsIgnoreCase("Dispatched"))
        {
            imageViewOrderStatus.setBackgroundResource(R.drawable.dispatched);
        }
        else if (getIntent().getExtras().getString("order_status").equalsIgnoreCase("Completed"))
        {
            imageViewOrderStatus.setBackgroundResource(R.drawable.completed);
        }
        if (getIntent().getExtras().getString("order_status").equalsIgnoreCase("Rejected"))
        {
            buttonCancelOrder.setEnabled(false);
        }
        
        buttonCancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(OrderDetailsActivity.this, "test click", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(OrderDetailsActivity.this);
                builder.setMessage("Are you sure to cancel the order?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Call<JsonObject> cancelOrderCall = RetrofitClient.getInstance().getMyApi().cancelOrder(String.valueOf(orderId));
                                cancelOrderCall.enqueue(new Callback<JsonObject>() {
                                    @Override
                                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                                        if (response.isSuccessful())
                                        {
                                            try
                                            {
                                                JSONObject jsonObject = new JSONObject(response.body().toString());
                                                if (!jsonObject.getBoolean("error"))
                                                {
                                                    Toast.makeText(OrderDetailsActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                                    finish();
                                                }
                                                else
                                                {
                                                    Toast.makeText(OrderDetailsActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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

                                        Toast.makeText(OrderDetailsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                                    }
                                });

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.cancel();

                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }
}