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
        if (post.getPost_image() == "null")
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

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context context = mCtx;

                Intent detailsIntent = new Intent(context, PostDetailsActivity.class);
                detailsIntent.putExtra("post_id", postsList.get(holder.getLayoutPosition()).getPost_id());
                detailsIntent.putExtra("post_date", postsList.get(holder.getLayoutPosition()).getPost_date());
                detailsIntent.putExtra("post_caption", postsList.get(holder.getLayoutPosition()).getPost_caption());
                detailsIntent.putExtra("post_image", postsList.get(holder.getLayoutPosition()).getPost_image());
                detailsIntent.putExtra("post_likes", postsList.get(holder.getLayoutPosition()).getPost_likes());
                detailsIntent.putExtra("post_comments", postsList.get(holder.getLayoutPosition()).getPost_comments());
                detailsIntent.putExtra("user_name", postsList.get(holder.getLayoutPosition()).getUser_name());

                context.startActivity(detailsIntent);
            }
        });

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
