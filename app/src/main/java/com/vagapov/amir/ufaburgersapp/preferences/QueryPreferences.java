package com.vagapov.amir.ufaburgersapp.preferences;


import android.content.Context;
import android.preference.PreferenceManager;

public class QueryPreferences {

    private static final String IS_NOTIFICATIONS_ON = "notifications";

    public static boolean isNotifiactionsOn(Context context){
        return PreferenceManager
                .getDefaultSharedPreferences(context)
                .getBoolean(IS_NOTIFICATIONS_ON, false);
    }

    public static void setNotificationsOn(Context context, boolean isOn){
        PreferenceManager
                .getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(IS_NOTIFICATIONS_ON, isOn)
                .apply();
    }
}
