package com.example.evelina.befit;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SetsRepeatsDialogFragment extends DialogFragment {
    TextView setsTV;
    TextView repeatsTV;
    EditText setsET;
    EditText repeatsET;
    Button cancelButton;
    Button okButton;
    TextView heading;


    public SetsRepeatsDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sets_repeats_dialog, container, false);
        String exerciseName = getArguments().getString("name");
        setsTV = (TextView) view.findViewById(R.id.sets_TV);
        repeatsTV = (TextView) view.findViewById(R.id.repeats_TV);
        setsET = (EditText) view.findViewById(R.id.sets_ET);
        repeatsET = (EditText) view.findViewById(R.id.repeats_ET );
        cancelButton = (Button) view.findViewById(R.id.cancel_button);
        okButton = (Button) view.findViewById(R.id.ok_button);
        heading = (TextView) view.findViewById(R.id.heading);
        if(exerciseName!=null){
            heading.setText(exerciseName);
        }
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setsET.setText("");
                repeatsET.setText("");
                dismiss();
            }
        });
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sets=setsET.getText().toString();
                String repeats = repeatsET.getText().toString();
                if(sets.isEmpty()){
                    setsET.setError("Please fill in");
                    setsET.requestFocus();
                    return;
                }
                if(repeats.isEmpty()){
                    repeatsET.setError("Please fill in");
                    repeatsET.requestFocus();
                    return;
                }
                int setsH;
                int repeatsH;
                try{
                    setsH=Integer.parseInt(sets);
                }catch (NumberFormatException e){
                    Log.e("TAG",e.getMessage());
                    setsET.setText("");
                    setsET.setError("Wrong format");
                    setsET.requestFocus();
                    return;
                }
                try{
                    repeatsH=Integer.parseInt(repeats);
                }catch (NumberFormatException e){
                    Log.e("TAG",e.getMessage());
                    repeatsET.setText("");
                    repeatsET.setError("Wrong format");
                    repeatsET.requestFocus();
                    return;
                }
                Toast.makeText(getContext(), "Here exercise is added", Toast.LENGTH_SHORT).show();
                //here we should have the numbers and add them to the challenge

            }
        });
        return view;
    }

}
