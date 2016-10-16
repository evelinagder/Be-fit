package com.example.evelina.befit;


import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.evelina.befit.model.DbManager;


public class SettingsEmailFragment extends DialogFragment {

    private EditText mEmailET;
    private Button mButtonOk;
    private Button mButtonCancel;

    public SettingsEmailFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings_email, container, false);
        mEmailET = (EditText) view.findViewById(R.id.email_ET);
        mButtonOk = (Button) view.findViewById(R.id.email_ok_button);
        mButtonCancel = (Button) view.findViewById(R.id.email_cancel_button);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if (getArguments().getString("email") != null) {
            mEmailET.setText(getArguments().getString("email"));
        }
        mButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailPattern = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

                String newEmail = mEmailET.getText().toString().trim();
                if (newEmail.isEmpty()) {
                    mEmailET.setError("Please fill in");
                    mEmailET.requestFocus();
                    return;
                }
                if (!newEmail.matches(emailPattern)) {
                    mEmailET.setText("");
                    mEmailET.setError("Not a valid email");
                    mEmailET.requestFocus();
                    return;
                }
                //here we update the value in DBManager with the new email for the user
                String username = getContext().getSharedPreferences("Login", Context.MODE_PRIVATE).getString("currentUser", "no user");
                DbManager.getInstance(getContext()).changeUserEmail(username, newEmail);
                dismiss();
            }
        });

        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        return view;
    }
}
