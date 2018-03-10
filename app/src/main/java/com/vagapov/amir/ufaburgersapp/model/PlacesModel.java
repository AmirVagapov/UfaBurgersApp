package com.vagapov.amir.ufaburgersapp.model;


import rx.Observable;

public interface PlacesModel {

    Observable<Place> getPlaces();
}
