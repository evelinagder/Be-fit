package com.example.evelina.befit.model;

import android.app.Activity;

import com.example.evelina.befit.TabbedActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Evelina on 9/27/2016.
 */
public class TrainingManager {
    private static TrainingManager ourInstance;
    ArrayList<Challenge> basicChallenges;
    ArrayList<Exercise> allExercises;
    public static TrainingManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new TrainingManager();
        }
        return ourInstance;
    }


    private TrainingManager() {
        basicChallenges=new ArrayList<Challenge>();
        allExercises= new ArrayList<Exercise>();
        Challenge abdomen= new Challenge("ABDOMEN",0, null);
        Challenge upperBody= new Challenge("UPPERBODY",0,null);
        Challenge lowerBody= new Challenge("LOWERBODY",0,null);
        Challenge wholeBody= new Challenge("WHOLEBODY",0,null);
        //TODO ADD REAL VIDEO ID!!!!!

        //ABS
        Exercise absOne=new Exercise("Plate cocoon",10,2,10,"Knees remain bent, fully extends arms above the chest, move the weights and feet simultaneously to the floor",12);
        Exercise absTwo =new Exercise("Plate sit up",10,2,15,"Lay back on the floor, arms vertical to the floor.Keep the weight abe you and lift your shoulders off the floor",12);
        Exercise absTree=new Exercise("Plate twist",10,2,10,"Lean back with feet off the floor. Move the weight to either side of your body",12);
        Exercise absFour=new Exercise("Planks",10,2,0,"Take push-up position and hold your body straight for 20 seconds.",12);
        Exercise absFive=new Exercise("Bicycle crunch",10,3,15,"Lie on your back with your hands behind your head, and your legs raised and bent at 90 degrees. Alternate sides by bringing your right elbow towards your left knee then your left elbow towards your right knee",12);
        abdomen.addExercise(absOne);
        abdomen.addExercise(absTwo);
        abdomen.addExercise(absTree);
        abdomen.addExercise(absFour);
        abdomen.addExercise(absFive);
        allExercises.add(absOne);
        allExercises.add(absTwo);
        allExercises.add(absTree);
        allExercises.add(absFour);
        allExercises.add(absFive);
//UPPERBODY
        Exercise upOne=new Exercise("Tricep Extensions",10,3,12,"Lay on a bench, place the weight with an overhand grip and keep the upper arms vertical, fully extend them upward.",12);
        Exercise upTwo=new Exercise("Upright Row",10,3,12,"Take the bar from standing position and pull up with your elbows.",12);
        Exercise upTree=new Exercise("Bench press",10,3,12,"Lay on a bench and lift the weight up, while keeping your elbows close to the body.",12);
        Exercise upFour=new Exercise("Arnold Shoulder Press",10,3,12,"Sit on a bench with back support and hold two weights. Raise the weights as you rotate the palms of your hands until they are facing forward, lower the weight by rotating the palms of your hands towards you. ",12);
        Exercise upFive=new Exercise("Burpee",10,3,12,"Drop into a squat position with your hands on the ground,Kick your feet back into a plank positionImmediately return your feet to the squat position and jump up.",12);
        upperBody.addExercise(upOne);
        upperBody.addExercise(upTwo);
        upperBody.addExercise(upTree);
        upperBody.addExercise(upFour);
        upperBody.addExercise(upFive);
        allExercises.add(upOne);
        allExercises.add(upTwo);
        allExercises.add(upTree);
        allExercises.add(upFour);
        allExercises.add(upFive);
        //LOWERBODY
        Exercise lowOne=new Exercise("Back squat",10,3,12,"Put the bar behind your neck and bend at the knees and hips.",12);
        Exercise lowTwo=new Exercise("Bodyweight DeadLift",10,2,15,"Slide your hands down your legs until uor fingers touch the top of your shoes.",12);
        Exercise lowTree=new Exercise("Bodyweight Lunge",10,2,10,"With rear knee touching the floor, lunge forward and hold the bottom position",12);
        Exercise lowFour=new Exercise("Plié Squat",10,3,12,"Stand tall, feet slightly wider than shoulder-width apart, toes pointed out at 45-degree angles,make deep squat and return to standing position",12);
        Exercise lowFive=new Exercise("Plank Leg Lift",10,3,15,"Get into a high plank position.Tap your left leg out to the side, then back to starting position,whitch legs.",12);
        upperBody.addExercise(lowOne);
        upperBody.addExercise(lowTwo);
        upperBody.addExercise(lowTree);
        upperBody.addExercise(lowFour);
        upperBody.addExercise(lowFive);
        allExercises.add(lowOne);
        allExercises.add(lowTwo);
        allExercises.add(lowTree);
        allExercises.add(lowFour);
        allExercises.add(lowFive);
        //WHOLEBODY

        upperBody.addExercise(absOne);
        upperBody.addExercise(upFour);
        upperBody.addExercise(lowFive);
        upperBody.addExercise(absFour);
        upperBody.addExercise(upFive);


        basicChallenges.add(abdomen);
        basicChallenges.add(upperBody);
        basicChallenges.add(lowerBody);
        basicChallenges.add(wholeBody);


    }

    public ArrayList<Challenge> getBasicChallenges(){

        return basicChallenges;
    }

    // for loading in exercise Inventory
    public ArrayList<Exercise> getAllExercises(){

        return allExercises;
    }

    public List<String> getBasicChallengesName(){
        List<String> list= new ArrayList<String>();
        for(int i = 0; i<basicChallenges.size();i++){
            list.add(basicChallenges.get(i).getName());
        }
        return list;

    }
    public List<String> getAllExercisesName(){
        List<String> list = new ArrayList<String>();
        for(int i = 0;i<allExercises.size();i++){
            list.add(allExercises.get(i).getName());
        }
        return list;
    }

}
