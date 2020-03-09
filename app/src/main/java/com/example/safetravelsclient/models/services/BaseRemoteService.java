package com.example.safetravelsclient.models.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.safetravelsclient.models.interfaces.PathElementInterface;

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

    //**********
    // Constructors.
    //**********
    BaseRemoteService(ApiObject apiObject)
    {
        this.apiObject = apiObject;
    }

    // URL: baseURL/markers
    public URL buildPath()
    {
        return this.buildPath(new PathElementInterface[0], "");
    }

    // URL: baseURL/markers/{userID}
    public URL buildPath(UUID userID)
    {
        return this.buildPath(new PathElementInterface[0], userID.toString());
    }

    // URL: baseURL/markers?{parameterValues}
    public URL buildPath(String parameterValue)
    {
        return this.buildPath(new PathElementInterface[0], parameterValue);
    }

    // URL: baseURL/markers/{pathElements}
    public URL buildPath(PathElementInterface[] pathElements)
    {
        return this.buildPath(pathElements, "");
    }

    // URL: baseURL/markers/{pathElements}{paramaters}
    public URL buildPath(PathElementInterface[] pathElements, String parameterValue)
    {
        String completePath = BASE_URL + this.apiObject.getPathValue();

        for (PathElementInterface pathElement : pathElements)
        {
            String pathEntry = pathElement.getPathValue();

            if (pathEntry.length() > 0)
            {
                completePath += pathEntry + URL_JOIN;
            }
        }

        // Parameters are added to the URL, if they exist.
        if (parameterValue.length() > 0)
        {
            completePath += parameterValue;
        }

        URL url;

        try
        {
            url = new URL(completePath);
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
            url = null;
        }

        return url;
    }

    // Get request, response from the given URL, saved into the apiResponse rawResponse.
    public <T extends Object> ApiResponse<T> getRequest(URL url){

        ApiResponse<T> apiResponse = new ApiResponse<>();

        if(url == null)
        {
            apiResponse.setValidResponse(false);
            apiResponse.setErrorMessage("Invalid URL sent to get request method.");
            return apiResponse;
        }

        String rawResponse = "";

        try {

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            int responseCode = conn.getResponseCode();

            if(this.isValidResponse(responseCode)) {

                apiResponse.setValidResponse(true);

                conn.setRequestMethod(GET_REQUEST_METHOD);

                conn.addRequestProperty(ACCEPT_REQUEST_PROPERTY, JSON_PAYLOAD_TYPE);

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String line = "";

                while ((line = reader.readLine()) != null) {
                    rawResponse += line;
                }

                reader.close();
                conn.disconnect();

                apiResponse.setRawResponse(rawResponse);
            }
            else
            {
                apiResponse.setValidResponse(false);
                apiResponse.setErrorMessage("Invalid response code in get request method.");
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();

            apiResponse.setValidResponse(false);
            apiResponse.setErrorMessage("IOException in guest request method: " + e.getMessage());
        }

        return apiResponse;
    }

    // Put request, sends JSON object to the given URL.
    public <T extends Object> ApiResponse<T> putRequest(URL url, JSONObject jsonObject)
    {
        ApiResponse<T> apiResponse = new ApiResponse<>();

        if(url == null)
        {
            apiResponse.setValidResponse(false);
            apiResponse.setErrorMessage("Invalid URL sent to putRequest.");
            return apiResponse;
        }

        try
        {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            int responseCode = conn.getResponseCode();

            if(this.isValidResponse(responseCode)) {

                apiResponse.setValidResponse(true);

                conn.setDoOutput(true);

                conn.setRequestMethod(PUT_REQUEST_METHOD);

                OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());

                out.write(jsonObject.toString());

                out.close();
                conn.disconnect();
            }
            else
            {
                apiResponse.setValidResponse(false);
                apiResponse.setErrorMessage("Invalid response code in putRequest.");
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();

            apiResponse.setValidResponse(false);
            apiResponse.setErrorMessage("IOException in putRequest: " + e.getMessage());
        }

        return apiResponse;
    }

    public ApiResponse<String> deleteRequest(URL url){

        ApiResponse<String> apiResponse = new ApiResponse<>();

        if(url == null)
        {
            apiResponse.setErrorMessage("Invalid URL in deleteRequest.");
            apiResponse.setValidResponse(false);
            return apiResponse;
        }

        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            int responseCode = conn.getResponseCode();

            if(this.isValidResponse(responseCode))
            {
                apiResponse.setValidResponse(true);

                conn.setRequestMethod(DELETE_REQUEST_METHOD);
                conn.setDoOutput(true);

                conn.connect();

                //***********
                // Not finished yet.
                //**********

                conn.disconnect();
            }
            else
            {
                apiResponse.setValidResponse(false);
                apiResponse.setErrorMessage("Invalid response code in delete request method.");
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        return apiResponse;
    }

    public JSONObject rawResponseToJSONObject(String rawResponse)
    {
        JSONObject jsonObject = new JSONObject();

        if(rawResponse.length() > 0)
        {
            try
            {
                jsonObject = new JSONObject(rawResponse);
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return jsonObject;
    }

    public JSONArray rawResponseToJSONArray(String rawResponse)
    {
        JSONArray jsonArray = null;

        if(!rawResponse.isEmpty())
        {
            try
            {
                jsonArray = new JSONArray(rawResponse);
            }
            catch(JSONException e)
            {
                e.printStackTrace();
            }
        }
        return jsonArray;
    }

    // Ensure the response code from the URL is valid.
    private boolean isValidResponse(int responseCode)
    {
        return (   (responseCode == HttpURLConnection.HTTP_OK)
                || (responseCode == HttpURLConnection.HTTP_CREATED)
                || (responseCode == HttpURLConnection.HTTP_ACCEPTED)
                || (responseCode == HttpURLConnection.HTTP_NO_CONTENT));
    }
}
