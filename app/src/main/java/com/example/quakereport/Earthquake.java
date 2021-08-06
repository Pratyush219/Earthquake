package com.example.quakereport;

public class Earthquake {
    private final double magnitude;
    private final String location;
    private final String date;
    private final String time;
    private final String url;

    public String getTime() {
        return time;
    }

    public String getUrl() {
        return url;
    }

    public Earthquake(double magnitude, String location, String date, String time, String url) {
        this.magnitude = magnitude;
        this.location = location;
        this.date = date;
        this.time = time;
        this.url = url;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public String getLocation() {
        return location;
    }

    public String getDate() {
        return date;
    }
}
