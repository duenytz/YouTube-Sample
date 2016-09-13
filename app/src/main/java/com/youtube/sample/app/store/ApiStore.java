package com.youtube.sample.app.store;


import com.youtube.sample.app.net.gson.VideoItem;

import java.util.List;

public interface ApiStore {
    List<VideoItem> getVideoItems();
}
