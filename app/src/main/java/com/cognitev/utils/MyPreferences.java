package com.cognitev.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.cognitev.App;

/**
 * Created by Eslam Hussein on 10/14/17.
 */

public class MyPreferences {

    private static final String IS_REAL_TIME = "IS_REAL_TIME";
    private static MyPreferences instance = null;
    private static Context ctx;
    private static SharedPreferences appPreferences;

    private MyPreferences() {
        this.ctx = App.get().getApplicationContext();
        appPreferences = PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static MyPreferences getInstance() {
        if (instance == null) {
            instance = new MyPreferences();
        }
        return instance;
    }

    public boolean getIsRealTime() {
        appPreferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        return appPreferences.getBoolean(IS_REAL_TIME, true);
    }

    public void setIsRealTime(boolean isRealTime) {
        appPreferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        appPreferences.edit().putBoolean(IS_REAL_TIME, isRealTime).apply();
    }
}
