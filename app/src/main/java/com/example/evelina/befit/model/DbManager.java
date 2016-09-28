package com.example.evelina.befit.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
    private static final String USERNAME_COLUMN="Username";
    private static final String PASSWORD_COLUMN="Password";
    private static final String EMAIL_COLUMN="Email";
    private static final String KILOGRAMS_COLUMN="Kilograms";
    private static final String HEIGHT_COLUMN="Height";
    //CHALLENGE table column names
    private static final String UID_CHALLENGE="Challenge_id";
    private static final String USER_TO_UID="User_id";
    private static final String TYPE_COLUMN="Type";
    private static final String TIMES_COLUMN="Times completed";
    private static final String DATE_COLUMN="Date completed";
    //EXERCISE table column names
    private static final String UID_CHALLENGE_TO="ToChallenge_id";
    private static final String EXERCISE_UID="Exercise_id";
    private static final String ENAME_COLUMN="Exercise name";
    private static final String POINTS_COLUMN="Points";
    private static final String SERIES_COLUMN="Series";
    private static final String REPEATS_COLUMN="Repeats";
    private static final String INSTRUCTIONS_COLUMN="Instructions";
    private static final String VIDEO_COLUMN="Video";





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
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
