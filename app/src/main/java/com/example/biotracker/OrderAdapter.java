package com.example.biotracker;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrdersViewHolder> {

    private Context mCtx;

    private List<Orders> ordersList;

    public OrderAdapter(Context mCtx, List<Orders> ordersList) {
        this.mCtx = mCtx;
        this.ordersList = ordersList;
    }

    @NonNull
    @Override
    public OrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_orders,null);

        return new OrdersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersViewHolder holder, int position) {

        Orders orders = ordersList.get(position);
        
        holder.textViewOrderStatus.setText(orders.getOrder_status());
        holder.textViewProductName.setText(orders.getProduct_name());
        holder.textViewOrderDate.setText(String.format("ordered on %s",orders.getOrder_date()));

        String img_location = "http://" + Constants.ipAddress + "/biotracker/seller/pages/" + orders.getProduct_image();
        try
        {
            URL url = new URL(img_location);
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            holder.imageViewOrderProduct.setImageBitmap(bmp); 
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

    @Override
    public int getItemCount() {
        return ordersList.size();
    }

    class OrdersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        private final Context context;

        TextView textViewProductName, textViewOrderStatus, textViewOrderDate;
        ImageView imageViewOrderProduct;

        public OrdersViewHolder(@NonNull View itemView) {
            super(itemView);

            context = itemView.getContext();
            itemView.setOnClickListener(this);
            textViewProductName = itemView.findViewById(R.id.textViewProductName);
            textViewOrderStatus = itemView.findViewById(R.id.textViewShortStatus);
            textViewOrderDate = itemView.findViewById(R.id.textViewOrderDate);
            imageViewOrderProduct = itemView.findViewById(R.id.imageViewProductOrder);

        }

        @Override
        public void onClick(View v) {

            Intent intent = new Intent();

            for (int i=0;i<getItemCount();i++)
            {
                if (v == itemView)
                {
                    intent = new Intent(context, OrderDetailsActivity.class);
                    intent.putExtra("order_id", ordersList.get(getLayoutPosition()).order_id);
                    intent.putExtra("prod_name", ordersList.get(getLayoutPosition()).product_name);
                    intent.putExtra("prod_img", ordersList.get(getLayoutPosition()).product_image);
                    intent.putExtra("order_qty", ordersList.get(getLayoutPosition()).order_qty);
                    intent.putExtra("order_amount", ordersList.get(getLayoutPosition()).order_amount);
                    intent.putExtra("pay_mode", ordersList.get(getLayoutPosition()).payment_mode);
                    intent.putExtra("order_address", ordersList.get(getLayoutPosition()).order_address);
                    intent.putExtra("pay_status", ordersList.get(getLayoutPosition()).pay_status);
                    intent.putExtra("order_status", ordersList.get(getLayoutPosition()).order_status);
                    intent.putExtra("order_date", ordersList.get(getLayoutPosition()).order_date);
                    intent.putExtra("estimated_time", ordersList.get(getLayoutPosition()).estimated_time);
                    context.startActivity(intent);
                    break;
                }
            }

        }
    }

}
