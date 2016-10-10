package com.example.evelina.befit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.example.evelina.befit.adapters.CompletedTrainingExpandableAdapter;
import com.example.evelina.befit.model.Challenge;
import com.example.evelina.befit.model.DbManager;
import com.example.evelina.befit.model.Exercise;
import com.example.evelina.befit.model.User;
import java.util.ArrayList;
import java.util.List;

public class CompletedTrainings extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView mRecyclerView;
    String username;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_trainings);
        toolbar = (Toolbar) findViewById(R.id.app_bar_completed_trainings);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Completed challenges");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_18dp);
        toolbar.setTitleTextColor(android.graphics.Color.WHITE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CompletedTrainings.this,TabbedActivity.class);
               // intent.putExtra("username",username);
                startActivity(intent);
                finish();
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.completed_trainings_expandable_recycler);
        username=getIntent().getStringExtra("username");
        user= DbManager.getInstance(CompletedTrainings.this).getUser(username);

        //TrainingSpecifications is a class which we will use in supplying the times of completion of challenge and it's last day of completion
        //i have filled the specifications with two objects for test view
        //we should get from DbManager/for the current User/ his completed challenges
        ArrayList<Challenge> completed = user.getCompletedTrainings();
        List<TrainingTypes> namesTrainings= new ArrayList<>();
        for(int i=0; i<completed.size();i++){
            Challenge current=completed.get(i);
           List<Exercise>exercises= current.getExercises();
            List<TrainingSpecifications> specifications = new ArrayList<>();
            TrainingSpecifications trainingOne = new TrainingSpecifications(current.getTimesCompleted(),current.getDateOfCompletion(),exercises);
            specifications.add(trainingOne);
            TrainingTypes one = new TrainingTypes(specifications,current.getName());
            namesTrainings.add(one);
        }


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
