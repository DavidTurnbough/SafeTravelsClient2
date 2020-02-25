package com.example.safetravelsclient.models.fields;

public enum LocationMarkerInformation{
    ROUTE_ID("id"),
    ARRIVAL_TIME("arrivalTime"),
    MARKER_ID("markerID"),
    LATITUDE("latitude"),
    LONGITUDE("longitude"),
    LOCATION("location"),
    TEMPERATURE("temperature"),
    PRECIPITATION_CHANCE("precipitationChance");

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
