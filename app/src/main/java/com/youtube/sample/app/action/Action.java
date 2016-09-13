package com.youtube.sample.app.action;

import java.util.HashMap;

public class Action {
    private int type;
    private final HashMap<Integer, Object> data;

    Action(int type, HashMap<Integer, Object> data) {
        this.type = type;
        this.data = data;
    }

    public static Builder type(int type) {
        return new Builder().with(type);
    }

    public int getType() {
        return type;
    }

    public HashMap getData() {
        return data;
    }

    public static class Builder {
        private int type;
        private HashMap<Integer, Object> data;

        public Builder with(int type) {
            this.type = type;
            this.data = new HashMap<>();
            return this;
        }

        public Builder bundle(int key, Object value) {
            if (value == null) {
                throw new IllegalArgumentException("Value may not be null.");
            }
            data.put(key, value);
            return this;
        }

        public Action build() {
            return new Action(type, data);
        }
    }
}
