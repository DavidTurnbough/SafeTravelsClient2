package com.example.safetravelsclient;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.safetravelsclient.models.HttpDataHandler;
import com.example.safetravelsclient.models.TaskLoadedCallback;
import com.example.safetravelsclient.models.WeatherListActivity;
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


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback, LocationListener {

    int destCheck = 0;
    private GoogleMap mMap;
    private MarkerOptions place1, place2;

    private LocationManager locationManager;
    private String provider;

//private FusedLocationProviderClient mFused;
    private final int FINE_LOCATION_PERMISSION = 9999;

    Button to_weather_list;


    Button getDirection;
    EditText getFrom;
    EditText getTo;

    //Button getCords;
    Button button;
    private Polyline currentPolyline;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        this.to_weather_list = findViewById(R.id.button_to_weather_list);


        button = findViewById(R.id.directionButton);

        this.to_weather_list.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //this.startActivity(new Intent(getApplicationContext(), WeatherListActivity.class));
                startActivityOnClick(view);
            }
        });
      
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_PERMISSION);
            mMap.setMyLocationEnabled(true);

        }
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        criteria.setAltitudeRequired(false);
        criteria.setSpeedRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(false);

        provider = locationManager.getBestProvider(new Criteria(), false);

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = locationManager.getLastKnownLocation(provider);
        if (location != null)
            Log.i("Log info", "Location Saved");
        else
            Log.i("Log info", "Location not found");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);

        Log.d("mylog", "Added Markers");

    }

    private void getDeviceLocation()
    {

    }



    public void onDirectionButtonClick(View view) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popUp = inflater.inflate(R.layout.direction_pop_up, null);

        //getCords = findViewById(R.id.button3);
        getFrom = popUp.findViewById(R.id.editText);
        getTo = popUp.findViewById(R.id.editText2);
        getDirection = (Button) popUp.findViewById(R.id.directions);

        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;
        final PopupWindow popupWindow = new PopupWindow(popUp, width, height, focusable);

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, -150);

        popUp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }

        });

        getDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new GetCoordinates().execute(getFrom.getText().toString().replace(" ", "+"));
                new GetCoordinates().execute(getTo.getText().toString().replace(" ", "+"));
                //   new FetchURL(MapsActivity.this).execute(getUrl(place1.getPosition(), place2.getPosition(), "driving"), "driving");


            }

        });


    public void startActivityOnClick(View view)
    {
        this.startActivity(new Intent(this.getApplicationContext(), WeatherListActivity.class));
    }

    @Override
    public void onLocationChanged(Location location) {

        Double lat;
        Double lng;

        lat = location.getLatitude();
        lng = location.getLongitude();
        LatLng latLng = new LatLng(lat, lng);
       // mMap.addMarker(new MarkerOptions().position(latLng).title("My position"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onResume() {
        super.onResume();
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(provider, 400, 1, this);
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
                    destCheck++;

                }
                else
                {
                    place2 = new MarkerOptions().position(new LatLng(Double.parseDouble(lat),Double.parseDouble(lng))).title("Location 2");
                    mMap.addMarker(place2);



                }

                if (!place1.equals(null) && !place2.equals(null))
                {
                    new FetchURL(MapsActivity.this).execute(getUrl(place1.getPosition(), place2.getPosition(), "driving"), "driving");

                }

                System.out.println("Cooridnates: " + lat + ", " + lng);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void onClick(View v)
    {
        new FetchURL(MapsActivity.this).execute(getUrl(place1.getPosition(), place2.getPosition(), "driving"), "driving");
        new GetCoordinates().execute(getFrom.getText().toString().replace(" ", "+"));
        new GetCoordinates().execute(getTo.getText().toString().replace(" ", "+"));
        //new FetchURL(MapsActivity.this).execute(getUrl(place1.getPosition(),place2.getPosition(), "driving"), "driving");
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