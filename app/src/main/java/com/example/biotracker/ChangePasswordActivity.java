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

public class ChangePasswordActivity extends AppCompatActivity {

    EditText editTextPass, editTextNewPass, editTextConfirmPass;
    Button buttonChangePass;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        editTextConfirmPass = findViewById(R.id.change_et_stud_confirm_new_pass);
        editTextNewPass = findViewById(R.id.change_et_stud_new_pass);
        editTextPass = findViewById(R.id.change_et_stud_pass);
        buttonChangePass = findViewById(R.id.change_button_change_password);

        user = SharedPrefManager.getInstance(this).getUser();

        buttonChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editTextPass.getText().toString().isEmpty())
                {
                    editTextPass.setError("Please enter your password");
                    editTextPass.requestFocus();
                }
                else if (editTextNewPass.getText().toString().isEmpty())
                {
                    editTextNewPass.setError("Please enter a new password");
                    editTextNewPass.requestFocus();
                }
                else if (editTextConfirmPass.getText().toString().isEmpty())
                {
                    editTextConfirmPass.setError("Please confirm your new password");
                    editTextConfirmPass.requestFocus();
                }
                else if (!(editTextConfirmPass.getText().toString().equals(editTextNewPass.getText().toString())))
                {
                    editTextConfirmPass.setError("Both passwords must be same");
                    editTextConfirmPass.requestFocus();
                }
                else
                {
                    Call<JsonObject> changePasswordCall = RetrofitClient.getInstance().getMyApi().changePassword(user.getUserMobile(),editTextPass.getText().toString(),editTextConfirmPass.getText().toString());
                    changePasswordCall.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                            if (response.isSuccessful())
                            {
                                try
                                {
                                    JSONObject jsonObject = new JSONObject(response.body().toString());
                                    if (!jsonObject.getBoolean("error"))
                                    {
                                        Toast.makeText(ChangePasswordActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                    else
                                    {
                                        Toast.makeText(ChangePasswordActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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

                            Toast.makeText(ChangePasswordActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }
        });
    }
}