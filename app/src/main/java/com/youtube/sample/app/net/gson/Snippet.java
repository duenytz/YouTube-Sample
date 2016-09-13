package com.youtube.sample.app.net.gson;

public class Snippet {
    public final String publishedAt;
    public final String title;
    public final String description;
    public final Thumbnails thumbnails;

    public Snippet(String publishedAt, String title, String description, Thumbnails thumbnails) {
        this.publishedAt = publishedAt;
        this.title = title;
        this.description = description;
        this.thumbnails = thumbnails;
    }
}
