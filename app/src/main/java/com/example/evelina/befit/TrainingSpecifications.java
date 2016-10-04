package com.example.evelina.befit;

/**
 * Created by User on 04-Oct-16.
 */


/**
 * Class for holding the challenge's times of completion and last day of completion.Needed for expandable recycler view.
 */
public class TrainingSpecifications  {
    private int mTimesCompleted ;
    private String mDateLastCompletion;


    public TrainingSpecifications(int mTimesCompleted, String mDateLastCompletion) {
        this.mTimesCompleted = mTimesCompleted;
        this.mDateLastCompletion = mDateLastCompletion;
    }


    public int getmTimesCompleted() {
        return mTimesCompleted;
    }

    public String getmDateLastCompletion() {
        return mDateLastCompletion;
    }
}
