package com.vagapov.amir.ufaburgersapp.presenter;

import android.support.annotation.NonNull;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.vagapov.amir.ufaburgersapp.model.Place;
import com.vagapov.amir.ufaburgersapp.model.PlacesModel;
import com.vagapov.amir.ufaburgersapp.presenter.interfaces.PlaceListPresenter;
import com.vagapov.amir.ufaburgersapp.view.interfaces.PlaceListView;

import java.util.ArrayList;

import rx.Subscription;


public class PlaceListPresenterImpl extends MvpBasePresenter<PlaceListView>
        implements PlaceListPresenter {

    private Subscription subscriptionAllPlaces;
    private Subscription subscriptionSearch;
    private PlacesModel model;
    private Subscription updating;


    public PlaceListPresenterImpl(@NonNull PlacesModel model) {
        this.model = model;
    }


    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if(retainInstance){
            tryToUnsubscribe(subscriptionAllPlaces);
            tryToUnsubscribe(subscriptionSearch);
        }
    }


    @Override
    public boolean searchPlace(String text) {
        tryToUnsubscribe(subscriptionSearch);
        if(!isSubscribed(subscriptionSearch)) {
            subscriptionSearch = model.getPlaces()
                    .doOnNext((place)-> getView().showLoading(true))
                    .filter(
                    place -> place
                            .getName()
                            .toLowerCase()
                            .contains(text.toLowerCase())
            )
                    .scan(new ArrayList<Place>(), (burgersList, burgers) -> {
                        burgersList.add(burgers);
                        return burgersList;
                    }).subscribe(this::setDataOnView, this::setDataOnView);
        }
        return true;
    }

    @Override
    public void loadPlaces() {
        if(!isSubscribed(subscriptionAllPlaces)){
            subscriptionAllPlaces = model.getPlaces()
                    .reduce(new ArrayList<Place>(), (list, burgers) -> {
                        list.add(burgers);
                        return list;
                    }).subscribe(this::setDataOnView,
                            this::setDataOnView/*,
                            () -> getView().showContent()*/);
        }
    }

    private boolean isSubscribed(Subscription subscription){
        return subscription != null && !subscription.isUnsubscribed();
    }

    private void tryToUnsubscribe(Subscription subscription){
        if (isSubscribed(subscription)) {
            subscription.unsubscribe();
        }
    }

    private void setDataOnView(ArrayList<Place> list){
        PlaceListView view = getView();
        if(isViewAttached()){
            view.setData(list);
            view.showContent();
        }
    }

    private void setDataOnView(Throwable throwable){
        PlaceListView view = getView();
        if(isViewAttached()){
            view.showError(throwable, false);
            view.showContent();
        }
    }

}
