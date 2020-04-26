package com.example.safetravelsclient.models.services;

import com.example.safetravelsclient.models.interfaces.PathElementInterface;

import java.util.UUID;
import org.json.JSONObject;

public class  LocationMarkerService extends BaseRemoteService
{
    // Adds 'markers' to the URL path.
    public LocationMarkerService()
    {
        super(ApiObject.MARKERS);
    }

    // Gets a single location marker along the route with the given userID and markerID.
    // URL = baseURL/markers?id={userID}&MarkerID={markerID}
    public ApiResponse<LocationMarker> getLocationMarker(UUID userID, int markerID)
    {
        String parameters = ("?id=" + userID.toString() + "&MarkerID=" + markerID);

        return this.readLocationMarkerDetailsFromRawResponse(
                this.<LocationMarker>getRequest(
                        this.buildPath(parameters)
                )
        );
    }

    // Gets a single location marker along the route with the given markerID.
    // URL = baseURL/markers/MarkerID/{markerID}
    public ApiResponse<LocationMarker> getLocationMarker(int markerID)
    {
        return this.readLocationMarkerDetailsFromRawResponse(
                this.<LocationMarker>getRequest(
                        this.buildPath(new PathElementInterface[]{MarkerApiMethod.MARKER_ID}, String.valueOf(markerID))
                )
        );
    }

    // Sends the single given location marker to the database.
    // URL = baseURL/markers
    public ApiResponse<LocationMarker> addLocationMarker(LocationMarker locationMarker)
    {
        //locationMarker.getUserID(
        return this.readLocationMarkerDetailsFromRawResponse(
                this.<LocationMarker>performPostRequest(
                        this.buildPath(), locationMarker.convertToJson()
                )
        );
    }


    // Deletes a single marker along the route with the give userID and markerID.
    // URL = baseURL/markers?id={userID}&MarkerID={markerID}
    public ApiResponse<String> deleteLocationMarker(UUID userID, int markerID)
    {
        String parameters = ("?id=" + userID.toString() + "&MarkerID=" + markerID);

        return this.deleteRequest(
                this.buildPath(parameters)
        );
    }

    // Reads the raw response data of the api response, from the get method, into the location marker of the api response.
    private ApiResponse<LocationMarker> readLocationMarkerDetailsFromRawResponse(ApiResponse<LocationMarker> apiResponse)
    {
        JSONObject rawJsonObject = this.rawResponseToJSONObject(apiResponse.getRawResponse());
        //String rawResponse = apiResponse.getRawResponse();

      //  if(rawResponse.length() > 0) {
           // JSONObject jsonObject = this.rawResponseToJSONObject(rawResponse);

            if(rawJsonObject != null)
            {
                //apiResponse.setValidResponse(true);
                apiResponse.setData(new LocationMarker().loadFromJson(rawJsonObject));
            }
          //  else
          //  {
          //      apiResponse.setErrorMessage("JSONObject == null, in read location marker details from raw response method.");
           //     apiResponse.setValidResponse(false);
           // }
        //}
      //  else
       // {
          //  apiResponse.setErrorMessage("Raw response is empty, in read location marker details from raw response method");
           // apiResponse.setValidResponse(false);
       // }

        return apiResponse;
    }
}
