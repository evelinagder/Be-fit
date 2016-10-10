package com.example.evelina.befit.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.evelina.befit.R;
import com.example.evelina.befit.TabbedActivity;

import java.util.List;

/**
 * Created by Evelina on 10/2/2016.
 */

public class CustomTrainingRecyclerAdapter extends RecyclerView.Adapter<CustomTrainingRecyclerAdapter.CustomVH> {

private TabbedActivity activity;
private List<String> customChallenges;


        CustomTrainingRecyclerAdapter(TabbedActivity activity, List<String> customList) {
        customChallenges = customList;
        this.activity = activity;
        }

@Override
public CustomVH onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View row = inflater.inflate(R.layout.custom_training_list_row, parent, false);
        row.findViewById(R.id.custom_row_layout).setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return new CustomVH(row);
        }

@Override
public void onBindViewHolder(CustomVH holder, final int position) {
        holder.name.setText(customChallenges.get(position).toString());
        //holder.name.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        holder.name.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
    if(customChallenges.get(position).toString() != null) {
        Toast.makeText(activity, "Training selected: " + customChallenges.get(position).toString(), Toast.LENGTH_SHORT).show();
        activity.ChallengeSelected(customChallenges.get(position),false);
    }
    else{
        Toast.makeText(activity, "You don`t have any custom trainings!", Toast.LENGTH_SHORT).show();
    }

        }
        });
        }

@Override
public int getItemCount() {
        return customChallenges.size();
        }

class CustomVH extends RecyclerView.ViewHolder {

    TextView name;

    public CustomVH(View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.custom_training_name);
    }
 }
}



