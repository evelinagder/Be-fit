package com.example.evelina.befit;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.evelina.befit.model.Challenge;
import com.example.evelina.befit.model.DbManager;
import com.example.evelina.befit.model.TrainingManager;
import com.example.evelina.befit.model.User;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareOpenGraphAction;
import com.facebook.share.model.ShareOpenGraphContent;
import com.facebook.share.model.ShareOpenGraphObject;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;

import java.util.Date;

public class TrainingCompleteActivity extends AppCompatActivity {
    private TextView challengeName;
    private Button ok;
    private boolean isBasic;
    private String username;
    private String challengeNameString;
    private Challenge challenge;
    private TextView heading;
    private User user;
    private NetworkStateChangedReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_complete);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "GreatVibes.ttf");
        challengeName = (TextView) findViewById(R.id.congrats_training_name);
        ok = (Button) findViewById(R.id.ok_complete);
        receiver = new NetworkStateChangedReceiver();
        registerReceiver(receiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        challengeNameString = getIntent().getStringExtra("challengeName");
        challengeName.setText(challengeNameString);
        heading = (TextView) findViewById(R.id.headingC);
        heading.setTypeface(typeface);
        username = getIntent().getStringExtra("username");
        isBasic = getIntent().getExtras().getBoolean("isBasic");
        user = DbManager.getInstance(this).getUser(username);
        if (isBasic) {
            challenge = TrainingManager.getInstance().getBasicChallenges(challengeNameString);
        } else {
            challenge = user.getCustomChallenges(challengeNameString);
        }
        Date today = new Date();
        String mid = today + "";
        String date = mid.substring(0, 10);
        if (isBasic) {
            Log.e("BASIC", "basic in db TCA");
            DbManager.getInstance(TrainingCompleteActivity.this).updateUserCompletedBasicChallenges(user, challenge, date);
            for (int i = 0; i < challenge.getExercises().size(); i++) {
                DbManager.getInstance(TrainingCompleteActivity.this).addExercisesToBasicChallenge(username, challenge.getName(), challenge.getExercises().get(i));
            }
        } else {
            DbManager.getInstance(TrainingCompleteActivity.this).updateUserCompletedChallenges(user, challenge, date);
        }
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrainingCompleteActivity.this, TabbedActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }
}
