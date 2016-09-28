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
    private int weight;
    private int height;
    ArrayList<Challenge> userChallenges;
    ArrayList<Challenge> customChallenges;

    public User( String username,String password, String email, int weight, int height) {
        this.password = password;
        this.username = username;
        this.email = email;
        this.weight = weight;
        this.height= height;

        userChallenges= TrainingManager.getInstance().getBasicChalenges();
        customChallenges= new ArrayList<Challenge>();

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return username.equals(user.username);

    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }
}
