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
    private int points;
    private String gender;
    ArrayList<Challenge> achievedChallenges;
    ArrayList<Challenge> customChallenges;

    public User( String username,String password, String email, String gender,int weight, int height, int points) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.gender=gender;
        this.weight = weight;
        this.height= height;
        this.points=points;

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

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
