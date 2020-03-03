package com.example.safetravelsclient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.safetravelsclient.models.transition.WeatherDataTransition;

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

    private WeatherDataTransition weatherDataTransition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_depth_view);

/*        setLocation(myLocation);
        setWeatherDescription(myDescription);
        setWind(myWindValue);
        setPrecipitation(myPrecipitation);
        setHumidity(myHumidity);
        setDate(myDate);
        setTemperature(myTemperature);
        setTemperatureHigh(myTemperatureHigh);
        setTemperatureLow(myTemperatureLow);
        */
       // this.weatherDataTransition = this.getIntent().getParcelableExtra(this.getString(R.string.intent_extra_product));

    }

    @Override
    protected void onResume() {
        super.onResume();


        //this.getProductLookupCodeEditText().setText(this.productTransition.getLookupCode());
        //this.setWeatherDescription().setText(this.weatherDataTransition.getDescription());
        //this.getProductCountEditText().setText(String.format(Locale.getDefault(), "%d", this.productTransition.getCount()));
       // this.getProductCreatedOnEditText().setText(
              //  (new SimpleDateFormat("MM/dd/yyyy", Locale.US)).format(this.productTransition.getCreatedOn())
       // );
    }

    public void setLocation(String myLocation){
        TextView location = findViewById(R.id.location);
        location.setText(myLocation);
    }

    public TextView setWeatherDescription(){
        return (TextView) this.findViewById(R.id.weatherDescription);

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


