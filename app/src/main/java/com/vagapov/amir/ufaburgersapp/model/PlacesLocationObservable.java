package com.vagapov.amir.ufaburgersapp.model;


import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class PlacesLocationObservable {

    public static Observable<Burgers> getPlaces(){
        return Observable.defer(() -> Observable.from(MockBurgersList.mockPlacelist()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
