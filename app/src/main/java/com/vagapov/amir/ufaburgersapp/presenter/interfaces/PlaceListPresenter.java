package com.vagapov.amir.ufaburgersapp.presenter.interfaces;


import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.vagapov.amir.ufaburgersapp.view.interfaces.PlaceListView;

public interface PlaceListPresenter extends MvpPresenter<PlaceListView>{

    boolean searchPlace(String text);
    void loadPlaces();
}
