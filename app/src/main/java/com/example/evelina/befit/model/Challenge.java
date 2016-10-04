package com.example.evelina.befit.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Evelina on 9/24/2016.
 */

public class Challenge {


    private String name;
    private int timesCompleted;
    private String dateOfCompletion;
    private String achieved;
    ArrayList<Exercise> exercises;

    public Challenge(String name, int timesCompleted, String dateOfCompletion) {
        this.name=name;
        this.timesCompleted = timesCompleted;
        this.dateOfCompletion = dateOfCompletion;
        exercises = new ArrayList<Exercise>();
    }

    public void addExercise(Exercise exercise){
        if( exercise != null){
            exercises.add(exercise);
        }
    }

    public String getName() {
        return name;
    }

    public void setAchieved(String achieved) {
        this.achieved = achieved;
    }

    public void setTimesCompleted(int timesCompleted) {
        this.timesCompleted = timesCompleted;
    }

    public void setDateOfCompletion(String dateOfCompletion) {
        this.dateOfCompletion = dateOfCompletion;
    }
}
