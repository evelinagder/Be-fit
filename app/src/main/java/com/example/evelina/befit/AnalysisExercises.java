package com.example.evelina.befit;

import android.content.Intent;
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
import java.util.Map;

public class AnalysisExercises extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView mRecyclerView;
    private AllExerciseProgressAdapter adapter;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis_exercises);
        toolbar = (Toolbar) findViewById(R.id.app_bar_analysis);
        toolbar.setTitle("Exercise statistics");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_18dp);
        toolbar.setTitleTextColor(android.graphics.Color.WHITE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AnalysisExercises.this,TabbedActivity.class);
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

        List<Challenge> challenges = DbManager.getInstance(AnalysisExercises.this).getUser(userName).getCompletedTrainings();
        for(int  i = 0 ;i<challenges.size();i++){
            Log.e("TAG","CHALLENGES FOR THE USER "+challenges.get(i).getName());
        }

        HashMap<Exercise,Integer> doneTimes = new HashMap<>(exercises.size());
        for(int i = 0;i<exercises.size();i++){
            doneTimes.put(exercises.get(i),0);
            Log.e("TAG","ALL EXERCISES IN THE MANAGER "+exercises.get(i).getName());
        }

        Map<Challenge,List<Exercise>> exercisesInChallenge= new HashMap<>();
        List<Exercise> helper;
        for (int i = 0 ;i<challenges.size();i++){
            helper = challenges.get(i).getExercises();
            exercisesInChallenge.put(challenges.get(i),helper);
            Log.e("TAG"," ZA trenirovka  = "+challenges.get(i).getName()+" ima "+ helper.size() + " broq uprajneniq");
                for(Exercise exercise :helper){
                    doneTimes.put(exercise,(doneTimes.get(exercise)+(exercise.getRepeats()*exercise.getSeries())));
                    Log.e("TAG","za uprajnenie "+exercise.getName()+ " ima "+exercise.getSeries()*exercise.getRepeats()+ "puti napraveno ");
                }
            }
        adapter = new AllExerciseProgressAdapter(this,exercises,doneTimes,DbManager.getInstance(this).getUser(userName));
        mRecyclerView.setAdapter(adapter);
        }
}
