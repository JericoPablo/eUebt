package com.hsrt.euebt;

/**
 * Created by Johannes on 11.12.2016.
 */

public class Training {

    private String name;
    private long timestamp;

    public Training(String name) {
        this.name  = name;
        this.timestamp = System.currentTimeMillis() / 1000;
    }

    public Training(String name, long timestamp) {
        this.name = name;
        this.timestamp = timestamp;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getName() {
        return name;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return name;
    }
}