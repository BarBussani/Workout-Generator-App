package com.example.workoutgenerator;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.content.Intent;
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
import androidx.appcompat.app.AppCompatActivity;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class WorkoutHistory extends AppCompatActivity {


    ArrayList<String> lvCounter;
    ArrayList<Workout> workoutList = new ArrayList<>();
    ListView lvWorkouts;
    Button btnBack;
    DatabaseHelper db;
    SQLiteDatabase sqdb;
    Workout workout, item;
    ArrayList<String> array, ids= new ArrayList<>();
    WorkoutListAdapter arrayAdapter;
    String currentStr = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_history);

        lvWorkouts = findViewById(R.id.lvWorkouts);
        btnBack = findViewById(R.id.btnBack);
        registerForContextMenu(lvWorkouts);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goBack = new Intent(WorkoutHistory.this, BuildWorkout.class);
                startActivity(goBack);
            }
        });

        db = new DatabaseHelper(this);
        sqdb = db.getWritableDatabase();
        ArrayList<String> lv = new ArrayList<>();


        Cursor c = sqdb.query(DatabaseHelper.TABLE_NAME2, null,null,null,null,null,null);

        int[] col=new int[7];
        col[0]=c.getColumnIndex(DatabaseHelper.COL_32);
        col[1]=c.getColumnIndex(DatabaseHelper.COL_42);
        col[2]=c.getColumnIndex(DatabaseHelper.COL_52);
        col[3]=c.getColumnIndex(DatabaseHelper.COL_62);
        col[4]=c.getColumnIndex(DatabaseHelper.COL_72);
        col[5]=c.getColumnIndex(DatabaseHelper.COL_82);
        col[6]=c.getColumnIndex(DatabaseHelper.COL_92);

        String[] t=new String[7];

        c.moveToFirst();
        while(!c.isAfterLast())
        {
            for(int i=0; i<col.length; i++)
            {
                t[i]=c.getString(col[i]);
            }
            ids.add(c.getString(c.getColumnIndex(DatabaseHelper.COL_12)));
            workout = new Workout(t[0], t[1], t[2], t[3], t[4], t[5], t[6]);
            //Toast.makeText(this, workout.toString(), Toast.LENGTH_LONG).show();
            //lv.add(workout.toString());
            workoutList.add(workout);
            c.moveToNext();
        }
       // Toast.makeText(this,ids.toString(), Toast.LENGTH_LONG).show();

        arrayAdapter = new WorkoutListAdapter(this, R.layout.activity_workout_item, workoutList);

        lvWorkouts.setAdapter(arrayAdapter);
        sqdb.close();


        lvWorkouts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                item = workoutList.get(position);

                String str = item.getStrings();
                array = turnStringToArrayList(str);

                if(array != null){

                String[] strings = {"זמן הכנה: ", "זמן עבודה: ", "זמן מנוחה: ", "תרגיל: ", "זמן מנוחה בין סטים: "};
                String print = strings[0] + getTimeFormatted(item.getPrepareTime()) + "\n";
                int sets = Integer.parseInt(item.getNumOfSets()), cycles = Integer.parseInt(item.getNumOfCycles());

                for(int k = 1; k<=sets; k++){
                    if( k!= 1)
                        print+= strings[4] + getTimeFormatted(item.getRestBetweenSetsTime())+"\n";
                    print+= "סט " +k+ ": \n";
                    for(int j = 1; j<=cycles; j++){
                        print+= "תרגיל " +j+": " +array.get(j-1 + (k-1)*cycles) + "\n"; //תרגיל _: שם תרגיל
                        print+= strings[1] + getTimeFormatted(item.getWorkTime()) + "\n";
                        print+= strings[2] + getTimeFormatted(item.getRestTime()) + "\n";
                    }
                }

                print+= " \n" +"האם ברצונך להתחיל את האימון?";

                AlertDialog.Builder builder = new AlertDialog.Builder(WorkoutHistory.this);
                builder.setMessage(print)
                        .setCancelable(false)
                        .setPositiveButton("כן", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();

                                    Intent goToTimerActivity = new Intent(WorkoutHistory.this, Timer.class);
                                    Bundle bundle  = new Bundle();
                                    bundle.putParcelable("workout", Parcels.wrap(item));
                                    goToTimerActivity.putExtras(bundle);
                                    goToTimerActivity.putExtra("choices",array);
                                    startActivity(goToTimerActivity);


                            }
                        })
                        .setNegativeButton("לא", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        }
        });
    }

    public ArrayList<String> turnStringToArrayList(String str){ // str = "[a,b,c]"
        String replace = str.replace("[",""); // replace = "a,b,c]"
        String replace1 = replace.replace("]",""); // replace1 = "a,b,c"
        List<String> myList = new ArrayList<String>(Arrays.asList(replace1.split(",")));
        return (ArrayList<String>) myList;
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.floating_context_menu_workout_history, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Workout s = workoutList.get(info.position);
        switch (item.getItemId()) {
            case R.id.optionDelete:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("האם אתה בטוח שברצונך למחוק את האימון?")
                        .setCancelable(false)
                        .setPositiveButton("כן", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                workoutList.remove(info.position);
                                arrayAdapter.notifyDataSetChanged();
                                DatabaseHelper db = new DatabaseHelper(WorkoutHistory.this);
                                db.deleteWorkoutByID(String.valueOf(ids.get(info.position)));
                                ids.remove(info.position);
                            }
                        })
                        .setNegativeButton("לא", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

                return true;
            default:
                return super.onContextItemSelected(item);
        }}

}