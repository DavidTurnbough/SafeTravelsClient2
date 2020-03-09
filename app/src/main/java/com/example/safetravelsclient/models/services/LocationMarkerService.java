package com.example.safetravelsclient.models.services;

import com.example.safetravelsclient.models.interfaces.PathElementInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LocationMarkerService extends BaseRemoteService
{
    // Adds 'markers' to the URL path.
    LocationMarkerService()
    {
        super(ApiObject.MARKERS);
    }

    public ApiResponse<List<LocationMarker>> getRoute(UUID userID)
    {
        // Gets the raw response list of all location markers with the given user id.
        ApiResponse<List<LocationMarker>> apiResponse = this.getRequest(
                this.buildPath(userID.toString())
        );

        // Converts the raw response from the get request into an array list of location markers.
        JSONArray rawJsonArray = this.rawResponseToJSONArray(apiResponse.getRawResponse());

        if(rawJsonArray != null)
        {
            ArrayList<LocationMarker> locationMarkers = new ArrayList<>(rawJsonArray.length());

            for(int i = 0; i < rawJsonArray.length(); ++i)
            {
                try{
                    locationMarkers.add((new LocationMarker()).loadFromJson(rawJsonArray.getJSONObject(i)));
                }
                catch(JSONException e)
                {
                    apiResponse.setErrorMessage(e.getMessage());
                }
            }

            apiResponse.setData(locationMarkers);
        }
        else
        {
            apiResponse.setData(new ArrayList<LocationMarker>(0));
        }

        return apiResponse;
    }


    // Sends the single given location marker in json format to the database.
    // URL = baseURL/markers
    //**********
    // ApiResponse myResponse = new ApiResponse<LocationMarker>();
    // LocationMarkerService myService = new LocationMarkerService();
    // myResponse = myService.getLocationMarker(userID);
    // LocationMarker myMarker = new LocationMarker();
    // myMarker = (LocationMarker) myResponse.getData();
    //**********
    public ApiResponse<LocationMarker> addLocationMarker(LocationMarker locationMarker)
    {
        return this.readLocationMarkerDetailsFromRawResponse(
                this.<LocationMarker>putRequest(
                        this.buildPath(), locationMarker.convertToJson()
                )
        );
    }

    /*
    // Deletes the given location marker from the database.
    // URL: baseURL/markers
    public ApiResponse<LocationMarker> deleteLocationMarker(LocationMarker locationMarker)
    {
        return this.<LocationMarker>deleteRequest(
                this.buildPath(), locationMarker.convertToJson()
        );
    }
     */


    // Reads the raw response data of the api response, from the get method, into the location marker of the api response.
    private ApiResponse<LocationMarker> readLocationMarkerDetailsFromRawResponse(ApiResponse<LocationMarker> apiResponse)
    {
        String rawResponse = apiResponse.getRawResponse();

        if(rawResponse.length() > 0) {
            JSONObject jsonObject = this.rawResponseToJSONObject(rawResponse);

            if(jsonObject != null)
            {
                apiResponse.setValidResponse(true);
                apiResponse.setData(new LocationMarker().loadFromJson(jsonObject));
            }
            else
            {
                apiResponse.setErrorMessage("JSONObject == null, in read location marker details from raw response method.");
                apiResponse.setValidResponse(false);
            }
        }
        else
        {
            apiResponse.setErrorMessage("Raw response is empty, in read location marker details from raw response method");
            apiResponse.setValidResponse(false);
        }

        return apiResponse;
    }
}
