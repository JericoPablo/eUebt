package com.hsrt.euebt;

/**
 * Created by Johannes on 11.12.2016.
 */

/**
 * A class for representing one training unit that was done by the user at a specific point in time.
 */
public class Training {

    private String name;
    private long timestamp;

    /**
     * Instantiates a training unit with the current system time as timestamp and the name provided.
     * @param name The name of the training unit that shall be instantiated.
     */
    public Training(String name) {
        this.name  = name;
        this.timestamp = System.currentTimeMillis() / 1000;
    }

    /**
     * Instantiates a training unit with the given timestamp and name.
     * @param name The name of the training unit that shall be instantiated.
     * @param timestamp A specific timestamp that will be assigned to the training unit.
     */
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

    @Override
    public String toString() {
        return name;
    }
}