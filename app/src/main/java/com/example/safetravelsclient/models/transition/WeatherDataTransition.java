package com.example.safetravelsclient.models.transition;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.safetravelsclient.models.fields.WeatherTransitionData;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

public class WeatherDataTransition implements Parcelable
{
    private UUID id;
    public UUID getId() {return this.id;}
    public WeatherDataTransition setId(UUID id) {this.id = id; return this;}
    ArrayList<WeatherTransitionData> data_array;
    WeatherTransitionData marker_data;
    int marker_id;

    public WeatherDataTransition()
    {
        //this.data_array = new ArrayList<WeatherTransitionData>();
        this.marker_data = new WeatherTransitionData();
        this.marker_id = -1;
    }

    public WeatherDataTransition(WeatherTransitionData weather_data)
    {
        this.marker_data = new WeatherTransitionData(weather_data);
        this.marker_id = -1;
    }

    public WeatherDataTransition(WeatherDataTransition copy)
    {
        /*Iterator<WeatherTransitionData> iter = copy.data_array.iterator();
        this.data_array = new ArrayList<WeatherTransitionData>();
        while(iter.hasNext())
        {
            WeatherTransitionData next = iter.next();
            this.data_array.add(new WeatherTransitionData(next.getlocation(), next.getTemperature(), next.getTemperatureHigh(), next.getTemperatureLow(), next.getPrecipitation(), next.getHumidity(), next.getDescription(), next.getWindVelocity(), next.getWindDirection()));
        }*/
        this.marker_data = new WeatherTransitionData(copy.getMarkerData());
        this.marker_id = copy.getMarkerId();
    }



    public WeatherDataTransition(Parcel weatherDataParcel)
    {
        /*destination.writeInt(marker_id);
        destination.writeString(marker_data.getlocation());
        destination.writeString(marker_data.getTemperature());
        destination.writeString(marker_data.getTemperatureHigh());
        destination.writeString(marker_data.getTemperatureLow());
        destination.writeString(marker_data.getHumidity());
        destination.writeString(marker_data.getDescription());
        destination.writeString(marker_data.getWindVelocity());
        destination.writeString(marker_data.getWindDirection());*/
        this.marker_data = new WeatherTransitionData();
        this.marker_id = weatherDataParcel.readInt();
        this.marker_data.setLocation(weatherDataParcel.readString());
        this.marker_data.setTemperature(weatherDataParcel.readString());
        this.marker_data.setTemperatureHigh(weatherDataParcel.readString());
        this.marker_data.setTemperatureLow(weatherDataParcel.readString());
        this.marker_data.setHumidity(weatherDataParcel.readString());
        this.marker_data.setDescription(weatherDataParcel.readString());
        this.marker_data.setWindVelocity(weatherDataParcel.readString());
        this.marker_data.setWindDirection(weatherDataParcel.readString());
    }

    public WeatherTransitionData getMarkerData() {return this.marker_data;}

    public int getMarkerId() {return this.marker_id;}
    public void setMarkerId(int id) {this.marker_id = id;}


    //temp, prec, humid, desc, windvelocity, winddirection
    /*String time;
    public String getTime() {return this.time;}
    public WeatherDataTransition setTime(String t) {this.time = t; return this;}

    String location;
    public String getlocation() {return this.location;}
    public WeatherDataTransition setLocation(String loc) {this.location = loc; return this;}

    String temperature_high;
    public String getTemperatureHigh() {return this.temperature_high;}
    public WeatherDataTransition setTemperatureHigh(String temp) {this.temperature_high = temp; return this;}

    String temperature_low;
    public String getTemperatureLow() {return this.temperature_low;}
    public WeatherDataTransition setTemperatureLow(String temp) {this.temperature_low = temp; return this;}

    String precipitation;
    public String getPrecipitation() {return this.precipitation;}
    public WeatherDataTransition setPrecipitation(String prec) {this.precipitation = prec; return this;}

    String humidity;
    public String getHumidity() {return this.humidity;}
    public WeatherDataTransition setHumidity(String humi) {this.humidity = humi; return this;}

    String description;
    public String getDescription() {return this.description;}
    public WeatherDataTransition setDescription(String desc) {this.description = desc; return this;}

    String wind_velocity;
    public String getWindVelocity() {return this.wind_velocity;}
    public WeatherDataTransition setWindVelocity(String w_v) {this.wind_velocity = w_v; return this;}

    String wind_direction;
    public String getWindDirection() {return this.wind_direction;}
    public WeatherDataTransition setWindDirection(String w_d) {this.wind_direction = w_d; return this;}*/

    @Override
    public int describeContents() {return 0;}

    @Override
    public void writeToParcel(Parcel destination, int flags) {
        //destination.writeByteArray(())
        destination.writeInt(marker_id);
        destination.writeString(marker_data.getLocation());
        destination.writeString(marker_data.getTemperature());
        destination.writeString(marker_data.getTemperatureHigh());
        destination.writeString(marker_data.getTemperatureLow());
        destination.writeString(marker_data.getHumidity());
        destination.writeString(marker_data.getDescription());
        destination.writeString(marker_data.getWindVelocity());
        destination.writeString(marker_data.getWindDirection());
    }


    public static final Parcelable.Creator<WeatherDataTransition> CREATOR = new Parcelable.Creator<WeatherDataTransition>()
    {
        public WeatherDataTransition createFromParcel(Parcel weatherDataTransitionParcel)
        {
            return new WeatherDataTransition(weatherDataTransitionParcel);
        }

        public WeatherDataTransition[] newArray(int size) {return new WeatherDataTransition[size];}
    };
}
