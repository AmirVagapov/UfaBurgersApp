package com.vagapov.amir.ufaburgersapp.presenter;

import android.support.v4.app.Fragment;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.vagapov.amir.ufaburgersapp.view.PlaceDescriptionView;



public interface PlaceDescriptionPresenter extends MvpPresenter<PlaceDescriptionView> {

    void openCommentFragment(Fragment fragment);
    void setPlacesFavourite();
    void loadPlace();
    void like();
    void dislike();
    void openImage();
}
