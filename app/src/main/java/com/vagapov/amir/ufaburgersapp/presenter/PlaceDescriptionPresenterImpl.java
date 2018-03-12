package com.vagapov.amir.ufaburgersapp.presenter;


import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.vagapov.amir.ufaburgersapp.model.Place;
import com.vagapov.amir.ufaburgersapp.presenter.interfaces.PlaceDescriptionPresenter;
import com.vagapov.amir.ufaburgersapp.view.interfaces.PlaceDescriptionView;

import java.text.DecimalFormat;

public class PlaceDescriptionPresenterImpl extends MvpBasePresenter<PlaceDescriptionView>
        implements PlaceDescriptionPresenter {

    private Place place;
    private PlaceDescriptionView view;

    @Override
    public void attachView(PlaceDescriptionView view) {
        super.attachView(view);
        this.view = view;
    }

    public PlaceDescriptionPresenterImpl(Place place) {
        this.place = place;
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
        view.setName(place.getName());
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
