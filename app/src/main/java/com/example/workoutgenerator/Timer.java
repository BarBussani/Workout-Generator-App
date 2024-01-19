package com.example.workoutgenerator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Parcel;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Locale;

import static java.lang.Integer.parseInt;

public class Timer extends AppCompatActivity {

    private static final String TAG = "Timer";
    ArrayList<String> choices= new ArrayList<>(), copy = new ArrayList<>();
    private TextView mTextViewCountDown,textViewStatus, text_view_exercise;
    private Button mButtonStartPause;
    private Button mButtonReset;
    private CountDownTimer mCountDownTimer;
    private ImageView imageView;
    private boolean mTimerRunning;
    private long mStartTimeInMillis;
    private long mTimeLeftInMillis;
    private long mEndTime;
    long millisInput;
    int num0fWorkAndRest, numOfSets, pointer = 0;
    String status;
    String[] workoutValues;
    boolean restDone;
    SoundPool soundPool;
    int soundId;
    String[] exercisesArr, images;
    int [] arrImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        arrImages = new int[]{R.drawable.sit_ups,R.drawable.plank, R.drawable.pull_ups,
                R.drawable.jumping_jacks, R.drawable.leg_lifts, R.drawable.climbing_stairs,
                R.drawable.squats, R.drawable.runnung_in_place, R.drawable.push_ups,
                R.drawable.weight_lifting, R.drawable.switching_legs, R.drawable.kneeling_to_chest,
                R.drawable.opposite_arm_and_leg_lift, R.drawable.jumping_with_rope, R.drawable.butterfly_backwords,
                R.drawable.front_sholder, R.drawable.middle_sholder, R.drawable.back_sholder,
                R.drawable.push_ups, R.drawable.weight_lifting_chest, R.drawable.pull_ups};

        mTextViewCountDown = findViewById(R.id.text_view_countdown);
        mButtonStartPause = findViewById(R.id.button_start_pause);
        mButtonReset = findViewById(R.id.button_reset);
        textViewStatus = findViewById(R.id.text_view_status);
        text_view_exercise = findViewById(R.id.text_view_exercise);
        imageView = findViewById(R.id.imageView);

       // images = new String[]{"sit_ups", "plank", "pull_ups","jumping_jacks", "leg_lifts", "climbing_stairs", "squats", "runnung_in_place", "push_ups", "weight_lifting" };

        soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        soundId = soundPool.load(this, R.raw.ding, 1);

        choices = getIntent().getStringArrayListExtra("choices");
        copy = new ArrayList<>(choices);

        final Workout myWorkout = Parcels.unwrap(getIntent().getParcelableExtra("workout"));
        workoutValues = new String[6];
        workoutValues[0] =  myWorkout.getPrepareTime();
        workoutValues[1] = myWorkout.getWorkTime();
        workoutValues[2] = myWorkout.getRestTime();
        workoutValues[3] = myWorkout.getNumOfCycles();
        workoutValues[4] = myWorkout.getNumOfSets();
        workoutValues[5] = myWorkout.getRestBetweenSetsTime();

        status = " ";
        restDone = false;
        num0fWorkAndRest = parseInt(workoutValues[3])*2;
        numOfSets = parseInt(workoutValues[4]);

