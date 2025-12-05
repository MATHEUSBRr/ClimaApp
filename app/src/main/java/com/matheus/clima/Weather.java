package com.matheus.clima;

public class Weather {
    public final String date;
    public final String minTemp;
    public final String maxTemp;
    public final String humidity;
    public final String description;
    public final String icon;

    public Weather(String date, double min, double max, double humidity, String description, String icon) {
        this.date = date;
        this.minTemp = String.format("%.1f", min);
        this.maxTemp = String.format("%.1f", max);
        this.humidity = String.format("%.0f%%", humidity * 100.0);
        this.description = description;
        this.icon = icon;
    }
}
