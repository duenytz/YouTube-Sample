package com.youtube.sample.app.net.gson;

public class VideoItem {
    public final VideoId id;
    public final Snippet snippet;

    public VideoItem(VideoId id, Snippet snippet) {
        this.id = id;
        this.snippet = snippet;
    }
}
