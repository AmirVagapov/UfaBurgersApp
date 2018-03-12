package com.vagapov.amir.ufaburgersapp.module;

import com.vagapov.amir.ufaburgersapp.model.Place;

import dagger.Module;
import dagger.Provides;

@Module
public class PlaceModule {

    private Place place;

    public PlaceModule(Place place) {
        this.place = place;
    }

    @Provides
    public Place providePlace(){
        return place;
    }
}
