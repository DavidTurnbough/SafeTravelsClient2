package com.example.safetravelsclient.models.services;

import com.example.safetravelsclient.models.interfaces.PathElementInterface;

public enum WeatherApiMethod implements PathElementInterface {
    NONE(""),
    CURRENT_LOCATION("currentLocation"),
    LOCATIONS_ALONG_ROUTE("locationsAlongRoute"),
    FORECASTED_WEATHER_CONDITIONS("forecastedWeatherConditions");

    private String value;

    WeatherApiMethod(String value)
    {
        this.value = value;
    }

    @Override
    public String getPathValue()
    {
        return this.value;
    }
}
