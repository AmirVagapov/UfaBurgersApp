package com.vagapov.amir.ufaburgersapp.module.interfaces;

import com.vagapov.amir.ufaburgersapp.module.MapDelegateModule;
import com.vagapov.amir.ufaburgersapp.module.MapModule;
import com.vagapov.amir.ufaburgersapp.map_delegate.MapDelegate;
import com.vagapov.amir.ufaburgersapp.view.MapFragment;

import dagger.Component;

@Component(modules = {MapDelegateModule.class, MapModule.class})
public interface MapComponent {

    void inject(MapFragment fragment);
    MapDelegate delegate();
}
