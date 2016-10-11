package com.example.evelina.befit.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.akexorcist.roundcornerprogressbar.TextRoundCornerProgressBar;
import com.akexorcist.roundcornerprogressbar.common.BaseRoundCornerProgressBar;
import com.example.evelina.befit.R;
import com.example.evelina.befit.model.Exercise;
import com.example.evelina.befit.model.User;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by User on 11-Oct-16.
 */

public class AllExerciseProgressAdapter extends RecyclerView.Adapter<AllExerciseProgressAdapter.AllExerciseViewHolder>{
    private Context context;
    private List<Exercise> exercises;
    private HashMap<Exercise,Integer> doneTimes;
    private User user;


    public AllExerciseProgressAdapter(Context context,List<Exercise> exercises,HashMap<Exercise,Integer> doneTimes,User user){
        this.context = context;
        this.exercises = exercises;
        this.doneTimes = doneTimes;
        this.user = user;
    }

    @Override
    public AllExerciseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.analysis_list_row,parent,false);
        return new AllExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AllExerciseViewHolder holder, int position) {
        holder.exerciseName.setText( exercises.get(position).getName());
        //TODO deside the max
        holder.progressBar.setMax(Collections.max(doneTimes.values()));
        Log.e("TAG","max is "+Collections.max(doneTimes.values()));
        int doneTimesH =doneTimes.get(exercises.get(position));
        holder.timesDone.setText(doneTimesH+" repeats");
        holder.progressBar.setProgress(doneTimesH);

    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }


    class AllExerciseViewHolder extends RecyclerView.ViewHolder{
        private TextView exerciseName;
        private TextView timesDone;
        private RoundCornerProgressBar progressBar;
        public AllExerciseViewHolder(View itemView) {
            super(itemView);
            exerciseName = (TextView) itemView.findViewById(R.id.name_exercise_progress);
            progressBar = (RoundCornerProgressBar) itemView.findViewById(R.id.progress_bar);
            timesDone = (TextView) itemView.findViewById(R.id.times_combined);
        }
    }
}
