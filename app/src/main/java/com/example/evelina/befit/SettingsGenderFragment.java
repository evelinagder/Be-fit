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
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.evelina.befit.model.DbManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsGenderFragment extends DialogFragment {
    private RadioGroup mGenderRadioButton;
    private Button cancel;

    public SettingsGenderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings_gender, container, false);
        final String username = getContext().getSharedPreferences("Login", Context.MODE_PRIVATE).getString("currentUser", "no user");
        mGenderRadioButton = (RadioGroup) view.findViewById(R.id.radio_group);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if (getArguments().getString("gender") != null) {
            if (getArguments().getString("gender").equalsIgnoreCase("female")) {
                RadioButton v = (RadioButton) view.findViewById(R.id.female_gender);
                v.setChecked(true);
            } else {
                RadioButton v = (RadioButton) view.findViewById(R.id.male_gender);
                v.setChecked(true);
            }
        }
        cancel = (Button) view.findViewById(R.id.cancel_button_gender);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        mGenderRadioButton.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.female_gender) {
                    //here change the gender in DB
                    DbManager.getInstance(getContext()).updateUserGender(username, "female");
                } else {
                    DbManager.getInstance(getContext()).updateUserGender(username, "male");
                }
            }
        });

        return view;
    }

}
