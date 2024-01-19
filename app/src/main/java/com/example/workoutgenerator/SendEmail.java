package com.example.workoutgenerator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import android.os.Bundle;

public class SendEmail extends AppCompatActivity {

    EditText emailEt, subjectEt, messageEt;
    Button sendBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_email);
        emailEt = findViewById(R.id.emailEt);
        subjectEt = findViewById(R.id.subjectEt);
        messageEt = findViewById(R.id.EmailMessageEt);
        sendBtn = findViewById(R.id.btnSend);

        Intent intent = getIntent();

        emailEt.setText("barbuss1234@gmail.com");

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEt.getText().toString().trim();
                String[] emailArr = {email};
                String subject = subjectEt.getText().toString().trim();
                String message = messageEt.getText().toString().trim();

                if (isValidEmail(email) == true && !subject.isEmpty() && !message.isEmpty())
                    sendEmail(emailArr, subject, message);
                else
                    Toast.makeText(SendEmail.this, "אחד מהשדות ריקים", Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void sendEmail(String[] emailArr, String subject, String message) {
        Intent sendEmail = new Intent(Intent.ACTION_SEND);
        sendEmail.setType("message/rfc822");
        sendEmail.putExtra(Intent.EXTRA_EMAIL, emailArr);
        sendEmail.putExtra(Intent.EXTRA_SUBJECT, subject);
        sendEmail.putExtra(Intent.EXTRA_TEXT, message);
        startActivity(Intent.createChooser(sendEmail, null));
    }

    public boolean isValidEmail(String email) {
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches())
            return true;
        else
            return false;
    }

}