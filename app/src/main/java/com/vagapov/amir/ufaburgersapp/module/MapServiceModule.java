package com.vagapov.amir.ufaburgersapp.module;

import com.vagapov.amir.ufaburgersapp.map_service.MapService;
import com.vagapov.amir.ufaburgersapp.module.interfaces.MapScopeInterface;

import dagger.Module;
import dagger.Provides;

@Module
public class MapServiceModule {

    @MapScopeInterface
    @Provides
    MapService provideMapService(){
        return new MapService();
    }
}
