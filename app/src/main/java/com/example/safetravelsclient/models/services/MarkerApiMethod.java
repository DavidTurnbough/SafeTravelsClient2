package com.example.safetravelsclient.models.services;

import com.example.safetravelsclient.models.interfaces.PathElementInterface;

public enum MarkerApiMethod implements PathElementInterface {
    NONE(""),
    ARRIVAL_TIME("ArrivalTime/"),
    MARKER_ID("MarkerID/");

    private String value;

    MarkerApiMethod(String value)
    {
        this.value = value;
    }

    @Override
    public String getPathValue()
    {
        return value;
    }

}
