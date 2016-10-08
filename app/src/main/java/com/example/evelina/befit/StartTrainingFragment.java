package com.example.evelina.befit;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.evelina.befit.adapters.StartTrainingRecyclerAdapter;
import com.example.evelina.befit.model.Challenge;
import com.example.evelina.befit.model.DbManager;
import com.example.evelina.befit.model.TrainingManager;
import com.example.evelina.befit.model.User;


/**
 * A simple {@link Fragment} subclass.
 */
public class StartTrainingFragment extends Fragment {
    IStartTraining activity;


    public interface IStartTraining{
    void beginWorkout();
    }

    public StartTrainingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (IStartTraining) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       // if(getIntent().) get if basic, get name  get from Training manager
       // Challenge challenge =
        //if custom get username, get from user`s list
        //put challenge exercises name
        View root =  inflater.inflate(R.layout.fragment_start_training, container, false);
        String challengeName=getArguments().getString("challenge");
        String username=getArguments().getString("username");
        boolean isBasic=getArguments().getBoolean("isBasic");
        User user= DbManager.getInstance(getActivity()).getUser(username);
        Log.e("MMM",username);
        Challenge challenge;
        if(isBasic){
            challenge= TrainingManager.getInstance().getBasicChallenges(challengeName);
            Log.e("MMM",challenge.getName());
        }
        else{
            challenge=user.getCustomChallenges(challengeName);
            Log.e("MMM",challenge.getName());
        }
        TextView heading= (TextView)root.findViewById(R.id.heading);
        heading.setText(challengeName);
        FloatingActionButton start=(FloatingActionButton)root.findViewById(R.id.Button_start_training);
        RecyclerView challengeView = (RecyclerView) root.findViewById(R.id.recycler_view_start);
        challengeView.setAdapter(new StartTrainingRecyclerAdapter((StartTrainingActivity) getActivity(),  challenge.getExercisesNames()));
        challengeView.setLayoutManager(new LinearLayoutManager(getActivity()));
    //TODO startONclicklistner??

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.beginWorkout();
            }
        });
        return root;
    }

}
