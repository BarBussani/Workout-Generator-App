package com.example.workoutgenerator;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;


public abstract class ExerciseInfo extends AppCompatActivity {

    private static final String SHARED_PREFS = "sharedPrefs";
    private static final String STORE = "store";
    TextView tvExercise, tvDescription, tvMuscleGroup;
    ImageView imageView;
    String[] exercisesArr;
    String[] descriptionsArr;
    String[] muscleGroupsArr;
    int[] imagesArr;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_info);

        tvExercise = findViewById(R.id.tvExercise);
        tvDescription = findViewById(R.id.tvDescription);
        tvMuscleGroup = findViewById(R.id.tvMuscleGroup);
        imageView = findViewById(R.id.imageView);

        Intent intent = getIntent();
        int pos = intent.getIntExtra("position",0);

        exercisesArr = getResources().getStringArray(R.array.exercises_names);
        descriptionsArr = getResources().getStringArray(R.array.exercise_description);
        muscleGroupsArr = getResources().getStringArray(R.array.muscleGroupForEachOne);
        imagesArr = getResources().getIntArray(R.array.images);


        tvExercise.setText(exercisesArr[pos]);
        tvDescription.setText(descriptionsArr[pos]);
        tvMuscleGroup.setText(muscleGroupsArr[pos]);
        imageView.setImageResource(imagesArr[pos]);
    }




}