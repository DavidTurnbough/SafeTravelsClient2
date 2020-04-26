package com.example.safetravelsclient.models.fields;

import com.example.safetravelsclient.models.transition.WeatherDataTransition;

public class WeatherTransitionData
{

    private int marker_id;
    private String location;
    private String temperature;
    private String temperature_high;
    private String temperature_low;
    private String precipitation;
    private String humidity;
    private String description;
    private String wind_velocity;
    private String wind_direction;
    private String image;

    public WeatherTransitionData()
    {
        this.location = "";
        this.temperature = "";
        this.temperature_high = "";
        this.temperature_low = "";
        this.precipitation = "";
        this.humidity = "";
        this.description = "";
        this.wind_velocity = "";
        this.wind_direction = "";
        this.image = "";
    }

    public WeatherTransitionData(int id, String loc, String temp, String prec, String wind)
    {
        this.marker_id = id;
        this.location = loc;
        this.temperature = temp;
        this.precipitation = prec;
        this.wind_velocity = wind;
    }

    public WeatherTransitionData(int id, String loc, String temp, String temp_high, String temp_low, String prec, String humi, String desc, String wind_v, String wind_d)
    {
        this.marker_id = id;
        this.location = loc;
        this.temperature = temp;
        this.temperature_high = temp_high;
        this.temperature_low = temp_low;
        this.precipitation = prec;
        this.humidity = humi;
        this.description = desc;
        this.wind_velocity = wind_v;
        this.wind_direction = wind_d;
    }

    public WeatherTransitionData(String loc, String temp, String prec, String img)
    {
        this.location = loc;
        this.temperature = temp;
        this.precipitation = prec;
        this.image = img;
    }

    public WeatherTransitionData(WeatherListSubjectData copy)
    {
        this.marker_id = copy.getMarkerId();
        this.location = copy.getLocation();
        this.temperature = copy.getTemperature();
        this.precipitation = copy.getPrecipitation();
        //this.humidity = copy.getHumidity();
        //this.description = copy.getDescription();
        this.wind_velocity = copy.getWindVelocity();
        //this.wind_direction = copy.getWindDirection();
    }

    public WeatherTransitionData(WeatherTransitionData copy)
    {
        this.location = copy.getLocation();
        this.temperature = copy.getTemperature();
        this.temperature_high = copy.getTemperatureHigh();
        this.temperature_low = copy.getTemperatureLow();
        this.precipitation = copy.getPrecipitation();
        this.humidity = copy.getHumidity();
        this.description = copy.getDescription();
        this.wind_velocity = copy.getWindVelocity();
        this.wind_direction = copy.getWindDirection();
    }

    public String packageStrings()
    {
        String out = "";
        out = out + this.marker_id + ", ";
        out = out + this.location + ", ";
        out = out + this.temperature + ", ";
        out = out + this.temperature_high + ", ";
        out = out + this.temperature_low + ", ";
        out = out + this.precipitation + ", ";
        out = out + this.humidity + ", ";
        out = out + this.description + ", ";
        out = out + this.wind_velocity + ", ";
        out = out + this.wind_direction;
        return out;
    }

    public int toInt(String in)
    {
        int out = 0;
        int tens = 1;
        for(int i = 0; i < in.length(); i++)
        {
            out = out + (tens * (in.charAt(in.length() - 1 - i) - (int)'0'));
            tens = tens * 10;
        }
        return out;
    }

    public int getMarkerId() {return this.marker_id;}
    public void setMarkerId(int id) {this.marker_id = id;}

    public String getLocation() {return this.location;}
    public void setLocation(String loc) {this.location = loc;}

    public String getTemperature() {return this.temperature;}
    public void setTemperature(String temp) {this.temperature = temp;}

    public String getTemperatureHigh() {return this.temperature_high;}
    public void setTemperatureHigh(String temp_high) {this.temperature_high = temp_high;}

    public String getTemperatureLow() {return this.temperature_low;}
    public void setTemperatureLow(String temp_low) {this.temperature_low = temp_low;}

    public String getPrecipitation() {return this.precipitation;}
    public void setPrecipitation(String prec) {this.precipitation = prec;}

    public String getHumidity() {return this.humidity;}
    public void setHumidity(String humi) {this.humidity = humi;}

    public String getDescription() {return this.description;}
    public void setDescription(String desc) {this.description = desc;}

    public String getWindVelocity() {return this.wind_velocity;}
    public void setWindVelocity(String wind_v) {this.wind_velocity = wind_v;}

    public String getWindDirection() {return this.wind_direction;}
    public void setWindDirection(String wind_d) {this.wind_direction = wind_d;}
}
