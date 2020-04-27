package com.example.safetravelsclient.models.transition;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.safetravelsclient.models.fields.WeatherTransitionData;

import java.util.ArrayList;
import java.util.UUID;

public class WeatherDataTransitionArray implements Parcelable
{
    ArrayList<WeatherDataTransition> transition_array;
    WeatherDataTransitionArray()
    {

    }

    public WeatherDataTransitionArray(Parcel weatherDataParcel)
    {
        this.transition_array = new ArrayList<WeatherDataTransition>();
        WeatherDataTransition to_add = new WeatherDataTransition();
        to_add.setUserId(UUID.fromString(weatherDataParcel.readString()));
        to_add.setMarkerId(weatherDataParcel.readInt());
        to_add.getMarkerData().setLocation(weatherDataParcel.readString());
        to_add.getMarkerData().setTemperature(weatherDataParcel.readString());
        to_add.getMarkerData().setTemperatureHigh(weatherDataParcel.readString());
        to_add.getMarkerData().setTemperatureLow(weatherDataParcel.readString());
        to_add.getMarkerData().setHumidity(weatherDataParcel.readString());
        to_add.getMarkerData().setDescription(weatherDataParcel.readString());
        to_add.getMarkerData().setWindVelocity(weatherDataParcel.readString());
        to_add.getMarkerData().setWindDirection(weatherDataParcel.readString());
        to_add.getMarkerData().setArrivalTime(weatherDataParcel.readString());
        to_add.getMarkerData().setImage(weatherDataParcel.readString());
        this.transition_array.add(to_add);
        /*this.marker_data = new WeatherTransitionData();
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
        this.marker_data.setImage(weatherDataParcel.readString());*/
    }

    public UUID getUserId()
    {
        return this.transition_array.get(0).getUserId();
    }

    public WeatherDataTransition getWeatherDataTransition(int index)
    {
        return this.transition_array.get(index);
    }

    public WeatherTransitionData getWeatherTransitionData(int index)
    {
        return this.transition_array.get(index).getMarkerData();
    }

    @Override
    public int describeContents() {return 0;}

    @Override
    public void writeToParcel(Parcel destination, int flags) {
        //destination.writeByteArray(())
        for(WeatherDataTransition trans: this.transition_array)
        {
            WeatherTransitionData marker_data = trans.getMarkerData();
            //destination.writeString(this.user_id.toString());
            //destination.writeString(trans.getUserId().toString());
            destination.writeString(trans.getUserId().toString());
            destination.writeInt(trans.getMarkerId());
            //destination.writeInt(trans.getMarkerId());
            destination.writeString(marker_data.getLocation());
            //destination.writeString(trans.getMarkerData().getLocation());
            destination.writeString(marker_data.getTemperature());
            //destination.writeString(trans.getMarkerData().getTemperature());
            destination.writeString(marker_data.getTemperatureHigh());
            //destination.writeString(trans.getMarkerData().getTemperatureHigh());
            destination.writeString(marker_data.getTemperatureLow());
            destination.writeString(marker_data.getHumidity());
            destination.writeString(marker_data.getDescription());
            destination.writeString(marker_data.getWindVelocity());
            destination.writeString(marker_data.getWindDirection());
            destination.writeString(marker_data.getArrivalTime());
            destination.writeString(marker_data.getImage());
        }

    }

    public static final Parcelable.Creator<WeatherDataTransitionArray> CREATOR = new Parcelable.Creator<WeatherDataTransitionArray>()
    {
        public WeatherDataTransitionArray createFromParcel(Parcel weatherDataTransitionParcel)
        {
            return new WeatherDataTransitionArray(weatherDataTransitionParcel);
        }

        public WeatherDataTransitionArray[] newArray(int size) {return new WeatherDataTransitionArray[size];}
    };
}
