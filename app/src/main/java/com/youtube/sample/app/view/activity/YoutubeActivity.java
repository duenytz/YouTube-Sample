package com.youtube.sample.app.view.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.youtube.sample.app.App;
import com.youtube.sample.app.R;
import com.youtube.sample.app.action.creators.ApiActionsCreator;
import com.youtube.sample.app.event.OnNoResultsFound;
import com.youtube.sample.app.event.OnSearchError;
import com.youtube.sample.app.event.OnVideoClicked;
import com.youtube.sample.app.event.OnVideoItemsReceived;
import com.youtube.sample.app.net.gson.VideoItem;
import com.youtube.sample.app.store.ApiStore;
import com.youtube.sample.app.view.adapter.VideosAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class YoutubeActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private static final int VIDEO_LIST = 0;
    private static final int INFO_TEXT = 1;
    private static final int PROGRESS_BAR = 2;

    @Inject
    EventBus bus;
    @Inject
    ApiActionsCreator actionsCreator;
    @Inject
    ApiStore apiStore;

    @Bind(R.id.video_list)
    RecyclerView videoList;
    @Bind(R.id.info_text)
    TextView infoText;
    @Bind(R.id.progress_bar)
    ProgressBar progressBar;

    private VideosAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getApplication()).getComponent().inject(this);
        setContentView(R.layout.activity_youtube);
        ButterKnife.bind(this);

        setupVideoList(apiStore.getVideoItems());
    }

    @Override
    protected void onResume() {
        super.onResume();
        bus.register(this);
        bus.register(apiStore);
    }

    @Override
    protected void onPause() {
        bus.unregister(apiStore);
        bus.unregister(this);
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        showView(PROGRESS_BAR);
        actionsCreator.searchVideos(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText.isEmpty()) {
            infoText.setText(R.string.initial_advice_text);
            showView(INFO_TEXT);
        }
        return false;
    }

    private void setupVideoList(List<VideoItem> videoItems) {
        if (adapter == null) {
            adapter = new VideosAdapter(getApplicationContext());
        }
        if (videoList.getAdapter() == null) {
            videoList.setAdapter(adapter);
            videoList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            videoList.setItemAnimator(null);
        }
        adapter.setVideos(videoItems);
    }

    @Subscribe
    public void onEvent(OnVideoItemsReceived event) {
        if (event.videoItems.isEmpty()) {
            infoText.setText(R.string.no_results_error);
            showView(INFO_TEXT);
            return;
        }
        setupVideoList(event.videoItems);
        showView(VIDEO_LIST);
    }

    @Subscribe
    public void onEvent(OnNoResultsFound event) {
        infoText.setText(R.string.no_results_error);
        showView(INFO_TEXT);
    }

    @Subscribe
    public void onEvent(OnSearchError event) {
        infoText.setText(event.throwable.getMessage());
        showView(INFO_TEXT);
    }

    @Subscribe
    public void onEvent(OnVideoClicked event) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.youtube_video_url, event.videoId))));
    }

    private void showView(int view) {
        videoList.setVisibility(view == VIDEO_LIST ? View.VISIBLE : View.GONE);
        infoText.setVisibility(view == INFO_TEXT ? View.VISIBLE : View.GONE);
        progressBar.setVisibility(view == PROGRESS_BAR ? View.VISIBLE : View.GONE);
    }
}
