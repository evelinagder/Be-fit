package com.example.evelina.befit;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.evelina.befit.model.DbManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsHeightFragment extends DialogFragment {
    private NumberPicker mMetersPicker;
    private NumberPicker mSantimetersPicker;
    private Button mOkButton;

    public SettingsHeightFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings_height, container, false);
        mMetersPicker = (NumberPicker) view.findViewById(R.id.meters_picker);
        mSantimetersPicker = (NumberPicker) view.findViewById(R.id.santimeters_picker);
        mOkButton = (Button) view.findViewById(R.id.height_ok_button);
        getDialog().setTitle("Height");
        mMetersPicker.setMinValue(1);
        mMetersPicker.setMaxValue(2);
        mSantimetersPicker.setMinValue(0);
        mSantimetersPicker.setValue(60);
        mSantimetersPicker.setMaxValue(99);
        String[] values = new String[100];
        for (int i = 0; i <= 99; i++) {
            values[i] = Integer.toString(i);
        }
        mSantimetersPicker.setDisplayedValues(values);
        mSantimetersPicker.setWrapSelectorWheel(false);
        mOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int meters = mMetersPicker.getValue();
                int santimeters = mSantimetersPicker.getValue();
                //here we will use meters and santimeters to update the DB
                String username = getContext().getSharedPreferences("Login", Context.MODE_PRIVATE).getString("currentUser", "no user");
                DbManager.getInstance(getContext()).updateUserHeight(username, ((meters * 100) + santimeters));
                dismiss();
            }
        });
        return view;
    }
}
