package com.example.evelina.befit.model;

/**
 * Created by Evelina on 10/3/2016.
 */

public class Alarm {
    private long alarmTime;
    private boolean isRepeating;

    Alarm(long alarmTime, boolean isRepeating) {
        this.alarmTime = alarmTime;
        this.isRepeating = isRepeating;
    }

    public boolean getIsRepeating() {
        return isRepeating;
    }
}
