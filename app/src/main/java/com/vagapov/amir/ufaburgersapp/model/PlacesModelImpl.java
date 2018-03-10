package com.vagapov.amir.ufaburgersapp.model;


import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class PlacesModelImpl implements PlacesModel {

    public Observable<Place> getPlaces(){
        return Observable.defer(() -> Observable.from(mockPlacelist()))
                .delay(600, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private  ArrayList<Place> mockPlacelist() {
        ArrayList<Place> mockList = new ArrayList<>();
        mockList.add(new Burgers("BurgerHeroes", new LatLng(54.7388, 55.9721), 8.0f));
        mockList.add(new Burgers("Great Britain Pound", new LatLng(54.7399, 55.9799), 3.0f));
        mockList.add(new Burgers("Shahta", new LatLng(54.7300, 55.9621), 6.8f));
        mockList.add(new Burgers("PrimeBurgers", new LatLng(54.7399, 55.9750), 9.4f));
        mockList.add(new Burgers("GurmanBurgers", new LatLng(54.7255, 55.9650), 2.4f));
        mockList.add(new Burgers("Tesla", new LatLng(54.7309, 55.9777), 5.6f));
        mockList.add(new Burgers("Morris", new LatLng(54.7318, 55.9791), 7.7f));
        mockList.add(new Burgers("Harat's", new LatLng(54.7305, 55.9751), 4.5f));
        mockList.add(new Burgers("MarcoPolo", new LatLng(54.7388, 55.9701), 10.0f));
        mockList.add(new Burgers("BurgerHeroes", new LatLng(54.7388, 55.9721), 7.8f));
        mockList.add(new Burgers("Great Britain Pound", new LatLng(54.7388, 55.9721), 9.4f));
        mockList.add(new Burgers("Shahta", new LatLng(54.7388, 55.9721), 7.5f));
        mockList.add(new Burgers("PrimeBurgers", new LatLng(54.7388, 55.9721), 6.6f));
        mockList.add(new Burgers("GurmanBurgers", new LatLng(54.7388, 55.9721), 6.9f));
        mockList.add(new Burgers("Tesla", new LatLng(54.7388, 55.9721), 9.9f));
        mockList.add(new Burgers("Morris", new LatLng(54.7388, 55.9721), 1.8f));
        mockList.add(new Burgers("Harat's", new LatLng(54.7388, 55.9721), 5.5f));
        mockList.add(new Burgers("MarcoPolo", new LatLng(54.7388, 55.9721), 6.9f));
        for (int i = 0; i < mockList.size(); i++) {
            if(i % 3 == 0){
                mockList.get(i).setFavourite(true);
            }
        }
        return mockList;
    }

}
