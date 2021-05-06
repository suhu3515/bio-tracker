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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private Context mCtx;

    private List<Products> productsList;

    public ProductAdapter(Context mCtx, List<Products> productsList) {
        this.mCtx = mCtx;
        this.productsList = productsList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_products,null);
        return new ProductViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

        Products products = productsList.get(position);

        holder.textViewTitle.setText(products.getProductName());
        holder.textViewDesc.setText(products.getProductDesc());
        holder.textViewPrice.setText(String.format("INR %s", products.getProductPrice()));
        String img_location = "http://192.168.0.105/biotracker/seller/pages/" + products.getProductImage();
        try {
            URL url = new URL(img_location);
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            holder.imageViewProduct.setImageBitmap(bmp);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        holder.cardViewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context context = mCtx;

                Intent detailsIntent = new Intent(context, ProductDetailsActivity.class);
                detailsIntent.putExtra("prod_name", productsList.get(holder.getLayoutPosition()).getProductName());
                detailsIntent.putExtra("prod_id", productsList.get(holder.getLayoutPosition()).getProductId());
                detailsIntent.putExtra("prod_price", productsList.get(holder.getLayoutPosition()).getProductPrice());
                detailsIntent.putExtra("prod_qty", productsList.get(holder.getLayoutPosition()).getProductQty());
                detailsIntent.putExtra("prod_desc", productsList.get(holder.getLayoutPosition()).getProductDesc());
                detailsIntent.putExtra("prod_img", productsList.get(holder.getLayoutPosition()).getProductImage());
                detailsIntent.putExtra("seller_name", productsList.get(holder.getLayoutPosition()).getSellerName());
                detailsIntent.putExtra("seller_id", productsList.get(holder.getLayoutPosition()).getSellerId());

                context.startActivity(detailsIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewDesc, textViewPrice;
        ImageView imageViewProduct;
        CardView cardViewProduct;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDesc = itemView.findViewById(R.id.textViewShortDesc);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            imageViewProduct = itemView.findViewById(R.id.imageViewProduct);
            cardViewProduct = itemView.findViewById(R.id.cardview_products);
        }
    }

}
