//***************************************
// Written By David Turnbough and William Henness
//*******************************************

package com.example.safetravelsclient.models.services;

import com.example.safetravelsclient.models.fields.LocationMarkerInformation;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

//**********
// Stores data about a given location along the user's route.
//**********
public class LocationMarker {

    //**********
    // Variable Declarations.
    //*********
    private UUID userID;
    private Date arrivalTime;
    private String location;
    private String weatherDescription;
    private String windVelocity;
    private int markerID;
    private int temperature;
    private int precipitationChance;
    private int humidity;
    private int temperatureHigh;
    private int temperatureLow;
    private double latitude;
    private double longitude;

    //**********
    // Constructors.
    //**********
    public LocationMarker()
    {

        this.userID = new UUID(0,0);
        this.markerID = 0;
        this.latitude = 0;
        this.longitude = 0;
        this.temperature = 0;
        this.precipitationChance = 0;
        this.location = "location";
        this.arrivalTime = new Date();
    }



    //**********
    // Getter Methods.
    //**********
    public UUID getUserID() { return this.userID; }

    public int getMarkerID() { return this.markerID; }

    public double getLatitude() { return this.latitude; }

    public double getLongitude() { return this.longitude; }

    public int getHumidity() { return this.humidity; }

    public int getTemperatureHigh() { return this.temperatureHigh; }

    public int getTemperatureLow() { return this.temperatureLow; }

    public Date getArrivalTime() { return arrivalTime; }

    public int getTemperature() { return this.temperature; }

    public int getPrecipitationChance() { return this.precipitationChance; }

    public String getLocation() { return this.location; }

    public String getWeatherDescription() { return this.weatherDescription; }

    public String getWindVelocity() {return this.windVelocity; }

    //**********
    // Setter Methods.
    //**********
    public void setUserID(UUID userID)
    {
        this.userID = userID;
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
            jsonObject.put(LocationMarkerInformation.USER_ID.getInformation(), this.userID.toString());
            jsonObject.put(LocationMarkerInformation.MARKER_ID.getInformation(), this.markerID);
            jsonObject.put(LocationMarkerInformation.ARRIVAL_TIME.getInformation(), (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S", Locale.US)).format(this.arrivalTime));
            jsonObject.put(LocationMarkerInformation.LATITUDE.getInformation(), this.latitude);
            jsonObject.put(LocationMarkerInformation.LONGITUDE.getInformation(), this.longitude);
            jsonObject.put(LocationMarkerInformation.LOCATION.getInformation(), this.location);
            jsonObject.put(LocationMarkerInformation.PRECIPITATION_CHANCE.getInformation(), this.precipitationChance);
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public LocationMarker loadFromJson(JSONObject rawJsonObject)
    {
        String temp = rawJsonObject.optString(LocationMarkerInformation.USER_ID.getInformation());

        if(temp.length() > 0)
        {
            this.userID = UUID.fromString(temp);
        }

        try {
            this.markerID = rawJsonObject.optInt(LocationMarkerInformation.MARKER_ID.getInformation());


            temp = rawJsonObject.optString(LocationMarkerInformation.ARRIVAL_TIME.getInformation());

            if(temp.length() > 0) {
                try {
                    this.arrivalTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S", Locale.US).parse(temp);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            if(temp.length() > 0) {
                this.longitude = rawJsonObject.getDouble(LocationMarkerInformation.LONGITUDE.getInformation());
                this.latitude = rawJsonObject.getDouble(LocationMarkerInformation.LATITUDE.getInformation());
                this.location = rawJsonObject.getString(LocationMarkerInformation.LOCATION.getInformation());
            }

            this.precipitationChance = rawJsonObject.getInt(LocationMarkerInformation.PRECIPITATION_CHANCE.getInformation());


        }catch (JSONException e) {
            e.printStackTrace();
        }

        return this;
    }

    public void printLocationMarker()
    {
        System.out.println("ID : " + this.userID.toString());
        System.out.println("Arrival Time : " + this.arrivalTime);
        System.out.printf(""
                + "Location : %s%n"
                + "Marker ID : %d%n"
                + "Temperature : %d%n"
                + "Precipitation : %d%n"
                + "Latitude : %f%n"
                + "Longitude : %f%n",
                this.location, this.markerID, this.temperature,
                this.precipitationChance, this.latitude, this.longitude);
    }
}