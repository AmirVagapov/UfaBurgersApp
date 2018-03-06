package com.vagapov.amir.ufaburgersapp.model;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by Tom on 05.03.2018.
 */

public class MockBurgersList {
    public static ArrayList<Burgers> mockPlacelist() {
        ArrayList<Burgers> mockList = new ArrayList<>();
        mockList.add(new Burgers("BurgerHeroes", new LatLng(54.7388, 55.9721)));
        mockList.add(new Burgers("Great Britain Pound", new LatLng(54.7399, 55.9799)));
        mockList.add(new Burgers("Shahta", new LatLng(54.7300, 55.9621)));
        mockList.add(new Burgers("PrimeBurgers", new LatLng(54.7399, 55.9750)));
        mockList.add(new Burgers("GurmanBurgers", new LatLng(54.7255, 55.9650)));
        mockList.add(new Burgers("Tesla", new LatLng(54.7309, 55.9777)));
        mockList.add(new Burgers("Morris", new LatLng(54.7318, 55.9791)));
        mockList.add(new Burgers("Harat's", new LatLng(54.7305, 55.9751)));
        mockList.add(new Burgers("MarcoPolo", new LatLng(54.7388, 55.9701)));
        mockList.add(new Burgers("BurgerHeroes", new LatLng(54.7388, 55.9721)));
        mockList.add(new Burgers("Great Britain Pound", new LatLng(54.7388, 55.9721)));
        mockList.add(new Burgers("Shahta", new LatLng(54.7388, 55.9721)));
        mockList.add(new Burgers("PrimeBurgers", new LatLng(54.7388, 55.9721)));
        mockList.add(new Burgers("GurmanBurgers", new LatLng(54.7388, 55.9721)));
        mockList.add(new Burgers("Tesla", new LatLng(54.7388, 55.9721)));
        mockList.add(new Burgers("Morris", new LatLng(54.7388, 55.9721)));
        mockList.add(new Burgers("Harat's", new LatLng(54.7388, 55.9721)));
        mockList.add(new Burgers("MarcoPolo", new LatLng(54.7388, 55.9721)));
        for (int i = 0; i < mockList.size(); i++) {
            if(i % 3 == 0){
                mockList.get(i).setFavourite(true);
            }
        }
        return mockList;
    }
}
