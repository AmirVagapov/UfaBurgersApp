package com.vagapov.amir.ufaburgersapp.presenter;


import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.vagapov.amir.ufaburgersapp.model.Place;
import com.vagapov.amir.ufaburgersapp.view.PlaceListView;

public interface PlaceListPresenter extends MvpPresenter<PlaceListView>{

    boolean searchPlace(String text);
    void loadPlaces();
}
