package com.example.workoutgenerator;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

public class ListOfExercises extends AppCompatActivity {

    ListView lvStores;
    String[] storesArr, muscleGroups, descriptions;
    SearchableSpinner spinnerCity;
    ArrayList<Exercise> selected = new ArrayList<>();
    ArrayList<Exercise> currentList = new ArrayList<>();
    ArrayAdapter<Exercise> adapter;
    View exerciseItem;
    int currentCategoryID = 0;
    ArrayList<Exercise> data_array;
    ArrayList<Exercise> updatedExerciseList;
    Exercise s;

    ExerciseListAdapter adp;

    private SharedPreferences sPref;
    private static final String L1= "l1", L2="l2", L3= "l3", L4="l4", L5= "l5", L6="l6", L7= "l7", L8="l8", L9= "l9", L10="l10",
            L11= "l11", L12="l12", L13= "l13", L14="l14", L15= "l15", L16="l16", L17= "l17", L18="l18", L19= "l19", L20="l20", L21="l21";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_exercises);

        lvStores = findViewById(R.id.lvStores);
        storesArr = getResources().getStringArray(R.array.exercises_names);
        spinnerCity=findViewById(R.id.spinnerCity);
        registerForContextMenu(lvStores);

        //setting the spinner
        descriptions = getResources().getStringArray(R.array.exercise_description);
        muscleGroups = getResources().getStringArray(R.array.muscleGroups);
        spinnerCity.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,muscleGroups));
        spinnerCity.setTitle("סנן לפי תרגיל");
        spinnerCity.setPositiveButton("סגור");
        spinnerCity.setSelection(0,false);

        ///setting the array
       // currentList = getExercises();
        loadData();
        selected = getExercises();
        adapter = new ExerciseListAdapter(this, R.layout.exercise_item, getUpdatedExercises(), this);
        adp = new ExerciseListAdapter(this, R.layout.exercise_item, getUpdatedExercises(), this);
        lvStores.setAdapter(adp);

        // when spinner is clicked
        spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                ((TextView) adapterView.getChildAt(0)).setTextColor(Color.BLACK);
                ((TextView) adapterView.getChildAt(0)).setTextSize(16);

                if (i >= 0 && i < selected.size())
                    getSelectedCategoryData(i);
                else
                    Toast.makeText(ListOfExercises.this, "Category not exists", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

       lvStores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) { // when a list item is clicked
                Exercise s = getUpdatedExercises().get(pos);
                String name = s.getName();
                String description = s.getDescription();
                int imageLoc = s.getImageLoc();
                String muscleGroup = "";

                for(int i = 0; i<muscleGroups.length; i++){
                    if( i == s.getCategoryId())
                        muscleGroup = muscleGroups[i];
                }

                Intent intent = new Intent(ListOfExercises.this, ExerciseView.class);
                intent.putExtra("name", name);
                intent.putExtra("description", description);
                intent.putExtra("muscleGroup", muscleGroup);
                intent.putExtra("imageLoc", imageLoc);
                startActivity(intent);
            }
        });
    }

    ArrayList<Exercise> getUpdatedExercises() {
       /* boolean equal = true;
        for(int i = 0; i<getExercises().size(); i++){
            if (!Exercise.isEqual(updatedExerciseList.get(i), getExercises().get(i)))
                equal = false;
        }

        if(equal)
            return getExercises();
        else*/
            return updatedExerciseList;
    }



    ArrayList<Exercise> getExercises() {
        ArrayList<Exercise> data = new ArrayList<>();
        data.clear();

        data.add(new Exercise("כפיפות בטן - בטן", descriptions[0], 1, R.drawable.sit_ups, false));
        data.add(new Exercise("פלאנק - גב", descriptions[1], 2, R.drawable.plank, false));
        data.add(new Exercise("עליות מתח - כתפיים", descriptions[2], 3, R.drawable.pull_ups, false));
        data.add(new Exercise("קפיצות כוכב - רגליים", descriptions[3], 4, R.drawable.jumping_jacks,false));
        data.add(new Exercise("הרמות רגליים - רגליים", descriptions[4], 4, R.drawable.leg_lifts,false));
        data.add(new Exercise("טיפוס מדרגות - רגליים", descriptions[5], 4, R.drawable.climbing_stairs,false));
        data.add(new Exercise("סקוואטים - רגליים", descriptions[6], 4, R.drawable.squats,false));
        data.add(new Exercise("ריצה במקום - רגליים", descriptions[7], 4, R.drawable.runnung_in_place,false));
        data.add(new Exercise("שכיבות שמיכה - ידיים", descriptions[8], 5, R.drawable.push_ups,false));
        data.add(new Exercise("הרמת משקולות יד -ידיים", descriptions[9], 5, R.drawable.weight_lifting,false));

        data.add(new Exercise("החלפות רגליים - בטן", descriptions[10], 1, R.drawable.switching_legs, false));
        data.add(new Exercise("כפיפות ברכיים אל החזה - בטן", descriptions[11], 1, R.drawable.kneeling_to_chest, false));
        data.add(new Exercise("הרמת רגל ויד נגדית - גב", descriptions[12], 2, R.drawable.opposite_arm_and_leg_lift, false));
        data.add(new Exercise("קפיצות בחבל - רגליים", descriptions[13], 4, R.drawable.jumping_with_rope,false));
        data.add(new Exercise("פרפר הפוך כנגד גומייה - כתפיים", descriptions[14], 3, R.drawable.butterfly_backwords,false));
        data.add(new Exercise("כתף קידמית - כתפיים", descriptions[15], 3, R.drawable.front_sholder,false));
        data.add(new Exercise("כתף אמצעית - כתפיים", descriptions[16], 3, R.drawable.middle_sholder,false));
        data.add(new Exercise("כתף אחורית - כתפיים", descriptions[17], 3, R.drawable.back_sholder,false));
        data.add(new Exercise("שכיבות שמיכה יהלום - חזה", descriptions[18], 6, R.drawable.push_ups,false));
        data.add(new Exercise("הרמת משקולות יד - חזה", descriptions[19], 6, R.drawable.weight_lifting_chest,false));
        data.add(new Exercise("עליות מתח רחבות - חזה", descriptions[20], 6, R.drawable.pull_ups, false));

        //updatedExerciseList = data;
        return data;
    }


    private void getSelectedCategoryData(int i) { // updating list according to spinner category...
        data_array = new ArrayList<>();
        currentCategoryID = i;
        if (i == 0) {
            adapter = new ExerciseListAdapter(this, R.layout.exercise_item, getUpdatedExercises(), this);
        } else {
            saveData();
            loadData();
            for (Exercise exercise : getUpdatedExercises()) {
                if (exercise.getCategoryId() == i) {
                    data_array.add(exercise);
                }
                //currentList = data_array;
                adapter = new ExerciseListAdapter(this, R.layout.exercise_item, data_array, this);
            }
        }
        lvStores.setAdapter(adapter);
    }


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
                Intent aboutIntent = new Intent(this, AboutMeActivity.class);
                startActivity(aboutIntent);
                return true;
            case R.id.item3:
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
                return super.onOptionsItemSelected(item); }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.floating_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        if(currentCategoryID == 0)
            s = updatedExerciseList.get(info.position);
        else
            s = data_array.get(info.position);
        switch (item.getItemId()) {

            case R.id.like:
                if(!s.isLiked)
                    s.setLiked(true);
                saveData();
                loadData();
                if(currentCategoryID == 0)
                    adp = new ExerciseListAdapter(this, R.layout.exercise_item, getUpdatedExercises(), this);
                else{
                    adp = new ExerciseListAdapter(this, R.layout.exercise_item, data_array , this);
                    String n = s.getName();
                    for(int i = 0; i<updatedExerciseList.size(); i++)
                        if(updatedExerciseList.get(i).getName() == n)
                            s = updatedExerciseList.get(i);
                    s.setLiked(true);
                    saveData();
                    loadData();
                }
                lvStores.setAdapter(adp);
                return true;

            case R.id.unlike:
                if(s.isLiked)
                    s.setLiked(false);
                saveData();
                loadData();
                if(currentCategoryID == 0)
                    adp = new ExerciseListAdapter(this, R.layout.exercise_item, getUpdatedExercises(), this);
                else{
                    adp = new ExerciseListAdapter(this, R.layout.exercise_item,data_array , this);
                    String n = s.getName();
                    for(int i = 0; i<updatedExerciseList.size(); i++)
                        if(updatedExerciseList.get(i).getName() == n)
                            s = updatedExerciseList.get(i);
                    s.setLiked(false);
                    saveData();
                    loadData();
                }
                lvStores.setAdapter(adp);
                return true;
            default:
                return super.onContextItemSelected(item);
        }}

     static class Exercise {
        private String name;
        private String description;
        private int categoryID;
        private int imageLoc;
        private boolean isLiked;

         public boolean isLiked() {
             return isLiked;
         }

         public void setLiked(boolean liked) {
             isLiked = liked;
         }

         public int getImageLoc() {
             return imageLoc;
         }

        public String getName() {
            return name;
        }

        public int getCategoryId() {
            return categoryID;
        }

         public String getDescription() {
             return description;
         }

         public void setDescription(String description) {
             this.description = description;
         }

         public Exercise(String name, String description, int categoryID, int imageLoc, boolean isLiked) {
            this.name = name;
            this.description = description;
            this.categoryID = categoryID;
            this.imageLoc = imageLoc;
            this.isLiked = isLiked;
        }

         @Override
         public String toString() {
             return "Exercise{" +
                     "name='" + name + '\'' +
                     ", description='" + description + '\'' +
                     ", categoryID=" + categoryID +
                     ", imageLoc=" + imageLoc +
                     ", isLiked=" + isLiked +
                     '}';
         }

         public static boolean isEqual(Exercise e1, Exercise e2){
             if( e1.name == e2.name && e1.description == e2.description && e1.categoryID == e2.categoryID &&
                            e1.imageLoc == e2.imageLoc && e1.isLiked == e2.isLiked)
                 return true;
             else
                 return false;
         }
     }

    private void saveData()
    {
        sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor edit = sPref.edit();
        edit.putBoolean(L1, getUpdatedExercises().get(0).isLiked());
        edit.putBoolean(L2, getUpdatedExercises().get(1).isLiked());
        edit.putBoolean(L3, getUpdatedExercises().get(2).isLiked());
        edit.putBoolean(L4, getUpdatedExercises().get(3).isLiked());
        edit.putBoolean(L5, getUpdatedExercises().get(4).isLiked());
        edit.putBoolean(L6, getUpdatedExercises().get(5).isLiked());
        edit.putBoolean(L7, getUpdatedExercises().get(6).isLiked());
        edit.putBoolean(L8, getUpdatedExercises().get(7).isLiked());
        edit.putBoolean(L9, getUpdatedExercises().get(8).isLiked());
        edit.putBoolean(L10, getUpdatedExercises().get(9).isLiked());

        edit.putBoolean(L11, getUpdatedExercises().get(10).isLiked());
        edit.putBoolean(L12, getUpdatedExercises().get(11).isLiked());
        edit.putBoolean(L13, getUpdatedExercises().get(12).isLiked());
        edit.putBoolean(L14, getUpdatedExercises().get(13).isLiked());
        edit.putBoolean(L15, getUpdatedExercises().get(14).isLiked());
        edit.putBoolean(L16, getUpdatedExercises().get(15).isLiked());
        edit.putBoolean(L17, getUpdatedExercises().get(16).isLiked());
        edit.putBoolean(L18, getUpdatedExercises().get(17).isLiked());
        edit.putBoolean(L19, getUpdatedExercises().get(18).isLiked());
        edit.putBoolean(L20, getUpdatedExercises().get(19).isLiked());
        edit.putBoolean(L21, getUpdatedExercises().get(20).isLiked());

        edit.commit();
    }

    private void loadData() {
        sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        updatedExerciseList = getExercises();
        getUpdatedExercises().get(0).setLiked(sPref.getBoolean(L1, false));
        getUpdatedExercises().get(1).setLiked(sPref.getBoolean(L2, false));
        getUpdatedExercises().get(2).setLiked(sPref.getBoolean(L3, false));
        getUpdatedExercises().get(3).setLiked(sPref.getBoolean(L4, false));
        getUpdatedExercises().get(4).setLiked(sPref.getBoolean(L5, false));
        getUpdatedExercises().get(5).setLiked(sPref.getBoolean(L6, false));
        getUpdatedExercises().get(6).setLiked(sPref.getBoolean(L7, false));
        getUpdatedExercises().get(7).setLiked(sPref.getBoolean(L8, false));
        getUpdatedExercises().get(8).setLiked(sPref.getBoolean(L9, false));
        getUpdatedExercises().get(9).setLiked(sPref.getBoolean(L10, false));

        getUpdatedExercises().get(10).setLiked(sPref.getBoolean(L11, false));
        getUpdatedExercises().get(11).setLiked(sPref.getBoolean(L12, false));
        getUpdatedExercises().get(12).setLiked(sPref.getBoolean(L13, false));
        getUpdatedExercises().get(13).setLiked(sPref.getBoolean(L14, false));
        getUpdatedExercises().get(14).setLiked(sPref.getBoolean(L15, false));
        getUpdatedExercises().get(15).setLiked(sPref.getBoolean(L16, false));
        getUpdatedExercises().get(16).setLiked(sPref.getBoolean(L17, false));
        getUpdatedExercises().get(17).setLiked(sPref.getBoolean(L18, false));
        getUpdatedExercises().get(18).setLiked(sPref.getBoolean(L19, false));
        getUpdatedExercises().get(19).setLiked(sPref.getBoolean(L20, false));
        getUpdatedExercises().get(20).setLiked(sPref.getBoolean(L21, false));

    }

    @Override
    protected void onStop() {
        super.onStop();
        saveData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadData();
    }
}

