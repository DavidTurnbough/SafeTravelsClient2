package com.example.safetravelsclient;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.example.safetravelsclient.InDepthViewActivity;
import com.example.safetravelsclient.MapsActivity;
import com.example.safetravelsclient.R;
import com.example.safetravelsclient.models.adapter.WeatherListAdapter;
import com.example.safetravelsclient.models.fields.WeatherTransitionData;
import com.example.safetravelsclient.models.services.ApiResponse;
import com.example.safetravelsclient.models.services.LocationMarker;
import com.example.safetravelsclient.models.services.LocationMarkerService;
import com.example.safetravelsclient.models.transition.WeatherDataTransition;
import com.example.safetravelsclient.models.fields.WeatherListSubjectData;

public class WeatherListActivity extends AppCompatActivity
{
    WeatherTransitionData weather_data;
    Parcelable[] weather_transition;

    private static final String TAG = "WeatherListActivity";
    private static final int NUM_VALUES = 4;
    private WeatherListAdapter weather_list_adapter;
    private LocationMarkerService location_service;
    //private List<WeatherListSubjectData> weather_list;
    private List<WeatherTransitionData> weather_list;
    private UUID user_id;
  //  public WeatherDataTransition weatherDataTransition[]
     ArrayList<WeatherDataTransition> incoming_list;
    public Parcelable[] transition_data = new WeatherDataTransition[20];
    public ArrayList<WeatherDataTransition> transitions = new ArrayList<WeatherDataTransition>();
    private ListView list_view;
    Button back_button;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.d("MyApp", "HERE WE GO");
        this.setContentView(R.layout.content_weather_list);
        this.back_button = findViewById(R.id.back_button_weather_list);

        this.location_service = new LocationMarkerService();
        this.list_view = this.getWeatherListView();
        this.list_view.setBackgroundColor(getColor(R.color.colorClear));
        //this.weather_list = new ArrayList<WeatherListSubjectData>();
        this.weather_list = new ArrayList<WeatherTransitionData>();
        //this.transition_data = new ArrayList<WeatherTransitionData>();
        this.weather_list_adapter = new WeatherListAdapter(this, R.layout.weather_list_entry_layout, this.weather_list);
        //int id, String loc, String temp, String temp_high, String temp_low, String prec, String humi, String desc, String wind_v, String wind_d
        this.weather_list.add(new WeatherTransitionData(0, "Location", "Temp", "TempH", "TempL", "Prec", "Humi", "Desc", "Wind Speed", "WindD", "Time", "Image"));
        this.list_view.setAdapter(weather_list_adapter);
        //int count =
        //this.weather_transition = this.getIntent().getParcelableArrayExtra("WeatherData");


        transitions = getIntent().getExtras().getParcelableArrayList("WeatherData");

        if(transitions != null)
        {
            for(WeatherDataTransition trans : transitions)
            {
                this.user_id = trans.getUserId();
                WeatherTransitionData add_data = new WeatherTransitionData(trans.getMarkerData());
                add_data.setMarkerId(trans.getMarkerId());
                add_data.setPrecipitation("30");
                this.weather_list.add(add_data);
            }
        }


