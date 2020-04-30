package com.example.safetravelsclient.models.transition;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.safetravelsclient.models.fields.WeatherTransitionData;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

public class WeatherDataTransition implements Parcelable
{
    private UUID user_id;
    public UUID getUserId() {return this.user_id;}
    public WeatherDataTransition setUserId(UUID id) {this.user_id = id; return this;}
    ArrayList<WeatherTransitionData> data_array;
    WeatherTransitionData marker_data;
    int marker_id;

    public WeatherDataTransition()
    {
        this.marker_data = new WeatherTransitionData();
        this.user_id = new UUID(0,0);
    }

    public WeatherDataTransition(UUID u_id, WeatherTransitionData weather_data)
    {
        this.user_id = u_id;
        this.marker_data = new WeatherTransitionData(weather_data);
        this.marker_id = -1;
    }

    public WeatherDataTransition(WeatherTransitionData weather_data)
    {
        this.user_id = new UUID(0,0);
        this.marker_data = new WeatherTransitionData(weather_data);
        this.marker_id = -1;
    }

    public WeatherDataTransition(WeatherDataTransition copy)
    {
        this.user_id = copy.getUserId();
        this.marker_data = new WeatherTransitionData(copy.getMarkerData());
    }



    public WeatherDataTransition(Parcel weatherDataParcel)
    {
        this.marker_data = new WeatherTransitionData();
        String u_id = weatherDataParcel.readString();
        this.user_id = UUID.fromString(u_id);
        this.marker_id = weatherDataParcel.readInt();
        this.marker_data.setLocation(weatherDataParcel.readString());
        this.marker_data.setTemperature(weatherDataParcel.readString());
        this.marker_data.setTemperatureHigh(weatherDataParcel.readString());
        this.marker_data.setTemperatureLow(weatherDataParcel.readString());
        this.marker_data.setHumidity(weatherDataParcel.readString());
        this.marker_data.setDescription(weatherDataParcel.readString());
        this.marker_data.setWindVelocity(weatherDataParcel.readString());
        this.marker_data.setWindDirection(weatherDataParcel.readString());
        this.marker_data.setArrivalTime(weatherDataParcel.readString());
        this.marker_data.setImage(weatherDataParcel.readString());
    }

    public WeatherTransitionData getMarkerData() {return this.marker_data;}

    public int getMarkerId() {return this.marker_id;}
    public void setMarkerId(int id) {this.marker_id = id;}


    @Override
    public int describeContents()
    {
        //return this;
        return 0;
    }

    @Override
    public void writeToParcel(Parcel destination, int flags) {
        //destination.writeByteArray(())
        destination.writeString(this.user_id.toString());
        destination.writeInt(marker_id);
        destination.writeString(marker_data.getLocation());
        destination.writeString(marker_data.getTemperature());
        destination.writeString(marker_data.getTemperatureHigh());
        destination.writeString(marker_data.getTemperatureLow());
        destination.writeString(marker_data.getHumidity());
        destination.writeString(marker_data.getDescription());
        destination.writeString(marker_data.getWindVelocity());
        destination.writeString(marker_data.getWindDirection());
        destination.writeString(marker_data.getArrivalTime());
        destination.writeString(marker_data.getImage());
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
