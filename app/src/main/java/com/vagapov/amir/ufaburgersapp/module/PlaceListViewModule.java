package com.vagapov.amir.ufaburgersapp.module;


import com.vagapov.amir.ufaburgersapp.view.PlacesAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public class PlaceListViewModule {

    @Provides
    public PlacesAdapter providePlaceAdapter(){
        return new PlacesAdapter();
    }

}
