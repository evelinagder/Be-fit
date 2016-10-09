package com.example.evelina.befit;

import android.app.FragmentManager;import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.evelina.befit.model.Challenge;
import com.example.evelina.befit.model.DbManager;
import com.example.evelina.befit.model.Exercise;
import com.example.evelina.befit.model.TrainingManager;
import com.example.evelina.befit.model.User;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PlayExerciseActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener,ConfirmationFragment.IConfirmation {
    private static final int RECOVERY_DIALOG_REQUEST = 1;
    private YouTubePlayerView mYouTubeView;
    private FloatingActionButton mFab;
    private Button mCompletedButton;
    private NetworkStateChangedReceiver receiver;
    private Challenge mCurrentChallenge;
    private EditText setsNum,repeatsNum,pointsNum;
    User user;
    String nameChallenge;

    ArrayList<Exercise> listExercises;
    private static int mCurrentExercise;
    private  YouTubePlayer player ;
    private String userName;
    ConfirmationFragment fragment;
    android.app.FragmentManager fm = getFragmentManager();


    //TODO here we should get the challenge from the intent/by name/ and instance it to mCurrentChallenge ,then load listExercises with the exercises in the current challenge and manipulate over them
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_play_exercise);
        mCompletedButton = (Button) findViewById(R.id.button_completed);
        mYouTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        mYouTubeView.initialize(Config.DEVELOPER_KEY, this);
        mFab = (FloatingActionButton) findViewById(R.id.fab_info);
        repeatsNum=(EditText)findViewById(R.id.repeat_num) ;
        setsNum=(EditText)findViewById(R.id.sets_num) ;
        pointsNum=(EditText)findViewById(R.id.points_num) ;
        receiver = new NetworkStateChangedReceiver();
        registerReceiver(receiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));


<<<<<<< HEAD
        nameChallenge = getIntent().getStringExtra("challenge");
        final String usern = getIntent().getStringExtra("username");
        user=DbManager.getInstance(this).getUser(usern);
        boolean isBasic=getIntent().getExtras().getBoolean("isBasic");
        if(isBasic){
            mCurrentChallenge= TrainingManager.getInstance().getBasicChallenges(nameChallenge);
        }else {
            mCurrentChallenge = DbManager.getInstance(this).getUser(usern).getCustomChallenges(nameChallenge);
        }
        listExercises = (ArrayList<Exercise>) mCurrentChallenge.getExercises();
        mCurrentExercise = 0;
        setsNum.setText(listExercises.get(mCurrentExercise).getSeries()+"");
        repeatsNum.setText(listExercises.get(mCurrentExercise).getRepeats()+"");
        pointsNum.setText(listExercises.get(mCurrentExercise).getPoints()+"");

=======
        String nameChallenge = getIntent().getStringExtra("challenge");
        userName = getIntent().getStringExtra("username");
        mCurrentChallenge = DbManager.getInstance(this).getUser(userName).getCustomChallenges(nameChallenge);
        listExercises = TrainingManager.getInstance().getAllExercises();
        mCurrentExercise = 0;
        fm = getFragmentManager();
>>>>>>> 62fab5802a5632625c538ff94bd502b7031d6a4a



        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PlayExerciseActivity.this, "Open dialog fragment with info here", Toast.LENGTH_SHORT).show();
            }
        });

        mCompletedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mCurrentExercise <listExercises.size()-1){
                    player.cueVideo(listExercises.get(mCurrentExercise).getVideo());
                    setsNum.setText(listExercises.get(mCurrentExercise).getSeries()+"");
                    repeatsNum.setText(listExercises.get(mCurrentExercise).getRepeats()+"");
                    pointsNum.setText(listExercises.get(mCurrentExercise).getPoints()+"");
                    int updatePoints=user.getPoints()+listExercises.get(mCurrentExercise).getPoints();
                    DbManager.getInstance(PlayExerciseActivity.this).changeUserPoints(usern,updatePoints);
                    mCurrentExercise++;
                }else{

                    Date today= new Date();
                    String date= today+"";
                    DbManager.getInstance(PlayExerciseActivity.this).updateUserCompletedChallenges(user,mCurrentChallenge,date);
                    Intent intent= new Intent(PlayExerciseActivity.this, TrainingCompleteActivity.class);
                    intent.putExtra("challengeName",nameChallenge);
                    intent.putExtra("username",usern);
                    startActivity(intent);

                }

            }
        });

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
        if (!wasRestored) {
            // loadVideo() will auto play video
            // Use cueVideo() method, if you don't want to play it automatically
            //youTubePlayer.loadVideo(Config.YOUTUBE_VIDEO_CODE);

            youTubePlayer.cueVideo(listExercises.get(mCurrentExercise).getVideo());
            youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);
            this.player = youTubePlayer;
            mCurrentExercise++;
        }

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else {
            String errorMessage = String.format(
                    getString(R.string.error_player), youTubeInitializationResult.toString());
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_DIALOG_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(Config.DEVELOPER_KEY, this);
        }
    }

    private YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView) findViewById(R.id.youtube_view);
    }

<<<<<<< HEAD
=======
//    @Override
//    public void onBackPressed() {
//        Toast.makeText(this, "Here should be a dialog fragment", Toast.LENGTH_SHORT).show();
//        fragment = new ConfirmationFragment();
//      // fragment.show(getFragmentManager(),"confirm");
//
//    }


>>>>>>> 62fab5802a5632625c538ff94bd502b7031d6a4a


    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }
<<<<<<< HEAD
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit.", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
=======

    @Override
    public void finishSelected() {
        Intent intent = new Intent(PlayExerciseActivity.this,TabbedActivity.class);
        intent.putExtra("username",userName);
        startActivity(intent);
    }


    }

>>>>>>> 62fab5802a5632625c538ff94bd502b7031d6a4a
