package com.example.evelina.befit;


import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.evelina.befit.model.DbManager;

import java.util.Calendar;


import static android.content.Context.ALARM_SERVICE;



public class NotificationsFragment extends DialogFragment {

    private CheckBox mRepeatCheckbox;
    private Button mTurnOff;
    private CheckBox mMonday;
    private CheckBox mTuesday;
    private CheckBox mWednesday;
    private CheckBox mThursday;
    private CheckBox mFriday;
    private CheckBox mSaturday;
    private CheckBox mSunday;
    private Button mOk;
    private SettingsActivity activity;

    Bundle data;


    public NotificationsFragment() {
        // Required empty public constructor

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (SettingsActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  =  inflater.inflate(R.layout.fragment_notifications, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        //getDialog().setTitle("Set app notifications"); REMOVED IT BECAUSE IT COULD`T FIT>>>>>>
        data = new Bundle();

        mRepeatCheckbox = (CheckBox) view.findViewById(R.id.repeat_checkbox);
        mTurnOff = (Button) view.findViewById(R.id.turn_off_button);
        mMonday = (CheckBox) view.findViewById(R.id.monday);
        mMonday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    data.putInt("monday", Calendar.MONDAY);

                }
            }
        });
        mTuesday = (CheckBox) view.findViewById(R.id.tuesday);
        mTuesday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    data.putInt("tuesday",Calendar.TUESDAY);
                }

            }
        });
        mWednesday = (CheckBox) view.findViewById(R.id.wednesday);
        mWednesday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    data.putInt("wednesday",Calendar.WEDNESDAY);
                }
            }
        });
        mThursday = (CheckBox) view.findViewById(R.id.thursday);
        mThursday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    data.putInt("thursday",Calendar.THURSDAY);
                }
            }
        });
        mFriday = (CheckBox) view.findViewById(R.id.friday);
        mFriday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    data.putInt("friday",Calendar.FRIDAY);
                }
            }
        });
        mSaturday = (CheckBox) view.findViewById(R.id.saturday);
        mSaturday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    data.putInt("saturday",Calendar.SATURDAY);
                }
            }
        });
        mSunday = (CheckBox) view.findViewById(R.id.sunday);
        mSunday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    data.putInt("sunday",Calendar.SUNDAY);
                }
            }
        });
        mOk = (Button) view.findViewById(R.id.ok_notifications);



        mOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //here we pass data to the activity and show another dialog fragment for time
                if(mRepeatCheckbox.isChecked()){
                    data.putBoolean("isRepeating",true);
                }else {
                    data.putBoolean("isRepeating",false);
                }
                dismiss();
                if(!mMonday.isChecked() && !mTuesday.isChecked() && !mWednesday.isChecked() && !mThursday.isChecked()&& !mFriday.isChecked() && !mSaturday.isChecked() && !mSunday.isChecked()){
                    Toast.makeText(getActivity(),"Please choose a weekday!",Toast.LENGTH_SHORT).show();
                }else {
                    activity.showFragment(data);
                }

            }
        });


        mTurnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMonday.setChecked(false);
                mTuesday.setChecked(false);
                mWednesday.setChecked(false);
                mThursday.setChecked(false);
                mFriday.setChecked(false);
                mSaturday.setChecked(false);
                mSunday.setChecked(false);
                mRepeatCheckbox.setChecked(false);
                DbManager.getInstance(getContext()).cancelAlarms(getContext());
                Toast.makeText(getContext(),"All notifications are deleted!",Toast.LENGTH_SHORT).show();
                dismiss();
            }
        } );


        return view;
    }



}