package com.example.safetravelsclient;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.content.Intent;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v7.app.AppCompatActivity;
import android.os.StrictMode;
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
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.example.safetravelsclient.models.HttpDataHandler;
import com.example.safetravelsclient.models.TaskLoadedCallback;
import com.example.safetravelsclient.WeatherListActivity;
import com.example.safetravelsclient.models.services.ApiResponse;
import com.example.safetravelsclient.models.services.LocationMarker;
import com.example.safetravelsclient.models.services.LocationMarkerService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.example.safetravelsclient.models.FetchURL;
import com.example.safetravelsclient.models.services.LocationMarker;

import com.example.safetravelsclient.models.transition.WeatherDataTransition;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback, LocationListener {
    ArrayList <LatLng> markers = new ArrayList<>();

    private WeatherDataTransition weatherDataTransition;
    int destCheck = 0;
    private GoogleMap mMap;
    private MarkerOptions place1, place2;

    private LocationManager locationManager;
    private String provider;

    //private FusedLocationProviderClient mFused;
    private final int FINE_LOCATION_PERMISSION = 9999;

    Button to_weather_list;

    Toolbar toolbar;
    int markerId = 1;
    Button getDirection;
    EditText getFrom;
    String fromText = "";
    String toText = "";
    EditText getTo;
    Switch checkLocation;
    Boolean checkLocationStatus;
    Boolean initialSetup = true;
    //Button getCords;
    Button button;
    private Polyline currentPolyline;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


       // this.to_weather_list = findViewById(R.id.button_to_weather_list);



        //   this.weatherDataTransition = this.getIntent().getParcelableExtra(this.getString(R.string.intent_extra_product));

//        this.getExampleData().setText(this.weatherDataTransition.getMarkerId());

        button = findViewById(R.id.directions);

       /* this.to_weather_list.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), InDepthViewActivity.class);

                intent.putExtra(
                        getString(R.string.intent_extra_product),
                        new WeatherDataTransition()
                );

                startActivityOnClick(view);
                //this.startActivity(new Intent(getApplicationContext(), WeatherListActivity.class));
               // startActivityOnClick(view);
            }
        });
*/
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_PERMISSION);
            // mMap.setMyLocationEnabled(true);

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


    public void inDepthViewOnClick(View view) {

        this.startActivity(new Intent(getApplicationContext(), WeatherListActivity.class));

        /*Intent intent = new Intent(getApplicationContext(), InDepthViewActivity.class);

        intent.putExtra(
                getString(R.string.intent_extra_product),
                new WeatherDataTransition()
        );

        this.startActivity(intent);*/
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        toolbar.setTitle(null);
        toolbar.setBackground(getResources().getDrawable(R.drawable.rounded_corners));

        //mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        Log.d("mylog", "Added Markers");

    }

    private void getDeviceLocation()
    {

    }


    // private TextView getExampleData() {
    //  return (TextView) this.findViewById(R.id.textView);
    //}

    public void onDirectionButtonClick(View view) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popUp = inflater.inflate(R.layout.direction_pop_up, null);

        //getCords = findViewById(R.id.button3);
        getFrom = popUp.findViewById(R.id.editText);
        getTo = popUp.findViewById(R.id.editText2);
        getDirection = (Button) popUp.findViewById(R.id.directions);
        checkLocation = popUp.findViewById(R.id.switch1);

        if (initialSetup.equals(true))
        {
            checkLocation.setChecked(true);
        }

        else
        {
            getFrom.setText(fromText);
            getTo.setText(toText);
        }

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

        checkLocation.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View view) {
                                                 if (checkLocation.isChecked()) {
                                                     getFrom.setClickable(false);
                                                 } else {
                                                     getFrom.setClickable(true);
                                                 }
                                             }
                                         });
        getDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initialSetup = false;
                fromText = getFrom.getText().toString();
                toText = getTo.getText().toString();
                checkLocationStatus = checkLocation.isChecked();
                new GetCoordinates().execute(getFrom.getText().toString().replace(" ", "+"));
                new GetCoordinates().execute(getTo.getText().toString().replace(" ", "+"));

            }

        });

    }
    public void startActivityOnClick(View view)
    {
        this.startActivity(new Intent(this.getApplicationContext(), InDepthViewActivity.class));
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

            s = "{   \"results\" : [      {         \"address_components\" : [            {               \"long_name\" : \"Tulsa\",               \"short_name\" : \"Tulsa\",               \"types\" : [ \"locality\", \"political\" ]            },            {               \"long_name\" : \"Tulsa County\",               \"short_name\" : \"Tulsa County\",               \"types\" : [ \"administrative_area_level_2\", \"political\" ]            },            {               \"long_name\" : \"Oklahoma\",               \"short_name\" : \"OK\",               \"types\" : [ \"administrative_area_level_1\", \"political\" ]            },            {               \"long_name\" : \"United States\",               \"short_name\" : \"US\",               \"types\" : [ \"country\", \"political\" ]            }         ],         \"formatted_address\" : \"Tulsa, OK, USA\",         \"geometry\" : {            \"bounds\" : {               \"northeast\" : {                  \"lat\" : 36.336506,                  \"lng\" : -95.6815429               },               \"southwest\" : {                  \"lat\" : 35.968097,                  \"lng\" : -96.074478               }            },            \"location\" : {               \"lat\" : 36.1539816,               \"lng\" : -95.99277500000001            },            \"location_type\" : \"APPROXIMATE\",            \"viewport\" : {               \"northeast\" : {                  \"lat\" : 36.336506,                  \"lng\" : -95.6815429               },               \"southwest\" : {                  \"lat\" : 35.968097,                  \"lng\" : -96.074478               }            }         },         \"place_id\" : \"ChIJjy7R3biStocR92rZG8gQaec\",         \"types\" : [ \"locality\", \"political\" ]      }   ],   \"status\" : \"OK\"}";


            Log.d("ONPOST", "S : " + s);

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
                    destCheck++;



                }

                if (destCheck == 2)
                {
                    new FetchURL(MapsActivity.this).execute(getUrl(place1.getPosition(), place2.getPosition(), "driving"), "driving");

                }

                // System.out.println("Cooridnates: " + lat + ", " + lng);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

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
        //System.out.println(points.get(0));
        ArrayList responseArray = new ArrayList<ApiResponse>();
        // ApiResponse apiResponse = new ApiResponse<LocationMarker>();

        for (int i = 0; i < markers.size(); i++)
        {
            LocationMarker locationMarker = createMarkerObject(markers.get(i));

            ApiResponse<LocationMarker> apiResponse = (
                    new LocationMarkerService().addLocationMarker(locationMarker)

            );
            markerId++;
        }

    }

    public LocationMarker createMarkerObject(LatLng latLng)
    {
        LocationMarker locationMarker = new LocationMarker();
        locationMarker.setLatitude((float)latLng.latitude);
        locationMarker.setLongitude((float)latLng.longitude);
        locationMarker.setMarkerID(markerId);
        //locationMarker.setUserID();

        return locationMarker;


    }

    public ArrayList<LatLng> GetMarkers(List<LatLng> points)
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
                markers.add(valueTwo);
                MarkerOptions location = new MarkerOptions().position(valueTwo).title("Location");
                mMap.addMarker(location);
                totalDistance = 0;
            }
        }

        return markers;
        //System.out.println("The total distance is: " + totalDistance);
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
        // Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
        // + " Meter   " + meterInDec);

        return Radius * c;
    }
}