        this.back_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(getApplicationContext(), MapsActivity.class));
                //startActivityOnClick(view);
            }
        });
        //this.location_service = new LocationMarkerService();
        //this.list_view = this.getWeatherListView();
        //this.list_view.setBackgroundColor(getColor(R.color.colorListView));
        //this.weather_list = new ArrayList<WeatherListSubjectData>();
       // this.weather_list = new ArrayList<WeatherTransitionData>();
        //this.transition_data = new ArrayList<WeatherTransitionData>();
        //this.weather_list_adapter = new WeatherListAdapter(this, R.layout.weather_list_entry_layout, this.weather_list);
        //int id, String loc, String temp, String temp_high, String temp_low, String prec, String humi, String desc, String wind_v, String wind_d
      //  this.weather_list.add(new WeatherTransitionData(0, "Location", "Temp", "TempH", "TempL", "Prec", "Humi", "Desc", "WindV", "WindD", "Time", "Image"));
        //this.list_view.setAdapter(weather_list_adapter);
        this.list_view.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id)
            {
                Log.d("MyApp", "INITEMCLICK");
                WeatherTransitionData at_pos = (WeatherTransitionData)adapter.getItemAtPosition(position);
                //Intent intent = new Intent(getApplicationContext(), InDepthViewActivity.class);
/*
                intent.putExtra("Id", at_pos.getMarkerId());
                intent.putExtra("Loc", at_pos.getlocation());
                intent.putExtra("Temp", at_pos.getTemperature());
                intent.putExtra("TempHigh", at_pos.getTemperatureHigh());
                intent.putExtra("TempLow", at_pos.getTemperatureLow());
                intent.putExtra("Humi", at_pos.getHumidity());
                intent.putExtra("Desc", at_pos.getHumidity());
                intent.putExtra("WindV", at_pos.getWindVelocity());
                intent.putExtra("WindD", at_pos.getWindDirection());*/
                /*WeatherDataTransition weatherDataTransition = new WeatherDataTransition((WeatherTransitionData)getWeatherListView().getItemAtPosition(position));
                intent.putExtra
                        (
                                getString(R.string.intent_weather_data),
                                weatherDataTransition
                        );
                startActivity(intent);*/
                Intent intent = new Intent(getApplicationContext(), InDepthViewActivity.class);
                //  intent.putExtras()
                intent.putExtra("item_to_display", new WeatherDataTransition((WeatherTransitionData)getWeatherListView().getItemAtPosition(position)));
                intent.putParcelableArrayListExtra("WeatherData", transitions);
                startActivity(intent);
            }
        });
        List<String[]> test_entries = populateListTest();
        //Log.d("MyApp", "HERE END");
        this.weather_list_adapter.notifyDataSetChanged();
    }

    public List<String[]> fetchFromServer()
    {
        List<String[]> out = new ArrayList<String[]>();
        LocationMarker marker = new LocationMarker();
        String id = "00000000000000000000000000000000";
        //String id = "1";
        UUID userID = new UUID(Long.parseLong(id.substring(0,16)),Long.parseLong(id.substring(16,32)));
        //userID.
        //marker = this.location_service.getUserMarkers(userID).getData();
        ArrayList<ApiResponse<LocationMarker>> get_markers = this.location_service.getUserMarkers(userID);
        for(ApiResponse<LocationMarker> mark : get_markers)
        {
            this.weather_list.add(new WeatherTransitionData(mark.getData()));
        }
        //marker = this.location_service.getLocationMarker(1).getData();
        //if(marker.getTemperature() == null) {Log.d("log: ", "Temperature- null" );}
        //this.weather_list.add(new WeatherTransitionData(marker));
        return out;
    }

    public List<String[]> populateListTest()
    {
        List<String[]> out = new ArrayList<String[]>();
        String[] entry_1 = new String[NUM_VALUES];
        entry_1[0] = "New York";
        entry_1[1] = "32";
        entry_1[2] = "10";
        entry_1[3] = "4";
        out.add(entry_1);

        String[] entry_2 = new String[NUM_VALUES];
        entry_2[0] = "San Francisco";
        entry_2[1] = "50";
        entry_2[2] = "0";
        entry_2[3] = "2";
        out.add(entry_2);

        String[] entry_3 = new String[NUM_VALUES];
        entry_3[0] = "Little Rock";
        entry_3[1] = "40";
        entry_3[2] = "5";
        entry_3[3] = "0";
        out.add(entry_3);

        //String test = entry_1[0] + ", " + entry_1[1] + ", " + entry_1[2] + ", " + entry_1[3];
        //log(test);
        return out;
    }

    private ListView getWeatherListView()
    {
        return (ListView) this.findViewById(R.id.content_weather_listing);
    }

    public void log(String out)
    {
        System.out.println(out);
    }
}
