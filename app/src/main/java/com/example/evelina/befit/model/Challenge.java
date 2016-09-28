package com.example.evelina.befit.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Evelina on 9/24/2016.
 */

public class Challenge {
    enum Type {UPPERBODY,LOWERBODY,ABDOMEN,WHOLEBODY,CUSTOM};

    Type type;
    int timesCompleted;
    Date dateOfCompletion;
    ArrayList<Exercise> exercises;

    public Challenge(Type type, int timesCompleted, Date dateOfCompletion) {
        this.type = type;
        this.timesCompleted = timesCompleted;
        this.dateOfCompletion = dateOfCompletion;
        exercises = new ArrayList<Exercise>();
    }

    public void addExercise(Exercise exercise){
        if( exercise != null){
            exercises.add(exercise);
        }
    }
    public Type getType(){
        return this.type;
    }
}
