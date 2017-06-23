package com.example.sean.initiativetracker;

import java.util.Comparator;

/**
 * Created by Sean on 09/11/2016.
 */

public class CharacterInfoComparator implements Comparator<CharacterInfo> {

    public int compare(CharacterInfo c1, CharacterInfo c2) {
        if(c1.getInitScore() + c1.getMod() == c2.getInitScore() + c2.getMod()) {
            if(c1.getMod() > c2.getMod()) {
                return -1;
            } else if (c1.getMod() < c2.getMod()) {
                return 1;
            } else {
                int random = (int)((Math.random()*3)-1);
                switch(random) {
                    case -1:
                    case 0:
                        return -1;
                    case 1:
                        return 1;
                    default:
                        return 1;
                }
            }
        } else if (c1.getInitScore() + c1.getMod() > c2.getInitScore() + c2.getMod()) {
            return -1;
        } else {
            return 1;
        }

    }

}
