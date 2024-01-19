package com.example.workoutgenerator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import static android.content.ContentValues.TAG;

public class DatabaseHelper extends SQLiteOpenHelper {

    static final int DB_VERSION=2;

    public static final String DATABASE_NAME = "WorkoutGenerator.db";
    public static final String TABLE_NAME1 = "registeruser";
    public static final String COL_1 = "id";
    public static final String COL_2 = "name";
    public static final String COL_3 = "username";
    public static final String COL_4 = "password";

    public static final String TABLE_NAME2 = "workouts";
    public static final String COL_12 = "id";
    public static final String COL_22 = "name";
    public static final String COL_32 = "preparetime";
    public static final String COL_42 = "worktime";
    public static final String COL_52 = "resttime";
    public static final String COL_62 = "numofcycles";
    public static final String COL_72 = "numofsets";
    public static final String COL_82 = "restbetweensets";
    public static final String COL_92 = "strings";

    public static final String TABLE_NAME3 = "exercises";
    public static final String COL_13 = "id";
    public static final String COL_23 = "name";
    public static final String COL_33 = "description";
    public static final String COL_43 = "musclegroup";
    public static final String COL_53 = "imageloc";
    public static final String COL_63 = "isliked";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        onUpgrade(db, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if(oldVersion < 1){

            db.execSQL("CREATE TABLE registeruser (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, username TEXT, password TEXT)");
            db.execSQL("CREATE TABLE workouts (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, preparetime TEXT, worktime TEXT, resttime TEXT, numofcycles TEXT, numofsets TEXT, restbetweensets TEXT)");

        }
        if(oldVersion < 2){
            db.execSQL("ALTER TABLE "+TABLE_NAME2+" ADD COLUMN "+COL_92+" TEXT");
        }

        /*
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME1);
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME2);
        onCreate(db);
        */
    }

    public long addUser(String name, String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("username", username);
        contentValues.put("password", password);
        long res = db.insert(TABLE_NAME1, null, contentValues);

        db.close();
        return res;
    }


    public long addWorkout(String name, String preparetime, String worktime,
                           String resttime, String numofcycles, String numofsets,
                           String restbetweensets, String strings){

        Log.d(TAG, "addWorkout: workout added!");

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("preparetime", preparetime);
        contentValues.put("worktime", worktime);
        contentValues.put("resttime", resttime);
        contentValues.put("numofcycles", numofcycles);
        contentValues.put("numofsets", numofsets);
        contentValues.put("restbetweensets", restbetweensets);
        contentValues.put("strings", strings);
        long res = db.insert(TABLE_NAME2, null, contentValues);

        db.close();
        return res;
    }

    public long addWorkoutForList(String name, String preparetime, String worktime,
                           String resttime, String numofcycles, String numofsets,
                           String restbetweensets, String exercisesstring ){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("preparetime", preparetime);
        contentValues.put("worktime", worktime);
        contentValues.put("resttime", resttime);
        contentValues.put("numofcycles", numofcycles);
        contentValues.put("numofsets", numofsets);
        contentValues.put("restbetweensets", restbetweensets);
        contentValues.put("strings", exercisesstring);
        long res = db.insert(TABLE_NAME2, null, contentValues);

        db.close();
        return res;
    }

    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {COL_1};
        String selection = COL_3 + "=?" + " and " + COL_4 + "=?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(TABLE_NAME1, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        Log.d(TAG, "checkUser: " + count);
        if (count >= 1)
            return true;
        else
            return false;
    }
    public Integer deleteUserRecord(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME1,"ID = ?",new String[] {id});
    }
    public Integer deleteWorkoutByID(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d(TAG, "onClick: Deleted!");
        return db.delete(TABLE_NAME2,"ID = ?",new String[] {id});
    }
}