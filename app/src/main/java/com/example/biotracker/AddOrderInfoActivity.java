package com.example.biotracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class AddOrderInfoActivity extends AppCompatActivity {

    RadioGroup radioGroupPayments;
    RadioButton radioButtonUpi, radioButtonCod;
    NumberPicker numberPickerQty;
    TextView textViewTotal;
    Button buttonReview;
    User user;
    String price,qty,payment;
    int selQty, productId, total, priceInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order_info);

        radioGroupPayments = findViewById(R.id.payments);
        radioButtonUpi = findViewById(R.id.radio_upi);
        radioButtonCod = findViewById(R.id.radio_cod);
        numberPickerQty = findViewById(R.id.info_qty_num);
        buttonReview = findViewById(R.id.button_review_order);

        qty = getIntent().getExtras().getString("prod_qty");
        price = getIntent().getExtras().getString("prod_price");
        productId = getIntent().getExtras().getInt("prod_id");

        numberPickerQty.setMinValue(1);
        numberPickerQty.setMaxValue(Integer.parseInt(qty));

        priceInt = Integer.parseInt(price);

        buttonReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (radioGroupPayments.getCheckedRadioButtonId() == R.id.radio_upi)
                {
                    payment = "UPI";
                }

                if (radioGroupPayments.getCheckedRadioButtonId() == R.id.radio_cod)
                {
                    payment = "COD";
                }
                selQty = numberPickerQty.getValue();
                total = selQty * priceInt;

                Intent confirmIntent = new Intent(AddOrderInfoActivity.this,ConfirmOrderActivity.class);
                confirmIntent.putExtra("pr_id", productId);
                confirmIntent.putExtra("order_qty", selQty);
                confirmIntent.putExtra("order_total", total);
                confirmIntent.putExtra("pay_mode", payment);
                startActivity(confirmIntent);
                finish();
            }
        });

    }
}