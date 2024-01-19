package com.example.workoutgenerator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class UserGuideActivity extends AppCompatActivity implements View.OnClickListener {

    Button[] btns = new Button[9];
    TextView[] tvs = new TextView[9];
    int[] filesID = new int[9];
    ImageView[] ivs = new ImageView[9];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_guide);

        defineElements();
    }

    private void defineElements() {
        btns[0] = (Button) findViewById(R.id.b1);
        btns[1] = (Button) findViewById(R.id.b2);
        btns[2] = (Button) findViewById(R.id.b3);
        btns[3] = (Button) findViewById(R.id.b4);
        btns[4] = (Button) findViewById(R.id.b5);
        btns[5] = (Button) findViewById(R.id.b6);
        btns[6] = (Button) findViewById(R.id.b7);
        btns[7] = (Button) findViewById(R.id.b8);
        btns[8] = (Button) findViewById(R.id.b9);

        for (int i = 0; i < btns.length; i++)
            btns[i].setOnClickListener(this);

        tvs[0] = (TextView) findViewById(R.id.tv1);
        tvs[1] = (TextView) findViewById(R.id.tv2);
        tvs[2] = (TextView) findViewById(R.id.tv3);
        tvs[3] = (TextView) findViewById(R.id.tv4);
        tvs[4] = (TextView) findViewById(R.id.tv5);
        tvs[5] = (TextView) findViewById(R.id.tv6);
        tvs[6] = (TextView) findViewById(R.id.tv7);
        tvs[7] = (TextView) findViewById(R.id.tv8);
        tvs[8] = (TextView) findViewById(R.id.tv9);


        for (int i = 0; i < tvs.length; i++) {
            tvs[i].setVisibility(View.GONE);
        }

        filesID[0] = R.raw.info1;
        filesID[1] = R.raw.info2;
        filesID[2] = R.raw.info3;
        filesID[3] = R.raw.info4;
        filesID[4] = R.raw.info5;
        filesID[5] = R.raw.info6;
        filesID[6] = R.raw.info7;
        filesID[7] = R.raw.info8;
        filesID[8] = R.raw.info9;

        ivs[0] = (ImageView) findViewById(R.id.iv1);
        ivs[1] = (ImageView) findViewById(R.id.iv2);
        ivs[2] = (ImageView) findViewById(R.id.iv3);
        ivs[3] = (ImageView) findViewById(R.id.iv4);
        ivs[4] = (ImageView) findViewById(R.id.iv5);
        ivs[5] = (ImageView) findViewById(R.id.iv6);
        ivs[6] = (ImageView) findViewById(R.id.iv7);
        ivs[7] = (ImageView) findViewById(R.id.iv8);
        ivs[8] = (ImageView) findViewById(R.id.iv9);

        for(int k = 0; k<ivs.length;k++)
            ivs[k].setVisibility(View.GONE);

    }


    private void viewText(int i) {
        for (int j = 0; j < tvs.length; j++)
            tvs[j].setVisibility(View.GONE);
        for(int t = 0; t<ivs.length; t++)
            ivs[t].setVisibility(View.GONE);
        tvs[i].setVisibility(View.VISIBLE);
        ivs[i].setVisibility(View.VISIBLE);
        InputStream is = getResources().openRawResource(filesID[i]);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String temp = "", all = "";
        try {
            while ((temp = br.readLine()) != null) {
                all += temp + "\n \n";
            }
            is.close();
            tvs[i].setText(all);
            ivs[i].setVisibility(View.VISIBLE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btns[0]) {
            if (tvs[0].getVisibility() == View.VISIBLE){
                tvs[0].setVisibility(View.GONE);
                ivs[0].setVisibility(View.GONE);
            }
            else
                viewText(0);
        }
        if (v == btns[1]) {
            if (tvs[1].getVisibility() == View.VISIBLE){
                tvs[1].setVisibility(View.GONE);
                ivs[1].setVisibility(View.GONE);
            }
            else
                viewText(1);
        }
        if (v == btns[2]) {
            if (tvs[2].getVisibility() == View.VISIBLE){
                tvs[2].setVisibility(View.GONE);
                ivs[2].setVisibility(View.GONE);
            }
            else
                viewText(2);
        }
        if (v == btns[3]) {
            if (tvs[3].getVisibility() == View.VISIBLE){
                tvs[3].setVisibility(View.GONE);
                ivs[3].setVisibility(View.GONE);}
            else
                viewText(3);
        }
        if (v == btns[4]) {
            if (tvs[4].getVisibility() == View.VISIBLE){
                tvs[4].setVisibility(View.GONE);
                ivs[4].setVisibility(View.GONE);}
            else
                viewText(4);
        }
        if (v == btns[5]) {
            if (tvs[5].getVisibility() == View.VISIBLE){
                tvs[5].setVisibility(View.GONE);
                ivs[5].setVisibility(View.GONE);}
            else
                viewText(5);
        }
        if (v == btns[6]) {
            if (tvs[6].getVisibility() == View.VISIBLE){
                tvs[6].setVisibility(View.GONE);
                ivs[6].setVisibility(View.GONE);}
            else
                viewText(6);
        }
        if (v == btns[7]) {
            if (tvs[7].getVisibility() == View.VISIBLE){
                tvs[7].setVisibility(View.GONE);
                ivs[7].setVisibility(View.GONE);}
            else
                viewText(7);
        }
        if (v == btns[8]) {
            if (tvs[8].getVisibility() == View.VISIBLE){
                tvs[8].setVisibility(View.GONE);
                ivs[8].setVisibility(View.GONE);}
            else
                viewText(8);
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
                return true;
            case R.id.item2:
                Intent aboutIntent = new Intent(this, AboutMeActivity.class);
                startActivity(aboutIntent);
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
            case R.id.item666:
                Intent goToHomeScreen = new Intent(this, HomeScreenActivity.class);
                startActivity(goToHomeScreen);
                return true;
            case R.id.item7:
                Intent SMSIntent = new Intent(this,SendSms.class);
                startActivity(SMSIntent);
                return true;
            case R.id.item8:
                Intent EmailIntent = new Intent(this,SendEmail.class);
                startActivity(EmailIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }}
