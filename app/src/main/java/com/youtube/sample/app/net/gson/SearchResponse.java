package com.youtube.sample.app.net.gson;

import java.util.List;

public class SearchResponse {
    public final String nextPageToken;
    public final List<VideoItem> items;

    public SearchResponse(String nextPageToken, List<VideoItem> items) {
        this.nextPageToken = nextPageToken;
        this.items = items;
    }
}
