package com.example.evelina.befit.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Evelina on 9/27/2016.
 */
public class TrainingManager {
    ArrayList<Challenge> basicChalenges;
    ArrayList<Exercise> allExercises;
    private static TrainingManager ourInstance = new TrainingManager();

    public static TrainingManager getInstance() {
        return ourInstance;
    }

    private TrainingManager() {
        basicChalenges=new ArrayList<Challenge>();
        Challenge abdomen= new Challenge(Challenge.Type.ABDOMEN,0, null);
        Challenge upperBody= new Challenge(Challenge.Type.UPPERBODY,0,null);
        Challenge lowerBody= new Challenge(Challenge.Type.LOWERBODY,0,null);
        Challenge wholeBody= new Challenge(Challenge.Type.WHOLEBODY,0,null);
        //add exercise:
        //one.addExercise(new Exercise(....)); 5 exercises!
        basicChalenges.add(abdomen);
        basicChalenges.add(upperBody);
        basicChalenges.add(lowerBody);
        basicChalenges.add(wholeBody);


    }

    public ArrayList<Challenge> getBasicChalenges(){

        return basicChalenges;
    }
    public ArrayList<Exercise> getAllExercises(){

        return allExercises;
    }

}
