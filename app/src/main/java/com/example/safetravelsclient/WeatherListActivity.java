package com.example.safetravelsclient;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

import com.example.safetravelsclient.InDepthViewActivity;
import com.example.safetravelsclient.MapsActivity;
import com.example.safetravelsclient.R;
import com.example.safetravelsclient.models.adapter.WeatherListAdapter;
import com.example.safetravelsclient.models.fields.WeatherTransitionData;
import com.example.safetravelsclient.models.transition.WeatherDataTransition;
import com.example.safetravelsclient.models.fields.WeatherListSubjectData;

public class WeatherListActivity extends AppCompatActivity
{
    WeatherTransitionData weather_data;
    WeatherDataTransition weather_transition;

    private static final String TAG = "WeatherListActivity";
    private static final int NUM_VALUES = 4;
    private WeatherListAdapter weather_list_adapter;
    //private List<WeatherListSubjectData> weather_list;
    private List<WeatherTransitionData> weather_list;
     ArrayList<WeatherDataTransition> incoming_list;
    //private List<WeatherTransitionData> transition_data;
    private ListView list_view;
    Button back_button;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.d("MyApp", "HERE WE GO");

        incoming_list = getIntent().getParcelableArrayListExtra("WeatherData");


        System.out.println("Lsit Size: " + incoming_list.size());
        this.weather_transition = this.getIntent().getParcelableExtra(this.getString(R.string.intent_weather_data));

        //    = bundle.getParcelable("WeatherData");

        this.setContentView(R.layout.content_weather_list);
        this.back_button = findViewById(R.id.back_button_weather_list);

        this.back_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(getApplicationContext(), MapsActivity.class));
                //startActivityOnClick(view);
            }
        });

        this.list_view = this.getWeatherListView();
        //this.weather_list = new ArrayList<WeatherListSubjectData>();
        this.weather_list = new ArrayList<WeatherTransitionData>();
        //this.transition_data = new ArrayList<WeatherTransitionData>();
        this.weather_list_adapter = new WeatherListAdapter(this, R.layout.weather_list_entry_layout, this.weather_list);
        //int id, String loc, String temp, String temp_high, String temp_low, String prec, String humi, String desc, String wind_v, String wind_d
        this.weather_list.add(new WeatherTransitionData(0, "Loc", "Temp", "TempH", "TempL", "Prec", "Humi", "Desc", "WindV", "WindD"));
        this.list_view.setAdapter(weather_list_adapter);
        this.list_view.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id)
            {
                Log.d("MyApp", "INITEMCLICK");
                WeatherTransitionData at_pos = (WeatherTransitionData)adapter.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), InDepthViewActivity.class);
                //WeatherListSubjectData subject_at_position = getWeatherListView().getItemAtPosition(position);
                //WeatherTransitionData data_at_position = transition_data.get()
                /*this.marker_id = weatherDataParcel.readInt();
                this.marker_data.setLocation(weatherDataParcel.readString());
                this.marker_data.setTemperature(weatherDataParcel.readString());
                this.marker_data.setTemperatureHigh(weatherDataParcel.readString());
                this.marker_data.setTemperatureLow(weatherDataParcel.readString());
                this.marker_data.setHumidity(weatherDataParcel.readString());
                this.marker_data.setDescription(weatherDataParcel.readString());
                this.marker_data.setWindVelocity(weatherDataParcel.readString());
                this.marker_data.setWindDirection(weatherDataParcel.readString());*/
                /*intent.p
                intent.putExtra("Id", at_pos.getMarkerId());
                intent.putExtra("Loc", at_pos.getlocation());
                intent.putExtra("Temp", at_pos.getTemperature());
                intent.putExtra("TempHigh", at_pos.getTemperatureHigh());
                intent.putExtra("TempLow", at_pos.getTemperatureLow());
                intent.putExtra("Humi", at_pos.getHumidity());
                intent.putExtra("Desc", at_pos.getHumidity());
                intent.putExtra("WindV", at_pos.getWindVelocity());
                intent.putExtra("WindD", at_pos.getWindDirection());*/
                WeatherDataTransition weatherDataTransition = new WeatherDataTransition((WeatherTransitionData)getWeatherListView().getItemAtPosition(position));
                intent.putExtra
                        (
                                getString(R.string.intent_weather_data),
                                weatherDataTransition
                        );
                startActivity(intent);
            }
        });
        List<String[]> test_entries = populateListTest();
        //for(String[] entry:test_entries)
        for(int i = 0; i < test_entries.size(); i++)
        {
            String loc = test_entries.get(i)[0];
            String temp = test_entries.get(i)[1];
            String prec = test_entries.get(i)[2];
            String wind =  test_entries.get(i)[3];
            //System.out.println(loc + ", " + temp + ", " + prec + ", " + wind);
            WeatherTransitionData add = new WeatherTransitionData(i, loc, temp, prec, wind);
            this.weather_list.add(add);
            //this.weather_list_adapter.add(add);
            //_adapter.add(add);
        }

        //Log.d("MyApp", "HERE END");
        this.weather_list_adapter.notifyDataSetChanged();
        //this.weather_list_adapter.
        //ArrayList<WeatherListSubjectData> weather_list = new ArrayList<WeatherListSubjectData>();
        //weather_list.add(new WeatherListSubjectData("Faye", "32", "10", "img"))
        /*this.list_view.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id)
            {
                Log.d("MyApp", "INITEMCLICK");
                WeatherTransitionData at_pos = (WeatherTransitionData)adapter.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), InDepthViewActivity.class);
                //WeatherListSubjectData subject_at_position = getWeatherListView().getItemAtPosition(position);
                //WeatherTransitionData data_at_position = transition_data.get()
                /*this.marker_id = weatherDataParcel.readInt();
                this.marker_data.setLocation(weatherDataParcel.readString());
                this.marker_data.setTemperature(weatherDataParcel.readString());
                this.marker_data.setTemperatureHigh(weatherDataParcel.readString());
                this.marker_data.setTemperatureLow(weatherDataParcel.readString());
                this.marker_data.setHumidity(weatherDataParcel.readString());
                this.marker_data.setDescription(weatherDataParcel.readString());
                this.marker_data.setWindVelocity(weatherDataParcel.readString());
                this.marker_data.setWindDirection(weatherDataParcel.readString());*/
                /*intent.p
                intent.putExtra("Id", at_pos.getMarkerId());
                intent.putExtra("Loc", at_pos.getlocation());
                intent.putExtra("Temp", at_pos.getTemperature());
                intent.putExtra("TempHigh", at_pos.getTemperatureHigh());
                intent.putExtra("TempLow", at_pos.getTemperatureLow());
                intent.putExtra("Humi", at_pos.getHumidity());
                intent.putExtra("Desc", at_pos.getHumidity());
                intent.putExtra("WindV", at_pos.getWindVelocity());
                intent.putExtra("WindD", at_pos.getWindDirection());
                intent.putExtra
                (
                        getString(R.string.intent_weather_data),
                        new WeatherDataTransition((WeatherTransitionData)getWeatherListView().getItemAtPosition(position))
                );
                startActivity(intent);
            }
        });*/
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
