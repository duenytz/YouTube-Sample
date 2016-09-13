package com.youtube.sample.app.action.creators;

import com.youtube.sample.app.dispatcher.Dispatcher;

import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public abstract class ActionsCreator {
    protected Dispatcher dispatcher;

    public ActionsCreator(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    @SuppressWarnings("unchecked") //
    protected void executeTask(Observable observable, Action1<?> success, Action1<Throwable> error) {
        observable.subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).cache().subscribe(success, error);
    }
}
