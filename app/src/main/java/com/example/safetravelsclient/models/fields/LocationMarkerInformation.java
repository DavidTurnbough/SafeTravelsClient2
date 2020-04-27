package com.example.safetravelsclient.models.fields;

public enum LocationMarkerInformation{
    USER_ID("id"),
    ARRIVAL_TIME("ArrivalTime"),
    MARKER_ID("MarkerID"),
    LATITUDE("Latitude"),
    LONGITUDE("Longitude"),
    LOCATION("location"),
    TEMPERATURE("Temperature"),
    PRECIPITATION_CHANCE("precipChance"),
    WEATHER_DESCRIPTION("weatherDescription"),
    WIND_VELOCITY("windVelocity"),
    HUMIDITY("humidity"),
    TEMPERATURE_HIGH("temperatureHigh"),
    TEMPERATURE_LOW("temperatureLow");

    private String information;

    LocationMarkerInformation(String information)
    {
        this.information = information;
    }

    public String getInformation()
    {
        return this.information;
    }

}