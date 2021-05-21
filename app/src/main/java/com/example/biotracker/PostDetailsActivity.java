package com.example.biotracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostDetailsActivity extends AppCompatActivity {

    TextView userName, postDate, postCaption, postStatistics;
    ImageView postContent;
    ImageButton likeButton, reportButton, dislikeButton;
    Button buttonComment;
    int postId;
    User user;
    RecyclerView recyclerView;
    List<Comments> commentsList;
    EditText editTextComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        userName = findViewById(R.id.details_textViewUserName);
        postDate = findViewById(R.id.details_textViewPostDate);
        postCaption = findViewById(R.id.details_textViewPostCaption);
        postContent = findViewById(R.id.details_imageViewPostContent);
        likeButton = findViewById(R.id.details_imageButton_like);
        dislikeButton = findViewById(R.id.details_imageButton_dislike);
        reportButton = findViewById(R.id.details_imageButton_report);
        postStatistics = findViewById(R.id.details_textViewPostStatistics);
        recyclerView = findViewById(R.id.recycler_comments);
        buttonComment = findViewById(R.id.details_buttonComment);
        editTextComment = findViewById(R.id.details_editTextComment);

        postId = getIntent().getExtras().getInt("post_id");
        user = SharedPrefManager.getInstance(this).getUser();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        commentsList = new ArrayList<>();

        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent reportIntent = new Intent(PostDetailsActivity.this, ReportPostsActivity.class);
                reportIntent.putExtra("postId", postId);
                startActivity(reportIntent);
                finish();
            }
        });

        buttonComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Call<JsonObject> addCommentCall = RetrofitClient.getInstance().getMyApi().addComment(String.valueOf(user.getId()),String.valueOf(postId),editTextComment.getText().toString());
                addCommentCall.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                        if (response.isSuccessful())
                        {
                            try
                            {
                                JSONObject object = new JSONObject(response.body().toString());
                                if (!object.getBoolean("error"))
                                {
                                    Toast.makeText(PostDetailsActivity.this, object.getString("message"), Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                                else
                                {
                                    Toast.makeText(PostDetailsActivity.this, object.getString("message"), Toast.LENGTH_SHORT).show();
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

                        Toast.makeText(PostDetailsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        Call<JsonArray> getCommentsCall = RetrofitClient.getInstance().getMyApi().getComments(String.valueOf(postId));
        getCommentsCall.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {

                if (response.isSuccessful())
                {
                    try
                    {
                        JSONArray array = new JSONArray(response.body().toString());
                        for(int i=0;i<array.length();i++)
                        {
                            JSONObject object = array.getJSONObject(i);
                            commentsList.add(new Comments(Integer.parseInt(object.getString("comment_id")),
                                    object.getString("commented_user"),
                                    object.getString("comment_text")));
                        }
                    }
                    catch (JSONException e)
                    {

                    }
                }

            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {

            }
        });

        CommentsAdapter adapter = new CommentsAdapter(this,commentsList);
        recyclerView.setAdapter(adapter);

        Call<JsonObject> isPostLikedCall = RetrofitClient.getInstance().getMyApi().isPostLiked(String.valueOf(user.getId()),String.valueOf(postId));
        isPostLikedCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if (response.isSuccessful())
                {
                    try
                    {
                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        if (!jsonObject.getBoolean("error"))
                        {
                            if (jsonObject.getBoolean("is_liked"))
                            {
                                likeButton.setEnabled(false);
                                dislikeButton.setEnabled(true);
                            }
                            else
                            {
                                likeButton.setEnabled(true);
                                dislikeButton.setEnabled(false);
                            }
                        }
                        else
                        {
                            Toast.makeText(PostDetailsActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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

                Toast.makeText(PostDetailsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        userName.setText(getIntent().getExtras().getString("user_name"));
        postDate.setText(String.format("posted on %s", getIntent().getExtras().getString("post_date")));
        postCaption.setText(getIntent().getExtras().getString("post_caption"));
        postStatistics.setText(String.format("%s Likes  •  %s Comments", getIntent().getExtras().getString("post_likes"),getIntent().getExtras().getString("post_comments")));
        if (getIntent().getExtras().getString("post_image")=="null")
        {
            postContent.setVisibility(View.GONE);
        }
        else
        {
            String img_location = "http://"+ Constants.ipAddress+"/biotracker/" + getIntent().getExtras().getString("post_image");
            try {
                URL url = new URL(img_location);
                Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                postContent.setImageBitmap(bmp);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Call<JsonObject> likePostCall = RetrofitClient.getInstance().getMyApi().likePost(String.valueOf(user.getId()),String.valueOf(postId));
                likePostCall.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                        if (response.isSuccessful())
                        {
                            try
                            {
                                JSONObject object = new JSONObject(response.body().toString());
                                Toast.makeText(PostDetailsActivity.this, object.getString("message"), Toast.LENGTH_SHORT).show();
                                likeButton.setEnabled(false);
                                dislikeButton.setEnabled(true);
                                postStatistics.setText(String.format("%s Likes  •  %s Comments", String.valueOf(Integer.parseInt(getIntent().getExtras().getString("post_likes")) + 1),getIntent().getExtras().getString("post_comments")));
                            }
                            catch (JSONException e)
                            {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                        Toast.makeText(PostDetailsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        dislikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Call<JsonObject> dislikePostCall = RetrofitClient.getInstance().getMyApi().dislikePost(String.valueOf(user.getId()),String.valueOf(postId));
                dislikePostCall.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                        if (response.isSuccessful())
                        {
                            try
                            {
                                JSONObject obj = new JSONObject(response.body().toString());
                                Toast.makeText(PostDetailsActivity.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
                                dislikeButton.setEnabled(false);
                                likeButton.setEnabled(true);
                                postStatistics.setText(String.format("%s Likes  •  %s Comments", String.valueOf(Integer.parseInt(getIntent().getExtras().getString("post_likes")) - 1),getIntent().getExtras().getString("post_comments")));
                            }
                            catch (JSONException e)
                            {
                                e.printStackTrace();
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                        Toast.makeText(PostDetailsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}