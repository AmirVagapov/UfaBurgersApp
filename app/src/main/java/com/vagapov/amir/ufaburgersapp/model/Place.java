package com.vagapov.amir.ufaburgersapp.model;


import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

public interface Place extends Serializable {

    LatLng getLatLng();

    void setLatLng(LatLng latLng);

    String getName();

    void setName(String name);

    String getAddress();

    void setAddress(String address);

    float getRating();

    void setRating(float rating);

    boolean isFavourite();

    void setFavourite(boolean favourite);

    String getDescription();

    void setDescription(String description);

    String getSite();

    void setSite(String site);

    String getPhoto();

    void setPhoto(String photo);
}
