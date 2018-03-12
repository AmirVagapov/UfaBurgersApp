package com.vagapov.amir.ufaburgersapp.module;

import com.vagapov.amir.ufaburgersapp.model.Place;
import com.vagapov.amir.ufaburgersapp.presenter.interfaces.PlaceDescriptionPresenter;
import com.vagapov.amir.ufaburgersapp.presenter.PlaceDescriptionPresenterImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class PlaceDescriptionPresenterModule {

    @Provides
    public PlaceDescriptionPresenter providePlaceListPresenter(Place place){
        return new PlaceDescriptionPresenterImpl(place);
    }
}
