package com.example.safetravelsclient.models;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

import com.example.safetravelsclient.R;
import com.example.safetravelsclient.models.adapter.WeatherListAdapter;
import com.example.safetravelsclient.models.transition.WeatherDataTransition;
import com.example.safetravelsclient.models.fields.WeatherListSubjectData;

public class WeatherListActivity extends AppCompatActivity
{
    private static final String TAG = "WeatherListActivity";
    private static final int NUM_VALUES = 4;
    private WeatherListAdapter weather_list_adapter;
    private ListView list_view;


    public void onCreate(Bundle savedInstanceState, WeatherDataTransition transition)
    {
        super.onCreate(savedInstanceState);
        setContentView(com.example.safetravelsclient.R.layout.activity_weather_list);
        this.list_view = (ListView)findViewById(R.id.content_weather_listing);
        this.weather_list_adapter = new WeatherListAdapter(getApplicationContext(), R.layout.weather_list_entry_layout);
        this.list_view.setAdapter(weather_list_adapter);

        List<String[]> test_entries = populateListTest();
        //for(String[] entry:test_entries)
        for(int i = 0; i < test_entries.size(); i++)
        {
            String loc = test_entries.get(i)[0];
            String temp = test_entries.get(i)[1];
            String prec = test_entries.get(i)[2];
            String wind =  test_entries.get(i)[3];
            WeatherListSubjectData add = new WeatherListSubjectData(i, loc, temp, prec, wind);
            this.weather_list_adapter.add(add);
        }
        //ArrayList<WeatherListSubjectData> weather_list = new ArrayList<WeatherListSubjectData>();
        //weather_list.add(new WeatherListSubjectData("Faye", "32", "10", "img"))
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
        return out;
    }
}
