package com.example.evelina.befit;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import com.example.evelina.befit.adapters.AllExerciseProgressAdapter;
import com.example.evelina.befit.model.Challenge;
import com.example.evelina.befit.model.DbManager;
import com.example.evelina.befit.model.Exercise;
import com.example.evelina.befit.model.TrainingManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class AnalysisExercises extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView mRecyclerView;
    private AllExerciseProgressAdapter adapter;
    private String userName;
    private NetworkStateChangedReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis_exercises);
        toolbar = (Toolbar) findViewById(R.id.app_bar_analysis);
        toolbar.setTitle("For all completed workouts");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_18dp);
        toolbar.setTitleTextColor(android.graphics.Color.WHITE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AnalysisExercises.this, TabbedActivity.class);
                intent.putExtra("username", userName);
                startActivity(intent);
                finish();
            }
        });

        userName = getIntent().getStringExtra("username");
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_analysis);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Exercise> exercises = new ArrayList<>();
        exercises.addAll(TrainingManager.getInstance().getAllExercises());
        Collections.sort(exercises, new Comparator<Exercise>() {
            @Override
            public int compare(Exercise exercise, Exercise t1) {
                return exercise.getName().compareTo(t1.getName());
            }
        });
//here first we take the completed trainings
        List<Challenge> challenges = DbManager.getInstance(AnalysisExercises.this).getUser(userName).getCompletedTrainings();
        //here we make the number of challenges  to be equal to times of completions since we are saving only the unique trainings
        List<Challenge> gg = new ArrayList<>();
        for (int i = 0; i < challenges.size(); i++) {
            Log.e("TAG", "CHALLENGES FOR THE USER " + challenges.get(i).getName());
            gg.add(challenges.get(i));
            if (challenges.get(i).getTimesCompleted() > 1) {
                int comp = challenges.get(i).getTimesCompleted();
                while (comp > 1) {
                    //if times of completion is >1 we add it as many times as times of completion
                    gg.add(challenges.get(i));
                    comp--;
                }
            }
        }
        Log.e("i gg size", gg.size() + "");
        //hashmap from all the existing exercises in TrainingManager with initial value 0
        HashMap<Exercise, Integer> doneTimes = new HashMap<>(exercises.size());
        for (int i = 0; i < exercises.size(); i++) {
            doneTimes.put(exercises.get(i), 0);
            Log.e("TAG", "ALL EXERCISES IN THE MANAGER " + exercises.get(i).getName());
        }
//helper for holding every seperate list of exercises for the challenge
        ArrayList<Exercise> helper;
        for (int i = 0; i < gg.size(); i++) {
            helper = gg.get(i).getExercises();
            for (int k = 0; k < helper.size(); k++) {
                //here we take the old value of the exercise  and add it to the new to form the sum of all repeats
                doneTimes.put(helper.get(k), (doneTimes.get(helper.get(k)) + (helper.get(k).getRepeats() * helper.get(k).getSeries())));
            }
        }
        adapter = new AllExerciseProgressAdapter(this, exercises, doneTimes, DbManager.getInstance(this).getUser(userName));
        mRecyclerView.setAdapter(adapter);
        receiver = new NetworkStateChangedReceiver();
        registerReceiver(receiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }
    //TODO
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AnalysisExercises.this,TabbedActivity.class);
        intent.putExtra("username", userName);
        startActivity(intent);
        finish();
    }
}
