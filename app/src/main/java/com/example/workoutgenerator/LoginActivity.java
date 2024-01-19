package com.example.workoutgenerator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnLogin;
    EditText etUsername, etPassword;
    TextView tvRegister;
    DatabaseHelper db;

    private SharedPreferences sPref;
    private static final String SAVED_NAME = "saved_name", SAVED_PASS = "saved_phone";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DatabaseHelper(this);
        btnLogin = findViewById(R.id.btnLogin);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        tvRegister = findViewById(R.id.tvRegister);

        btnLogin.setOnClickListener(this);
        tvRegister.setOnClickListener(this);


    }

    public void clearLoginAndPass() {
        etUsername.setText("");
        etPassword.setText("");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvRegister:
                Intent registerIntent = new Intent(this,RegisterActivity.class);
                startActivity(registerIntent);
                finish();
                break;
            case R.id.btnLogin:
                String user = etUsername.getText().toString().trim();
                String pwd = etPassword.getText().toString().trim();
                boolean res = db.checkUser(user, pwd);
                if(res==true){
                    Toast.makeText(this,"You logged in successfully!", Toast.LENGTH_LONG).show();
                    Intent moveToApp = new Intent(this,HomeScreenActivity.class);
                    startActivity(moveToApp);
                }
                else
                    Toast.makeText(this,"Login Error.", Toast.LENGTH_LONG).show();
                break;
        }
    }

    private void saveText()
    {
        sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor edit = sPref.edit();
        edit.putString(SAVED_NAME, etUsername.getText().toString());
        edit.putString(SAVED_PASS, etPassword.getText().toString());
        edit.commit();
    }

    private void loadText() {
        sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        String savedName = sPref.getString(SAVED_NAME, "");
        String savedPass = sPref.getString(SAVED_PASS, "");
        etUsername.setText(savedName);
        etPassword.setText(savedPass);
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