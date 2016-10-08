package com.example.evelina.befit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.example.evelina.befit.adapters.CompletedTrainingExpandableAdapter;
import com.example.evelina.befit.model.Exercise;
import com.example.evelina.befit.model.TrainingManager;

import java.util.ArrayList;
import java.util.List;

public class CompletedTrainings extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_trainings);
        toolbar = (Toolbar) findViewById(R.id.app_bar_completed_trainings);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Completed challenges");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecyclerView = (RecyclerView) findViewById(R.id.completed_trainings_expandable_recycler);

        //TrainingSpecifications is a class which we will use in supplying the times of completion of challenge and it's last day of completion
        //i have filled the specifications with two objects for test view
        //we should get from DbManager/for the current User/ his completed challenges
        ArrayList<Exercise> exercises = TrainingManager.getInstance().getAllExercises();

        TrainingSpecifications trainingOne = new TrainingSpecifications(5,"10.05.2016",exercises);
       // TrainingSpecifications trainingSpecificationsTwo = new TrainingSpecifications(7,"02.09.2012",exercises);
        List<TrainingSpecifications> specifications = new ArrayList<>();
        specifications.add(trainingOne);
       // specifications.add(trainingSpecificationsTwo);

        //TrainingTypes is a class which we use to supply the expandable recyclerview with parent objects - the names of the challenges
        //we should get from DbManager/for the current User / his completed challenges's names
        List<TrainingTypes> namesTrainings = new ArrayList<>();
        namesTrainings.add(new TrainingTypes(specifications,"trenirovka 2"));
        namesTrainings.add(new TrainingTypes(specifications,"trenirovka 2"));
        namesTrainings.add(new TrainingTypes(specifications,"trenirovka 3"));
        //for the adapter we need the parent objects - TrainingTypes/as list/ and the activity in which we want the expandable recycler view
        final CompletedTrainingExpandableAdapter adapter = new CompletedTrainingExpandableAdapter(namesTrainings,CompletedTrainings.this);

        adapter.setExpandCollapseListener(new ExpandableRecyclerAdapter.ExpandCollapseListener() {
            @Override
            public void onListItemExpanded(int position) {
                adapter.expandParent(position);
            }

            @Override
            public void onListItemCollapsed(int position) {
                adapter.collapseParent(position);
            }
        });


        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));



    }
}
