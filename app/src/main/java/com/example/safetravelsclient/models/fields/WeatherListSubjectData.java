package com.example.safetravelsclient.models.fields;

import android.widget.ImageView;
import android.widget.TextView;

public class WeatherListSubjectData
{
    private int marker_id;
    private TextView tv_marker_id;
    private String location;
    private TextView tv_location;
    private String temperature;
    private TextView tv_temperature;
    private String precipitation;
    private TextView tv_precipitation;

    private String wind_velocity;
    private TextView tv_wind_velocity;
    private String image;
    private ImageView iv_image;

    public WeatherListSubjectData()
    {
        this.marker_id = -1;
        this.location = "";
        this.temperature = "";
        this.precipitation = "";

        this.wind_velocity = "";
        this.image = "";
    }

    public WeatherListSubjectData(int id, String loc, String temp, String prec, String wind_v)
    {
        this.marker_id = id;
        this.location = loc;
        this.temperature = temp;
        this.precipitation = prec;
        this.wind_velocity = wind_v;
    }

    public WeatherListSubjectData(int id, String loc, String temp, String prec, String wind_v, String img)
    {
        this.marker_id = id;
        this.location = loc;
        this.temperature = temp;
        this.precipitation = prec;
        this.wind_velocity = wind_v;
        this.image = img;
    }

    public WeatherListSubjectData(WeatherListSubjectData copy)
    {
        this.marker_id = copy.getMarkerId();
        this.location = copy.getLocation();
        this.temperature = copy.getTemperature();
        this.precipitation = copy.getPrecipitation();
        this.wind_velocity = copy.getWindVelocity();
    }

    public String packageStrings()
    {
        String out = "";
        out = out + this.location + ", ";
        out = out + this.temperature + ", ";
        out = out + this.precipitation + ", ";
        out = out + this.wind_velocity + ", ";
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
    public TextView getTvMarkerId() {return this.tv_marker_id;}
    public void setMarkerId(int marker) {this.marker_id = marker;}
    public void setTvMarkerId(TextView marker) {this.tv_marker_id = marker;}

    public String getLocation() {return this.location;}
    public TextView getTvLocation() {return this.tv_location;}
    public void setLocation(String loc) {this.location = loc;}
    public void setTvLocation(TextView loc) {this.tv_location = loc;}

    public String getTemperature() {return this.temperature;}
    public TextView getTvTemperature() {return this.tv_temperature;}
    public void setTemperature(String temp) {this.temperature = temp;}
    public void setTvTemperature(TextView temp) {this.tv_temperature = temp;}

    public String getPrecipitation() {return this.precipitation;}
    public TextView getTvPrecipitation() {return this.tv_precipitation;}
    public void setPrecipitation(String prec) {this.precipitation = prec;}
    public void setTvPrecipitation(TextView prec) {this.tv_precipitation = prec;}


    public String getWindVelocity() {return this.wind_velocity;}
    public TextView getTvWindVelocity() {return this.tv_wind_velocity;}
    public void setWindVelocity(String wind_v) {this.wind_velocity = wind_v;}
    public void setTvWindVelocity(TextView wind_v) {this.tv_wind_velocity = wind_v;}


    public String getImg() {return this.image;}
    public void setImg(String img) {this.image = img;}
}
