//*********************************************
//Written By Zach Cantrell
//******************************************
package com.example.safetravelsclient.models.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.safetravelsclient.R;
import com.example.safetravelsclient.models.fields.WeatherListSubjectData;
import com.example.safetravelsclient.models.fields.WeatherTransitionData;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;

import static com.example.safetravelsclient.R.color.colorListEntryBG;
import static com.example.safetravelsclient.R.layout.weather_list_entry_layout;
import static java.lang.Integer.parseInt;

public class WeatherListAdapter extends ArrayAdapter<WeatherTransitionData>
{
    private static final String TAG = "WeatherListArrayAdapter";
    List<WeatherTransitionData> weather_list;
    Context context;

    public WeatherListAdapter(Context context, int textViewResourceId, List<WeatherTransitionData> w_list)
    {

        super(context, R.layout.weather_list_entry_layout, w_list);
        this.context = context;
        this.weather_list = w_list;
    }

    @Override
    public WeatherTransitionData getItem(int pos)
    {
        return this.weather_list.get(pos);
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent)
    {
        View view = convertView;

        if (view == null)
        {
            LayoutInflater inflater = LayoutInflater.from(this.getContext());
            view = inflater.inflate(weather_list_entry_layout, parent, false);
        }

        WeatherTransitionData subj = this.getItem(position);
        if (subj != null)
        {
            ImageView image_view = view.findViewById(R.id.list_entry_img);
            String weatherDescription = subj.getImage();
            if(image_view != null)
            {

                if(weatherDescription.equals("Clouds"))
                {
                    image_view.setImageResource(R.drawable.cloudy);

                }
                else if(weatherDescription.equals("Fog"))
                {
                    image_view.setImageResource(R.drawable.fog);

                }
                else if(weatherDescription.equals("Partly Cloudy"))
                {
                    image_view.setImageResource(R.drawable.partly_cloudy);

                }
                else if(weatherDescription.equals("Rain"))
                {
                    image_view.setImageResource(R.drawable.rainy);

                }
                else if(weatherDescription.equals("Snow"))
                {
                    image_view.setImageResource(R.drawable.snowy);

                }
                else if(weatherDescription.equals("Clear"))
                {
                    image_view.setImageResource(R.drawable.sunny1);

                }
                else if(weatherDescription.equals("Thunderstorm"))
                {
                    image_view.setImageResource(R.drawable.thunderstorm);
                }
                else
                {
                    image_view.setImageResource(R.drawable.logo);
                }
            }

            TextView locationTextView = view.findViewById(R.id.list_entry_location);
            if (locationTextView != null)
            {
                locationTextView.setText(subj.getLocation());
            }

            TextView tempTextView = view.findViewById(R.id.list_entry_temperature);
            if (tempTextView != null)
            {
                tempTextView.setText(subj.getTemperature()+"°");
            }
            TextView precTextView = view.findViewById(R.id.list_entry_precipitation);
            if (precTextView != null)
            {
                precTextView.setText(subj.getPrecipitation()+"%");
            }
            TextView windTextView = view.findViewById(R.id.list_entry_wind_velocity);
            if (windTextView != null)
            {
                if (subj.getWindVelocity().equals("Wind Speed")) {
                    windTextView.setText(subj.getWindVelocity() + "");
                }
                else
                {
                    if (Integer.parseInt(subj.getWindVelocity()) > 9)
                    {

                    }
                    windTextView.setText(subj.getWindVelocity()+" MPH");
                }


            }
        }
        view.setBackgroundColor(context.getResources().getColor(R.color.colorListEntryBG));

        return view;
    }

    public Bitmap decodeToBitmap(byte[] decoded_byte)
    {
        return BitmapFactory.decodeByteArray(decoded_byte, 0, decoded_byte.length);
    }

}


