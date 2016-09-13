package com.youtube.sample.app.dispatcher;

import com.youtube.sample.app.action.Action;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Singleton;

@Singleton //
public class Dispatcher {
    private EventBus bus;

    public Dispatcher(EventBus bus) {
        this.bus = bus;
    }

    public void dispatch(int action, Object... data) {
        if (action < 0) {
            throw new IllegalArgumentException("Type must not be empty");
        }

        if (data.length % 2 != 0) {
            throw new IllegalArgumentException("Data must be a valid list of key,value pairs");
        }

        Action.Builder actionBuilder = Action.type(action);
        int i = 0;
        while (i < data.length) {
            int key = (int) data[i++];
            Object value = data[i++];
            actionBuilder.bundle(key, value);
        }
        post(actionBuilder.build());
    }

    private void post(final Object event) {
        bus.post(event);
    }
}
