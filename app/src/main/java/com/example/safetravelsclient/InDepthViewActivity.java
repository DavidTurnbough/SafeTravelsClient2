package com.example.safetravelsclient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class InDepthViewActivity extends AppCompatActivity {

    String myLocation = "Springdale, AR";
    String myDescription = "Sunny";
    String myWindValue = "NNW 15mph";
    String myPrecipitation = "23%";
    String myHumidity = "24%";
    String myDate = "Mon Feb 02, 2020";
    String myTemperature = "74";
    String myTemperatureHigh = "95";
    String myTemperatureLow = "8";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_depth_view);

        setLocation(myLocation);
        setWeatherDescription(myDescription);
        setWind(myWindValue);
        setPrecipitation(myPrecipitation);
        setHumidity(myHumidity);
        setDate(myDate);
        setTemperature(myTemperature);
        setTemperatureHigh(myTemperatureHigh);
        setTemperatureLow(myTemperatureLow);
    }


    public void setLocation(String myLocation){
        TextView location = findViewById(R.id.location);
        location.setText(myLocation);
    }

    public void setWeatherDescription(String myDescription){
        TextView weatherDescription = findViewById(R.id.weatherDescription);
        weatherDescription.setText(myDescription);
    }

    public void setWind(String myWind){
        TextView windValue = findViewById(R.id.windValue);
        windValue.setText(myWind);
    }

    public void setHumidity(String myHumidity){
        TextView humidity = findViewById(R.id.humidityValue);
        humidity.setText(myHumidity);
    }

    public void setPrecipitation(String myPrecipitation){
        TextView precipitation = findViewById(R.id.percipitationValue);
        precipitation.setText(myPrecipitation);
    }

    public void setDate(String myDate){
        TextView date = findViewById(R.id.date);
        date.setText(myDate);
    }

    public void setTemperature(String myTemperature){
        TextView temperature = findViewById(R.id.temperatureValue);
        temperature.setText(myTemperature);
    }

    public void setTemperatureHigh(String myTemperature){
        TextView temperatureHigh = findViewById(R.id.highTemperature);
        temperatureHigh.setText(myTemperature);
    }

    public void setTemperatureLow(String myTemperature){
        TextView temperatureLow = findViewById(R.id.lowTemperature);
        temperatureLow.setText(myTemperature);
    }
}


