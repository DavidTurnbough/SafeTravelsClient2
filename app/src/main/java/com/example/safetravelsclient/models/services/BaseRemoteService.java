package com.example.safetravelsclient.models.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import com.example.safetravelsclient.models.interfaces.PathElementInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class BaseRemoteService
{
    private static final String URL_JOIN = "/";
    private static final String GET_REQUEST_METHOD = "GET";
    private static final String PUT_REQUEST_METHOD = "PUT";
    private static final String POST_REQUEST_METHOD = "POST";
    private static final String DELETE_REQUEST_METHOD = "DELETE";
    private static final String UTF8_CHARACTER_ENCODING = "UTF8";
    private static final String ACCEPT_REQUEST_PROPERTY = "Accept";
    private static final String JSON_PAYLOAD_TYPE = "application/json";
    private static final String CONTENT_TYPE_REQUEST_PROPERTY = "Content-Type";
    private static final String BASE_URL = "https://weatherways-server.herokuapp.com/api/";

    private ApiObject apiObject;

    BaseRemoteService(ApiObject apiObject)
    {
        this.apiObject = apiObject;
    }

    URL buildPath()
    {
        return this.buildPath(new PathElementInterface[0], "");
    }

    URL buildPath(UUID userID)
    {
        return this.buildPath(new PathElementInterface[0], userID.toString());
    }

    URL buildPath(String parameterValue)
    {
        return this.buildPath(new PathElementInterface[0], parameterValue);
    }

    URL buildPath(PathElementInterface[] pathElements)
    {
        return this.buildPath(pathElements, "");
    }

    URL buildPath(PathElementInterface[] pathElements, String parameterValue)
    {
        String completePath = BASE_URL + this.apiObject.getPathValue();

        for(PathElementInterface pathElement : pathElements)
        {
            String pathEntry = pathElement.getPathValue();

            if(pathEntry.length() > 0)
            {
                completePath += pathEntry + URL_JOIN;
            }
        }

        if(parameterValue.length() > 0)
        {
            completePath += parameterValue;
        }

        URL connectionUrl;
        try
        {
            connectionUrl = new URL(completePath);
        }
        catch(MalformedURLException e)
        {
            e.printStackTrace();
            connectionUrl = null;
        }

        return connectionUrl;

    }

    <T extends Object> ApiResponse<T> getRequest(URL connectionUrl){
        ApiResponse<T> apiResponse = new ApiResponse<>();

        String rawResponse = "";

        if(connectionUrl == null)
        {
            return apiResponse.setValidResponse(false).setMessage("Invalid network path provided.");
        }

        try {
            HttpURLConnection conn = (HttpURLConnection) connectionUrl.openConnection();

            conn.setRequestMethod(GET_REQUEST_METHOD);

            conn.addRequestProperty(ACCEPT_REQUEST_PROPERTY, JSON_PAYLOAD_TYPE);

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = "";

            while((line = reader.readLine()) != null)
            {
                rawResponse += line;
            }

            reader.close();

            conn.disconnect();
        }
        catch(IOException e) {
            e.printStackTrace();
        }

        return apiResponse.setRawResponse(rawResponse);
    }

    <T extends Object> ApiResponse<T> putRequest(URL connectionUrl, JSONObject jsonObject)
    {
        return this.uploadRequest(PUT_REQUEST_METHOD, connectionUrl, jsonObject);
    }

    <T extends Object> ApiResponse<T> postRequest(URL connectionUrl, JSONObject jsonObject)
    {
        return this.uploadRequest(POST_REQUEST_METHOD, connectionUrl, jsonObject);
    }


    // Perform Upload Request
    private <T extends Object> ApiResponse<T> uploadRequest(String requestType, URL connectionURL,
                                                                   JSONObject jsonObject) {
        ApiResponse<T> apiResponse = new ApiResponse<>();


        if (connectionURL == null) {
            apiResponse.setMessage("Invalid URL");
            apiResponse.setValidResponse(false);
            return apiResponse;
        }

        try {
            HttpURLConnection conn = (HttpURLConnection) connectionURL.openConnection();

            conn.setDoOutput(true);

            conn.setRequestMethod(requestType);

            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());

            out.write(jsonObject.toString());

            out.close();

            conn.disconnect();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            apiResponse.setMessage(e.getMessage());
            apiResponse.setValidResponse(false);
        }

        return apiResponse;
    }

    // perform Delete Request
    public <T extends Object> ApiResponse<T> deleteRequest(URL connectionURL)
    {
        ApiResponse<T> apiResponse = new ApiResponse<>();

        if(connectionURL == null)
        {
            apiResponse.setMessage("Invalid URL");
            apiResponse.setValidResponse(false);

            return apiResponse;
        }

        try {
            HttpURLConnection conn = (HttpURLConnection) connectionURL.openConnection();

            conn.setDoOutput(true);

            conn.setRequestMethod(DELETE_REQUEST_METHOD);

            //***********
            //Not done yet.
            //***********

            conn.disconnect();


        } catch(IOException e)
        {
            e.printStackTrace();
            apiResponse.setMessage(e.getMessage());
            apiResponse.setValidResponse(false);

        }

        return apiResponse;
    }


    JSONObject rawResponseToJSONObject(String rawResponse)
    {
        JSONObject jsonObject = null;

        if(rawResponse.length() > 0)
        {
            try {
                jsonObject = new JSONObject(rawResponse);
            }
            catch(JSONException e) {
                e.printStackTrace();
            }
        }

        return jsonObject;
    }

    JSONArray rawResponseToJSONArray(String rawResponse)
    {
        JSONArray jsonArray = null;

        if(rawResponse.length() > 0)
        {
            try{
                jsonArray = new JSONArray(rawResponse);
            }
            catch(JSONException e)
            {
                e.printStackTrace();
            }
        }

        return jsonArray;
    }

    private boolean isValidResponse(int responseCode)
    {
        return( (responseCode == HttpURLConnection.HTTP_OK) ||
                (responseCode == HttpURLConnection.HTTP_CREATED) ||
                (responseCode == HttpURLConnection.HTTP_ACCEPTED) ||
                (responseCode == HttpURLConnection.HTTP_NO_CONTENT));
    }
}
