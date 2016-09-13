package com.youtube.sample.app.store;

import com.youtube.sample.app.action.Action;

import org.greenrobot.eventbus.EventBus;

import rx.android.schedulers.AndroidSchedulers;
import rx.subjects.Subject;

public abstract class Store {

    protected final EventBus bus;

    protected Store(EventBus bus) {
        this.bus = bus;
    }

    protected void emitStoreChange(Object event) {
        Subject.create(subscriber -> bus.post(event))
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public abstract void onEvent(Action action);
}
