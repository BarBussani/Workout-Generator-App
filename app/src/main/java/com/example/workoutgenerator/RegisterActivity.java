package com.example.workoutgenerator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etName, etUsernameR, etPasswordR, etEmail;
    Button btnRegister;
    TextView tvLogin, tvNote1, tvNote2;
    DatabaseHelper db;
    //CheckBox cb1, cb2, cb3, cb4, cb5, cb6;

    private SharedPreferences sPref;
    private static final String SAVED_FULL_NAME = "saved_full_name", SAVED_USER_NAME = "saved_user_name",
            SAVED_PASSWORD = "saved_password", SAVED_EMAIL = "saved_email",
            CB1 = "cb1",  CB2 = "cb2",  CB3 = "cb3",  CB4 = "cb4", CB5 = "cb5",  CB6 = "cb6";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DatabaseHelper(this);
        btnRegister = findViewById(R.id.btnRegister);
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etUsernameR = findViewById(R.id.etUsernameR);
        etPasswordR = findViewById(R.id.etPasswordR);
        tvLogin = findViewById(R.id.tvLogin);
        tvNote1 = findViewById(R.id.tvNote1);
        tvNote2 = findViewById(R.id.tvNote2);
        tvNote1.setVisibility(View.GONE);
        tvNote2.setVisibility(View.GONE);

        etUsernameR.setOnClickListener(this);
        etPasswordR.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        tvLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvLogin:
                Intent loginIntent = new Intent(this,LoginActivity.class);
                startActivity(loginIntent);
                break;
            case R.id.btnRegister:
                String name = etName.getText().toString().trim();
                String user = etUsernameR.getText().toString().trim();
                String pwd = etPasswordR.getText().toString().trim();
                String email = etEmail.getText().toString().trim();

                if(isUsernameValid(user) && pwd.length()>=6 && isEmailValid(email)){
                    long val = db.addUser(name, user, pwd);
                    if(val>0){
                        Toast.makeText(this,"You have registered.", Toast.LENGTH_LONG).show();
                        Intent moveToLogin = new Intent(this,LoginActivity.class);
                        startActivity(moveToLogin);
                        finish();
                    }
                    else
                        Toast.makeText(this,"the system was unable to add your details.", Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(this,"Username, password or Email is not valid.", Toast.LENGTH_LONG).show();
                break;
            case R.id.etUsernameR:
                if (tvNote1.getVisibility()==View.GONE){
                    tvNote1.setVisibility(View.VISIBLE);
                    tvNote1.postDelayed(new Runnable() {
                        public void run() {
                            tvNote1.setVisibility(View.GONE);
                        }
                    }, 4000);
                }
                break;
            case R.id.etPasswordR:
                if (tvNote2.getVisibility()==View.GONE){
                    tvNote2.setVisibility(View.VISIBLE);
                    tvNote2.postDelayed(new Runnable() {
                        public void run() {
                            tvNote2.setVisibility(View.GONE);
                        }
                    }, 4000);
                }
                break;
        }
    }

    public static boolean isUsernameValid(String s) {
        if (s == null)
            return false;

        if(s.length()<6)
            return false;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!(c >= 'A' && c <= 'Z') && !(c >= 'a' && c <= 'z')) {
                return false;
            }
        }
        return true;
    }

    public static boolean isEmailValid(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    private void saveText()
    {
        sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor edit = sPref.edit();
        edit.putString(SAVED_FULL_NAME, etName.getText().toString());
        edit.putString(SAVED_USER_NAME, etUsernameR.getText().toString());
        edit.putString(SAVED_PASSWORD, etPasswordR.getText().toString());
        edit.putString(SAVED_EMAIL, etEmail.getText().toString());
        edit.commit();
    }

    private void loadText() {
        sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        String savedName = sPref.getString(SAVED_FULL_NAME, "");
        String savedUserName = sPref.getString(SAVED_USER_NAME, "");
        String savedPassword = sPref.getString(SAVED_PASSWORD, "");
        String savedEmail = sPref.getString(SAVED_EMAIL, "");

        boolean c1 = sPref.getBoolean(CB1,false);
        boolean c2 = sPref.getBoolean(CB2,false);
        boolean c3 = sPref.getBoolean(CB3,false);
        boolean c4 = sPref.getBoolean(CB4,false);
        boolean c5 = sPref.getBoolean(CB5,false);
        boolean c6 = sPref.getBoolean(CB6,false);

        etName.setText(savedName);
        etUsernameR.setText(savedUserName);
        etPasswordR.setText(savedPassword);
        etEmail.setText(savedEmail);
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveText();
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadText();
    }


}