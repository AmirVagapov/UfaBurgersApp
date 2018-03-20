package com.vagapov.amir.ufaburgersapp.presenter;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.vagapov.amir.ufaburgersapp.model.PlacesModel;
import com.vagapov.amir.ufaburgersapp.presenter.interfaces.SettingsPresenter;
import com.vagapov.amir.ufaburgersapp.view.interfaces.SettingsFragmentView;



public class SettingsPresenterImpl extends MvpBasePresenter<SettingsFragmentView>
        implements SettingsPresenter {

    private PlacesModel model;

    public SettingsPresenterImpl(PlacesModel model) {
        this.model = model;
    }

    @Override
    public void startService() {

    }

    @Override
    public void stopService() {

    }
}
