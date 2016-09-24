package com.example.evelina.befit.model;

/**
 * Created by Evelina on 9/24/2016.
 */

public class Exercise {
    String name;
    int series;
    int repeats;
    String instructions;
    String shortDescription;
    // video


    public Exercise(String name, int series, int repeats, String instructions, String shortDescription) {
        this.name = name;
        this.series = series;
        this.repeats = repeats;
        this.instructions = instructions;
        this.shortDescription = shortDescription;
    }
}
