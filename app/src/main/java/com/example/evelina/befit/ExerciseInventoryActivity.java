package com.example.evelina.befit;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.evelina.befit.model.Exercise;
import com.example.evelina.befit.model.TrainingManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ExerciseInventoryActivity extends AppCompatActivity {
    Toolbar toolbar;
    private RecyclerView mRecyclerView;
    private NetworkStateChangedReceiver receiver;
    private ExerciseRecyclerAdapter mExercisesAdapter;
    private FloatingActionButton fab ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_inventory);
        toolbar= (Toolbar) findViewById(R.id.toolbar_exercise_inventory);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Exercise inventory");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        fab = (FloatingActionButton) findViewById(R.id.fab_exercise_inventory);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ExerciseInventoryActivity.this, "Adding finished", Toast.LENGTH_SHORT).show();
                //here we have already added the exercises in DB and the user
                //here we go to some activity
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO here we get null pointer for user ...we need Db or shared preffs to load the user
//                Intent intent = new Intent(ExerciseInventoryActivity.this,TabbedActivity.class);
//                intent.putExtra("username","lalalalla");
//                startActivity(intent);
            }
        });
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_exercise_inventory);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Exercise> exercises = new ArrayList<>();
        int size = TrainingManager.getInstance().getAllExercises().size();
        exercises.addAll(TrainingManager.getInstance().getAllExercises());
        Collections.sort(exercises, new Comparator<Exercise>() {
            @Override
            public int compare(Exercise exercise, Exercise t1) {
                return exercise.getName().compareTo(t1.getName());
            }
        });
        mExercisesAdapter = new ExerciseRecyclerAdapter(this,exercises,mRecyclerView);
        mRecyclerView.setAdapter(mExercisesAdapter);
        receiver = new NetworkStateChangedReceiver();
        registerReceiver(receiver,new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
    }


    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }
}
