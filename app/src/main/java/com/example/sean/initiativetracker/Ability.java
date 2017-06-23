package com.example.sean.initiativetracker;

/**
 * Created by Sean on 12/11/2016.
 */

public class Ability {
    private String abilityName;
    private int duration;

    public Ability(String an, int d) {
        abilityName = an;
        duration = d;
    }

    public String getAbilityName() {
        return abilityName;
    }

    public int getDuration() {
        return duration;
    }

    public void setAbilityName(String n) {
        abilityName = n;
    }

    public void setDuration(int n) {
        duration = n;
    }
}