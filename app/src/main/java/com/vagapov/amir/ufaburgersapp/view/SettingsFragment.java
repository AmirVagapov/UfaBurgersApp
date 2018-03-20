package com.vagapov.amir.ufaburgersapp.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.hannesdorfmann.mosby.mvp.MvpFragment;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.vagapov.amir.ufaburgersapp.R;
import com.vagapov.amir.ufaburgersapp.map_service.MapService;
import com.vagapov.amir.ufaburgersapp.model.PlacesModelImpl;
import com.vagapov.amir.ufaburgersapp.module.interfaces.DaggerMapServiceComponent;
import com.vagapov.amir.ufaburgersapp.module.interfaces.MapServiceComponent;
import com.vagapov.amir.ufaburgersapp.preferences.QueryPreferences;
import com.vagapov.amir.ufaburgersapp.presenter.SettingsPresenterImpl;
import com.vagapov.amir.ufaburgersapp.view.interfaces.SettingsFragmentView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsFragment extends MvpFragment
        implements SettingsFragmentView, CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.checkbox_settings_notification)
    CheckBox checkBox;

    private MapServiceComponent component;

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        component = DaggerMapServiceComponent.create();
        ButterKnife.bind(this, view);
        setCheckBox();
        checkBox.setOnCheckedChangeListener(this);
    }

    private void setCheckBox() {
        checkBox.setChecked(QueryPreferences.isNotifiactionsOn(getActivity()));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);
        return v;
    }

    @Override
    public MvpPresenter createPresenter() {
        return new SettingsPresenterImpl(new PlacesModelImpl());
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        QueryPreferences.setNotificationsOn(getActivity(), b);
        if (b) {
            Log.d("CHECKED", "ON");
        } else {
            Log.d("CHECKED", "OFF");
        }
        component.service().setServiceAlarm(getActivity(), b);
    }
}
