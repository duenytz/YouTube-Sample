package com.youtube.sample.app.action.creators;

import android.content.Context;

import com.youtube.sample.app.R;
import com.youtube.sample.app.action.Actions;
import com.youtube.sample.app.action.Keys;
import com.youtube.sample.app.dispatcher.Dispatcher;
import com.youtube.sample.app.net.service.ApiService;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton //
public class ApiActionsCreator extends ActionsCreator {

    private Context context;
    private ApiService apiService;

    @Inject
    public ApiActionsCreator(Dispatcher dispatcher, Context context, ApiService apiService) {
        super(dispatcher);
        this.context = context;
        this.apiService = apiService;
    }

    public void searchVideos(String query) {
        executeTask(apiService.searchVideos(query, context.getString(R.string.youtube_key)),
                response -> dispatcher.dispatch(Actions.RESPONSE_RECEIVED, Keys.RESPONSE, response),
                e -> dispatcher.dispatch(Actions.SEARCH_ERROR, Keys.EXCEPTION, e));
    }
}