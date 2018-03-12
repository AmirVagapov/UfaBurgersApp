package com.vagapov.amir.ufaburgersapp.module.interfaces;

import com.vagapov.amir.ufaburgersapp.module.PlaceDescriptionPresenterModule;
import com.vagapov.amir.ufaburgersapp.module.PlaceDescriptionViewModule;
import com.vagapov.amir.ufaburgersapp.module.PlaceModule;
import com.vagapov.amir.ufaburgersapp.presenter.interfaces.PlaceDescriptionPresenter;
import com.vagapov.amir.ufaburgersapp.view.interfaces.PlaceDescriptionView;

import javax.inject.Named;

import dagger.Component;

import static com.vagapov.amir.ufaburgersapp.module.PlaceDescriptionViewModule.URI_LOCATION;

@Component(modules = {PlaceDescriptionPresenterModule.class, PlaceDescriptionViewModule.class, PlaceModule.class})
public interface PlaceDescriptionComponent {

    void inject(PlaceDescriptionView view);
    PlaceDescriptionPresenter presenter();

    @Named(URI_LOCATION)
    String uriLocation();
}
