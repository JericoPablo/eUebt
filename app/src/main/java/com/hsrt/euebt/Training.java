package com.hsrt.euebt;

/**
 * Created by Johannes on 11.12.2016.
 */

/**
 * A class for representing one training unit that was done by the user at a specific place and point in time.
 */
public class Training {

    private String name;
    private String location;
    private long timestamp;

    /**
     * Instantiates a training unit with an empty location, the current system time as timestamp and the name provided.
     * @param name The name of the training unit that shall be instantiated.
     */
    public Training(String name) {
        this.name  = name;
        this.timestamp = System.currentTimeMillis() / 1000;
        this.location = "";
    }

    /**
     * Instantiates a training unit with the current system time as timestamp and the name provided.
     * @param name The name of the training unit that shall be instantiated.
     * @param location The location where this specific training was done as a string.
     */
    public Training(String name, String location) {
        this.name  = name;
        this.timestamp = System.currentTimeMillis() / 1000;
        this.location = location;
    }

    /**
     * Instantiates a training unit with the given timestamp and name.
     * @param name The name of the training unit that shall be instantiated.
     * @param timestamp A specific timestamp that will be assigned to the training unit.
     * @param location The location where this specific training was done as a string.
     */
    public Training(String name, long timestamp, String location) {
        this.name = name;
        this.timestamp = timestamp;
        this.location = location;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    /**
     * The overridden toString function.
     * @return Returns the name of this training unit.
     */
    @Override
    public String toString() {
        return name;
    }
}