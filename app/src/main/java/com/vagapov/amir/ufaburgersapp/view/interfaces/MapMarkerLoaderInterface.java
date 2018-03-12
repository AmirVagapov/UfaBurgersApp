package com.vagapov.amir.ufaburgersapp.view.interfaces;

import com.vagapov.amir.ufaburgersapp.model.Place;

public interface MapMarkerLoaderInterface {

    void showErrorLoading(Throwable throwable);
    void addMarkers(Place place);
    void postMarkers();
    void openPlace(Place place);
}
