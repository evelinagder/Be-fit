package com.example.evelina.befit.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.HashMap;

/**
 * Created by Evelina on 9/28/2016.
 */
public class DbManager extends SQLiteOpenHelper{
    Context context;
    private static DbManager sInstance;
    HashMap<String , User> allUsers;

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
        allUsers= new HashMap<String, User>();
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
    public void loadUsers(){
        if( allUsers.isEmpty()){
            Cursor cursor=getWritableDatabase().rawQuery("SELECT "+USER_USERNAME+", "+USER_PASSWORD+", "+USER_EMAIL+", "+USER_WEIGHT+
            ", "+USER_HEIGHT+ " FROM "+USERS_TABLE,null);
            while(cursor.moveToNext()){
                String username=cursor.getString(cursor.getColumnIndex(USER_USERNAME));
                String password=cursor.getString(cursor.getColumnIndex(USER_PASSWORD));
                String email=cursor.getString(cursor.getColumnIndex(USER_EMAIL));
                int weight=cursor.getInt(cursor.getColumnIndex(USER_WEIGHT));
                int height=cursor.getInt(cursor.getColumnIndex(USER_HEIGHT));
                User u= new User(username,password,email,weight,height);
                Cursor cursorChallenge=getWritableDatabase().rawQuery("SELECT "+CHALLENGE_TYPE+", "+CHALLENGE_TIMES+", "+CHALLENGE_DATE+" FROM "+CHALLENGES_TABLE+", "+USERS_TABLE+" WHERE "+CHALLENGE_USER_UID+"=" +USER_UID,null);
                while(cursorChallenge.moveToNext()){
                   //????? Challenge.Type type=cursorChallenge.getString(cursorChallenge.getColumnIndex(CHALLENGE_TYPE)); if type equals CUSTOM
                    int times=cursorChallenge.getInt(cursorChallenge.getColumnIndex(CHALLENGE_TIMES));
                    String date=cursorChallenge.getString(cursorChallenge.getColumnIndex(CHALLENGE_DATE));
                    Challenge challenge= new Challenge(Challenge.Type.CUSTOM,times,date);
                    Cursor cursorExercise=getWritableDatabase().rawQuery("SELECT "+EXERCISE_NAME+", "+EXERCISE_POINTS+", "+EXERCISE_SERIES+", "+EXERCISE_REPEATS+", "+EXERCISE_INSTRUCTIONS+
                            ", "+EXERCISE_VIDEO+" FROM "+EXERCISE_TABLE+", "+CHALLENGES_TABLE+" WHERE "+EXERCISE_CHALLENGE_UID+"=" +CHALLENGE_UID,null);
                    while(cursorExercise.moveToNext()){
                        String name=cursorExercise.getString(cursorExercise.getColumnIndex(EXERCISE_NAME));
                        int points=cursorExercise.getInt(cursorExercise.getColumnIndex(EXERCISE_POINTS));
                        int series=cursorExercise.getInt(cursorExercise.getColumnIndex(EXERCISE_SERIES));
                        int repeats=cursorExercise.getInt(cursorExercise.getColumnIndex(EXERCISE_REPEATS));
                        String instructions=cursorExercise.getString(cursorExercise.getColumnIndex(EXERCISE_INSTRUCTIONS));
                        int video=cursorExercise.getInt(cursorExercise.getColumnIndex(EXERCISE_VIDEO));
                        Exercise exercise= new Exercise(name, points,series,repeats,instructions,video);
                        challenge.addExercise(exercise);
                    }
                    u.addCustomChallenge(challenge);
                    //if NOT: u. add.Challenge(chalenge);
                }
                allUsers.put(username, u);
            }
            //TODO LOAD CHALENGES AND EXERCISES
            Cursor cursorChallenge=getWritableDatabase().rawQuery("SELECT "+CHALLENGE_TYPE+", "+CHALLENGE_TIMES+", "+CHALLENGE_DATE+" FROM "+CHALLENGES_TABLE+" WHERE ",null);
        }
    }
    public boolean existsUser( String username){
        return allUsers.containsKey(username);
    }
}
