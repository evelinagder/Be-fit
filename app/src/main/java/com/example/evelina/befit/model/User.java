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
    private int height;
    ArrayList<Challenge> userChallenges;
    ArrayList<Challenge> customChallenges;

    public User(String password, String username, String email, int kilograms, int height) {
        this.password = password;
        this.username = username;
        this.email = email;
        this.kilograms = kilograms;

        userChallenges= TrainingManager.getInstance().getBasicChalenges();
        customChallenges= new ArrayList<Challenge>();

    }

}
