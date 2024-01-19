package com.example.workoutgenerator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ChooseExercises extends AppCompatActivity {

    private static final String TAG = "ChooseExercises";
    Spinner spinner1, spinner2;
    int setCount = 1, cycleCount = 1, length;
    TextView tvSetNum, tvCycleNum, tvCurrentChoosing, tvBuild;
    Button btnGoToBuild;
    int numOfCycles, numOfSets;
    ArrayList<ListOfExercises.Exercise> items = new ArrayList<>();
    String[] descriptions;
    ArrayAdapter<ListOfExercises.Exercise> adapter;
    ArrayList<String> choices= new ArrayList<>();
    String workoutStr = "", exercisesStr = "\n";

    private SharedPreferences sPref;
    private static final String L1= "l1", L2="l2", L3= "l3", L4="l4", L5= "l5", L6="l6", L7= "l7", L8="l8", L9= "l9", L10="l10",
            L11= "l11", L12="l12", L13= "l13", L14="l14", L15= "l15", L16="l16", L17= "l17", L18="l18", L19= "l19", L20="l20", L21="l21";
    ArrayList<ListOfExercises.Exercise> allExercisesUpdated = new ArrayList<>() , favoriteExercises = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_exercises);




        spinner1 = findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);
        btnGoToBuild = findViewById(R.id.btnGoToBuild);
        descriptions = getResources().getStringArray(R.array.exercise_description);
        tvSetNum = findViewById(R.id.tvSetNum);
        tvCurrentChoosing = findViewById(R.id.tvCurrentChoosing);
        tvCycleNum = findViewById(R.id.tvCycleNum);
        tvBuild = findViewById(R.id.tvBuild);

        tvSetNum.setText("סט 1");
        tvCycleNum.setText("מחזור 1");
        tvCurrentChoosing.setText(workoutStr);

        Intent intent = getIntent();
        String s1 = intent.getStringExtra("cycles");
        numOfCycles = Integer.parseInt(s1);
        String s2 = intent.getStringExtra("sets");
        numOfSets = Integer.parseInt(s2);
        length = numOfCycles * numOfSets;

        //setting tvBuild...
        for(int i = 1; i<=numOfSets; i++){
            workoutStr+= "סט " +i+ ": \n";
            for(int j = 1; j<=numOfCycles; j++){
                workoutStr+= "מחזור " +j+": \n";
            }
        }
        tvBuild.setText(workoutStr);

        //setting spinner1...
        items = getExercises();
        adapter = new ExerciseSpinnerAdapter(this, R.layout.exercise_item, items, this);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);

        btnGoToBuild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(choices.size() == length){
                    Intent goToBuildWorkout = new Intent(ChooseExercises.this, BuildWorkout.class);
                    goToBuildWorkout.putExtra("choicesArray", choices);
                    startActivity(goToBuildWorkout);
                }
                else
                    Toast.makeText(ChooseExercises.this, "עלייך לבחור מספיק תרגילים בשביל כל האימון.", Toast.LENGTH_LONG).show();

            }
        });

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i >= 0 && i < items.size())
                {
                    if(setCount<= numOfSets){
                        ListOfExercises.Exercise item=items.get(i);
                        if((!item.getName().equals("בחר תרגיל מהתרגילים האהובים")) && (! item.getName().equals("בחר תרגיל מכל התרגילים"))){
                            choices.add(item.getName());
                            exercisesStr+= item.getName() +"\n";
                            tvCurrentChoosing.setText(exercisesStr);
                            if(cycleCount == numOfCycles){
                                setCount++;
                                cycleCount = 1;
                                exercisesStr+="\n";
                            } else
                                cycleCount++;
                            if(setCount<= numOfSets){
                                tvSetNum.setText("סט "+ setCount);
                                tvCycleNum.setText("מחזור " + cycleCount);
                            }
                        }
                    }
                }
                else
                    Toast.makeText(ChooseExercises.this, "התרגיל לא קיים.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        allExercisesUpdated = getExercises();
        allExercisesUpdated.remove(0);
        allExercisesUpdated.get(0).setLiked(sPref.getBoolean(L1, false));
        allExercisesUpdated.get(1).setLiked(sPref.getBoolean(L2, false));
        allExercisesUpdated.get(2).setLiked(sPref.getBoolean(L3, false));
        allExercisesUpdated.get(3).setLiked(sPref.getBoolean(L4, false));
        allExercisesUpdated.get(4).setLiked(sPref.getBoolean(L5, false));
        allExercisesUpdated.get(5).setLiked(sPref.getBoolean(L6, false));
        allExercisesUpdated.get(6).setLiked(sPref.getBoolean(L7, false));
        allExercisesUpdated.get(7).setLiked(sPref.getBoolean(L8, false));
        allExercisesUpdated.get(8).setLiked(sPref.getBoolean(L9, false));
        allExercisesUpdated.get(9).setLiked(sPref.getBoolean(L10, false));

        allExercisesUpdated.get(10).setLiked(sPref.getBoolean(L11, false));
        allExercisesUpdated.get(11).setLiked(sPref.getBoolean(L12, false));
        allExercisesUpdated.get(12).setLiked(sPref.getBoolean(L13, false));
        allExercisesUpdated.get(13).setLiked(sPref.getBoolean(L14, false));
        allExercisesUpdated.get(14).setLiked(sPref.getBoolean(L15, false));
        allExercisesUpdated.get(15).setLiked(sPref.getBoolean(L16, false));
        allExercisesUpdated.get(16).setLiked(sPref.getBoolean(L17, false));
        allExercisesUpdated.get(17).setLiked(sPref.getBoolean(L18, false));
        allExercisesUpdated.get(18).setLiked(sPref.getBoolean(L19, false));
        allExercisesUpdated.get(19).setLiked(sPref.getBoolean(L20, false));
        allExercisesUpdated.get(20).setLiked(sPref.getBoolean(L21, false));

        for(int i = 0; i<allExercisesUpdated.size(); i++)
            if(allExercisesUpdated.get(i).isLiked())
                favoriteExercises.add(allExercisesUpdated.get(i));


        if(favoriteExercises!=null){
            String str="";
            for(int i = 0; i<favoriteExercises.size(); i++)
                str+= favoriteExercises.get(i).getName() +"\n";
        }

        //setting spinner2...
        final ArrayList<ListOfExercises.Exercise> favoriteExercises1 = new ArrayList<>();
        favoriteExercises1.add(new ListOfExercises.Exercise("בחר תרגיל מהתרגילים האהובים", "", 1, R.drawable.ic_launcher_foreground, false));
        for(int i = 0; i<favoriteExercises.size(); i++)
            favoriteExercises1.add(favoriteExercises.get(i));
        adapter = new ExerciseSpinnerAdapter(this, R.layout.exercise_item, favoriteExercises1, this);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter);

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i >= 0 && i < items.size())
                {
                    if(setCount<= numOfSets){
                        ListOfExercises.Exercise item=favoriteExercises1.get(i);
                        if( (!item.getName().equals("בחר תרגיל מהתרגילים האהובים")) && (! item.getName().equals("בחר תרגיל מכל התרגילים")) ){
                            choices.add(item.getName());
                            exercisesStr+= item.getName() +"\n";
                            tvCurrentChoosing.setText(exercisesStr);
                            if(cycleCount == numOfCycles){
                                setCount++;
                                cycleCount = 1;
                                exercisesStr+="\n";
                            } else
                                cycleCount++;
                            if(setCount<= numOfSets){
                                tvSetNum.setText("סט "+ setCount);
                                tvCycleNum.setText("מחזור " + cycleCount);
                            }
                        }
                    }
                }
                else
                    Toast.makeText(ChooseExercises.this, "התרגיל לא קיים.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });



    }

    private void getItemCategoryData(int i) {

    }

    ArrayList<ListOfExercises.Exercise> getExercises() {
        ArrayList<ListOfExercises.Exercise> data = new ArrayList<>();
        data.clear();

        data.add(new ListOfExercises.Exercise("בחר תרגיל מכל התרגילים", "", 1, R.drawable.ic_launcher_foreground, false));
        data.add(new ListOfExercises.Exercise("כפיפות בטן - בטן", descriptions[0], 1, R.drawable.sit_ups, false));
        data.add(new ListOfExercises.Exercise("פלאנק - גב", descriptions[1], 2, R.drawable.plank, false));
        data.add(new ListOfExercises.Exercise("עליות מתח - כתפיים", descriptions[2], 3, R.drawable.pull_ups, false));
        data.add(new ListOfExercises.Exercise("קפיצות כוכב - רגליים", descriptions[3], 4, R.drawable.jumping_jacks,false));
        data.add(new ListOfExercises.Exercise("הרמות רגליים - רגליים", descriptions[4], 4, R.drawable.leg_lifts,false));
        data.add(new ListOfExercises.Exercise("טיפוס מדרגות - רגליים", descriptions[5], 4, R.drawable.climbing_stairs,false));
        data.add(new ListOfExercises.Exercise("סקוואטים - רגליים", descriptions[6], 4, R.drawable.squats,false));
        data.add(new ListOfExercises.Exercise("ריצה במקום - רגליים", descriptions[7], 4, R.drawable.runnung_in_place,false));
        data.add(new ListOfExercises.Exercise("שכיבות שמיכה - ידיים", descriptions[8], 5, R.drawable.push_ups,false));
        data.add(new ListOfExercises.Exercise("הרמת משקולות יד -ידיים", descriptions[9], 5, R.drawable.weight_lifting,false));

        data.add(new ListOfExercises.Exercise("החלפות רגליים - בטן", descriptions[10], 1, R.drawable.switching_legs, false));
        data.add(new ListOfExercises.Exercise("כפיפות ברכיים אל החזה - בטן", descriptions[11], 1, R.drawable.kneeling_to_chest, false));
        data.add(new ListOfExercises.Exercise("הרמת רגל ויד נגדית - גב", descriptions[12], 2, R.drawable.opposite_arm_and_leg_lift, false));
        data.add(new ListOfExercises.Exercise("קפיצות בחבל - רגליים", descriptions[13], 4, R.drawable.jumping_with_rope,false));
        data.add(new ListOfExercises.Exercise("פרפר הפוך כנגד גומייה - כתפיים", descriptions[14], 3, R.drawable.butterfly_backwords,false));
        data.add(new ListOfExercises.Exercise("כתף קידמית - כתפיים", descriptions[15], 3, R.drawable.front_sholder,false));
        data.add(new ListOfExercises.Exercise("כתף אמצעית - כתפיים", descriptions[16], 3, R.drawable.middle_sholder,false));
        data.add(new ListOfExercises.Exercise("כתף אחורית - כתפיים", descriptions[17], 3, R.drawable.back_sholder,false));
        data.add(new ListOfExercises.Exercise("שכיבות שמיכה יהלום - חזה", descriptions[18], 6, R.drawable.push_ups,false));
        data.add(new ListOfExercises.Exercise("הרמת משקולות יד - חזה", descriptions[19], 6, R.drawable.weight_lifting_chest,false));
        data.add(new ListOfExercises.Exercise("עליות מתח רחבות - חזה", descriptions[20], 6, R.drawable.pull_ups, false));

        return data;
    }
}