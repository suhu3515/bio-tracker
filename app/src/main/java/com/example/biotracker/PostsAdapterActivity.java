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
import java.net.URL;
import java.util.List;

public class PostsAdapterActivity extends RecyclerView.Adapter<PostsAdapterActivity.PostsViewHolder> {

    private Context mCtx;
    private List<Posts> postsList;

    public PostsAdapterActivity(Context mCtx, List<Posts> postsList) {
        this.mCtx = mCtx;
        this.postsList = postsList;
    }

    @NonNull
    @Override
    public PostsAdapterActivity.PostsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_posts,null);
        return new PostsAdapterActivity.PostsViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull PostsAdapterActivity.PostsViewHolder holder, int position) {

        Posts post = postsList.get(position);

        holder.textViewUserName.setText(post.getUser_name());
        holder.textViewpostedDate.setText(String.format("posted on %s",post.getPost_date()));
        holder.textViewPostCaption.setText(post.getPost_caption());
        holder.textViewPostLikesComments.setText(String.format("%s Likes  •  %s Comments",post.getPost_likes(),post.getPost_comments()));
        if (post.getPost_image() == null)
        {
            holder.imageViewPostContent.setVisibility(View.GONE);
        }
        else
        {
            String img_location = "http://"+ Constants.ipAddress+"/biotracker/" + post.getPost_image();
            try {
                URL url = new URL(img_location);
                Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                holder.imageViewPostContent.setImageBitmap(bmp);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /*holder.cardViewProduct.setOnClickListener(new View.OnClickListener() {
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
        });*/

    }

    @Override
    public int getItemCount() {
        return postsList.size();
    }

    class PostsViewHolder extends RecyclerView.ViewHolder {

        TextView textViewUserName, textViewpostedDate, textViewPostCaption, textViewPostLikesComments;
        ImageView imageViewPostContent;
        CardView cardView;

        public PostsViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewUserName = itemView.findViewById(R.id.textViewUserName);
            textViewpostedDate = itemView.findViewById(R.id.textViewPostDate);
            textViewPostCaption = itemView.findViewById(R.id.textViewPostCaption);
            textViewPostLikesComments = itemView.findViewById(R.id.textViewPostLikesAndComments);
            imageViewPostContent = itemView.findViewById(R.id.imageViewPostImage);
            cardView = itemView.findViewById(R.id.cardview_posts);
        }
    }

}
