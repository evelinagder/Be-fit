package com.example.evelina.befit.adapters;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.example.evelina.befit.CompletedTrainings;
import com.example.evelina.befit.R;
import com.example.evelina.befit.TrainingSpecifications;
import com.example.evelina.befit.TrainingTypes;
import com.example.evelina.befit.model.Exercise;
import com.example.evelina.befit.model.TrainingManager;

import java.util.List;

/**
 * Created by User on 04-Oct-16.
 */

public class CompletedTrainingExpandableAdapter extends ExpandableRecyclerAdapter<CompletedTrainingExpandableAdapter.TrainingParentViewHolder,CompletedTrainingExpandableAdapter.TrainingChildViewHolder> {
    private CompletedTrainings activity;
    private LayoutInflater mInflater;
    private Typeface typeface;

    /**
     * Primary constructor. Sets up {@link #mParentItemList} and {@link #mItemList}.
     * <p>
     * Changes to {@link #mParentItemList} should be made through add/remove methods in
     * {@link ExpandableRecyclerAdapter}
     *
     * @param parentItemList List of all {@link ParentListItem} objects to be
     *                       displayed in the RecyclerView that this
     *                       adapter is linked to
     *
     *
     */
    public CompletedTrainingExpandableAdapter(@NonNull List<? extends ParentListItem> parentItemList,CompletedTrainings activity) {
        super(parentItemList);
        this.activity =activity;
        mInflater = LayoutInflater.from(activity);
        typeface = Typeface.createFromAsset(activity.getAssets(),  "RockoUltraFLF.ttf");
    }

    @Override
    public TrainingParentViewHolder onCreateParentViewHolder(ViewGroup parentViewGroup) {
        //here we inflate the parent's xml
        View view = mInflater.inflate(R.layout.training_type_row, parentViewGroup, false);
        return new TrainingParentViewHolder(view);
    }

    @Override
    public TrainingChildViewHolder onCreateChildViewHolder(ViewGroup childViewGroup) {
        //here we inflate the child /when parent expands/
        View view = mInflater.inflate(R.layout.list_item_training_child, childViewGroup, false);
        return new TrainingChildViewHolder(view);
    }

    @Override
    public void onBindParentViewHolder(final TrainingParentViewHolder parentViewHolder, int position, ParentListItem parentListItem) {
        //here we set the name of the challenge
        TrainingTypes nameTraining = (TrainingTypes) parentListItem;
        parentViewHolder.trainingName.setText(nameTraining.getName());
        parentViewHolder.trainingName.setTextColor(Color.WHITE);
        parentViewHolder.trainingName.setTextSize(20);
        parentViewHolder.trainingName.setTypeface(typeface);
        parentViewHolder.cardRow.setBackgroundColor(Color.argb(10,6,214,160));
    }

    @Override
    public void onBindChildViewHolder(TrainingChildViewHolder childViewHolder, int position, Object childListItem) {
        //here we set the times for completion of the challenge and the last day of completion
        TrainingSpecifications trainingSpecifications = (TrainingSpecifications) childListItem;
        childViewHolder.timesCompletionTrainingTV.setText(trainingSpecifications.getmTimesCompleted()+"");
        childViewHolder.dateCompletionTrainingTV.setText(trainingSpecifications.getmDateLastCompletion());
        List<String> exercisesNames = trainingSpecifications.getExercisesName();
        ChildRecyclerAdapterCompletedTrainings childRecyclerAdapterCompletedTrainings = new ChildRecyclerAdapterCompletedTrainings(exercisesNames,activity);
        childViewHolder.exercisesPerTrainingRV.setAdapter(childRecyclerAdapterCompletedTrainings);
        childViewHolder.exercisesPerTrainingRV.setLayoutManager(new LinearLayoutManager(activity));
        childViewHolder.completedTimeLayout.setBackgroundColor(Color.argb(10,6,214,160));
        childViewHolder.completedTrainingsLayout.setBackgroundColor(Color.argb(10,6,214,160));
    }

    public class TrainingChildViewHolder extends ChildViewHolder{
        public TextView dateCompletionTrainingTV;
        public TextView timesCompletionTrainingTV;
        public RecyclerView exercisesPerTrainingRV;
        public LinearLayout completedTimeLayout;
        public LinearLayout completedTrainingsLayout;

        /**
         * Default constructor.
         *
         *
         * @param itemView The {@link View} being hosted in this ViewHolder
         */
        public TrainingChildViewHolder(View itemView) {
            super(itemView);
            dateCompletionTrainingTV = (TextView) itemView.findViewById(R.id.date_completion_training);
            timesCompletionTrainingTV = (TextView) itemView.findViewById(R.id.times_completion_training);
            exercisesPerTrainingRV = (RecyclerView) itemView.findViewById(R.id.recycler_view_completed_exercises_challenge);
            completedTimeLayout = (LinearLayout) itemView.findViewById(R.id.layout_completed_info);
            completedTrainingsLayout = (LinearLayout) itemView.findViewById(R.id.layout_completed_info_2);

        }

    }
    public class TrainingParentViewHolder extends ParentViewHolder{
        public TextView trainingName;
        public CardView cardRow;

        /**
         * Default constructor.
         *
         * @param itemView The {@link View} being hosted in this ViewHolder
         */
        public TrainingParentViewHolder(View itemView) {
            super(itemView);
            cardRow = (CardView) itemView.findViewById(R.id.card_row);
            trainingName = (TextView) itemView.findViewById(R.id.training_name);
        }
    }
}
