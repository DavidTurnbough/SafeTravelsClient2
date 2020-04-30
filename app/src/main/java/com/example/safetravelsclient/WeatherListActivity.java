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
        this.weather_list = new ArrayList<WeatherTransitionData>();
        this.weather_list_adapter = new WeatherListAdapter(this, R.layout.weather_list_entry_layout, this.weather_list);
        this.weather_list.add(new WeatherTransitionData(0, "Location", "Temp", "TempH", "TempL", "Prec", "Humi", "Desc", "Wind Speed", "WindD", "Time", "Image"));
        this.list_view.setAdapter(weather_list_adapter);

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
        this.list_view.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id)
            {
                Log.d("MyApp", "INITEMCLICK");
                WeatherTransitionData at_pos = (WeatherTransitionData)adapter.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), InDepthViewActivity.class);
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
        //String id = "00000000000000000000000000000000";
        //UUID userID = new UUID(Long.parseLong(id.substring(0,16)),Long.parseLong(id.substring(16,32)));
        //userID.
        //marker = this.location_service.getUserMarkers(userID).getData();
        ArrayList<ApiResponse<LocationMarker>> get_markers = this.location_service.getUserMarkers(this.user_id);
        for(ApiResponse<LocationMarker> mark : get_markers)
        {
            this.weather_list.add(new WeatherTransitionData(mark.getData()));
        }
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
