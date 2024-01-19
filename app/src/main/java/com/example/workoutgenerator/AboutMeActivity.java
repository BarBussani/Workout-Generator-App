package com.example.workoutgenerator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class AboutMeActivity extends AppCompatActivity {

    TextView tv2, tv3;
    ImageView ivMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);

        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        ivMe = (ImageView) findViewById(R.id.ivMe);
        ivMe.setImageResource(R.drawable.me);
        shareText();
    }

    private void shareText() {
        InputStream is = getResources().openRawResource(R.raw.info11);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String temp = "", all = "";
        try {
            while ((temp = br.readLine()) != null) {
                all += temp + "\n \n";
            }
            is.close();
            tv2.setText(all);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        InputStream is2 = getResources().openRawResource(R.raw.info10);
        InputStreamReader isr2 = new InputStreamReader(is2);
        BufferedReader br2 = new BufferedReader(isr2);
        temp = ""; all = "";
        try {
            while ((temp = br2.readLine()) != null) {
                all += temp + "\n \n";
            }
            is2.close();
            tv3.setText(all);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                Intent guideIntent = new Intent(this, UserGuideActivity.class);
                startActivity(guideIntent);
                return true;
            case R.id.item2:
                return true;
            case R.id.item3:
                Intent exerciseListIntent = new Intent(this, ListOfExercises.class);
                startActivity(exerciseListIntent);
                return true;
            case R.id.item4:
                Intent buildWorkoutIntent = new Intent(this, BuildWorkout.class);
                startActivity(buildWorkoutIntent);
                return true;
            case R.id.item55:
                Intent WorkoutHistoryIntent = new Intent(this, WorkoutHistory.class);
                startActivity(WorkoutHistoryIntent);
                return true;
            case R.id.item7:
                Intent sendMessageIntent = new Intent(this, SendSms.class);
                startActivity(sendMessageIntent);
                return true;
            case R.id.item8:
                Intent sendEmailIntent = new Intent(this, SendEmail.class);
                startActivity(sendEmailIntent);
                return true;
            case R.id.item666:
                Intent goToHomeScreen = new Intent(this, HomeScreenActivity.class);
                startActivity(goToHomeScreen);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}