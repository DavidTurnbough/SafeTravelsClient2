//************
// David Turnbough
//**************

package com.example.safetravelsclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.safetravelsclient.WeatherListActivity;
import com.example.safetravelsclient.models.fields.WeatherTransitionData;
import com.example.safetravelsclient.models.transition.WeatherDataTransition;

import java.util.ArrayList;

public class InDepthViewActivity extends AppCompatActivity {

    //**********
    // Variable Declarations.
    //**********
    WeatherTransitionData weather_data;
    WeatherDataTransition weather_transition;
    public ArrayList<WeatherDataTransition> transitions = new ArrayList<WeatherDataTransition>();

    Button back_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_depth_view);
        this.back_button = findViewById(R.id.backButton);

        this.weather_transition = this.getIntent().getParcelableExtra("item_to_display");
        transitions = getIntent().getExtras().getParcelableArrayList("WeatherData");
        this.weather_data = this.weather_transition.getMarkerData();

        this.back_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), WeatherListActivity.class);

                intent.putParcelableArrayListExtra("WeatherData", transitions);
                startActivity(intent);
            }
        });

        this.setLocation(this.weather_data.getLocation());
        this.setWeatherDescription(this.weather_data.getImage());
        this.setWind(this.weather_data.getWindVelocity() + " MPH");
        this.setPrecipitation(this.weather_data.getPrecipitation() + "%");
        this.setHumidity(this.weather_data.getHumidity() + "%");
        //this.setDate(myDate);
        this.setTemperature(this.weather_data.getTemperature() + (char) 0x00B0);

    }


    //**********
    // Setter Methods.
    //**********
    public void setLocation(String myLocation){
        TextView location = findViewById(R.id.location);
        location.setText(myLocation);
    }

    public void setWeatherDescription(String myDescription){
        TextView weatherDescription = findViewById(R.id.weatherDescription);
        weatherDescription.setText(myDescription);
        this.setWeatherImage(myDescription);
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

    }

    public void setTemperatureLow(String myTemperature){

    }

    public void setWeatherImage(String weatherDescription)
    {
        ImageView weatherImage = (ImageView) findViewById(R.id.weatherImage);

        if (weatherDescription.equals("Clear"))
        {
            weatherImage.setImageResource(R.drawable.sunny1);

        }

        else if(weatherDescription.equals("Clouds"))
        {
            weatherImage.setImageResource(R.drawable.cloudy);

        }
        else if(weatherDescription.equals("Fog"))
        {
            weatherImage.setImageResource(R.drawable.fog);

        }
        else if(weatherDescription.equals("Partly Cloudy"))
        {
            weatherImage.setImageResource(R.drawable.partly_cloudy);

        }
        else if(weatherDescription == "Rain")
        {
            weatherImage.setImageResource(R.drawable.rainy);

        }
        else if(weatherDescription.equals("Snow"))
        {
            weatherImage.setImageResource(R.drawable.snowy);

        }

        else if(weatherDescription.equals("Thunderstorm"))
        {
            weatherImage.setImageResource(R.drawable.thunderstorm);
        }
        else
        {
            weatherImage.setImageResource(R.drawable.logo);
        }

    }

}


