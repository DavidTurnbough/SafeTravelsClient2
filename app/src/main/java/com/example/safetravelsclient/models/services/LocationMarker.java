package com.example.safetravelsclient.models.services;

import com.example.safetravelsclient.models.fields.LocationMarkerInformation;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class LocationMarker {

    UUID getUserId;
    Date arrivalTime;
    String location;
    int markerID;
    int temperature;
    int precipitationChance;
    double latitude;
    double longitude;

    //**********
    // Constructors.
    //**********
    public LocationMarker()
    {
        this.getUserId = new UUID(0,0);
        this.markerID = 0;
        this.latitude = 0;
        this.longitude = 0;
        this.temperature = 0;
        this.arrivalTime = new Date();
        this.precipitationChance = 0;
        this.location = "";
    }

    //**********
    // Getter Methods.
    //**********
    public UUID getUserId()
    {
        return this.getUserId;
    }

    public int getMarkerID()
    {
        return this.markerID;
    }

    public double getLatitude()
    {
        return this.latitude;
    }

    public double getLongitude()
    {
        return this.longitude;
    }

    public Date getArrivalTime()
    {
        return arrivalTime;
    }

    public int getTemperature()
    {
        return this.temperature;
    }

    public int getPrecipitationChance()
    {
        return this.precipitationChance;
    }

    public String getLocation()
    {
        return this.location;
    }

    //**********
    // Setter Methods.
    //**********
    public void setRouteID(UUID getUserId)
    {
        this.getUserId = getUserId;
    }

    public void setMarkerID(int markerID)
    {
        this.markerID = markerID;
    }

    public void setLatitude(double latitude)
    {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude)
    {
        this.longitude = longitude;
    }

    public void setTemperature(int temperature)
    {
        this.temperature = temperature;
    }

    public void setArrivalTime(Date arrivalTime)
    {
        this.arrivalTime = arrivalTime;
    }

    public void setPrecipitationChance(int precipitationChance)
    {
        this.precipitationChance = precipitationChance;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    //**********
    // Other Methods.
    //**********
    public JSONObject convertToJson()
    {
        JSONObject jsonObject = new JSONObject();

        try
        {
            jsonObject.put(LocationMarkerInformation.ROUTE_ID.getInformation(), this.getUserId.toString());
            jsonObject.put(LocationMarkerInformation.MARKER_ID.getInformation(), this.markerID);
            jsonObject.put(LocationMarkerInformation.ARRIVAL_TIME.getInformation(), (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S", Locale.US)).format(this.arrivalTime));
            jsonObject.put(LocationMarkerInformation.LATITUDE.getInformation(), this.latitude);
            jsonObject.put(LocationMarkerInformation.LONGITUDE.getInformation(), this.longitude);
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public LocationMarker loadFromJson(JSONObject rawJsonObject)
    {
        String temp = rawJsonObject.optString(LocationMarkerInformation.ROUTE_ID.getInformation());

        if(temp.length() > 0)
        {
            this.getUserId = UUID.fromString(temp);
        }

        try {
            this.markerID = rawJsonObject.optInt(LocationMarkerInformation.MARKER_ID.getInformation());


            temp = rawJsonObject.optString(LocationMarkerInformation.ARRIVAL_TIME.getInformation());

            if(temp.length() > 0)
            {
                try
                {
                    this.arrivalTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S", Locale.US).parse(temp);
                }
                catch (ParseException e) {
                    e.printStackTrace();
                }
            }


            this.longitude = rawJsonObject.getDouble(LocationMarkerInformation.LONGITUDE.getInformation());
            this.latitude = rawJsonObject.getDouble(LocationMarkerInformation.LATITUDE.getInformation());

            //this.location = rawJsonObject.optString(LocationMarkerInformation.LOCATION.getInformation());
            //this.temperature = rawJsonObject.optInt(LocationMarkerInformation.TEMPERATURE.getInformation());
            //this.precipitationChance = rawJsonObject.getInt(LocationMarkerInformation.PRECIPITATION_CHANCE.getInformation());

        }catch (JSONException e) {
            e.printStackTrace();
        }

        return this;
    }
}
