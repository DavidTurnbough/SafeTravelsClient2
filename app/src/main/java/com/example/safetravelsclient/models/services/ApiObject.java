package com.example.safetravelsclient.models.services;

import com.example.safetravelsclient.models.interfaces.PathElementInterface;

public enum ApiObject implements PathElementInterface {
    NONE(""),
    WEATHER("routeWeatherInformation/");

    private String value;

    ApiObject(String value)
    {
        this.value = value;
    }

    @Override
    public String getPathValue()
    {
        return this.value;
    }

}
