package com.example.workoutgenerator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;


public class ExerciseView extends AppCompatActivity {

    TextView tvExerciseName, tvExerciseMuscleGroup, tvExerciseDescription1;
    ImageView iv;
    Intent i;
    String name, description, muscleGroup;
    int imageLoc;
    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_view);

        tvExerciseName = findViewById(R.id.tvExerciseName);
        iv = findViewById(R.id.iv);
        tvExerciseMuscleGroup = findViewById(R.id.tvExerciseMuscleGroup);
        tvExerciseDescription1= findViewById(R.id.tvExerciseDescription1);
        videoView = findViewById(R.id.video_view);

        i = getIntent();
        name = i.getStringExtra("name");
        imageLoc = i.getIntExtra("imageLoc", R.drawable.ic_launcher_foreground);
        description = i.getStringExtra("description");
        muscleGroup = i.getStringExtra("muscleGroup");

        String[] arrName = getResources().getStringArray(R.array.exercises_names);
        String[] arrDescription = getResources().getStringArray(R.array.exercise_description);
        String[] arrMuscleGroup = getResources().getStringArray(R.array.muscleGroupForEachOne);
        int [] arrGif = {R.raw.sit_ups_video,R.raw.plank_video, R.raw.pull_ups_video,
                R.raw.jumping_jacks_video, R.raw.leg_lifts_video, R.raw.climbing_stairs_video,
                R.raw.squats_video, R.raw.running_in_place_video, R.raw.push_ups_video,
                R.raw.weight_lifting_arm_video, R.raw.switching_legs_video, R.raw.kneeling_to_chest_video,
                R.raw.opposite_arm_and_leg_lift_video, R.raw.jumping_rope_video, R.raw.inverted_butterfly_video,
                R.raw.front_shoulder_video, R.raw.middle_shoulder_video, R.raw.back_shoulder_video, R.raw.diamond_push_ups_video,
                R.raw.weight_lifting_chest_video, R.raw.wide_pull_ups_video};

        // setting the exercise view...
        tvExerciseName.setText(name);
        iv.setImageResource(imageLoc);
        tvExerciseDescription1.setText(description);
        tvExerciseMuscleGroup.setText(muscleGroup);

        String videoPath ="android.resource://" + getPackageName() + "/" +R.raw.back_shoulder_video;
        for(int j = 0; j<arrName.length; j++){
            if(name.equals(arrName[j])){
                videoPath = "android.resource://" + getPackageName() + "/" + arrGif[j];
            }
        }

        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);
        videoView.requestFocus();
        videoView.start();


        videoView.setOnPreparedListener( new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setVolume(0f, 0f);
            }
        });

        videoView.setOnCompletionListener ( new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                videoView.start();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        String[] arrName = getResources().getStringArray(R.array.exercises_names);
        int [] arrGif = {R.raw.sit_ups_video,R.raw.plank_video, R.raw.pull_ups_video,
                R.raw.jumping_jacks_video, R.raw.leg_lifts_video, R.raw.climbing_stairs_video,
                R.raw.squats_video, R.raw.running_in_place_video, R.raw.push_ups_video,
                R.raw.weight_lifting_arm_video, R.raw.switching_legs_video, R.raw.kneeling_to_chest_video,
                R.raw.opposite_arm_and_leg_lift_video, R.raw.jumping_rope_video, R.raw.inverted_butterfly_video,
                R.raw.front_shoulder_video, R.raw.middle_shoulder_video, R.raw.back_shoulder_video, R.raw.diamond_push_ups_video,
                R.raw.weight_lifting_chest_video, R.raw.wide_pull_ups_video};

        videoView = findViewById(R.id.video_view);
        String videoPath ="android.resource://" + getPackageName() + "/" +R.raw.back_shoulder_video;
        for(int j = 0; j<arrName.length; j++){
            if(name.equals(arrName[j])){
                videoPath = "android.resource://" + getPackageName() + "/" + arrGif[j];
            }
        }

        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);
        videoView.requestFocus();
        videoView.start();
    }
}