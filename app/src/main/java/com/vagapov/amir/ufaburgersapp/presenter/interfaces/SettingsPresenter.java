package com.vagapov.amir.ufaburgersapp.presenter.interfaces;


import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.vagapov.amir.ufaburgersapp.view.interfaces.SettingsFragmentView;

public interface SettingsPresenter extends MvpPresenter<SettingsFragmentView> {


    void startService();
    void stopService();
}
