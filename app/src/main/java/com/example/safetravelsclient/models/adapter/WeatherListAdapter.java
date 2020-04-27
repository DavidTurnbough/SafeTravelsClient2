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

import static com.example.safetravelsclient.R.layout.weather_list_entry_layout;

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
        //this.weather_list = new ArrayList<WeatherListSubjectData>();
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

    /*
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        WeatherListSubjectData viewHolder;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.weather_list_entry_layout, parent, false);
            viewHolder = new WeatherListSubjectData();
            //viewHolder.setImg = (ImageView) row.findViewById(R.id.fruitImg);
            viewHolder.setTvMarkerId((TextView)row.findViewById(R.id.list_entry_id));
            viewHolder.setTvLocation((TextView)row.findViewById(R.id.list_entry_location));
            viewHolder.setTvTemperature((TextView)row.findViewById(R.id.list_entry_temperature));
            viewHolder.setTvPrecipitation((TextView)row.findViewById(R.id.list_entry_precipitation));
            viewHolder.setTvWindVelocity((TextView)row.findViewById(R.id.list_entry_wind_velocity));
            row.setTag(viewHolder);
        } else {
            viewHolder = (WeatherListSubjectData)row.getTag();
        }
        WeatherListSubjectData entry = getItem(position);
        viewHolder.getTvMarkerId().setText(entry.getMarkerId());
        viewHolder.getTvLocation().setText(entry.getlocation());
        viewHolder.getTvTemperature().setText(entry.getTemperature());
        viewHolder.getTvPrecipitation().setText(entry.getPrecipitation());
        viewHolder.getTvWindVelocity().setText(entry.getWindVelocity());
        return row;
    }*/


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

                if(weatherDescription == "Cloudy")
                {
                    image_view.setImageResource(R.drawable.cloudy);

                }
                else if(weatherDescription == "Fog")
                {
                    image_view.setImageResource(R.drawable.fog);

                }
                else if(weatherDescription == "Partly Cloudy")
                {
                    image_view.setImageResource(R.drawable.partly_cloudy);

                }
                else if(weatherDescription == "Rainy")
                {
                    image_view.setImageResource(R.drawable.rainy);

                }
                else if(weatherDescription == "Snowy")
                {
                    image_view.setImageResource(R.drawable.snowy);

                }
                else if(weatherDescription == "Sunny")
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
                tempTextView.setText(subj.getTemperature());
            }
            TextView precTextView = view.findViewById(R.id.list_entry_precipitation);
            if (precTextView != null)
            {
                precTextView.setText(subj.getPrecipitation());
            }
            TextView windTextView = view.findViewById(R.id.list_entry_wind_velocity);
            if (windTextView != null)
            {
                windTextView.setText(subj.getWindVelocity());
            }
        }

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
