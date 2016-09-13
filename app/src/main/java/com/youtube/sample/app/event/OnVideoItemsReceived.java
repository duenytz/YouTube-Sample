package com.youtube.sample.app.event;

import com.youtube.sample.app.net.gson.VideoItem;

import java.util.List;

public class OnVideoItemsReceived {
    public final List<VideoItem> videoItems;

    public OnVideoItemsReceived(List<VideoItem> videoItems) {
        this.videoItems = videoItems;
    }
}
