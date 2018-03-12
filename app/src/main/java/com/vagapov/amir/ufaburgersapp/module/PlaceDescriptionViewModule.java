package com.vagapov.amir.ufaburgersapp.module;

import com.google.android.gms.maps.model.LatLng;
import com.vagapov.amir.ufaburgersapp.model.Place;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class PlaceDescriptionViewModule {

    public static final String URI_MAP = "uri_map";
    public static final String URI_LOCATION = "location";

    @Provides
    @Named(URI_MAP)
    public String provideUriMap(){
        return "geo:";
    }


    @Provides
    @Named(URI_LOCATION)
    public String provideUriLocation(@Named(URI_MAP) String uriMap, Place place){
        StringBuilder builder = new StringBuilder(uriMap);
        builder.append(place.getLatLng().latitude)
                .append(", ")
                .append(place.getLatLng().longitude)
                .append("?q=")
                .append(place.getName());
        return builder.toString();
    }
}
