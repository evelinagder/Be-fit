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

    public static synchronized TrainingManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new TrainingManager();
        }
        return ourInstance;
    }


    private TrainingManager() {
        basicChallenges = new ArrayList<Challenge>();
        allExercises = new ArrayList<Exercise>();
        Challenge abdomen = new Challenge("ABS", 0, null, "no");
        Challenge upperBody = new Challenge("UPPERBODY", 0, null, "no");
        Challenge lowerBody = new Challenge("LOWERBODY", 0, null, "no");
        Challenge wholeBody = new Challenge("WHOLEBODY", 0, null, "no");
        //ABS
        Exercise absOne = new Exercise("Plate cocoon", 10, 2, 10, "Knees remain bent, fully extends arms above the chest, move the weights and feet simultaneously to the floor", "GpIx-Lowkh8");
        Exercise absTwo = new Exercise("Plate sit up", 10, 2, 15, "Lay back on the floor, arms vertical to the floor.Keep the weight above you and lift your upperbody off the floor", "rBLLfvmNIIs");
        Exercise absTree = new Exercise("Plate twist", 10, 3, 14, "Lean back with feet off the floor. Move the weight to either side of your body", "vcw9O-Cc1LY");
        Exercise absFour = new Exercise("Planks", 10, 2, 13, "Take push-up position and hold your body straight for 20 seconds.", "pSHjTRCQxIw");
        Exercise absFive = new Exercise("Bicycle crunch", 10, 3, 15, "Lie on your back with your hands behind your head, and your legs raised and bent at 90 degrees. Alternate sides by bringing your right elbow towards your left knee then your left elbow towards your right knee", "9FGilxCbdz8");
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
        Exercise upOne = new Exercise("Tricep Extensions", 10, 3, 12, "Lay on a bench, place the weight with an overhand grip and keep the upper arms vertical, fully extend them upward.", "nRiJVZDpdL0");
        Exercise upTwo = new Exercise("Upright Row", 10, 2, 13, "Take the bar from standing position and pull up with your elbows.", "amCU-ziHITM");
        Exercise upTree = new Exercise("Bench press", 10, 3, 11, "Lay on a bench and lift the weight up, while keeping your elbows close to the body.", "gRVjAtPip0Y&spfreload=10");
        Exercise upFour = new Exercise("Arnold Shoulder Press", 10, 2, 15, "Sit on a bench with back support and hold two weights. Raise the weights as you rotate the palms of your hands until they are facing forward, lower the weight by rotating the palms of your hands towards you. ", "6Z15_WdXmVw");
        Exercise upFive = new Exercise("Burpee", 10, 3, 12, "Drop into a squat position with your hands on the ground,Kick your feet back into a plank positionImmediately return your feet to the squat position and jump up.", "JZQA08SlJnM");
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
        Exercise lowOne = new Exercise("Back squat", 10, 3, 12, "Put the bar behind your neck and bend at the knees and hips.", "Dy28eq2PjcM");
        Exercise lowTwo = new Exercise("Bodyweight DeadLift", 10, 2, 15, "Slide your hands down your legs until uor fingers touch the top of your shoes.", "hJn2UPaicZc");
        Exercise lowTree = new Exercise("Bodyweight Lunge", 10, 3, 10, "With rear knee touching the floor, lunge forward and hold the bottom position", "rKh41FO_eao");
        Exercise lowFour = new Exercise("Plié Squat", 10, 2, 12, "Stand tall, feet slightly wider than shoulder-width apart, toes pointed out at 45-degree angles,make deep squat and return to standing position", "yNKVK2axeNI");
        Exercise lowFive = new Exercise("Hip trusters", 10, 3, 15, "Sit so that the bench is behind you and start lifting your hips until they are vertical to the bench.", "RF936hv-GiA");
        lowerBody.addExercise(lowOne);
        lowerBody.addExercise(lowTwo);
        lowerBody.addExercise(lowTree);
        lowerBody.addExercise(lowFour);
        lowerBody.addExercise(lowFive);
        allExercises.add(lowOne);
        allExercises.add(lowTwo);
        allExercises.add(lowTree);
        allExercises.add(lowFour);
        allExercises.add(lowFive);
        //WHOLEBODY

        wholeBody.addExercise(absOne);
        wholeBody.addExercise(upFour);
        wholeBody.addExercise(lowFive);
        wholeBody.addExercise(absFour);
        wholeBody.addExercise(upFive);


        basicChallenges.add(abdomen);
        basicChallenges.add(upperBody);
        basicChallenges.add(lowerBody);
        basicChallenges.add(wholeBody);


    }

    public ArrayList<Challenge> getBasicChallenges() {

        return basicChallenges;
    }

    // for loading in exercise Inventory
    public ArrayList<Exercise> getAllExercises() {

        return allExercises;
    }

    public List<String> getBasicChallengesName() {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < basicChallenges.size(); i++) {
            list.add(basicChallenges.get(i).getName());
        }
        return list;

    }

    public List<String> getAllExercisesName() {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < allExercises.size(); i++) {
            list.add(allExercises.get(i).getName());
        }
        return list;
    }

    public Challenge getBasicChallenges(String name) {
        Challenge c = null;
        for (int i = 0; i < basicChallenges.size(); i++) {
            if (name.equals(basicChallenges.get(i).getName())) {
                c = basicChallenges.get(i);
            }
        }
        return c;
    }

    public Exercise getExercise(String name) {
        Exercise e = null;
        for (int i = 0; i < allExercises.size(); i++) {
            if (name.equals(allExercises.get(i).getName())) {
                e = allExercises.get(i);
            }
        }
        return e;
    }


}
