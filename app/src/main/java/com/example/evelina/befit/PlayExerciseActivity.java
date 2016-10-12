package com.example.evelina.befit;

import android.app.FragmentManager;import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import java.util.Date;
import java.util.List;

public class PlayExerciseActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    private static final int RECOVERY_DIALOG_REQUEST = 1;
    private YouTubePlayerView mYouTubeView;
    private FloatingActionButton mFab;
    private Button mCompletedButton;
    private NetworkStateChangedReceiver receiver;
    private Challenge mCurrentChallenge;
    private TextView setsNum,repeatsNum,pointsNum;
    private User user;
    private String nameChallenge;
    private TextView mDescriptionTV;
    private ArrayList<Exercise> listExercises;
    private static int mCurrentExercise;
    private  YouTubePlayer player ;
    private String userName;
    private Typeface typeface;
    private TextView mPointsTV;



    //TODO here we should get the challenge from the intent/by name/ and instance it to mCurrentChallenge ,then load listExercises with the exercises in the current challenge and manipulate over them
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_play_exercise);
        typeface  = Typeface.createFromAsset(getAssets(),  "RockoFLF.ttf");
        mCompletedButton = (Button) findViewById(R.id.button_completed);
        mYouTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        mYouTubeView.initialize(Config.DEVELOPER_KEY, this);
        repeatsNum=(TextView)findViewById(R.id.repeat_num);
        setsNum=(TextView)findViewById(R.id.sets_num);
        mDescriptionTV = (TextView) findViewById(R.id.description_TV);
        mPointsTV = (TextView) findViewById(R.id.points_TV);

        receiver = new NetworkStateChangedReceiver();
        registerReceiver(receiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));

        mCompletedButton.setTypeface(typeface);
        repeatsNum.setTypeface(typeface);
        setsNum.setTypeface(typeface);
        mDescriptionTV.setTypeface(typeface);

        nameChallenge = getIntent().getStringExtra("challenge");
        final String usern = getIntent().getStringExtra("username");
        user=DbManager.getInstance(this).getUser(usern);
        final boolean isBasic=getIntent().getExtras().getBoolean("isBasic");
        if(isBasic){
            mCurrentChallenge= TrainingManager.getInstance().getBasicChallenges(nameChallenge);
        }else {
            mCurrentChallenge = DbManager.getInstance(this).getUser(usern).getCustomChallenges(nameChallenge);
        }
        listExercises = (ArrayList<Exercise>) mCurrentChallenge.getExercises();
        mCurrentExercise = 0;
        setsNum.setText(listExercises.get(mCurrentExercise).getSeries()+"");
        repeatsNum.setText(listExercises.get(mCurrentExercise).getRepeats()+"");
        mPointsTV.setText("*This exercise gives you "+listExercises.get(mCurrentExercise).getPoints()+" points");







        mCompletedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mCurrentExercise <listExercises.size()-1){
                    player.cueVideo(listExercises.get(mCurrentExercise).getVideo());
                    setsNum.setText(listExercises.get(mCurrentExercise).getSeries()+"");
                    repeatsNum.setText(listExercises.get(mCurrentExercise).getRepeats()+"");
                    //pointsNum.setText(listExercises.get(mCurrentExercise).getPoints()+"");
                    mDescriptionTV.setText(listExercises.get(mCurrentExercise).getInstructions());
                    int updatePoints=user.getPoints()+listExercises.get(mCurrentExercise).getPoints();
                    DbManager.getInstance(PlayExerciseActivity.this).changeUserPoints(usern,updatePoints);
                    mCurrentExercise++;
                }else{


                    Intent intent= new Intent(PlayExerciseActivity.this, TrainingCompleteActivity.class);
                    intent.putExtra("challengeName",nameChallenge);
                    intent.putExtra("username",usern);
                    intent.putExtra("isBasic",isBasic);
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
            mDescriptionTV.setText(listExercises.get(mCurrentExercise).getInstructions());
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




    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }

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


