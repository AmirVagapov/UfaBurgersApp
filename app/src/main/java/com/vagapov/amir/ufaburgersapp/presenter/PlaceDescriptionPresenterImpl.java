package com.vagapov.amir.ufaburgersapp.presenter;


import android.support.v4.app.Fragment;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.vagapov.amir.ufaburgersapp.model.Place;
import com.vagapov.amir.ufaburgersapp.view.FragmentClickOpenPlaceInterface;
import com.vagapov.amir.ufaburgersapp.view.PlaceDescriptionView;

import java.text.DecimalFormat;

public class PlaceDescriptionPresenterImpl extends MvpBasePresenter<PlaceDescriptionView>
        implements PlaceDescriptionPresenter{

    private Place place;
    private FragmentClickOpenPlaceInterface openInterface;
    private PlaceDescriptionView view;

    @Override
    public void attachView(PlaceDescriptionView view) {
        super.attachView(view);
        this.view = view;
    }

    public PlaceDescriptionPresenterImpl(Place place, FragmentClickOpenPlaceInterface openInterface) {
        this.place = place;
        this.openInterface = openInterface;
    }


    @Override
    public void openCommentFragment(Fragment fragment) {
        openInterface.openFragment(fragment);
    }

    @Override
    public void setPlacesFavourite() {
        place.setFavourite(!place.isFavourite());
        view.checkFavourite();
    }

    @Override
    public void loadPlace() {
        view.checkFavourite();
        //view.setDescription(place.getDescription());
        String rating = new DecimalFormat("#0.0")
                .format(place.getRating() + 0.1f);
        view.setRating(rating);
    }

    @Override
    public void like() {
        place.setRating(place.getRating() + 0.1f);
        loadPlace();
    }

    @Override
    public void dislike() {
        place.setRating(place.getRating() - 0.1f);
        loadPlace();
    }

    @Override
    public void openImage() {

    }
}
