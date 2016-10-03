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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.Calendar;


import static android.content.Context.ALARM_SERVICE;



public class NotificationsFragment extends DialogFragment {

    private CheckBox mRepeatCheckbox;
    private CheckBox mTurnOffCheckbox;
    private RadioButton mMonday;
    private RadioButton mTuesday;
    private RadioButton mWednesday;
    private RadioButton mThursday;
    private RadioButton mFriday;
    private RadioButton mSaturday;
    private RadioButton mSunday;
    private Button mOk;
    private SettingsActivity activity;

    Bundle data;
    int weekDay;

    private boolean isWeeklyChecked;



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
        getDialog().setTitle("Set app notifications");
        data = new Bundle();

        mRepeatCheckbox = (CheckBox) view.findViewById(R.id.repeat_checkbox);
        mTurnOffCheckbox = (CheckBox) view.findViewById(R.id.turn_off_checkbox);
        mMonday = (RadioButton) view.findViewById(R.id.monday);
        mMonday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    data.putInt("monday", Calendar.MONDAY);

                }
            }
        });
        mTuesday = (RadioButton) view.findViewById(R.id.tuesday);
        mTuesday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    data.putInt("tuesday",Calendar.TUESDAY);
                }
            }
        });
        mWednesday = (RadioButton) view.findViewById(R.id.wednesday);
        mWednesday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    data.putInt("wednesday",Calendar.WEDNESDAY);
                }
            }
        });
        mThursday = (RadioButton) view.findViewById(R.id.thursday);
        mThursday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    data.putInt("thursday",Calendar.THURSDAY);
                }
            }
        });
        mFriday = (RadioButton) view.findViewById(R.id.friday);
        mFriday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    data.putInt("friday",Calendar.FRIDAY);
                }
            }
        });
        mSaturday = (RadioButton) view.findViewById(R.id.saturday);
        mSaturday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    data.putInt("saturday",Calendar.SATURDAY);
                }
            }
        });
        mSunday = (RadioButton) view.findViewById(R.id.sunday);
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
                if(!mTurnOffCheckbox.isChecked()){
                  //  TimePickerNotificationFragment fragment = new TimePickerNotificationFragment();
                    //activity.getFragmentManager().popBackStackImmediate();
                    dismiss();
                 activity.showFragment(data);
                  //  activity.showTime();
                }else{
                    dismiss();
                }
            }
        });

        mRepeatCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){

                    data.putBoolean("isRepeating",true);
                }else{
                    data.putBoolean("isRepeating",false);

                    //set repeating for every week until the user turns it off

                }
            }
        });
        mTurnOffCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    //turrn off the alarm

                }else{
                    //triger the alarm

                }
            }
        });

        return view;
    }



}
