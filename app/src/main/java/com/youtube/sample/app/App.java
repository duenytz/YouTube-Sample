package com.youtube.sample.app;

import android.app.Application;

import com.youtube.sample.app.dagger.component.AppComponent;
import com.youtube.sample.app.dagger.component.DaggerAppComponent;
import com.youtube.sample.app.dagger.module.ApiModule;
import com.youtube.sample.app.dagger.module.AppModule;
import com.youtube.sample.app.dagger.module.StoreModule;

import timber.log.Timber;


public class App extends Application {
    private AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        component = createComponent();
    }

    protected AppComponent createComponent() {
        return DaggerAppComponent.builder().appModule(new AppModule(this)).apiModule(new ApiModule()).storeModule(new StoreModule()).build();
    }

    public AppComponent getComponent() {
        return component;
    }
}
