package com.vagapov.amir.ufaburgersapp.presenter.interfaces;

import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.vagapov.amir.ufaburgersapp.view.interfaces.PlaceDescriptionView;



public interface PlaceDescriptionPresenter extends MvpPresenter<PlaceDescriptionView> {

    void setPlacesFavourite();
    void loadPlace();
    void like();
    void dislike();
    void openImage();
}
