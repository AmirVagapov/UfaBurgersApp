package com.vagapov.amir.ufaburgersapp.module;

import com.vagapov.amir.ufaburgersapp.model.PlacesModel;
import com.vagapov.amir.ufaburgersapp.presenter.interfaces.PlaceListPresenter;
import com.vagapov.amir.ufaburgersapp.presenter.PlaceListPresenterImpl;

import dagger.Module;
import dagger.Provides;


@Module
public class PlaceListPresenterModule {

    @Provides
    public PlaceListPresenter providePlaceListPresenter(PlacesModel model){
        return new PlaceListPresenterImpl(model);
    }

}
