package com.farhan.trivia.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {
    private SharedPreferences preferences;

    public Prefs(Activity context) {
        this.preferences = context.getPreferences(Context.MODE_PRIVATE);

    }
    public void saveHighestScore(int score)
    {
        int lastscore=preferences.getInt("highest_score",0);
        if(score >lastscore){
            preferences.edit().putInt("highest_score", score).apply();
        }
    }
    public int getHghestScore()
    {
        return preferences.getInt("highest_score",0);
    }
    public void setState(int index){
        preferences.edit().putInt("trivia",index).apply();
    }
    public int getState()
    {
        return preferences.getInt("trivia",0);
    }
}

