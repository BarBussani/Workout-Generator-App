package com.example.workoutgenerator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

public class Splash extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final ProgressBar simpleProgressBar = (ProgressBar) findViewById(R.id.simpleProgressBar);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent t = new Intent(Splash.this, LoginActivity.class);
                startActivity(t);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}