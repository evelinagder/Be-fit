package com.example.evelina.befit;

import android.content.Intent;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;


import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;



public class StartTrainingActivity extends AppCompatActivity implements StartTrainingFragment.IStartTraining {
    private String username;
    private String challengeName;
    private boolean isBasic;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_training);
        username = getIntent().getStringExtra("username");
        challengeName = getIntent().getStringExtra("challenge");
        isBasic = getIntent().getExtras().getBoolean("isBasic");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(challengeName);
        //todo oshte neshta na toolbara
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_18dp);
        toolbar.setTitleTextColor(android.graphics.Color.WHITE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartTrainingActivity.this,TabbedActivity.class);
                intent.putExtra("username",username);
                startActivity(intent);
                finish();
            }
        });

        Bundle bundle = new Bundle();
        bundle.putString("username", username);
        bundle.putString("challenge", challengeName);
        bundle.putBoolean("isBasic", isBasic);
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getFragments() == null || fm.getFragments().size() == 0) {
            FragmentTransaction transaction = fm.beginTransaction();
            Fragment challengeFragment = new StartTrainingFragment();
           // transaction.add(R.id.activity_start_training, challengeFragment, "FRAGMENT");
            transaction.replace(R.id.container,challengeFragment);
            challengeFragment.setArguments(bundle);
            Log.e("HHHH", "adding fragment");
            transaction.commit();
            Log.e("HHHH", "transaction fragment");

        }

    }

    @Override
    public void beginWorkout() {
        Intent intent = new Intent(StartTrainingActivity.this, PlayExerciseActivity.class);
        intent.putExtra("challenge", challengeName);
        intent.putExtra("username", username);
        intent.putExtra("isBasic", isBasic);
        startActivity(intent);
    }


}
