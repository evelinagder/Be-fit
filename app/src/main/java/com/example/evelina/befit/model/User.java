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
    ArrayList<Challenge> achievedChallenges;
    ArrayList<Challenge> customChallenges;

    public User( String username,String password, String email, int weight, int height) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.weight = weight;
        this.height= height;

        customChallenges= new ArrayList<Challenge>();
        achievedChallenges= new ArrayList<Challenge>();

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

    public void addCustomChallenge(Challenge c){
        customChallenges.add(c);
    }
    public void addAchievedChallenge(Challenge c){
        achievedChallenges.add(c);
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
