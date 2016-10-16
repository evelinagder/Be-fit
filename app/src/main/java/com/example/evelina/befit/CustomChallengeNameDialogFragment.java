package com.example.evelina.befit;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.evelina.befit.model.Challenge;
import com.example.evelina.befit.model.DbManager;
import com.example.evelina.befit.model.User;


/**
 * A simple {@link Fragment} subclass.
 */
public class CustomChallengeNameDialogFragment extends DialogFragment {
    private EditText challengeName;
    private Button ok, cancel;
    private User user;
    private String username;
    private  String name;


    public CustomChallengeNameDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_custom_challenge_name_dialog, container, false);
        challengeName = (EditText) view.findViewById(R.id.ET_challenge_name);
        challengeName.setImeOptions(EditorInfo.IME_ACTION_DONE);
        ok = (Button) view.findViewById(R.id.ok_button_Ch_name);
        cancel = (Button) view.findViewById(R.id.cancel_buttonCh_name);
        username = getArguments().getString("username");
        Log.e("USER", username);
        user = DbManager.getInstance(getActivity()).getUser(username);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = challengeName.getText().toString();
                if (name.isEmpty()) {
                    challengeName.setError("Please fill in!");
                    challengeName.requestFocus();
                    return;
                }
                Challenge customChallenge = new Challenge(name, 0, "", "no");
                DbManager.getInstance(getActivity()).addCustomChallenge(username, customChallenge);
                Intent intent = new Intent(getActivity(), ExerciseInventoryActivity.class);
                intent.putExtra("username", username);
                intent.putExtra("challengeName", name);
                startActivity(intent);
                dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                challengeName.setText("");
                dismiss();
            }
        });
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return view;
    }

}
