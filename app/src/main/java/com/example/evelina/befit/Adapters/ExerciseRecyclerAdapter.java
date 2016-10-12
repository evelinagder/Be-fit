package com.example.evelina.befit.adapters;


import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.evelina.befit.ExerciseInventoryActivity;
import com.example.evelina.befit.R;
import com.example.evelina.befit.model.Challenge;
import com.example.evelina.befit.model.DbManager;
import com.example.evelina.befit.model.Exercise;
import com.example.evelina.befit.model.TrainingManager;
import com.example.evelina.befit.model.User;

import java.util.List;

/**
 * Created by User on 03-Oct-16.
 */

public class ExerciseRecyclerAdapter extends RecyclerView.Adapter<ExerciseRecyclerAdapter.ExerciseViewHolder>{
    private ExerciseInventoryActivity activity;
    private List<Exercise> exercises;
    private RecyclerView mRecyclerView;
    private String challengeName;
    private String username;
    private User user;
    private final View.OnClickListener customOnclickListener = new CustomOnclickListener();


    public ExerciseRecyclerAdapter(ExerciseInventoryActivity activity, List<Exercise> exercises,RecyclerView mRecyclerView,String challengeName,String username) {
        this.activity = activity;
        this.exercises = exercises;
        this.mRecyclerView = mRecyclerView;
        this.challengeName=challengeName;
        this.username=username;
        user=DbManager.getInstance(activity).getUser(username);

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
       holder.dataSetRow.setVisibility(View.GONE);
        holder.okButton.setVisibility(View.GONE);
        holder.okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sets=holder.setsET.getText().toString();
                String repeats = holder.repeatsET.getText().toString();
                if(sets.isEmpty()){
                    holder.setsET.setError("Please fill in!");
                    holder.setsET.requestFocus();
                    return;
                }
                if(repeats.isEmpty()){
                    holder.repeatsET.setError("Please fill in!");
                    holder.repeatsET.requestFocus();
                    return;
                }

                int setsH= Integer.parseInt(holder.setsET.getText().toString());
                int repeatsH=Integer.parseInt(holder.repeatsET.getText().toString());
                if(repeatsH==0){
                    holder.repeatsET.setError("Repeats should be greater than 0 to add exercise");
                    holder.repeatsET.setText("");
                    holder.repeatsET.requestFocus();
                    return;
                }
                if(setsH==0){
                    holder.setsET.setError("Sets should be greater than 0 to add exercise");
                    holder.setsET.setText("");
                    holder.setsET.requestFocus();
                    return;
                }
                Exercise exercise= TrainingManager.getInstance().getExercise( exercises.get(position).getName());
                exercise.setRepeats(repeatsH);
                exercise.setSeries(setsH);
                exercise.setPoints(setsH*repeatsH);
                Challenge challenge=user.getCustomChallenges(challengeName);
                DbManager.getInstance(activity).addExercisesToCustomChallenge(username,challenge.getName(),exercise);
                holder.dataSetRow.setVisibility(View.GONE);
                holder.okButton.setVisibility(View.GONE);

            }
        });

        holder.addedCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.addedCheckbox.isChecked()){
                    holder.dataSetRow.setVisibility(View.VISIBLE);
                    holder.okButton.setVisibility(View.VISIBLE);
                    Log.e("TAG","clicked " + exercises.get(position).getName());


                }else{
                    holder.addedCheckbox.setSelected(false);
                    holder.dataSetRow.setVisibility(View.GONE);
                    holder.okButton.setVisibility(View.GONE);

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
        private EditText setsET,repeatsET;
        private LinearLayout dataSetRow;
        Button okButton;
        String username;
        String challengeName;

         public ExerciseViewHolder(View itemView) {
             super(itemView);
             nameExercise = (TextView) itemView.findViewById(R.id.exercise_name_TV);
             descriptionExercise = (TextView) itemView.findViewById(R.id.exercise_description_TV);
             addedCheckbox = (CheckBox) itemView.findViewById(R.id.checkbox_added_exercises);
             dataSetRow = (LinearLayout) itemView.findViewById(R.id.sets_data_row);
             setsET=(EditText)itemView.findViewById(R.id.sets_EditText);
             repeatsET=(EditText)itemView.findViewById(R.id.repeats_EditText);
             username = activity.getUsername();
             challengeName=activity.getChallengeName();
             okButton=(Button)itemView.findViewById(R.id.OK_EX_card);
         }

     }


    public  class CustomOnclickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int itemPosition = mRecyclerView.getChildLayoutPosition(v);
        }
    }

}
