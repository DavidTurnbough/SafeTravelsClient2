package com.example.safetravelsclient;

import android.os.AsyncTask;
import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.safetravelsclient.models.HttpDataHandler;
import com.example.safetravelsclient.models.TaskLoadedCallback;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.example.safetravelsclient.models.FetchURL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback {

    int destCheck = 0;
    private GoogleMap mMap;
    private MarkerOptions place1, place2;
    Button getDirection;

    Button getCords;
    EditText getFrom;
    EditText getTo;
    private Polyline currentPolyline;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        getCords = findViewById(R.id.button3);
        getFrom = findViewById(R.id.plain_text_input);
        getTo = findViewById(R.id.plain_text_input2);
        getDirection = findViewById(R.id.button);

        getCords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new GetCoordinates().execute(getFrom.getText().toString().replace(" ", "+"));
                new GetCoordinates().execute(getTo.getText().toString().replace(" ", "+"));

            }

        });


        getDirection.setOnClickListener(new View.OnClickListener() {
        public void onClick(View view) {
                new FetchURL(MapsActivity.this).execute(getUrl(place1.getPosition(), place2.getPosition(), "driving"), "driving");
            }
        });

                    SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager()
                    .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Log.d("mylog", "Added Markers");
        LatLng latLng = new LatLng(36.0822, -94.1719);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

    }

    private class GetCoordinates extends AsyncTask<String,Void,String> {
        ProgressBar bar = new ProgressBar(MapsActivity.this);

        @Override
        protected String doInBackground(String... strings) {
            String response;
            try {
                String address = strings[0];
                HttpDataHandler http = new HttpDataHandler();
                String url = String.format("https://maps.googleapis.com/maps/api/geocode/json?address=%s",address + "&key=" + getString(R.string.google_maps_key));
                response = http.getaHttpData(url);
                return response;
            }

            catch (Exception ex) {
            }
        return null;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
           // bar.set
        }



        @Override
        protected void onPostExecute(String s)
        {

            try {
                JSONObject jsonObject = new JSONObject(s);

                String lat = ((JSONArray)jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry")
                        .getJSONObject("location").get("lat").toString();
                String lng = ((JSONArray)jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry")
                        .getJSONObject("location").get("lng").toString();

                if (destCheck == 0)
                {

                    place1 = new MarkerOptions().position(new LatLng(Double.parseDouble(lat),Double.parseDouble(lng))).title("Location 1");
                    mMap.addMarker(place1);

                }
                else
                {
                    place2 = new MarkerOptions().position(new LatLng(Double.parseDouble(lat),Double.parseDouble(lng))).title("Location 2");
                    mMap.addMarker(place2);

                }

                System.out.println("Cooridnates: " + lat + ", " + lng);
                destCheck++;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void onClick(View v)
    {
        new FetchURL(MapsActivity.this).execute(getUrl(place1.getPosition(),place2.getPosition(), "driving"), "driving");
    }

    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);
        return url;
    }

    @Override
    public void onTaskDone(Object... values) {

        List<LatLng> points;

        if (currentPolyline != null)
            currentPolyline.remove();

        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
        points = currentPolyline.getPoints();
        GetMarkers(points);
        System.out.println(points.get(0));
    }

    public void GetMarkers(List<LatLng> points)
    {
        double totalDistance = 0;
        for (int i = 0; i < points.size() - 1; i++)
        {
            String stringOne = points.get(i).toString();
            String stringTwo = points.get(i+1).toString();

            LatLng valueOne = parseString(stringOne);
            LatLng valueTwo = parseString(stringTwo);

            totalDistance = totalDistance + calculationByDistance(valueOne,valueTwo);
            if (totalDistance >= 96.5606)
            {
                MarkerOptions location = new MarkerOptions().position(valueTwo).title("Location");
                mMap.addMarker(location);
                totalDistance = 0;
            }
        }

        System.out.println("The total distance is: " + totalDistance);
    }

    public LatLng parseString(String value)
    {
        String values[];

        value = value.replace("lat/lng: (","");
        value = value.replace(")","");

        values = value.split(",");

        LatLng marker = new LatLng(Double.parseDouble(values[0]), Double.parseDouble(values[1]));

        return marker;
    }


    public double calculationByDistance(LatLng StartP, LatLng EndP) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        //DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
                + " Meter   " + meterInDec);

        return Radius * c;
    }
}