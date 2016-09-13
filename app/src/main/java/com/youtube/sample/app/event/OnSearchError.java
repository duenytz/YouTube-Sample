package com.youtube.sample.app.event;

public class OnSearchError {
    public final Throwable throwable;

    public OnSearchError(Throwable throwable) {
        this.throwable = throwable;
    }
}
