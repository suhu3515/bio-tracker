package com.example.biotracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ProductDetailsActivity extends AppCompatActivity {

    ImageView imageView;
    TextView textViewTitle, textViewPrice, textViewDescription, textViewSeller, textViewQty;
    Button buttonOrder;
    int sellerId, productId;
    String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        imageView = findViewById(R.id.details_image_view);
        textViewTitle = findViewById(R.id.details_text_view_title);
        textViewPrice = findViewById(R.id.details_text_view_price);
        textViewDescription = findViewById(R.id.details_text_view_desc_text);
        textViewSeller = findViewById(R.id.details_text_view_seller);
        textViewQty = findViewById(R.id.details_text_view_qty);
        buttonOrder = findViewById(R.id.details_button_add_order);

        textViewTitle.setText(getIntent().getExtras().getString("prod_name"));
        textViewPrice.setText(String.format("INR %S",getIntent().getExtras().getString("prod_price")));
        textViewDescription.setText(getIntent().getExtras().getString("prod_desc"));
        textViewSeller.setText(String.format("Seller : %s",getIntent().getExtras().getString("seller_name")));
        textViewQty.setText(String.format("Available Quantity : %s", getIntent().getExtras().getString("prod_qty")));

        sellerId = getIntent().getExtras().getInt("seller_id");
        productId = getIntent().getExtras().getInt("prod_id");
        location = String.format("http://"+URLs.ipAddress+"/biotracker/seller/pages/%s",getIntent().getExtras().getString("prod_img"));

        textViewSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent sellerIntent = new Intent(ProductDetailsActivity.this, SellerDetailsActivity.class);
                sellerIntent.putExtra("seller_id", sellerId);
                startActivity(sellerIntent);

            }
        });

        buttonOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent orderIntent = new Intent(ProductDetailsActivity.this, AddOrderInfoActivity.class);
                orderIntent.putExtra("prod_qty", getIntent().getExtras().getString("prod_qty"));
                orderIntent.putExtra("prod_price", getIntent().getExtras().getString("prod_price"));
                orderIntent.putExtra("prod_id", productId);
                startActivity(orderIntent);
            }
        });

        try
        {
            URL url = new URL(location);
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            imageView.setImageBitmap(bmp);
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}