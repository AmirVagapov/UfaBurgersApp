package com.vagapov.amir.ufaburgersapp.map_service;

import android.app.Activity;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;



public class NotificationsReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("LOCATIONSERVICE", "RECEIVER. Code " + getResultCode());
        if(getResultCode() != Activity.RESULT_OK){
            return;
        }

        int requestCode = intent.getIntExtra(MapService.REQUEST_CODE_INTENT, 0);
        Notification notification = intent.getParcelableExtra(MapService.NOTIFICATION);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(requestCode, notification);
    }
}
