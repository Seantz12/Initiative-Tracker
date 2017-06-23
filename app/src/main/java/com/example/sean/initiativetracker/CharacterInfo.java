package com.example.sean.initiativetracker;

import android.os.Parcel;
import android.os.Parcelable;

import static android.content.ComponentName.readFromParcel;

/**
 * Created by Sean on 09/11/2016.
 */

public class CharacterInfo implements Parcelable{
    private String name;
    private int initScore;
    private int mod;

    public CharacterInfo(String n, int i, int m) {
        name = n;
        initScore = i;
        mod = m;
    }

    public CharacterInfo(Parcel in) {
        super();
        readFromParcel(in);
    }

    public static final Parcelable.Creator<CharacterInfo> CREATOR = new Parcelable.Creator<CharacterInfo>() {
        public CharacterInfo createFromParcel(Parcel in) {
            return new CharacterInfo(in);
        }

        public CharacterInfo[] newArray(int size) {
            return new CharacterInfo[size];
        }
    };

    public void readFromParcel(Parcel in) {
        name = in.readString();
        initScore = in.readInt();
        mod = in.readInt();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(initScore);
        dest.writeInt(mod);
    }

    public String getName() {
        return name;
    }

    public int getInitScore() {
        return initScore;
    }

    public int getMod() {
        return mod;
    }

    public void setName(String n) {
        name = n;
    }

    public void setInitScore(int n) {
        initScore = n;
    }

    public void setMod(int n) {
        mod = n;
    }

}
