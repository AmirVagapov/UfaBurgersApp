package com.vagapov.amir.ufaburgersapp.module;

import com.vagapov.amir.ufaburgersapp.model.PlacesModel;
import com.vagapov.amir.ufaburgersapp.map_delegate.MapDelegate;
import com.vagapov.amir.ufaburgersapp.view.interfaces.MapMarkerLoaderInterface;

import dagger.Module;
import dagger.Provides;

@Module
public class MapDelegateModule {

    @Provides
    MapDelegate provideMapDelegate(PlacesModel placesModel, MapMarkerLoaderInterface loader){
        return new MapDelegate(placesModel, loader);
    }
}
