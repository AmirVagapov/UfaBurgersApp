package com.vagapov.amir.ufaburgersapp.module;


import com.vagapov.amir.ufaburgersapp.model.PlacesModel;
import com.vagapov.amir.ufaburgersapp.view.interfaces.MapMarkerLoaderInterface;

import dagger.Module;
import dagger.Provides;

@Module
public class MapModule {

    private MapMarkerLoaderInterface loader;
    private PlacesModel model;

    public MapModule(MapMarkerLoaderInterface loader, PlacesModel model) {
        this.loader = loader;
        this.model = model;
    }

    @Provides
    MapMarkerLoaderInterface provideLoader(){
        return loader;
    }

    @Provides
    PlacesModel provideModel(){
        return model;
    }
}
