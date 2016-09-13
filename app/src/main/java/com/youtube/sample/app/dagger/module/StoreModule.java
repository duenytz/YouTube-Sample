package com.youtube.sample.app.dagger.module;

import com.youtube.sample.app.store.ApiStore;
import com.youtube.sample.app.store.impl.ApiStoreImpl;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Singleton
@Module //
public final class StoreModule {
    @Provides
    @Singleton
    ApiStore provideApiStore(EventBus bus) {
        return new ApiStoreImpl(bus);
    }
}
