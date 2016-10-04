package com.example.evelina.befit;


import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import com.example.evelina.befit.model.Exercise;

import java.util.List;

/**
 * Created by User on 03-Oct-16.
 */

public class ExerciseRecyclerAdapter extends RecyclerView.Adapter<ExerciseRecyclerAdapter.ExerciseViewHolder>{
    private ExerciseInventoryActivity activity;
    private List<Exercise> exercises;
    private RecyclerView mRecyclerView;
    private final View.OnClickListener customOnclickListener = new CustomOnclickListener();


    public ExerciseRecyclerAdapter(ExerciseInventoryActivity activity, List<Exercise> exercises,RecyclerView mRecyclerView) {
        this.activity = activity;
        this.exercises = exercises;
        this.mRecyclerView = mRecyclerView;
    }

    @Override
    public ExerciseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_list_row,parent,false);
        return new ExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ExerciseViewHolder holder, final int position) {

        holder.nameExercise.setText( exercises.get(position).getName());
        holder.descriptionExercise.setText(exercises.get(position).getInstructions());
        holder.addedCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.addedCheckbox.isChecked()){
                    //here we should check if we
                    Log.e("TAG","clicked " + exercises.get(position).getName());
                    SetsRepeatsDialogFragment fragment = new SetsRepeatsDialogFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("name", exercises.get(position).getName());
                    bundle.putInt("position",position);
                    fragment.setArguments(bundle);
                    fragment.show(activity.getSupportFragmentManager(),"sets");
                }else{
                    holder.addedCheckbox.setSelected(false);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    class ExerciseViewHolder extends RecyclerView.ViewHolder{
        private TextView nameExercise ;
        private TextView descriptionExercise;
        private CheckBox addedCheckbox;

         public ExerciseViewHolder(View itemView) {
             super(itemView);
             nameExercise = (TextView) itemView.findViewById(R.id.exercise_name_TV);
             descriptionExercise = (TextView) itemView.findViewById(R.id.exercise_description_TV);
             addedCheckbox = (CheckBox) itemView.findViewById(R.id.checkbox_added_exercises);
         }
     }


    public  class CustomOnclickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int itemPosition = mRecyclerView.getChildLayoutPosition(v);
        }
    }
}
