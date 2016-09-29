package com.example.evelina.befit.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
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
    private static final String CHALLENGE_NAME="name";
    private static final String CHALLENGE_ACHIEVED="achieved";
    private static final String CHALLENGE_TIMES="times_completed";
    private static final String CHALLENGE_DATE="date_completed";
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
        this.context=context;
        loadUsers();
    }

    @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE "+USERS_TABLE+" ( "+USER_UID  +" INTEGER PRIMARY KEY AUTOINCREMENT, "+USER_USERNAME +" text, "
                    +USER_PASSWORD + " text, "+USER_EMAIL+" text, "+USER_WEIGHT+" text,  "+USER_HEIGHT+" text);");
            db.execSQL("CREATE TABLE "+CHALLENGES_TABLE+" ( "+CHALLENGE_UID  +" INTEGER PRIMARY KEY AUTOINCREMENT, "+CHALLENGE_USER_UID +" INTEGER , "
                    +CHALLENGE_NAME + " text, "+CHALLENGE_ACHIEVED+" text, "+CHALLENGE_TIMES+" INTEGER, "+CHALLENGE_DATE+" text, FOREIGN KEY("+CHALLENGE_USER_UID+
                    ") REFERENCES "+ USERS_TABLE+"("+USER_UID+"));");
        db.execSQL("CREATE TABLE "+EXERCISE_TABLE+" ( "+EXERCISE_UID  +" INTEGER PRIMARY KEY AUTOINCREMENT, "+EXERCISE_CHALLENGE_UID +" INTEGER, "
                +EXERCISE_NAME + " text, "+EXERCISE_POINTS+" INTEGER, "+EXERCISE_SERIES+" INTEGER, "+EXERCISE_REPEATS+" INTEGER, "+EXERCISE_INSTRUCTIONS+" text "+EXERCISE_VIDEO+" INTEGER, FOREIGN KEY("+EXERCISE_CHALLENGE_UID +
       " ) REFERENCES "+ CHALLENGES_TABLE+"("+CHALLENGE_UID+"));");
        Toast.makeText(context, "DB Created", Toast.LENGTH_SHORT).show();
        }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        Toast.makeText(context, "DB Upgraded", Toast.LENGTH_SHORT).show();
        db.execSQL("DROP TABLE "+USERS_TABLE);
        db.execSQL("DROP TABLE "+CHALLENGES_TABLE);
        db.execSQL("DROP TABLE "+EXERCISE_TABLE);
        onCreate(db);


    }
    public void loadUsers(){
        if( allUsers.isEmpty()){
            Cursor cursor=getWritableDatabase().rawQuery("SELECT "+USER_UID+", "+USER_USERNAME+", "+USER_PASSWORD+", "+USER_EMAIL+", "+USER_WEIGHT+
            ", "+USER_HEIGHT+ " FROM "+USERS_TABLE,null);
            while(cursor.moveToNext()){
                int userId=cursor.getInt(cursor.getColumnIndex(USER_UID));
                String username=cursor.getString(cursor.getColumnIndex(USER_USERNAME));
                String password=cursor.getString(cursor.getColumnIndex(USER_PASSWORD));
                String email=cursor.getString(cursor.getColumnIndex(USER_EMAIL));
                int weight=cursor.getInt(cursor.getColumnIndex(USER_WEIGHT));
                int height=cursor.getInt(cursor.getColumnIndex(USER_HEIGHT));
                User u= new User(username,password,email,weight,height);
                Cursor cursorChallenge=getWritableDatabase().rawQuery("SELECT "+CHALLENGE_UID+", "+CHALLENGE_USER_UID+", "+CHALLENGE_NAME+", "+CHALLENGE_TIMES+", "+CHALLENGE_ACHIEVED+", "+CHALLENGE_DATE+" FROM "+CHALLENGES_TABLE+", "+USERS_TABLE+" WHERE "+CHALLENGE_USER_UID+"= ?",new String[] {String.valueOf(userId)});
                while(cursorChallenge.moveToNext()){
                    int challengeId=cursor.getInt(cursor.getColumnIndex(CHALLENGE_UID));
                   String nameC=cursorChallenge.getString(cursorChallenge.getColumnIndex(CHALLENGE_NAME));
                    int times=cursorChallenge.getInt(cursorChallenge.getColumnIndex(CHALLENGE_TIMES));
                    String date=cursorChallenge.getString(cursorChallenge.getColumnIndex(CHALLENGE_DATE));
                    String achieved=cursorChallenge.getString(cursorChallenge.getColumnIndex(CHALLENGE_ACHIEVED));
                    Challenge challenge= new Challenge(nameC,times,date);
                    Cursor cursorExercise=getWritableDatabase().rawQuery("SELECT "+EXERCISE_NAME+", "+EXERCISE_POINTS+", "+EXERCISE_SERIES+", "+EXERCISE_REPEATS+", "+EXERCISE_INSTRUCTIONS+
                            ", "+EXERCISE_VIDEO+" FROM "+EXERCISE_TABLE+", "+CHALLENGES_TABLE+" WHERE "+EXERCISE_CHALLENGE_UID+"= ?" ,new String[] {String.valueOf(challengeId)});
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
                    if(achieved.equals("yes")){
                        // TODO adding after achieved?
                        u.addAchievedChallenge(challenge);
                    }
                    else {
                        u.addCustomChallenge(challenge);
                    }
                }
                allUsers.put(username, u);
            }
            if(!cursor.isClosed())
                cursor.close();
        }
    }
    public boolean existsUser( String username) {
        return allUsers.containsKey(username);
    }
    public void addUser(String username,String password, String email, int weight, int height){
        getWritableDatabase().beginTransaction();
        ContentValues values = new ContentValues();
        values.put(USER_USERNAME, username);
        values.put(USER_PASSWORD, password);
        values.put(USER_EMAIL, email);
        values.put(USER_WEIGHT, weight);
        values.put(USER_HEIGHT, height);
        getWritableDatabase().insert(USERS_TABLE, null, values);
        getWritableDatabase().endTransaction();
        allUsers.put(username, new User(username, password,email,weight,height));
    }
    public void addCustomChallenge( User user, String ChallengeName){
        //TODO

    }
    public void updateUserInfo(User user){
        //TODO all fields from profile change
    }
    public void changeUserPoints( User user, int newPoints){
        //TODO
    }
    public void changeUserChallenges(User user, Challenge completedChallenge, String date){
        //TODO on completing a Challenge, adds to user Alist- DATE?
    }
    public User getUser(String username){
       User user= allUsers.get(username);
        return user;
    }
    public boolean validateLogin(String username, String password){
        if (!existsUser(username)) {
            Log.e("F", "user does not exist in map");
            return false;
        }
        if (!allUsers.get(username).getPassword().equals(password)) {
            Log.e("F", "user pass is wrong");
            return false;
        }
        return true;
    }
}


