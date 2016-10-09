package com.example.evelina.befit.model;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.example.evelina.befit.NotificationsFragment;
import com.example.evelina.befit.R;


import java.util.Calendar;
import java.util.HashMap;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by Evelina on 9/28/2016.
 */
public class DbManager extends SQLiteOpenHelper{
    Context context;
    private static DbManager sInstance;
    public HashMap<String , User> allUsers;

    private static final String DATABASE_NAME = "beFitDatabase";
    private static final int DATABASE_VERSION = 1;
    //Table names;
    private static final String USERS_TABLE = "Users";
    private static final String CHALLENGES_TABLE = "Challenges";
    private static final String EXERCISE_TABLE = "Exercises";
    private static final String ALARM_TABLE = "Alarm";
    //USER table column names
    private static final String USER_UID="_id";
    private static final String USER_USERNAME="username";
    private static final String USER_PASSWORD="password";
    private static final String USER_EMAIL="email";
    private static final String USER_GENDER="gender";
    private static final String USER_POINTS="points";
    private static final String USER_WEIGHT="weight";
    private static final String USER_HEIGHT="height";
    private static final String USER_PICTURE="picture";
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
    //ALARM table column names

    private static final String ALARM_UID="alarm_id";
    private static final String ALARM_USER_USERNAME="user_username";
    private static final String ALARM_TIME="alarm_time";
    private static final String ALARM_REPEATING="alarm_repeating";






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
            db.execSQL("CREATE TABLE "+USERS_TABLE+" ( "+USER_UID  +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                    +USER_USERNAME +" text, "
                    +USER_PASSWORD + " text, "
                    +USER_EMAIL+" text, "
                    +USER_GENDER+" text, "
                    +USER_WEIGHT+" INTEGER,  "
                    +USER_HEIGHT+" INTEGER, "
                    +USER_PICTURE+" text, "
                    +USER_POINTS+" INTEGER);");
            db.execSQL("CREATE TABLE "+CHALLENGES_TABLE+" ( "+CHALLENGE_UID  +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                    +CHALLENGE_USER_UID +" INTEGER , "
                    +CHALLENGE_NAME + " text, "
                    +CHALLENGE_ACHIEVED+" text, "
                    +CHALLENGE_TIMES+" INTEGER, "
                    +CHALLENGE_DATE+" text, FOREIGN KEY("+CHALLENGE_USER_UID+
                    ") REFERENCES "+ USERS_TABLE+"("+USER_UID+"));");
        db.execSQL("CREATE TABLE "+EXERCISE_TABLE+" ( "+EXERCISE_UID  +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +EXERCISE_CHALLENGE_UID +" INTEGER, "
                +EXERCISE_NAME + " text, "
                +EXERCISE_POINTS+" INTEGER, "
                +EXERCISE_SERIES+" INTEGER, "
                +EXERCISE_REPEATS+" INTEGER, "
                +EXERCISE_INSTRUCTIONS+" text "
                +EXERCISE_VIDEO+" text, FOREIGN KEY("+EXERCISE_CHALLENGE_UID +
       " ) REFERENCES "+ CHALLENGES_TABLE+"("+CHALLENGE_UID+"));");
        db.execSQL("CREATE TABLE "+ALARM_TABLE+" ( "+ALARM_UID  +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +ALARM_USER_USERNAME +" text, "
                +ALARM_REPEATING+" text, "
                +ALARM_TIME+" INTEGER,  FOREIGN KEY("+ALARM_USER_USERNAME+
                ") REFERENCES "+ USERS_TABLE+"("+USER_UID+"));"
        );
        Toast.makeText(context, "DB Created", Toast.LENGTH_SHORT).show();
        }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        Toast.makeText(context, "DB Upgraded", Toast.LENGTH_SHORT).show();
        db.execSQL("DROP TABLE "+USERS_TABLE);
        db.execSQL("DROP TABLE "+CHALLENGES_TABLE);
        db.execSQL("DROP TABLE "+EXERCISE_TABLE);
        db.execSQL("DROP TABLE "+ALARM_TABLE);
        onCreate(db);


    }
    public void loadUsers(){
        Log.e("DBUSERS", "load users start");
        if( allUsers.isEmpty()){
            Cursor cursor=getWritableDatabase().rawQuery("SELECT "+USER_UID+", "
                    +USER_USERNAME+", "
                    +USER_PASSWORD+", "
                    +USER_EMAIL+", "
                    +USER_GENDER+", "
                    +USER_WEIGHT+ ", "
                    +USER_HEIGHT+", "
                    +USER_PICTURE+", "
                    +USER_POINTS+ " FROM " +USERS_TABLE,null);
            while(cursor.moveToNext()){
                int userId=cursor.getInt(cursor.getColumnIndex(USER_UID));
                String username=cursor.getString(cursor.getColumnIndex(USER_USERNAME));
                String password=cursor.getString(cursor.getColumnIndex(USER_PASSWORD));
                String email=cursor.getString(cursor.getColumnIndex(USER_EMAIL));
                String gender=cursor.getString(cursor.getColumnIndex(USER_GENDER));
                String picture=cursor.getString(cursor.getColumnIndex(USER_PICTURE));
                int weight=cursor.getInt(cursor.getColumnIndex(USER_WEIGHT));
                int height=cursor.getInt(cursor.getColumnIndex(USER_HEIGHT));
                int pointsU=cursor.getInt(cursor.getColumnIndex(USER_POINTS));
                User u= new User(username,password,email,gender,weight,height,pointsU);
                if(picture != null) {
                    Uri userPicture = Uri.parse(picture);
                    u.setProfilePic(userPicture);
                }
                u.setUserDBId(userId);
                Cursor cursorChallenge=getWritableDatabase().rawQuery("SELECT "+CHALLENGE_UID+", "
                        +CHALLENGE_USER_UID+", "
                        +CHALLENGE_NAME+", "
                        +CHALLENGE_TIMES+", "
                        +CHALLENGE_ACHIEVED+", "
                        +CHALLENGE_DATE+" FROM "+CHALLENGES_TABLE+", "+USERS_TABLE+" WHERE "+CHALLENGE_USER_UID+"= ?",new String[] {String.valueOf(userId)});
                while(cursorChallenge.moveToNext()){
                    int challengeId=cursorChallenge.getInt(cursorChallenge.getColumnIndex(CHALLENGE_UID));
                    String nameC=cursorChallenge.getString(cursorChallenge.getColumnIndex(CHALLENGE_NAME));
                    int times=cursorChallenge.getInt(cursorChallenge.getColumnIndex(CHALLENGE_TIMES));
                    String date=cursorChallenge.getString(cursorChallenge.getColumnIndex(CHALLENGE_DATE));
                    String achieved=cursorChallenge.getString(cursorChallenge.getColumnIndex(CHALLENGE_ACHIEVED));
                    Challenge challenge= new Challenge(nameC,times,date);
                    challenge.setChallengeID(challengeId);
                    Cursor cursorExercise=getWritableDatabase().rawQuery("SELECT "+EXERCISE_NAME+", "
                            +EXERCISE_POINTS+", "
                            +EXERCISE_SERIES+", "
                            +EXERCISE_REPEATS+", "
                            +EXERCISE_INSTRUCTIONS+ ", "
                            +EXERCISE_VIDEO+" FROM "+EXERCISE_TABLE+", "+CHALLENGES_TABLE+" WHERE "+EXERCISE_CHALLENGE_UID+"= ?" ,new String[] {String.valueOf(challengeId)});
                    while(cursorExercise.moveToNext()){
                        String name=cursorExercise.getString(cursorExercise.getColumnIndex(EXERCISE_NAME));
                        int points=cursorExercise.getInt(cursorExercise.getColumnIndex(EXERCISE_POINTS));
                        int series=cursorExercise.getInt(cursorExercise.getColumnIndex(EXERCISE_SERIES));
                        int repeats=cursorExercise.getInt(cursorExercise.getColumnIndex(EXERCISE_REPEATS));
                        String instructions=cursorExercise.getString(cursorExercise.getColumnIndex(EXERCISE_INSTRUCTIONS));
                        String video=cursorExercise.getString(cursorExercise.getColumnIndex(EXERCISE_VIDEO));
                        Exercise exercise= new Exercise(name, points,series,repeats,instructions,video);
                        challenge.addExercise(exercise);
                    }
                    if(achieved.equals("yes")){
                        u.addAchievedChallenge(challenge);
                    }
                    else {
                        u.addCustomChallenge(challenge);
                    }
                }
                allUsers.put(username, u);
                Log.e("DBUSERS", "user added - " + u);
            }
        }
        Log.e("DBUSERS", "load users end");
    }
    public boolean existsUser( String username) {
        return allUsers.containsKey(username);
    }

    public void addUser(String username,String password, String email,String gender, int weight, int height,int points){

        ContentValues values = new ContentValues();
        values.put(USER_USERNAME, username);
        values.put(USER_PASSWORD, password);
        values.put(USER_EMAIL, email);
        values.put(USER_GENDER,gender);
        values.put(USER_WEIGHT, weight);
        values.put(USER_HEIGHT, height);
        values.put(USER_POINTS, points);
       long id = getWritableDatabase().insert(USERS_TABLE, null, values);
        allUsers.put(username, new User(username, password,email,gender,weight,height,points));
    }
    public void addCustomChallenge( String username, Challenge challenge){
        //adds new challende when user pressed ADD and gave it a name!
            User user=getUser(username);
            SQLiteDatabase db = getReadableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(CHALLENGE_NAME, challenge.getName());
            cv.put(CHALLENGE_USER_UID, user.getUserDBId());
            db.insert(CHALLENGES_TABLE, null, cv);
        user.addCustomChallenge(new Challenge(challenge.getName(),0,""));
    }
    public void addExercisesToCustomChallenge(String username,String challengeName, Exercise exercise){
                User user=getUser(username);
                Challenge challenge= user.getCustomChallenges(challengeName);
                SQLiteDatabase db = getReadableDatabase();
                ContentValues cv = new ContentValues();
                cv.put(EXERCISE_NAME, exercise.getName());
                cv.put(EXERCISE_CHALLENGE_UID, challenge.getChallengeID());
                cv.put(EXERCISE_SERIES, exercise.getSeries());
                cv.put(EXERCISE_REPEATS, exercise.getRepeats());
                db.insert(EXERCISE_TABLE, null, cv);
                 challenge.addExercise(exercise);
    }
    public void updateProfilePicture(String username,Uri picture){
        //adds picture to user and to db
        User user= allUsers.get(username);
        String pic=picture.toString();
        SQLiteDatabase db = getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(USER_PICTURE,pic);
        db.update(USERS_TABLE,cv,USER_USERNAME+" =? ",new String[] {user.getUsername()});
        user.setProfilePic(picture);
        allUsers.put(username, user);

    }
    public void changeUserEmail(String username, String newEmail){
        //used in settings -change user email option
        User user= allUsers.get(username);
        SQLiteDatabase db = getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(USER_EMAIL,newEmail);
        db.update(USERS_TABLE,cv,USER_USERNAME+" =? ",new String[] {user.getUsername()});
        user.setEmail(newEmail);
        allUsers.put(username, user);
        Toast.makeText(context, "New email "+ newEmail, Toast.LENGTH_SHORT).show();
    }
    public void changeUserWeight(String username, int newWeight){
        //used in settings -change user weight option ??
        User user= allUsers.get(username);
        SQLiteDatabase db = getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(USER_WEIGHT,newWeight);
        db.update(USERS_TABLE,cv,USER_USERNAME+" =? ",new String[] {user.getUsername()});
        user.setWeight(newWeight);
        allUsers.put(username, user);
        Toast.makeText(context, "New weight "+ newWeight, Toast.LENGTH_SHORT).show();
    }
    public void updateUserHeight(String username, int height){
        //used in settings -change user height option ??
        User user= allUsers.get(username);
        SQLiteDatabase db = getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(USER_HEIGHT,height);
        db.update(USERS_TABLE,cv,USER_USERNAME+" =? ",new String[] {user.getUsername()});
        user.setHeight(height);
        allUsers.put(username, user);
        Toast.makeText(context, "New height "+ height, Toast.LENGTH_SHORT).show();
    }
    public void updateUserGender(String username, String gender){
        //used when user changes gender from settings
        User user= allUsers.get(username);
        SQLiteDatabase db = getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(USER_GENDER,gender);
        db.update(USERS_TABLE,cv,USER_USERNAME+" =? ",new String[] {user.getUsername()});
        user.setGender(gender);
        allUsers.put(username, user);
        Toast.makeText(context, "New gender "+ gender, Toast.LENGTH_SHORT).show();
    }
    public void changeUserPoints( String username, int newPoints){
        //used when user completes an Exercise, upgrade user`s points in d and in map!
        User user= allUsers.get(username);
        SQLiteDatabase db = getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(USER_POINTS,newPoints);
        db.update(USERS_TABLE,cv,USER_USERNAME+" =? ",new String[] {user.getUsername()});
        user.setPoints(user.getPoints()+newPoints);
        allUsers.put(username, user);
    }
    public void updateUserCompletedChallenges(User user, Challenge completedChallenge, String  date){
        //TODO CONVERT DATE TO STRING
        SQLiteDatabase db = getReadableDatabase();
        ContentValues cv = new ContentValues();
        int id= user.getUserDBId();
        completedChallenge.setAchieved("yes");
        completedChallenge.setDateOfCompletion(date);
        cv.put(CHALLENGE_ACHIEVED,"yes");
        cv.put(CHALLENGE_DATE,date);
        db.update(CHALLENGES_TABLE,cv,CHALLENGE_USER_UID+" =? ",new String[] {String.valueOf(id)});
        user.addAchievedChallenge(completedChallenge);


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
    public void saveNotifications(String username, long alarmTime, boolean isAlarmRepeating, Context activity) {
        //  save the new alarm
        User user = allUsers.get(username);
        String repeat;
        if(isAlarmRepeating) {
            repeat = "true";
        }else{
            repeat = "false";
        }
        Alarm alarm = new Alarm(alarmTime, isAlarmRepeating);
        SQLiteDatabase db = getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ALARM_TIME, alarmTime);
        cv.put(ALARM_REPEATING, repeat);
        db.update(ALARM_TABLE, cv, ALARM_USER_USERNAME + " =? ", new String[]{user.getUsername()});
        user.addAlarmn(alarm);
        startAlarm(username,alarmTime,isAlarmRepeating,activity);
    }





    public void loadNotifications(String username, Context activity) {
        // set the radio buttons in Notifications framgment.
        //check if there is a running alarm , if not start one with the larm object alarmTime!
        if (allUsers.get(username).userAlarms.size() != 0) {
            Cursor cursor = getWritableDatabase().rawQuery("SELECT " + ALARM_UID + ", "
                    + ALARM_TIME + ", "
                    + ALARM_REPEATING + " FROM " + ALARM_TABLE + ", " + USERS_TABLE + " WHERE " + USER_USERNAME + "= ?", new String[]{username});
            while (cursor.moveToNext()) {
                long alarmTime = cursor.getInt(cursor.getColumnIndex(ALARM_TIME));
                String isRepeating = cursor.getString(cursor.getColumnIndex(ALARM_REPEATING));
                boolean isAlarmRepeating;
                if (isRepeating.equals("true")) {
                    isAlarmRepeating = true;
                } else {
                    isAlarmRepeating = false;
                }
                startAlarm(username,alarmTime, isAlarmRepeating, activity);

            }
        }
    }
    public void startAlarm(String username,long alarmTime, boolean isRepeating, Context activity){
        Alarm alarm= new Alarm(alarmTime,isRepeating);
        Intent alarmIntent = new Intent("ALARM");
        alarmIntent.putExtra("ALARM TIME",alarmTime);
        alarmIntent.putExtra("username",username);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(activity, 0, alarmIntent, 0);

        AlarmManager am = (AlarmManager) activity.getSystemService(ALARM_SERVICE);
        if( alarm.getIsRepeating()){
            am.setRepeating(AlarmManager.RTC_WAKEUP,alarmTime,AlarmManager.INTERVAL_DAY * 7, pendingIntent);
        }
        else{
            am.set(AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent);
        }
    }
    public void cancelAlarms(Context activity){
        AlarmManager am = (AlarmManager) activity.getSystemService(ALARM_SERVICE);
        Intent alarmIntent = new Intent("ALARM");
        alarmIntent.putExtra("ALARM TIME",0);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(activity, 0, alarmIntent, 0);
        am.cancel(pendingIntent);

    }

}


