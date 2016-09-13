package com.youtube.sample.app.dagger.module;

import android.content.Context;

import com.youtube.sample.app.App;
import com.youtube.sample.app.BuildConfig;
import com.youtube.sample.app.dispatcher.Dispatcher;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Singleton
@Module //
public final class AppModule {
    private final App app;

    public AppModule(App app) {
        this.app = app;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return app;
    }

    @Provides
    @Singleton
    EventBus provideEventBus() {
        return EventBus.builder().throwSubscriberException(BuildConfig.DEBUG).installDefaultEventBus();
    }

    @Provides
    @Singleton
    Dispatcher provideDispatcher(EventBus bus) {
        return new Dispatcher(bus);
    }
}
