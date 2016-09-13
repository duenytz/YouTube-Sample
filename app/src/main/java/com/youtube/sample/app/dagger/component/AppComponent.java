package com.youtube.sample.app.dagger.component;

import com.youtube.sample.app.dagger.module.ApiModule;
import com.youtube.sample.app.dagger.module.AppModule;
import com.youtube.sample.app.dagger.module.StoreModule;
import com.youtube.sample.app.view.activity.YoutubeActivity;
import com.youtube.sample.app.view.adapter.VideosAdapter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton //Just one component to inject the whole app is enough in this case
@Component(modules = {AppModule.class, ApiModule.class, StoreModule.class,}) //
public interface AppComponent {
    void inject(YoutubeActivity youtubeActivity);

    void inject(VideosAdapter videosAdapter);
}
