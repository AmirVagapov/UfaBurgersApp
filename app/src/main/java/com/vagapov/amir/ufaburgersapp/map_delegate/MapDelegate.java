package com.vagapov.amir.ufaburgersapp.map_delegate;

import com.vagapov.amir.ufaburgersapp.model.PlacesModel;
import com.vagapov.amir.ufaburgersapp.view.interfaces.MapMarkerLoaderInterface;

import rx.Subscription;


public class MapDelegate {

    private Subscription subscriptionLoadData;
    private PlacesModel model;
    private MapMarkerLoaderInterface loader;

    public MapDelegate(PlacesModel model, MapMarkerLoaderInterface loader) {
        this.model = model;
        this.loader = loader;
    }


     public void loadData(){
        subscriptionLoadData = model.getPlaces()
                .subscribe(place -> loader.addMarkers(place)
                        , throwable -> loader.showErrorLoading(throwable),
                        () -> loader.postMarkers());
    }


    public void loadPlace(String title){
         tryToUnsubscribe();
         subscriptionLoadData = model.getPlaces()
                 .filter( place -> place.getName().equals(title))
                 .first()
                 .subscribe(place -> loader.openPlace(place));
    }

    private boolean isSubscribed(){
        return subscriptionLoadData != null && !subscriptionLoadData.isUnsubscribed();
    }

    public void tryToUnsubscribe(){
        if(isSubscribed()){
            subscriptionLoadData.unsubscribe();
        }
    }
}
