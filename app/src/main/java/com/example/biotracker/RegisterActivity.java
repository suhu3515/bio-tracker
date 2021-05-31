package com.example.biotracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {

    EditText editTextName, editTextDob, editTextEmail, editTextMob, editTextPass, editTextCPass;
    Button buttonRegister;
    TextView textViewLogin;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String newDate;
    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextName = findViewById(R.id.register_et_name);
        editTextDob = findViewById(R.id.register_et_dob);
        editTextEmail = findViewById(R.id.register_et_mail);
        editTextMob = findViewById(R.id.register_et_mob);
        editTextPass = findViewById(R.id.register_et_pass);
        editTextCPass = findViewById(R.id.register_et_cpass);
        buttonRegister = findViewById(R.id.register_btn_register);
        textViewLogin = findViewById(R.id.register_tv_login);

        SharedPreferences sharedPreferences = this.getSharedPreferences("language_settings", Context.MODE_PRIVATE);
        String language = sharedPreferences.getString("selected_language",null);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                Calendar minAge = new GregorianCalendar();
                minAge.add(Calendar.YEAR, -18);
                if (minAge.before(myCalendar))
                {
                    if (language.equals("Malayalam"))
                    {
                        Toast.makeText(RegisterActivity.this, "ഉപയോക്താവിന് കുറഞ്ഞത് 18 വയസ്സ് ആയിരിക്കണം", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(RegisterActivity.this, "User must be atleast 18 years", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    String myFormat = "dd-MM-yyyy";
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                    editTextDob.setText(sdf.format(myCalendar.getTime()));
                    SimpleDateFormat ndf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                    newDate = ndf.format(myCalendar.getTime());
                }
            }
        };


        if (language.equals("Malayalam"))
        {
            editTextName.setHint(R.string.full_name_ml);
            editTextDob.setHint(R.string.date_of_birth_ml);
            editTextEmail.setHint(R.string.email_address_ml);
            editTextMob.setHint(R.string.mobile_number_ml);
            editTextPass.setHint(R.string.password_ml);
            editTextCPass.setHint(R.string.confirm_password_ml);
            buttonRegister.setText(R.string.register_ml);
            textViewLogin.setText(R.string.back_to_login_ml);
        }

        editTextDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(RegisterActivity.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });


        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editTextName.getText().toString().isEmpty())
                {
                    if (language.equals("Malayalam"))
                    {
                        editTextName.setError("ദയവായി മുഴുവൻ പേര് ചേർക്കുക");
                        editTextName.requestFocus();
                    }
                    else
                    {
                        editTextName.setError("Please enter full name");
                        editTextName.requestFocus();
                    }
                }
                else if (editTextName.getText().length()<3)
                {
                    if (language.equals("Malayalam"))
                    {
                        editTextName.setError("പേര് 3 അക്ഷരത്തിൽ കൂടുതൽ വേണം");
                        editTextName.requestFocus();
                    }
                    else
                    {
                        editTextName.setError("Name must have minimum 3 letters");
                        editTextName.requestFocus();
                    }
                }
                else if (editTextDob.getText().toString().isEmpty())
                {
                    if (language.equals("Malayalam"))
                    {
                        editTextDob.setError("ദയവായി ജനന തിയ്യതി ചേർക്കുക");
                        editTextDob.getText().clear();
                        editTextDob.requestFocus();
                    }
                    else
                    {
                        editTextDob.setError("Please enter date of birth");
                        editTextDob.getText().clear();
                        editTextDob.requestFocus();
                    }
                }
                else if (editTextEmail.getText().toString().isEmpty())
                {
                    if (language.equals("Malayalam"))
                    {
                        editTextEmail.setError("ദയവായി ഇമെയിൽ വിലാസം ചേർക്കുക");
                        editTextEmail.requestFocus();
                    }
                    else
                    {
                        editTextEmail.setError("Please enter email address");
                        editTextEmail.requestFocus();
                    }
                }
                else if (!(editTextEmail.getText().toString().matches(emailPattern)))
                {
                    if (language.equals("Malayalam"))
                    {
                        editTextEmail.setError("ഇമെയിൽ വിലാസം ശരിക്ക് ചേർക്കുക");
                        editTextEmail.requestFocus();
                    }
                    else
                    {
                        editTextEmail.setError("Email doesn't match the format.");
                        editTextEmail.requestFocus();
                    }
                }

                else if (editTextMob.getText().toString().isEmpty())
                {
                    if (language.equals("Malayalam"))
                    {
                        editTextMob.setError("ദയവായി മൊബൈൽ നമ്പർ ചേർക്കുക");
                        editTextMob.requestFocus();
                    }
                    else
                    {
                        editTextMob.setError("Please enter mobile number");
                        editTextMob.requestFocus();
                    }
                }
                else if (editTextMob.getText().length()<10)
                {
                    if (language.equals("Malayalam"))
                    {
                        editTextMob.setError("മൊബൈൽ നമ്പർ മിനിമം 10 അക്കങ്ങൾ വേണം");
                        editTextMob.requestFocus();
                    }
                    else
                    {
                        editTextMob.setError("Mobile number should have 10 digits");
                        editTextMob.requestFocus();
                    }
                }
                else if (editTextPass.getText().toString().isEmpty())
                {
                    if (language.equals("Malayalam"))
                    {
                        editTextPass.setError("ദയവായി പാസ്സ്\u200Cവേർഡ് ചേർക്കുക");
                        editTextPass.requestFocus();
                    }
                    else
                    {
                        editTextPass.setError("Please enter password");
                        editTextPass.requestFocus();
                    }
                }
                else if (editTextPass.getText().length() <= 4)
                {
                    if (language.equals("Malayalam"))
                    {
                        editTextPass.setError("പാസ്സ്\u200Cവേർഡ് മിനിമം 5 അക്ഷരം വേണം");
                        editTextPass.requestFocus();
                    }
                    else
                    {
                        editTextPass.setError("Password should be minimum 5 characters");
                        editTextPass.requestFocus();
                    }
                }
                else if (editTextPass.getText().toString().isEmpty())
                {
                    if (language.equals("Malayalam"))
                    {
                        editTextPass.setError("ദയവായി പാസ്സ്\u200Cവേർഡ് വീണ്ടും ചേർക്കുക");
                        editTextPass.requestFocus();
                    }
                    else
                    {
                        editTextPass.setError("Please enter password again");
                        editTextPass.requestFocus();
                    }
                }
                else if (editTextCPass.getText().toString().isEmpty())
                {
                    if (language.equals("Malayalam"))
                    {
                        editTextCPass.setError("ദയവായി പാസ്സ്\u200Cവേർഡ് വീണ്ടും ചേർക്കുക");
                        editTextCPass.requestFocus();
                    }
                    else
                    {
                        editTextCPass.setError("Please enter password again");
                        editTextCPass.requestFocus();
                    }
                }
                else if (!(editTextCPass.getText().toString().equals(editTextPass.getText().toString())))
                {
                    if (language.equals("Malayalam"))
                    {
                        editTextCPass.setError("പാസ്സ്\u200Cവേർഡുകൾ സമാനമായിരിക്കണം");
                        editTextCPass.requestFocus();
                    }
                    else
                    {
                        editTextCPass.setError("Passwords must be same");
                        editTextCPass.requestFocus();
                    }
                }
                else
                {
                    Intent addressIntent = new Intent(RegisterActivity.this,AddAddressActivity.class);
                    addressIntent.putExtra("fullName", editTextName.getText().toString().trim());
                    addressIntent.putExtra("dob", newDate);
                    addressIntent.putExtra("mailid", editTextEmail.getText().toString().trim());
                    addressIntent.putExtra("mobileno", editTextMob.getText().toString().trim());
                    addressIntent.putExtra("password", editTextPass.getText().toString().trim());
                    startActivity(addressIntent);
                    finish();
                }
            }
        });

        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                finish();

            }
        });
    }


}