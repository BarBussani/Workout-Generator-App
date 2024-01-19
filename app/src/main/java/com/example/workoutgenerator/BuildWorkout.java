package com.example.workoutgenerator;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Locale;


public class BuildWorkout extends AppCompatActivity implements View.OnClickListener {

    private EditText tvNumber1, tvNumber2, tvNumber3, tvNumber4, tvNumber5, tvNumber6;
    private Button btnPlus1, btnPlus2, btnPlus3, btnPlus4, btnPlus5, btnPlus6, btnCreate, btnWatch, btnStart, btnChooseExercisese;
    private Button btnMinus1, btnMinus2, btnMinus3, btnMinus4, btnMinus5, btnMinus6;
    private int num;
    ArrayList<String> choices= new ArrayList<>();

    private SharedPreferences sPref;
    private static final String SAVED_PREPARE_TIME = "saved_prepare_time", SAVED_WORK_TIME = "saved_work_time",
            SAVED_REST_TIME = "saved_rest_time", SAVED_NUM_OF_CYCLES = "saved_num_of_cycles", SAVED_NUM_OF_SETS = "saved_num_of_sets",
            SAVED_REST_BETWEEN_SETS = "saved_rest_between_sets";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build_workout);

        setAllFeatures();

        btnMinus1.setOnClickListener(this);
        btnMinus2.setOnClickListener(this);
        btnMinus3.setOnClickListener(this);
        btnMinus4.setOnClickListener(this);
        btnMinus5.setOnClickListener(this);
        btnMinus6.setOnClickListener(this);

        btnPlus1.setOnClickListener(this);
        btnPlus2.setOnClickListener(this);
        btnPlus3.setOnClickListener(this);
        btnPlus4.setOnClickListener(this);
        btnPlus5.setOnClickListener(this);
        btnPlus6.setOnClickListener(this);

        btnChooseExercisese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tvNumber1.getText().toString()!="" && tvNumber2.getText().toString()!="" && tvNumber3.getText().toString()!="" && tvNumber4.getText().toString()!="" &&
                        tvNumber5.getText().toString()!="" && tvNumber6.getText().toString()!=""){
                    Intent goToChooseExercises = new Intent(BuildWorkout.this, ChooseExercises.class);
                    goToChooseExercises.putExtra("cycles", tvNumber4.getText().toString());
                    goToChooseExercises.putExtra("sets", tvNumber5.getText().toString());
                    startActivity(goToChooseExercises);
                }else
                    Toast.makeText(BuildWorkout.this, "עלייך לבחור תחילה את זמני האימון.", Toast.LENGTH_SHORT).show();

            }
        });


        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int cycles = Integer.parseInt(tvNumber4.getText().toString()),
                    sets = Integer.parseInt(tvNumber5.getText().toString());
                String[] strings = {"זמן הכנה: ", "זמן עבודה: ", "זמן מנוחה: ", "תרגיל: ", "זמן מנוחה בין סטים: "};
                String str = strings[0] + getTimeFormatted(tvNumber1.getText().toString()) + "\n";

                //getting the exercises choices by array...
                Intent i = getIntent();
                choices = i.getStringArrayListExtra("choicesArray");

                if(choices!=null){
                //setting str...
                for(int k = 1; k<=sets; k++){
                    if( k!= 1)
                        str+= strings[4] + getTimeFormatted(tvNumber6.getText().toString())+"\n";
                    str+= "סט " +k+ ": \n";
                    for(int j = 1; j<=cycles; j++){
                        str+= "תרגיל " +j+": " +choices.get(j-1 + (k-1)*cycles) + "\n"; //תרגיל _: שם תרגיל
                        str+= strings[1] + getTimeFormatted(tvNumber2.getText().toString()) + "\n";
                        str+= strings[2] + getTimeFormatted(tvNumber3.getText().toString()) + "\n";
                    }
                }

                str+=" \n" +"האם אתה מאשר את בניית האימון?";


                AlertDialog.Builder builder = new AlertDialog.Builder(BuildWorkout.this);
                builder.setMessage(str)
                        .setCancelable(false)
                        .setPositiveButton("אישור", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                DatabaseHelper db = new DatabaseHelper(BuildWorkout.this);

                                db.addWorkout( "name",tvNumber1.getText().toString(),
                                        tvNumber2.getText().toString(),tvNumber3.getText().toString(),
                                        tvNumber4.getText().toString(),tvNumber5.getText().toString(),
                                        tvNumber6.getText().toString(), choices.toString());
                                Toast.makeText(BuildWorkout.this, "נוצר האימון.", Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton("ביטול", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();}
                else
                    Toast.makeText(BuildWorkout.this, "עלייך לבחור תחילה תרגילים לאימון.", Toast.LENGTH_LONG).show();
            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Workout myWorkout = new Workout(tvNumber1.getText().toString(), tvNumber2.getText().toString(),
                        tvNumber3.getText().toString(), tvNumber4.getText().toString(),
                        tvNumber5.getText().toString(), tvNumber6.getText().toString());

                // getting choices array...
                Intent i = getIntent();
                choices = i.getStringArrayListExtra("choicesArray");

                if(choices!=null){
                    Intent goToTimerActivity = new Intent(BuildWorkout.this, Timer.class);
                    Bundle bundle  = new Bundle();
                    bundle.putParcelable("workout", Parcels.wrap(myWorkout));
                    goToTimerActivity.putExtras(bundle);
                    goToTimerActivity.putExtra("choices",choices);
                    startActivity(goToTimerActivity);
                } else
                    Toast.makeText(BuildWorkout.this, "עלייך לבחור תחילה תרגילים לאימון.", Toast.LENGTH_LONG).show();


            }
        });

        btnWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToWorkoutHistory = new Intent(BuildWorkout.this, WorkoutHistory.class);
                startActivity(goToWorkoutHistory);
            }
        });
    }

    private void setAllFeatures() {

        tvNumber1 = findViewById(R.id.tvNumber1);
        tvNumber2 = findViewById(R.id.tvNumber2);
        tvNumber3 = findViewById(R.id.tvNumber3);
        tvNumber4 = findViewById(R.id.tvNumber4);
        tvNumber5 = findViewById(R.id.tvNumber5);
        tvNumber6 = findViewById(R.id.tvNumber6);

        btnPlus1 = findViewById(R.id.btnPlus1);
        btnPlus2 = findViewById(R.id.btnPlus2);
        btnPlus3 = findViewById(R.id.btnPlus3);
        btnPlus4 = findViewById(R.id.btnPlus4);
        btnPlus5 = findViewById(R.id.btnPlus5);
        btnPlus6 = findViewById(R.id.btnPlus6);

        btnMinus1 = findViewById(R.id.btnMinus1);
        btnMinus2 = findViewById(R.id.btnMinus2);
        btnMinus3 = findViewById(R.id.btnMinus3);
        btnMinus4 = findViewById(R.id.btnMinus4);
        btnMinus5 = findViewById(R.id.btnMinus5);
        btnMinus6 = findViewById(R.id.btnMinus6);

        btnCreate = findViewById(R.id.btnCreate);
        btnWatch = findViewById(R.id.btnWatch);
        btnStart = findViewById(R.id.btnStart);
        btnChooseExercisese = findViewById(R.id.btnChooseExercises);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnMinus1:
                if(tvNumber1.getText().toString() == "")
                    num = 1;
                else
                    num = Integer.parseInt(tvNumber1.getText().toString());
                if(num>1)
                    tvNumber1.setText((num - 1) + "");
                else
                    Toast.makeText(this,"יש להכניס מספר חיובי", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnMinus2:
                if(tvNumber2.getText().toString() == "")
                    num = 1;
                else
                    num = Integer.parseInt(tvNumber2.getText().toString());
                if(num>1)
                    tvNumber2.setText((num - 1) + "");
                else
                    Toast.makeText(this,"יש להכניס מספר חיובי", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnMinus3:
                if(tvNumber3.getText().toString() == "")
                    num = 1;
                else
                    num = Integer.parseInt(tvNumber3.getText().toString());
                if(num>1)
                    tvNumber3.setText((num - 1) + "");
                else
                    Toast.makeText(this,"יש להכניס מספר חיובי", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnMinus4:
                if(tvNumber4.getText().toString() == "")
                    num = 1;
                else
                    num = Integer.parseInt(tvNumber4.getText().toString());
                if(num>1)
                    tvNumber4.setText((num - 1) + "");
                else
                    Toast.makeText(this,"יש להכניס מספר חיובי", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnMinus5:
                if(tvNumber5.getText().toString() == "")
                    num = 1;
                else
                    num = Integer.parseInt(tvNumber5.getText().toString());
                if(num>1)
                    tvNumber5.setText((num - 1) + "");
                else
                    Toast.makeText(this,"יש להכניס מספר חיובי", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnMinus6:
                if(tvNumber6.getText().toString() == "")
                    num = 1;
                else
                    num = Integer.parseInt(tvNumber6.getText().toString());
                if(num>1)
                    tvNumber6.setText((num - 1) + "");
                else
                    Toast.makeText(this,"יש להכניס מספר חיובי", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnPlus1:
                num = Integer.parseInt(tvNumber1.getText().toString());
                tvNumber1.setText( (num + 1) + "");
                break;
            case R.id.btnPlus2:
                num = Integer.parseInt(tvNumber2.getText().toString());
                tvNumber2.setText( (num + 1) +"");
                break;
            case R.id.btnPlus3:
                num = Integer.parseInt(tvNumber3.getText().toString());
                tvNumber3.setText((num + 1) +"");
                break;
            case R.id.btnPlus4:
                num = Integer.parseInt(tvNumber4.getText().toString());
                tvNumber4.setText((num + 1) +"");
                break;
            case R.id.btnPlus5:
                num = Integer.parseInt(tvNumber5.getText().toString());
                tvNumber5.setText((num + 1) +"");
                break;
            case R.id.btnPlus6:
                num = Integer.parseInt(tvNumber6.getText().toString());
                tvNumber6.setText((num + 1) +"");
                break;
            default:
                break;
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
                Intent aboutIntent = new Intent(this, AboutMeActivity.class);
                startActivity(aboutIntent);
                return true;
            case R.id.item3:
                Intent exerciseListIntent = new Intent(this, ListOfExercises.class);
                startActivity(exerciseListIntent);
                return true;
            case R.id.item4:
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
    }

    public String getTimeFormatted(String time){
        Long millisInput = Long.parseLong(time) * 1000;
        int hours = (int) (millisInput / 1000) / 3600;
        int minutes = (int) ((millisInput / 1000) % 3600) / 60;
        int seconds = (int) (millisInput / 1000) % 60;
        String timeLeftFormatted;
        if (hours > 0) {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%d:%02d:%02d", hours, minutes, seconds);
        }
        else {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%02d:%02d", minutes, seconds);
        }
        return timeLeftFormatted;
    }

    private void saveText()
    {
        sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor edit = sPref.edit();
        edit.putString(SAVED_PREPARE_TIME, tvNumber1.getText().toString());
        edit.putString(SAVED_WORK_TIME, tvNumber2.getText().toString());
        edit.putString(SAVED_REST_TIME, tvNumber3.getText().toString());
        edit.putString(SAVED_NUM_OF_CYCLES, tvNumber4.getText().toString());
        edit.putString(SAVED_NUM_OF_SETS, tvNumber5.getText().toString());
        edit.putString(SAVED_REST_BETWEEN_SETS, tvNumber6.getText().toString());
        edit.commit();
    }

    private void loadText() {
        sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        String savedPrepareTime = sPref.getString(SAVED_PREPARE_TIME, "");
        String savedWorkTime = sPref.getString(SAVED_WORK_TIME, "");
        String savedRestTime = sPref.getString(SAVED_REST_TIME, "");
        String savedNumOfCycles = sPref.getString(SAVED_NUM_OF_CYCLES, "");
        String savedNumOfSets = sPref.getString(SAVED_NUM_OF_SETS, "");
        String savedRestBetweenSets = sPref.getString(SAVED_REST_BETWEEN_SETS, "");

        tvNumber1.setText(savedPrepareTime);
        tvNumber2.setText(savedWorkTime);
        tvNumber3.setText(savedRestTime);
        tvNumber4.setText(savedNumOfCycles);
        tvNumber5.setText(savedNumOfSets);
        tvNumber6.setText(savedRestBetweenSets);
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveText();
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadText();
    }

}