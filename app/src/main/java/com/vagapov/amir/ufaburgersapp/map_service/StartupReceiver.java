package com.vagapov.amir.ufaburgersapp.map_service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.vagapov.amir.ufaburgersapp.module.interfaces.DaggerMapServiceComponent;
import com.vagapov.amir.ufaburgersapp.module.interfaces.MapServiceComponent;
import com.vagapov.amir.ufaburgersapp.preferences.QueryPreferences;


public class StartupReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("STARTUPRECEIVER", "" + intent.getAction());

        boolean isOn = QueryPreferences.isNotifiactionsOn(context);
//        MapServiceComponent component = DaggerMapServiceComponent.create();
//       // Log.d("STARTUPRECEIVER", "" + isOn);
//        component.service().setServiceAlarm(context, isOn);
        new MapService().setServiceAlarm(context, isOn);
    }
}
