package com.example.workoutgenerator;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;

public class HomeScreenActivity extends AppCompatActivity {

    private static final String TAG = "HomeScreenActivity";
    private static final String SAVED_NAME = "saved_name", SAVED_PASS = "saved_phone", SAVED_FULL_NAME = "saved_full_name", SAVED_USER_NAME = "saved_user_name",
            SAVED_PASSWORD = "saved_password", SAVED_EMAIL = "saved_email",
            CB1 = "cb1",  CB2 = "cb2",  CB3 = "cb3",  CB4 = "cb4", CB5 = "cb5",  CB6 = "cb6",
            CHANNEL_ID = " ";
    int count;
    String name = "התראת סוללה", Description ="הסוללה נמוכה", title = "סוללה נמוכה, נא חבר את המכשיר לחשמל", message = "חבר את המכשיר לחשמל";

    EditText etDelete;
    Button btnDelete;
    TextView welcomeTv;
    private SharedPreferences sPref;

    DatabaseHelper db = new DatabaseHelper(this);

    CardView buildWorkoutCard, exercisesListCard, workoutHistoryCard, UserGuideCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        String savedFullName = sPref.getString(SAVED_FULL_NAME, "");
        welcomeTv = findViewById(R.id.welcomeTv);
        welcomeTv.setText("שלום "+ savedFullName+"! \n" +"ברוך הבא לWorkoutGenerator");


        /* btnDelete = findViewById(R.id.btnDelete);
        etDelete = findViewById(R.id.etDelete);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper db = new DatabaseHelper(HomeScreenActivity.this);
                db.deleteWorkoutByID(etDelete.getText().toString());
            }
        }); */

        buildWorkoutCard = findViewById(R.id.buildWorkoutCard);
        exercisesListCard = findViewById(R.id.exercisesListCard);
        workoutHistoryCard = findViewById(R.id.workoutHistoryCard);
        UserGuideCard = findViewById(R.id.UserGuideCard);

        buildWorkoutCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent buildWorkoutIntent = new Intent(HomeScreenActivity.this, BuildWorkout.class);
                startActivity(buildWorkoutIntent);
            }
        });

        exercisesListCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent exerciseListIntent = new Intent(HomeScreenActivity.this, ListOfExercises.class);
                startActivity(exerciseListIntent);
            }
        });

        workoutHistoryCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent workoutHistoryIntent = new Intent(HomeScreenActivity.this, WorkoutHistory.class);
                startActivity(workoutHistoryIntent);
            }
        });

        UserGuideCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent guideIntent = new Intent(HomeScreenActivity.this,UserGuideActivity.class);
                startActivity(guideIntent);
            }
        });

        count = 0;
        this.registerReceiver(this.BatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,0,500, "התנתק \uD83D\uDD1A");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    public BroadcastReceiver BatInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context arg0, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            if (level <= 15 && count == 0)
            {
                    createNotification(HomeScreenActivity.this, level);
            }
            count++;
        }
    };

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item1:
                Intent guideIntent = new Intent(this,UserGuideActivity.class);
                startActivity(guideIntent);
                return true;
            case R.id.item2:
                Intent aboutIntent = new Intent(this,AboutMeActivity.class);
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
            case R.id.item7:
                Intent SMSIntent = new Intent(this,SendSms.class);
                startActivity(SMSIntent);
                return true;
            case R.id.item8:
                Intent EmailIntent = new Intent(this,SendEmail.class);
                startActivity(EmailIntent);
                return true;
            case 0:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("האם אתה בטוח שברצונך להתנתק?")
                        .setCancelable(false)
                        .setPositiveButton("כן", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                                SharedPreferences.Editor edit = pref.edit();
                                edit.putString(SAVED_NAME, "");
                                edit.putString(SAVED_PASS, "");
                                edit.putString(SAVED_FULL_NAME, "");
                                edit.putString(SAVED_USER_NAME, "");
                                edit.putString(SAVED_PASSWORD, "");
                                edit.putString(SAVED_EMAIL, "");
                                edit.putBoolean(CB1, false);
                                edit.putBoolean(CB2, false);
                                edit.putBoolean(CB3, false);
                                edit.putBoolean(CB4, false);
                                edit.putBoolean(CB5, false);
                                edit.putBoolean(CB6, false);
                                edit.commit();
                                finish();
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
                return super.onOptionsItemSelected(item);
        }
    }

    private void createNotification(Context ctx, int batteryLevel) {

        int NOTIFICATION_ID = 234;

        NotificationManager notificationManager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setDescription(Description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mChannel.setShowBadge(false);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(ctx, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText("אחוז הסוללה "+batteryLevel + "%");

        Intent resultIntent = new Intent(ctx, Splash.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(ctx);
        stackBuilder.addParentStack(Splash.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}