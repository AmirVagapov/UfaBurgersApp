package com.vagapov.amir.ufaburgersapp.model;




import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class Burgers implements Place {


    private String name;
    private String address;
    private float rating;
    private boolean isFavourite;
    private String description;
    private String site;
    private String photo;
    private transient LatLng latLng;
    private ArrayList<Comment> comments;


    public Burgers(String name, LatLng latLng, float rating) {
        this.name = name;
        this.latLng = latLng;
        this.rating = rating;
    }

    @Override
    public LatLng getLatLng() {
        return latLng;
    }

    @Override
    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public float getRating() {
        return rating;
    }

    @Override
    public void setRating(float rating) {
        this.rating = rating;
    }

    @Override
    public boolean isFavourite() {
        return isFavourite;
    }

    @Override
    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getSite() {
        return site;
    }

    @Override
    public void setSite(String site) {
        this.site = site;
    }

    @Override
    public String getPhoto() {
        return photo;
    }

    @Override
    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
