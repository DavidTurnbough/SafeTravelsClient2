package com.example.safetravelsclient;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.example.safetravelsclient.R;
import com.example.safetravelsclient.models.adapter.WeatherListAdapter;
import com.example.safetravelsclient.models.transition.WeatherDataTransition;
import com.example.safetravelsclient.models.fields.WeatherListSubjectData;

public class WeatherListActivity extends AppCompatActivity
{
    private static final String TAG = "WeatherListActivity";
    private static final int NUM_VALUES = 4;
    private WeatherListAdapter weather_list_adapter;
    private List<WeatherListSubjectData> weather_list;
    private ListView list_view;

    private WeatherDataTransition weatherDataTransition;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.d("MyApp", "HERE WE GO");
        this.setContentView(R.layout.content_weather_list);
        /*this.setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null)
        {
            this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }*/

        this.weatherDataTransition = this.getIntent().getParcelableExtra(this.getString(R.string.intent_extra_product));

        this.list_view = this.getWeatherListView();

        this.weather_list = new ArrayList<WeatherListSubjectData>();
        this.weather_list_adapter = new WeatherListAdapter(this, R.layout.weather_list_entry_layout, this.weather_list);
        this.weather_list.add(new WeatherListSubjectData(0, "i", "j", "k", "l"));
        this.list_view.setAdapter(weather_list_adapter);

     /*   this.getWeatherListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ProductViewActivity.class);

                intent.putExtra(
                        getString(R.string.intent_extra_product),
                       // new WeatherDataTransition((Product) getProductsListView().getItemAtPosition(position))
                );

                //startActivity(intent);
            }
        });

      */
        //this.weather_list.setA
        //this.getWeatherListView().setAdapter(this.weather_list_adapter);
        log("HERE");
        List<String[]> test_entries = populateListTest();
        //for(String[] entry:test_entries)
        for(int i = 0; i < test_entries.size(); i++)
        {
            String loc = test_entries.get(i)[0];
            String temp = test_entries.get(i)[1];
            String prec = test_entries.get(i)[2];
            String wind =  test_entries.get(i)[3];
            //System.out.println(loc + ", " + temp + ", " + prec + ", " + wind);
            WeatherListSubjectData add = new WeatherListSubjectData(i, loc, temp, prec, wind);
            this.weather_list_adapter.add(add);
        }
        Log.d("MyApp", "HERE END");
        this.weather_list_adapter.notifyDataSetChanged();
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

        //String test = entry_1[0] + ", " + entry_1[1] + ", " + entry_1[2] + ", " + entry_1[3];
        //log(test);
        return out;
    }

  /*  @Override
    protected void onResume()
    {
        super.onResume();


        this.getProductLookupCodeEditText().setText(this.productTransition.getLookupCode());
        this.getProductCountEditText().setText(String.format(Locale.getDefault(), "%d", this.productTransition.getCount()));
        this.getProductCreatedOnEditText().setText(
                (new SimpleDateFormat("MM/dd/yyyy", Locale.US)).format(this.productTransition.getCreatedOn())
        );
    }
*/
    private ListView getWeatherListView()
    {
        return (ListView) this.findViewById(R.id.content_weather_listing);
    }

    public void log(String out)
    {
        System.out.println(out);
    }
}
