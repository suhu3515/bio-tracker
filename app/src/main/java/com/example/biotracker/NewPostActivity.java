package com.example.biotracker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.annotation.Nullable;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import android.util.Base64;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewPostActivity extends AppCompatActivity {

    Button buttonCreatePost, buttonSelectImage;
    EditText editTextPostCaption;
    ImageView imageViewPostImage;
    User user;

    private int PICK_IMAGE_REQUEST = 1;
    private static final int STORAGE_PERMISSION_CODE = 123;
    private Bitmap bitmap;
    private Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        buttonCreatePost = findViewById(R.id.newPost_button_create);
        buttonSelectImage = findViewById(R.id.newPost_button_add_img);
        editTextPostCaption = findViewById(R.id.newPost_post_caption);
        imageViewPostImage = findViewById(R.id.newPost_user_image);

        user = SharedPrefManager.getInstance(this).getUser();

        requestStoragePermission();

        buttonSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showFileChooser();

            }
        });

        buttonCreatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editTextPostCaption.getText().toString().isEmpty())
                {
                    editTextPostCaption.setError("Required field");
                }
                else
                {
                    if (imageViewPostImage.getVisibility()==View.VISIBLE)
                    {

                        Call<JsonObject> newPostImgCall = RetrofitClient.getInstance().getMyApi().uploadImage(String.valueOf(user.getId()),editTextPostCaption.getText().toString(),encodeTobase64(bitmap));
                        newPostImgCall.enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                                if (response.isSuccessful())
                                {
                                    Log.e( "onResponse: ", response.body().toString() );
                                    try
                                    {
                                        JSONObject object = new JSONObject(response.body().toString());
                                        if (!object.getBoolean("error"))
                                        {
                                            Toast.makeText(NewPostActivity.this, object.getString("message"), Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                        else
                                        {
                                            Toast.makeText(NewPostActivity.this, object.getString("message"), Toast.LENGTH_SHORT).show();
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

                                Toast.makeText(NewPostActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                Log.e("onFailure: ", t.getMessage());
                            }
                        });
                    }
                    else if (imageViewPostImage.getVisibility()==View.GONE)
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(NewPostActivity.this);
                        builder.setMessage("No image is selected. Post without Image?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        Call<JsonObject> newPostCall = RetrofitClient.getInstance().getMyApi().addPosts(String.valueOf(user.getId()),editTextPostCaption.getText().toString());
                                        newPostCall.enqueue(new Callback<JsonObject>() {
                                            @Override
                                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                                                if (response.isSuccessful())
                                                {
                                                    try
                                                    {
                                                        JSONObject object = new JSONObject(response.body().toString());
                                                        if (!object.getBoolean("error"))
                                                        {
                                                            Toast.makeText(NewPostActivity.this, object.getString("message"), Toast.LENGTH_SHORT).show();
                                                            finish();
                                                        }
                                                        else
                                                        {
                                                            Toast.makeText(NewPostActivity.this, object.getString("message"), Toast.LENGTH_SHORT).show();
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

                                                Toast.makeText(NewPostActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                                Log.e( "onError: ", t.getMessage() );
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
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                }
            }
        });

    }


    public static String encodeTobase64(Bitmap image) {
        Bitmap immagex=image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b,Base64.DEFAULT);

        Log.e("LOOK", imageEncoded);
        return imageEncoded;
    }


    private void showFileChooser() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"),PICK_IMAGE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            filePath = data.getData();
            InputStream inputStream = null;

            try
            {
                inputStream = this.getContentResolver().openInputStream(filePath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                imageViewPostImage.setVisibility(View.VISIBLE);
                imageViewPostImage.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void requestStoragePermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
        {
            return;
        }

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE))
        {

        }

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }
}