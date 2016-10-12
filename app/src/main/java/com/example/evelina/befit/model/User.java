package com.example.evelina.befit.model;

import android.net.Uri;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    private Uri profilePic;
    private String gender;
    private int userDBId;
    HashMap<String, Challenge> achievedChallenges;
    HashMap<String, Challenge> customChallenges;
    ArrayList<Alarm> userAlarms;

    public User( String username,String password, String email, String gender,int weight, int height, int points) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.gender=gender;
        this.weight = weight;
        this.height= height;
        this.points=points;


        customChallenges= new HashMap<String, Challenge>();
        achievedChallenges= new HashMap<String, Challenge>();
        userAlarms= new ArrayList<Alarm>();

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
        Log.e("Challenge add to USER",c.getName());
        customChallenges.put(c.getName(),c);
    }
    public void addAchievedChallenge(Challenge c){
        achievedChallenges.put(c.getName(),c);
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

    public void setProfilePic(Uri profilePic) {
        this.profilePic = profilePic;
    }

    public Uri getProfilePic() {
        return profilePic;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<String> getCustomChallengesName(){
        List<String> list=  new ArrayList<>() ;
        list.addAll( customChallenges.keySet());
        return list;

    }
    public boolean hasCustomChallenges(){
        if(customChallenges.isEmpty()){
            return false;
        }
            return true;
    }
    public void addAlarmn(Alarm alarm){
        if(alarm != null){
            userAlarms.add(alarm);
        }
    }
    public Challenge getCustomChallenges(String name) {
        Challenge c = customChallenges.get(name);
        return c;
    }
    public Challenge getAcheivedChallenge(String name) {
        Challenge c = achievedChallenges.get(name);
        return c;
    }

    public void setUserDBId(int userDBId) {
        this.userDBId = userDBId;
    }

    public int getUserDBId() {
        return userDBId;
    }
    public int getCompletedTrainingsNum(){
        return achievedChallenges.size();
    }
   public List getCompletedTrainingsNames(){
       List<String> list=  new ArrayList<>() ;
       list.addAll( achievedChallenges.keySet());
       return list;
    }
    public boolean noCompletedChallenges(){
        return achievedChallenges.isEmpty();
    }

   public ArrayList<Challenge> getCompletedTrainings(){
       ArrayList<Challenge>list=new ArrayList<>();
               list.addAll(achievedChallenges.values());
       return list;
    }
}
