package com.youtube.sample.app.store.impl;

import com.youtube.sample.app.action.Action;
import com.youtube.sample.app.action.Actions;
import com.youtube.sample.app.action.Keys;
import com.youtube.sample.app.event.OnNoResultsFound;
import com.youtube.sample.app.event.OnSearchError;
import com.youtube.sample.app.event.OnVideoItemsReceived;
import com.youtube.sample.app.net.gson.SearchResponse;
import com.youtube.sample.app.net.gson.VideoItem;
import com.youtube.sample.app.store.ApiStore;
import com.youtube.sample.app.store.Store;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import timber.log.Timber;

@Singleton //
public class ApiStoreImpl extends Store implements ApiStore {

    private List<VideoItem> videoItems;

    @Inject
    public ApiStoreImpl(EventBus bus) {
        super(bus);
    }

    @Override
    @Subscribe
    public void onEvent(Action action) {
        switch (action.getType()) {
            case Actions.RESPONSE_RECEIVED:
                SearchResponse searchResponse = (SearchResponse) action.getData().get(Keys.RESPONSE);
                videoItems = searchResponse.items;
                if (videoItems == null || videoItems.isEmpty()) {
                    emitStoreChange(new OnNoResultsFound());
                    return;
                }
                emitStoreChange(new OnVideoItemsReceived(videoItems));
                break;
            case Actions.SEARCH_ERROR:
                Throwable e = (Throwable) action.getData().get(Keys.EXCEPTION);
                Timber.e(e, e.getMessage());
                emitStoreChange(new OnSearchError(e));
                break;
            default:
                //Nothing happens
        }
    }

    @Override
    public List<VideoItem> getVideoItems() {
        return videoItems == null ? new ArrayList<>() : videoItems;
    }
}
