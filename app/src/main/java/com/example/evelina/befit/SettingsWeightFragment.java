package com.example.evelina.befit;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;

import com.example.evelina.befit.model.DbManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsWeightFragment extends DialogFragment {
    private NumberPicker mWeightsPicker;
    private Button mOkButton;


    public SettingsWeightFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_settings_weight, container, false);
        mWeightsPicker = (NumberPicker) view.findViewById(R.id.weight_picker);
        mOkButton = (Button) view.findViewById(R.id.weight_ok_button);
        getDialog().setTitle("Set weight");
        mWeightsPicker.setMinValue(40);
        mWeightsPicker.setMaxValue(200);
        mWeightsPicker.setWrapSelectorWheel(false);
        String [] values = new String[201];
        for(int i = 0 ;i<=200;i++){
            values[i]=Integer.toString(i);
        }

        mOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int weight = mWeightsPicker.getValue();
                //here we have the weight and should update it in the DB

                String  username = getContext().getSharedPreferences("Login", Context.MODE_PRIVATE).getString("currentUser","no user");
                DbManager.getInstance(getContext()).changeUserWeight(username,weight);
                dismiss();
            }
        });
        return view;
    }

}
