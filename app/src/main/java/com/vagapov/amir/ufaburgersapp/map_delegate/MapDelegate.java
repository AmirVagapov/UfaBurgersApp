package com.vagapov.amir.ufaburgersapp.map_delegate;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;
import com.vagapov.amir.ufaburgersapp.model.Place;
import com.vagapov.amir.ufaburgersapp.model.PlacesModel;
import com.vagapov.amir.ufaburgersapp.view.interfaces.MapMarkerLoaderInterface;

import rx.Observable;
import rx.Subscription;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.internal.util.SubscriptionList;


public class MapDelegate {

    private Subscription subscriptionLoadData;
    private Subscription subscriptionLoadPlace;
    private SubscriptionList subscriptionList;
    private PlacesModel model;
    private MapMarkerLoaderInterface loader;

    public MapDelegate(PlacesModel model, MapMarkerLoaderInterface loader) {
        this.model = model;
        this.loader = loader;
    }


     public void loadData(){
         tryToUnsubscribe(subscriptionLoadData);
         subscriptionLoadData = model.getPlaces()
                .subscribe(place -> loader.addMarkers(place)
                        , throwable -> loader.showErrorLoading(throwable),
                        () -> loader.postMarkers());
         subscriptionList.add(subscriptionLoadData);
    }


    public void loadPlace(String title){
         tryToUnsubscribe(subscriptionLoadPlace);
         subscriptionLoadPlace = model.getPlaces()
                 .filter( place -> place.getName().equals(title))
                 .first()
                 .subscribe(place -> loader.openPlace(place));
         subscriptionList.add(subscriptionLoadPlace);
    }



    private boolean isSubscribed(Subscription subscription){
        return subscription != null && !subscription.isUnsubscribed();
    }

    public void tryToUnsubscribe(Subscription subscription){
        if(isSubscribed(subscription)){
            subscription.unsubscribe();
        }
    }

    public void unsubscribeAll(){
        subscriptionList.unsubscribe();
    }
}
