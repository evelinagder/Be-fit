package com.example.evelina.befit;


import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.evelina.befit.model.DbManager;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class TimePickerNotificationFragment extends DialogFragment  {
    private TimePicker mTimePicker;
    private Button mOkButton;
    private Button mCancelButton;
  //  public    int hour,minutes;



    public TimePickerNotificationFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_time_picker_notification, container, false);

        final Calendar c = Calendar.getInstance();


        mTimePicker = (TimePicker) view.findViewById(R.id.time_picker);
        mTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int hour, int minutes) {
                c.set(Calendar.HOUR_OF_DAY,hour);
                c.set(Calendar.MINUTE,minutes);
            }
        });
        mOkButton = (Button) view.findViewById(R.id.ok_alarm);
        mOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), " hour" + c.get(Calendar.HOUR_OF_DAY) +" minutes "+ c.get(Calendar.MINUTE), Toast.LENGTH_SHORT).show();
                //here we have the alarm time and we set the alarm
                if(getArguments().get("monday")!=null){
                    c.set(Calendar.DAY_OF_WEEK, (Integer) getArguments().get("monday"));
                    long time=c.getTimeInMillis();
                     boolean isRepeating= (boolean) getArguments().get("isRepeating");
                    String username= getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE).getString("currentUser", "no Users");
                     DbManager.getInstance(getContext()).saveNotifications(username,time,isRepeating,getContext());


                }

            }
        });
        mCancelButton = (Button) view.findViewById(R.id.cancel_alarm);
        mCancelButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              dismiss();
          }
      });



        return view;
    }

}
