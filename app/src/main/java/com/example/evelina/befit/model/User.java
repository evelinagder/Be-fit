package com.example.evelina.befit.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Evelina on 9/24/2016.
 */

public class User implements Serializable{
    private String username;
    private String password;
    private String email;
    private int kilograms;
    ArrayList<Challenge> userChallenges;
    ArrayList<Challenge> customChallenges;

    public User(String password, String username, String email, int kilograms) {
        this.password = password;
        this.username = username;
        this.email = email;
        this.kilograms = kilograms;
        userChallenges= new ArrayList<Challenge>();
        Challenge one= new Challenge(Challenge.Type.ABDOMEN,0,null);
        Challenge two= new Challenge(Challenge.Type.UPPERBODY,0,null);
        Challenge three= new Challenge(Challenge.Type.LOWERBODY,0,null);
        Challenge four= new Challenge(Challenge.Type.WHOLEBODY,0,null);
        //add exercise:
        //one.addExercise(new Exercise(....)); 5 exercises!
        userChallenges.add(one);
        userChallenges.add(two);
        userChallenges.add(three);
        userChallenges.add(four);
        customChallenges= new ArrayList<Challenge>();

    }

}
