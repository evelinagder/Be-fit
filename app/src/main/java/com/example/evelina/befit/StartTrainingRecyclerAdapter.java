package com.example.evelina.befit;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Evelina on 10/4/2016.
 */

public class StartTrainingRecyclerAdapter extends RecyclerView.Adapter<StartTrainingRecyclerAdapter.StartVH> {

    private StartTrainingActivity activity;
    private List<String> exercises;


    StartTrainingRecyclerAdapter(StartTrainingActivity activity, List<String> exercisesList) {
        exercises = exercisesList;
        this.activity = activity;
    }

    @Override
    public StartVH onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View row = inflater.inflate(R.layout.start_training_list_row, parent, false);
        row.findViewById(R.id.start_training_row).setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return new StartVH(row);
    }

    @Override
    public void onBindViewHolder(StartVH holder, final int position) {
        holder.exercises.setText(exercises.get(position).toString());

    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }


class StartVH extends RecyclerView.ViewHolder{


    TextView exercises;
    public StartVH(View itemView) {
        super(itemView);
        exercises = (TextView)itemView.findViewById(R.id.exercise_name);

    }

}

}

