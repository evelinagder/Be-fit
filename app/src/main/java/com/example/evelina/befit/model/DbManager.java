package com.example.evelina.befit.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by Evelina on 9/28/2016.
 */
public class DbManager extends SQLiteOpenHelper{
    Context context;
    private static DbManager sInstance;

    private static final String DATABASE_NAME = "beFitDatabase";
    private static final int DATABASE_VERSION = 1;
    //Table names;
    private static final String USERS_TABLE = "Users";
    private static final String CHALLENGES_TABLE = "Challenges";
    private static final String EXERCISE_TABLE = "Exercises";
    //USER table column names
    private static final String USER_UID="_id";
    private static final String USER_USERNAME="username";
    private static final String USER_PASSWORD="password";
    private static final String USER_EMAIL="email";
    private static final String USER_WEIGHT="weight";
    private static final String USER_HEIGHT="height";
    //CHALLENGE table column names
    private static final String CHALLENGE_UID="challenge_id";
    private static final String CHALLENGE_USER_UID="user_id";
    private static final String CHALLENGE_TYPE="type";
    private static final String CHALLENGE_TIMES="times_completed";
    private static final String CHALLENGE_DATE="ate_completed";
    //EXERCISE table column names
    private static final String EXERCISE_CHALLENGE_UID="toChallenge_id";
    private static final String EXERCISE_UID="exercise_id";
    private static final String EXERCISE_NAME="exercise_name";
    private static final String EXERCISE_POINTS="points";
    private static final String EXERCISE_SERIES="series";
    private static final String EXERCISE_REPEATS="repeats";
    private static final String EXERCISE_INSTRUCTIONS="instructions";
    private static final String EXERCISE_VIDEO="video";




    public static synchronized DbManager getInstance(Context context) {

        if(sInstance == null){
            sInstance = new DbManager(context.getApplicationContext());
        }
        return sInstance;
    }

    private DbManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE "+USERS_TABLE+" ( "+USER_UID  +"INTEGER PRIMARY KEY AUTOINCREMENT,"+USER_USERNAME +"text,"
                    +USER_PASSWORD + "text,"+USER_EMAIL+"text,"+USER_WEIGHT+"text,"+USER_HEIGHT+"text);");
            db.execSQL("CREATE TABLE "+CHALLENGES_TABLE+" ( "+CHALLENGE_UID  +"INTEGER PRIMARY KEY AUTOINCREMENT,"+CHALLENGE_USER_UID +"INTEGER ,FOREIGN KEY("+CHALLENGE_USER_UID+
            ") REFERENCES"+ USERS_TABLE+"("+USER_UID+"), "
                    +CHALLENGE_TYPE + "text,"+CHALLENGE_TIMES+"INTEGER,"+CHALLENGE_DATE+"text );");
        db.execSQL("CREATE TABLE "+EXERCISE_TABLE+" ( "+EXERCISE_UID  +"INTEGER PRIMARY KEY AUTOINCREMENT,"+EXERCISE_CHALLENGE_UID +"INTEGER ,FOREIGN KEY("+EXERCISE_CHALLENGE_UID+
                ") REFERENCES"+ CHALLENGES_TABLE+"("+CHALLENGE_UID+"), "
                +EXERCISE_NAME + "text,"+EXERCISE_POINTS+"INTEGER,"+EXERCISE_SERIES+"INTEGER,"+EXERCISE_REPEATS+"INTEGER,"+EXERCISE_INSTRUCTIONS+"text"+EXERCISE_VIDEO+"INTEGER );");
        Toast.makeText(context, "DB Created", Toast.LENGTH_SHORT).show();
        }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