        millisInput = Long.parseLong(workoutValues[0]) * 1000;
        textViewStatus.setText("התכונן!");
        imageView.setImageResource(R.drawable.prepare);
        setTime(millisInput);


        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });

        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });
    }

   /* private String updateStatus(String s){
        if(s.equals("work")){
            status = s;
            return status;
        }
        if(s.equals("rest")) {
            status = s;
            return status;
        }
        if(s.equals("break between sets")){
            status = s;
            return status;
        }
        return "";
    } */

    private void callRest() {
        textViewStatus.setText("זמן מנוחה!");
        text_view_exercise.setVisibility(View.INVISIBLE);
        imageView.setImageResource(R.drawable.rest);
        soundPool.play(soundId, 1, 1, 0, 0, 1);
        millisInput = Long.parseLong(workoutValues[2]) * 1000;
        setTime(millisInput);
        startTimer();

        final Workout myWorkout = Parcels.unwrap(getIntent().getParcelableExtra("workout"));
        int wait = parseInt(myWorkout.getRestTime());
        int minutes = (int) ((wait * 10 ) % 3600) / 60;
        int seconds = (int) (wait) % 60;
        if (minutes>0){wait = minutes;}
        else {wait = seconds;}
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                restDone  = true;
            }
        }, (wait -1) * 1000 );


    }

    private void callWork() {
        exercisesArr = getResources().getStringArray(R.array.exercises_names);
        int pos = 0;
        textViewStatus.setText("זמן עבודה!");
        if(pointer<copy.size()){
            text_view_exercise.setVisibility(View.VISIBLE);
            text_view_exercise.setText(copy.get(pointer));
            for(int i = 0; i< exercisesArr.length; i++)
                if((copy.get(pointer)).equals(exercisesArr[i])){
                    pos = i;
                }
            pointer++;
        }
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageResource(arrImages[pos]);
        Log.d(TAG, "callWork: ------------------------ " + pos);
        soundPool.play(soundId, 1, 1, 0, 0, 1);

        millisInput = Long.parseLong(workoutValues[1]) * 1000;
        setTime(millisInput);
        startTimer();

    }

    private void setTime(long milliseconds) {
        mStartTimeInMillis = milliseconds;
        resetTimer();
    }

    private void resetTimer() {
        mTimeLeftInMillis = mStartTimeInMillis;
        updateCountDownText();
        updateWatchInterface();
    }

    private void updateCountDownText() {
        int hours = (int) (mTimeLeftInMillis / 1000) / 3600;
        int minutes = (int) ((mTimeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        String timeLeftFormatted;
        if (hours > 0) {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%d:%02d:%02d", hours, minutes, seconds);
        }
        else {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%02d:%02d", minutes, seconds);
        }
        mTextViewCountDown.setText(timeLeftFormatted);
    }

    private void updateWatchInterface() {
        if (mTimerRunning) {
            mButtonReset.setVisibility(View.INVISIBLE);
            mButtonStartPause.setText("עצור");
        }
        else {
            mButtonStartPause.setText("התחל");
            if (mTimeLeftInMillis < 1000) {
                mButtonStartPause.setVisibility(View.INVISIBLE);
            }
            else {
                mButtonStartPause.setVisibility(View.VISIBLE);
            }
            if (mTimeLeftInMillis < mStartTimeInMillis) {
                mButtonReset.setVisibility(View.VISIBLE);
            }
            else {
                mButtonReset.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        updateWatchInterface();
    }

    private void startTimer() {
        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }
            @Override
            public void onFinish() {
                mTimerRunning = false;
                updateWatchInterface();
                if(numOfSets>0){
                    if(num0fWorkAndRest!=0){
                        if(num0fWorkAndRest%2==0)
                        {
                            callWork();
                        }
                        if(num0fWorkAndRest%2==1){

                            restDone = false;
                            callRest();
                        }
                        num0fWorkAndRest--;}
                    if(num0fWorkAndRest==0 && restDone){
                        Log.d(TAG, "onFinish: rest is done!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                        final Workout myWorkout = Parcels.unwrap(getIntent().getParcelableExtra("workout"));
                        numOfSets--;
                        restDone = false;
                        num0fWorkAndRest = parseInt(myWorkout.getNumOfCycles())*2;
                        if(numOfSets!=0)
                            callBreakBetweenSets();
                    }
                }
                if(numOfSets==0){
                    final Workout myWorkout = Parcels.unwrap(getIntent().getParcelableExtra("workout"));
                    int wait = parseInt(myWorkout.getRestBetweenSetsTime());
                    int minutes = (int) ((wait * 10 ) % 3600) / 60;
                    int seconds = (int) (wait *10) % 60;
                    if (minutes>0){wait = minutes;}
                    else {wait = seconds;}
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            restDone  = true;
                        }
                    }, (wait -1) * 1000 );
                    soundPool.play(soundId, 1, 1, 0, 0, 1);
                    textViewStatus.setText("סיימת את האימון בהצלחה" + "!");
                    imageView.setImageResource(R.drawable.finished);
                }

            }
        }.start();
        mTimerRunning = true;
        updateWatchInterface();
    }

    private void callBreakBetweenSets() {
        millisInput = Long.parseLong(workoutValues[5]) * 1000;
        textViewStatus.setText("הפסקה בין סטים!");
        soundPool.play(soundId, 1, 1, 0, 0, 1);
        imageView.setImageResource(R.drawable.break_between_sets);
        setTime(millisInput);
        startTimer();
    }



}