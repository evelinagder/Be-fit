package com.example.evelina.befit;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


public class ProfileFragment extends Fragment {
    ImageView profilePicture;
    TextView usernameTV;
    TextView numberPointsTV;
    TextView numberTrainingsTV;
    Button viewTrainingsButton;
    TextView kilogramsTV;
    TextView metersTV;
    IProfileCommunicator activity;
    FloatingActionButton fab;


//TODO here communicator interface and a class variable from this interface
    public interface IProfileCommunicator{
    //if action button is clicked alarm the activity
    //if view button is clicked alarm the activity

}
    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //TODO here if we have data should fill it in the fields
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        profilePicture = (ImageView) view.findViewById(R.id.picture_profile);


        numberPointsTV = (TextView) view.findViewById(R.id.number_points_profile_TV);
        numberTrainingsTV = (TextView) view.findViewById(R.id.number_trainings_profile_TV);
        viewTrainingsButton = (Button) view.findViewById(R.id.view_trainings_profile_Button);
        kilogramsTV = (TextView) view.findViewById(R.id.kg_profile_TV);
        metersTV = (TextView) view.findViewById(R.id.meters_profile_TV);
        fab= (FloatingActionButton) view.findViewById(R.id.show_chart);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to another activity whowing the chart ref. see snackbar?
            }
        });
        viewTrainingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //another actitivity displaying the exercise
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (IProfileCommunicator) context;
    }
}
