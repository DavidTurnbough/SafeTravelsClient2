package com.example.safetravelsclient.models.transition;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.UUID;

public class WeatherDataTransition implements Parcelable
{
    private UUID id;
    public UUID getId() {return this.id;}
    public WeatherDataTransition setId(UUID id) {this.id = id; return this;}

    @Override
    public int describeContents() {return 0;}

    @Override
    public void writeToParcel(Parcel destination, int flags)
    {
        //destination.writeByteArray(())
    }

    public static final Parcelable.Creator<WeatherDataTransition> CREATOR = new Parcelable.Creator<WeatherDataTransition>()
    {
        public WeatherDataTransition createFromParcel(Parcel weatherDataTransitionParcel)
        {
            return new WeatherDataTransition(weatherDataTransitionParcel);
        }

        public WeatherDataTransition[] newArray(int size) {return new WeatherDataTransition[size];}
    };

    public WeatherDataTransition()
    {
        this.id = new UUID(0, 0);
    }

    public WeatherDataTransition(Parcel weatherDataParcel)
    {
        //
    }
}
