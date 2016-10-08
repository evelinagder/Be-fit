package com.example.evelina.befit;

import android.content.Intent;
import android.support.annotation.BoolRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.evelina.befit.model.Challenge;
import com.example.evelina.befit.model.DbManager;
import com.example.evelina.befit.model.TrainingManager;
import com.example.evelina.befit.model.User;

public class StartTrainingActivity extends AppCompatActivity implements StartTrainingFragment.IStartTraining{
    private String username;
    private String challengeName;
    private boolean isBasic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_training);
        username = getIntent().getStringExtra("username");
        challengeName = getIntent().getStringExtra("challenge");
        isBasic = getIntent().getExtras().getBoolean("isBasic");

        Bundle bundle = new Bundle();
        bundle.putString("username", username);
        bundle.putString("challenge", challengeName);
        bundle.putBoolean("isBasic", isBasic);
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getFragments() == null || fm.getFragments().size() == 0) {
            FragmentTransaction transaction = fm.beginTransaction();
            Fragment challengeFragment = new StartTrainingFragment();
            transaction.add(R.id.activity_start_training, challengeFragment, "FRAGMENT");
            challengeFragment.setArguments(bundle);
            Log.e("HHHH", "adding fragment");
            transaction.commit();
            Log.e("HHHH", "transaction fragment");

        }
    }

    @Override
    public void beginWorkout() {
        Intent intent = new Intent(StartTrainingActivity.this,PlayExerciseActivity.class);
        intent.putExtra("challenge",challengeName);
        intent.putExtra("username",username);
        intent.putExtra("isBasic",isBasic);
        startActivity(intent);
    }
}
