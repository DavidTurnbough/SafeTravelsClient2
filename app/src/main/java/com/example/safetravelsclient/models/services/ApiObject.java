package com.example.safetravelsclient.models.services;

import com.example.safetravelsclient.models.interfaces.PathElementInterface;

//Create API Object for making calls to marker database
public enum ApiObject implements PathElementInterface {
    NONE(""),
    MARKERS("markers/");

    private String value;

    ApiObject(String value) {
        this.value = value;
    }

    @Override
    public String getPathValue() {
        return this.value;
    }

}