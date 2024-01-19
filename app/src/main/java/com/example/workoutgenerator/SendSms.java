package com.example.workoutgenerator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import android.os.Bundle;

public class SendSms extends AppCompatActivity {

    EditText phoneEt,messageEt ;
    Button sendBtn, sendEmailInsteadBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sms);

        phoneEt = findViewById(R.id.phoneEt);
        messageEt = findViewById(R.id.messageEt);
        sendBtn = findViewById(R.id.sendBtn);
        phoneEt.setText("0587243922");


        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permissionCheck();
            }
        });
    }


    public void permissionCheck() {
        int result =  ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
        if (result == PackageManager.PERMISSION_GRANTED )
            sendSMS();
        else
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 0);
    }


    public void sendSMS () {
        String phone = phoneEt.getText().toString().trim();
        String message = messageEt.getText().toString().trim();

        if (phone.length() == 10 && !message.isEmpty() ) {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phone, null, message, null, null);
            Toast.makeText(this, "ההודעה נשלחה", Toast.LENGTH_LONG).show();

        } else
            Toast.makeText(this, "אחד מהשדות לא תקינים", Toast.LENGTH_LONG).show();
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        if (requestCode == 0) {


            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                sendSMS();
            else
                Toast.makeText(this, "ההרשאה נחוצה לשליחת המסרון", Toast.LENGTH_LONG).show();
        }
    }}

