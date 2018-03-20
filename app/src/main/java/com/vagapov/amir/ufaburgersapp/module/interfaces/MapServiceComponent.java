package com.vagapov.amir.ufaburgersapp.module.interfaces;

import com.vagapov.amir.ufaburgersapp.map_service.MapService;
import com.vagapov.amir.ufaburgersapp.module.MapServiceModule;

import dagger.Component;


@Component(modules = MapServiceModule.class)
@MapScopeInterface
public interface MapServiceComponent {

    MapService service();
}
