package com.example.safetravelsclient.models.services;

import org.json.JSONObject;

import java.util.UUID;

public class RouteService extends BaseRemoteService {

    RouteService(ApiObject apiObject) {
        super(apiObject.MARKERS);
    }

    // Returns weather conditions about all markers along the given route.
    // Buildpath = baseurl/markers/{route id}
    public <T extends Object> ApiResponse<T> getRoute(UUID routeID)
    {
        return this.getRequest(this.buildPath(routeID));
    }

    // Returns weather conditions about a given marker on a given route.
    // Buildpath = baseurl/markers?id={route id}&markerID={route marker id}
    public <T extends Object> ApiResponse<T> getMarker(UUID routeID, int markerID) {
        String parameters = ("%id=" + routeID.toString() + "&markerID" + markerID);

        return this.getRequest(this.buildPath(parameters));
    }


    public ApiResponse<LocationMarker> addMarker(LocationMarker locationMarker)
    {
        return readMarkerDetails(
                this.<LocationMarker>postRequest(this.buildPath(), locationMarker.convertToJson()));
    }

    private ApiResponse<LocationMarker> readMarkerDetails(ApiResponse<LocationMarker> apiResponse)
    {
        JSONObject rawJsonObject = this.rawResponseToJSONObject(
                apiResponse.getRawResponse());

        if(rawJsonObject != null)
        {
            apiResponse.setData(
                    (new LocationMarker()).loadFromJson(rawJsonObject));
        }

        return apiResponse;
    }

    // Delete route
    // Buildpath = baseURL/markers/{route ID}
    public <T extends Object> ApiResponse<T> deleteRoute(UUID routeID)
    {
        return this.deleteRequest(this.buildPath(routeID));
    }

    //Delete marker
    // Buildpath = baseURL/markers?id={route id}&markerID={marker id}
    public <T extends Object> ApiResponse<T> deleteMarker(UUID routeID, int markerID)
    {
        String parameters = ("%id=" + routeID.toString() + "&markerID=" + markerID);

        return this.deleteRequest(this.buildPath(parameters));
    }

    // Buildpath = baseURL/markers/{route ID}/markerID/{markerID value}


}
