package com.example.evelina.befit.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.evelina.befit.R;

import java.util.List;

/**
 * Created by User on 04-Oct-16.
 */

public class ChildRecyclerAdapterCompletedTrainings extends RecyclerView.Adapter<ChildRecyclerAdapterCompletedTrainings.NameExerciseVH>{
    List<String> namesOfExercises ;
    Context context ;

    public ChildRecyclerAdapterCompletedTrainings(List<String> namesOfExercises, Context context) {
        this.namesOfExercises = namesOfExercises;
        this.context = context;
    }

    @Override
    public NameExerciseVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.plain_exercise_name_row, parent, false);
        return new NameExerciseVH(itemView);

    }

    @Override
    public void onBindViewHolder(NameExerciseVH holder, int position) {
        holder.exercises.setText(namesOfExercises.get(position));

    }

    @Override
    public int getItemCount() {
        return namesOfExercises.size();
    }

    public  class NameExerciseVH extends RecyclerView.ViewHolder{
        TextView exercises;
        public NameExerciseVH(View itemView) {
            super(itemView);
            exercises = (TextView) itemView.findViewById(R.id.plain_exercise_name);
        }
    }
}
