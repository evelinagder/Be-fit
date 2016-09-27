package com.example.evelina.befit.model;

/**
 * Created by Evelina on 9/27/2016.
 */
public class ExerciseManager {
    private static ExerciseManager ourInstance = new ExerciseManager();

    public static ExerciseManager getInstance() {
        return ourInstance;
    }

    private ExerciseManager() {
    }

}
