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
    //List<WeatherListSubjectData> weather_list = new ArrayList<WeatherListSubjectData>();
    //List<WeatherListSubjectData> weather_list;
    List<WeatherTransitionData> weather_list;
    Context context;

    /*static class WeatherViewHolder
    {
        ImageView weather_image;
        TextView location;
    }*/

    public WeatherListAdapter(Context context, int textViewResourceId, List<WeatherTransitionData> w_list)
    {
        //super(context, textViewResourceId, w_list);
        super(context, R.layout.weather_list_entry_layout, w_list);
        //context.
        //this.weather_list = new ArrayList<WeatherListSubjectData>();
        this.context = context;
        this.weather_list = w_list;
        //this.weather_list = weath_list;

        //this.context = context;
    }

    /*@Override
    public void add(WeatherListSubjectData obj)
    {
        weather_list.add(obj);
        super.add(obj);
    }

    @Override
    public int getCount()
    {
        return this.weather_list.size();
    }*/

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

        //Product product = this.getItem(position);
        //WeatherListSubjectData subj = this.getItem(position);
        WeatherTransitionData subj = this.getItem(position);
        if (subj != null)
        {
            //TextView markerIdTextView = view.findViewById(R.id.list_entry_id);
            ImageView image_view = view.findViewById(R.id.list_entry_img);
            //if (markerIdTextView != null)
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
                else if(weatherDescription == "Thunderstorm")
                {
                    image_view.setImageResource(R.drawable.thunderstorm);
                }
                else
                {
                    image_view.setImageResource(R.drawable.logo);
                }
                //markerIdTextView.setText(Integer.toString(subj.getMarkerId()));
                //String image_name = subj.getImage();
                //image_view.setImageDrawable(R.drawable.image_name);
                //image_view.setImageResource()
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
                        //subj.setWindVelocity("9");

                    }
                    windTextView.setText(subj.getWindVelocity()+" MPH");
                }
               // int value = Integer.parseInt(subj.getWindVelocity());


            }
        }
        view.setBackgroundColor(context.getResources().getColor(R.color.colorListEntryBG));

        return view;
    }

    public Bitmap decodeToBitmap(byte[] decoded_byte)
    {
        return BitmapFactory.decodeByteArray(decoded_byte, 0, decoded_byte.length);
    }

    /*@Override
    public boolean areAllItemsEnabled() {
        return false;
    }
    @Override
    public boolean isEnabled(int position) {
        return true;
    }
    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
    }
    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
    }
    @Override
    public int getCount() {
        return weather_list.size();
    }
    @Override
    public Object getItem(int position) {
        return position;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public boolean hasStableIds() {
        return false;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        WeatherListSubjectData listData = weather_list.get(position);
        if(convertView == null)
        {
            LayoutInflater layout_inflater = LayoutInflater.from(context);
            convertView = layout_inflater.inflate(R.layout.list_row, null);
        }
    }*/
}
