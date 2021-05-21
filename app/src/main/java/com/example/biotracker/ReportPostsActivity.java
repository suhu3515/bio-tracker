package com.example.biotracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportPostsActivity extends AppCompatActivity {

    int postid;
    Button buttonReport;
    EditText reportReason;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_posts);

        postid = getIntent().getExtras().getInt("postId");
        buttonReport = findViewById(R.id.reportPost_button_report);
        reportReason = findViewById(R.id.reportPost_reason);
        user = SharedPrefManager.getInstance(this).getUser();

        buttonReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (reportReason.getText().toString().isEmpty())
                {
                    reportReason.setError("Please enter a reason to report post");
                    reportReason.requestFocus();
                }
                else
                {
                    Call<JsonObject> reportPostCall = RetrofitClient.getInstance().getMyApi().reportPosts(String.valueOf(user.getId()),String.valueOf(postid),reportReason.getText().toString());
                    reportPostCall.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                            if (response.isSuccessful())
                            {
                                try
                                {
                                    JSONObject object = new JSONObject(response.body().toString());
                                    if (!object.getBoolean("error"))
                                    {
                                        Toast.makeText(ReportPostsActivity.this, object.getString("message"), Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                    else
                                    {
                                        Toast.makeText(ReportPostsActivity.this, object.getString("message"), Toast.LENGTH_SHORT).show();
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

                            Toast.makeText(ReportPostsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}