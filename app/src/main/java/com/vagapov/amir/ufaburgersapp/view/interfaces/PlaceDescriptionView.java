package com.vagapov.amir.ufaburgersapp.view.interfaces;

import com.hannesdorfmann.mosby.mvp.MvpView;



public interface PlaceDescriptionView extends MvpView {

    void checkFavourite();
    void setName(String name);
    void setRating(String rating);
    void setDescription(String text);
}
