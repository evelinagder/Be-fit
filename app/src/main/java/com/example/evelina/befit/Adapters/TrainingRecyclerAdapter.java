package com.example.evelina.befit.adapters;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.evelina.befit.R;
import com.example.evelina.befit.TabbedActivity;

import java.util.List;

/**
 * Created by Evelina on 10/1/2016.
 */

public class TrainingRecyclerAdapter extends RecyclerView.Adapter<TrainingRecyclerAdapter.BasicVH> {
    private TabbedActivity activity;
    private List<String> challenges;
    boolean isBasic;


    public TrainingRecyclerAdapter(TabbedActivity activity, List<String> categoryList, boolean isBasic) {
        challenges = categoryList;
        this.activity = activity;
        this.isBasic = isBasic;
    }

    @Override
    public BasicVH onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View row = inflater.inflate(R.layout.basic_training_list_row, parent, false);
        row.findViewById(R.id.basic_row_layout).setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        return new BasicVH(row);
    }

    @Override
    public void onBindViewHolder(BasicVH holder, final int position) {
        holder.name.setText(challenges.get(position).toString());
        Typeface typeface = Typeface.createFromAsset(activity.getAssets(), "RockoUltraFLF.ttf");
        holder.name.setTypeface(typeface);
        holder.layoutCard.setBackgroundColor(Color.argb(10, 6, 214, 160));
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!challenges.get(position).equals("Please add your custom Training")) {
                    activity.ChallengeSelected(challenges.get(position), isBasic);
                } else {
                    Toast.makeText(activity, "You don`t have any custom trainings!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return challenges.size();
    }

    class BasicVH extends RecyclerView.ViewHolder {
        CardView layoutCard;
        TextView name;

        public BasicVH(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.training_name);
            layoutCard = (CardView) itemView.findViewById(R.id.card_view);
        }
    }
}
