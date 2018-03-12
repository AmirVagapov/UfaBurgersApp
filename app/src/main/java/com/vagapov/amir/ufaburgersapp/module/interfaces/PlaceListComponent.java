package com.vagapov.amir.ufaburgersapp.module.interfaces;


import com.vagapov.amir.ufaburgersapp.module.PlaceListPresenterModule;
import com.vagapov.amir.ufaburgersapp.module.PlaceListViewModule;
import com.vagapov.amir.ufaburgersapp.module.PlacesModelModule;
import com.vagapov.amir.ufaburgersapp.presenter.interfaces.PlaceListPresenter;
import com.vagapov.amir.ufaburgersapp.view.PlaceListFragment;
import com.vagapov.amir.ufaburgersapp.view.PlacesAdapter;

import dagger.Component;

@Component(modules = {PlaceListPresenterModule.class, PlaceListViewModule.class, PlacesModelModule.class})
public interface PlaceListComponent {

    void inject(PlaceListFragment fragment);
    PlaceListPresenter presenter();
    PlacesAdapter adapter();
}
