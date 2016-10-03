package com.example.evelina.befit.model;

/**
 * Created by Evelina on 9/24/2016.
 */

public class Exercise {
    private String name;
    private int points;
    private  int series;
    private int repeats;
    private String instructions;
    private int video;

    // video


    public Exercise(String name,int points, int series, int repeats, String instructions,int video) {
        this.name = name;
        this.series = series;
        this.repeats = repeats;
        this.points=points;
        this.instructions = instructions;
        this.video= video;

    }

    public String getName() {
        return name;
    }

    public String getInstructions() {
        return instructions;
    }
}
