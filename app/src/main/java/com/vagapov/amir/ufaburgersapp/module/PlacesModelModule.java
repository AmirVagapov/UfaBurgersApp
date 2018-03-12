package com.vagapov.amir.ufaburgersapp.module;

import com.vagapov.amir.ufaburgersapp.model.PlacesModel;
import com.vagapov.amir.ufaburgersapp.model.PlacesModelImpl;

import dagger.Module;
import dagger.Provides;


@Module
public class PlacesModelModule {

    @Provides
    public PlacesModel provideInfoModel(){
        return new PlacesModelImpl();
    }
}
