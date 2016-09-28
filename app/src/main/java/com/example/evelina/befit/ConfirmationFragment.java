package com.example.evelina.befit;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class ConfirmationFragment extends DialogFragment  {
    private Button continueButton;
    private Button finishButton;
    private IConfirmation activity;

    //here interface to communicate with the activity
    public interface IConfirmation{
        void finishSelected();
    }
    public ConfirmationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity= (IConfirmation) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_confirmation, container, false);
        continueButton= (Button) view.findViewById(R.id.continue_button);
        finishButton = (Button) view.findViewById(R.id.finish_button);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.finishSelected();
            }
        });
        return view;
    }

}
