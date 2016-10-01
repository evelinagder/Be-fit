package com.example.evelina.befit;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by User on 30-Sep-16.
 */

public class SettingsAdapter  extends RecyclerView.Adapter<SettingsAdapter.SettingsViewHolder>{
    private List<String> settings;
    private SettingsActivity activity;

    class SettingsViewHolder extends RecyclerView.ViewHolder{
        protected TextView settingsTV;
        protected CardView row;
        public SettingsViewHolder(View itemView) {
            super(itemView);
            settingsTV = (TextView) itemView.findViewById(R.id.settings_TV);
            row= (CardView) itemView.findViewById(R.id.settings_row);
        }
    }

    public SettingsAdapter(Context context,List<String> settings){
        this.activity = (SettingsActivity) context;
        this.settings =settings;
    }

    @Override
    public SettingsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.settings_list_row, parent, false);
        return new SettingsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SettingsViewHolder holder, int position) {
        final String setting = settings.get(position);
        holder.settingsTV.setText(setting);
        holder.row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //here show dialog fragment for change
                //all should be done according to the current user data !
                DialogFragment fragment;
                Bundle bundle;
                switch (setting){
                    case "email":
                        //here we should take the current logged user email
                        //put it in bundle and set it to the dialog fragment
                        fragment = new SettingsEmailFragment();
                        bundle = new Bundle();
                        bundle.putString("email","ivet@abv.bg");
                        fragment.setArguments(bundle);
                        fragment.show(activity.getSupportFragmentManager(),"email");
                        break;
                    case "gender":
                        fragment = new SettingsGenderFragment();
                        bundle = new Bundle();
                        bundle.putString("gender","male");
                        fragment.setArguments(bundle);
                        fragment.show(activity.getSupportFragmentManager(),"gender");
                        break;
                    case "height":
                        fragment = new SettingsHeightFragment();
                        //probably we should take the current height if there is any and send it to the fragment
                        fragment.show(activity.getSupportFragmentManager(),"height");
                        break;
                    case "weight":
                        fragment = new SettingsWeightFragment();
                        fragment.show(activity.getSupportFragmentManager(),"weight");
                        break;

                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return settings.size();
    }
    public void showFrag(){

    }


}
