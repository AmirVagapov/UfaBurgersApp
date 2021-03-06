package com.vagapov.amir.ufaburgersapp.map_service;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.JobIntentService;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.vagapov.amir.ufaburgersapp.MainActivity;
import com.vagapov.amir.ufaburgersapp.R;
import com.vagapov.amir.ufaburgersapp.model.Place;
import com.vagapov.amir.ufaburgersapp.model.PlacesModel;
import com.vagapov.amir.ufaburgersapp.model.PlacesModelImpl;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;


public class MapService extends JobIntentService {
    private static final int REQUEST_CODE = 0;
    private PlacesModel model;
    private Location myLocation;
    private Subscription subscription;
    public static final String REQUEST_CODE_INTENT = "request_code";
    public static final String NOTIFICATION = "notification";
    public static final String ACTION_SHOW_NOTIFICATION = "com.vagapov.amir.burgersapp.SHOW_NOTIFICATION";
    public static final String PLACE = "place";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";

    public static Intent newIntent(Context context) {
        return new Intent(context, MapService.class);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        Log.d("LOCATIONSERVICE", "HandleWork");
        model = new PlacesModelImpl();
        Observable<LatLng> myLocationObservable = Observable.just(getLocation())
                .flatMap(
                        location -> Observable
                                .just(new LatLng(location.getLatitude(), location.getLongitude()))
                );
        subscription = loadNearestPlaces(myLocationObservable)
                .subscribe(place -> {
                            Log.d("LOCATIONSERVICE", "OBSER LOCATION LOADED " + place.getName());
                            Intent i = createIntent(place);
                            PendingIntent pi = PendingIntent
                                    .getActivity(this, 0,
                                            i, PendingIntent.FLAG_CANCEL_CURRENT);
                            sendBroadcast(place, pi);
                        },
                        throwable -> Log.d("LOCATIONSERVICE",
                                "OBSERVABLE ERR " + throwable + " " + throwable.getMessage()));


    }

    private void sendBroadcast(Place place, PendingIntent pi) {
        Notification notification = new Notification.Builder(this)
                .setTicker(getResources().getString(R.string.burger_is_near))
                .setSmallIcon(R.drawable.app_icon_burger)
                .setContentTitle(getResources().getString(R.string.burger_is_near))
                .setContentIntent(pi)
                .setContentText(getResources().getString(R.string.burgers_name_near) + " " + place.getName())
                .setAutoCancel(true)
                .build();

        Intent intent = new Intent(ACTION_SHOW_NOTIFICATION);
        intent.putExtra(REQUEST_CODE_INTENT, 100);
        intent.putExtra(NOTIFICATION, notification);
        sendOrderedBroadcast(intent, null, null,
                null, Activity.RESULT_OK, null, null);
    }

    private Intent createIntent(Place place) {
        Intent i = MainActivity.newIntent(this);
        i.putExtra(PLACE, place);
        i.putExtra(LONGITUDE, place.getLatLng().longitude);
        i.putExtra(LATITUDE, place.getLatLng().latitude);
        return i;
    }

    public void setServiceAlarm(Context context, boolean isOn) {
        Intent intent = MapService.newIntent(context);
        PendingIntent pi = PendingIntent.getService(context, REQUEST_CODE, intent, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);

        if (isOn) {
            Log.d("LOCATIONSERVICE", "setServiceAlarm " + isOn);
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(),
                    TimeUnit.MINUTES.toMillis(1), pi);
        } else {
            Log.d("LOCATIONSERVICE", "setServiceAlarm " + isOn);
            alarmManager.cancel(pi);
            pi.cancel();
            tryToUbsubscribed();
        }

    }

    private Observable<Place> loadNearestPlaces(Observable<LatLng> latLng) {
        return Observable
                .combineLatest(latLng, model.getPlaces(), (latLng1, place) -> {
                    if ((Math.abs(((float) place.getLatLng().latitude - (float) latLng1.latitude))
                            < 0.001)
                            && Math.abs(((float) place.getLatLng().longitude - (float) latLng1.longitude))
                            < 0.001) {
                        return place;
                    }
                    return null;
                })
                .filter(place -> place != null)
                .first();
    }

    private Location getLocation() {

        FusedLocationProviderClient fusedLocationProviderClient
                = LocationServices.getFusedLocationProviderClient(getApplicationContext());
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        fusedLocationProviderClient
                .getLastLocation()
                .addOnFailureListener(e -> Log.d("LOCATIONSERVICE", "ERROR LOAD LOC" + e.getMessage()))
                .addOnSuccessListener(location -> {
                    myLocation = location;
                });
        try {
            TimeUnit.MILLISECONDS.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return myLocation;
    }

    private void tryToUbsubscribed() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
