package com.example.evelina.befit;


import android.app.FragmentManager;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class TimePickerNotificationFragment extends DialogFragment {
    private TimePicker mTimePicker;
    private Button mOkButton;
    private Button mCancelButton;


    public TimePickerNotificationFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_time_picker_notification, container, false);
        mTimePicker = (TimePicker) view.findViewById(R.id.time_picker);
        mOkButton = (Button) view.findViewById(R.id.ok_alarm);
        mCancelButton = (Button) view.findViewById(R.id.cancel_alarm);

      mCancelButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              dismiss();
          }
      });
        mOkButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
              int hour=  mTimePicker.getHour();
                int minutes = mTimePicker.getMinute();
                dismiss();

            }
        });
        return view;
    }


}
