//*****************
// Written By William Henness
//*****************


package com.example.safetravelsclient;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.content.Intent;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import com.android.volley.toolbox.JsonArrayRequest;
import com.example.safetravelsclient.models.fields.WeatherTransitionData;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.android.volley.toolbox.Volley;
import com.example.safetravelsclient.models.HttpDataHandler;
import com.example.safetravelsclient.models.TaskLoadedCallback;
import com.example.safetravelsclient.models.adapter.WeatherListAdapter;
import com.example.safetravelsclient.models.interfaces.VolleyCallback;
import com.example.safetravelsclient.models.services.ApiResponse;
import com.example.safetravelsclient.models.services.LocationMarker;
import com.example.safetravelsclient.models.services.LocationMarkerService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.example.safetravelsclient.models.FetchURL;

import com.example.safetravelsclient.models.transition.WeatherDataTransition;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;


//*****************************
//Maps Class and Implementation
//*****************************
public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback, LocationListener {

    //******************
    //Variable Declarations
    //******************
    ArrayList<LatLng> markers = new ArrayList<>();
    private static WeatherDataTransition weatherDataTransition = new WeatherDataTransition();
    int destCheck = 0;
    private GoogleMap mMap;
    private MarkerOptions place1, place2;
    static int startup = 0;
    private String location;
    private LocationManager locationManager;
    private String provider;
    String returnFromText;
    String returnToText;
    private final int FINE_LOCATION_PERMISSION = 9999;
    private final int CODE_ASK_PERMISSIONS = 10;
    private boolean permissions_granted = false;
    public LocationMarker tempLocationMarker = new LocationMarker();
    public ArrayList<WeatherDataTransition> practice = new ArrayList<WeatherDataTransition>();
    public ArrayList<WeatherDataTransition> transition = new ArrayList<WeatherDataTransition>();
    int total = 0;
    int count = 0;
    int markerCount = 0;
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
    Button button;
    private Polyline currentPolyline;
    static UUID uuid = UUID.randomUUID();
    int prec = 0;
   static ArrayList<Integer> precArray = new ArrayList<Integer>();

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);

        //*******************
        //Check Permissions
        //*******************
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            this.checkPermissions();
        }
        else
        {
            this.permissions_granted = true;
        }
        if(permissions_granted) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            button = findViewById(R.id.directions);

        //***************
        //Get Map Fragment
        //****************
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_COARSE);
            criteria.setAltitudeRequired(false);
            criteria.setSpeedRequired(false);
            criteria.setBearingRequired(false);
            criteria.setCostAllowed(false);

            provider = locationManager.getBestProvider(criteria, false);

            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            Location location = locationManager.getLastKnownLocation(provider);
            if (location != null)
                Log.i("Log info", "Location Saved");
            else
                Log.i("Log info", "Location not found");
        }

    }


    // Creates a pop-up when the user clicks the i symbol instructing the user on how to
    // use the application.
    public void openInfoDialog(View view) {
        InfoDialog infoDialog = new InfoDialog();
        infoDialog.show(getSupportFragmentManager(), "infoDialog");
    }


    //******************
    //Open List View of weather
    //*******************
    public void listViewOnClick(View view) {

        Intent intent = new Intent(getApplicationContext(), WeatherListActivity.class);
        intent.putParcelableArrayListExtra("WeatherData", practice);
        this.startActivity(intent);
    }


    //******************
    //On Map Ready Method
    //******************
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        toolbar.setTitle(null);
        toolbar.setBackground(getResources().getDrawable(R.drawable.rounded_corners));

        //*********************************
        // On Click listener for all markers
        //*********************************
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                int position = Integer.parseInt(marker.getTitle());

                WeatherDataTransition weatherDataTransition = practice.get(position);
                Intent intent = new Intent(getApplicationContext(), InDepthViewActivity.class);
                intent.putExtra("item_to_display", weatherDataTransition);
                intent.putParcelableArrayListExtra("WeatherData", practice);

                View v = findViewById(android.R.id.content).getRootView();
                startActivity(intent);

                return false;
            }
        });
        Log.d("mylog", "Added Markers");

    }

    //***************************
    //If the get Directions button is clicked
    //***************************
    public void onDirectionButtonClick(View view) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popUp = inflater.inflate(R.layout.direction_pop_up, null);

        getFrom = popUp.findViewById(R.id.editText);
        getTo = popUp.findViewById(R.id.editText2);
        getDirection = popUp.findViewById(R.id.directions);
        checkLocation = popUp.findViewById(R.id.switch1);

        if (initialSetup.equals(true)) {
            checkLocation.setChecked(true);
        } else {
            getFrom.setText(fromText);
            getTo.setText(toText);
        }

        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;

        //********************************
        //Pop up window method
        //*********************************
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
                    getFrom.setEnabled(false);
                    getFrom.setText("Current Location");
                    location = "Current Location";
                } else {
                    getFrom.setEnabled(true);
                }
            }
        });
        getDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uuid = UUID.randomUUID();
                mMap.clear();
                markers.clear();
                practice.clear();
                markerId = 1;
                destCheck = 0;
                startup = 0;
                count = 0;
                markerCount = 0;

                initialSetup = false;
                fromText = getFrom.getText().toString();
                if (fromText.equals("Current Location") || fromText.equals("")) {
                    try {
                        getCurrentAddress(getTo);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    toText = getTo.getText().toString();
                    checkLocationStatus = checkLocation.isChecked();

                    new GetCoordinates().execute(getFrom.getText().toString().replace(" ", "+"));
                    new GetCoordinates().execute(getTo.getText().toString().replace(" ", "+"));
                }
                popupWindow.dismiss();


            }

        });

    }

    //************************************
    //Get address from pop up window if on current location
    //************************************

    public void getCurrentAddress(EditText getTo) throws IOException {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        List<Address> getFrom = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
        Address address = getFrom.get(0);
        new GetCoordinates().execute(address.getAddressLine(0).replace(" ", "+"));
        new GetCoordinates().execute(getTo.getText().toString().replace(" ", "+"));

    }

    //************************************
    //Monitors Users Location
    //************************************
    @Override
    public void onLocationChanged(Location location) {

        Double lat;
        Double lng;

        lat = location.getLatitude();
        lng = location.getLongitude();
        LatLng latLng = new LatLng(lat, lng);
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

    //*****************************
    //On Map Return, Reload Information
    //*****************************
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onResume() {
        super.onResume();

        markerId= 1;
        markerCount = 0;
        precArray.clear();
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            this.checkPermissions();
        }
        if (permissions_granted) {
            locationManager.requestLocationUpdates(provider, 400, 1, this);
            if (startup != 0) {
                practice = getIntent().getExtras().getParcelableArrayList("WeatherData");

                getRouteData(new VolleyCallback() {
                                 @Override
                                 public void onSuccess(JSONObject result) throws JSONException {
                                     JSONArray rows = result.getJSONArray("rows");
                                     JSONObject rowsObject = rows.getJSONObject(0);
                                     JSONArray elements = rowsObject.getJSONArray("elements");
                                     JSONObject elementsObject = elements.getJSONObject(0);
                                     JSONObject duration = elementsObject.getJSONObject("duration");
                                     String addedTime = duration.getString("text");


                                 }

                             }
                );
            }
        }

    }


    //**************************
    //Cetner screen on users location
    //**************************
    public void centerScreen(View view) {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        onLocationChanged(location);
    }


    //*********************************
    //Get route data from the server
    //********************************
    private void getRouteData(final VolleyCallback callback){//, //Double latitude2, Double longitude2) {
        String url = String.format("https://weatherways-server.herokuapp.com/api/markers/"+uuid);

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try{

                            updateRoute(response);

                        } catch (JSONException | IOException ex) {
                            ex.printStackTrace();
                        }
                    }

                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        // Do something when error occurred
                    }
                }
        );

        queue.add(jsonArrayRequest);
    }



    //********************************
    //Begin placing markers on map
    //********************************
    public void updateRoute(JSONArray jsonArray) throws JSONException, IOException {

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Double latitude = jsonObject.getDouble("Latitude");
            Double longitude = jsonObject.getDouble("Longitude");
            LatLng latLng = new LatLng(latitude, longitude);
            markers.add(latLng);
        }

            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            LatLng from = markers.get(0);
            LatLng to = markers.get(markers.size() - 1);

            List<Address> getFrom = geocoder.getFromLocation(from.latitude, from.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            List<Address> getTo = geocoder.getFromLocation(to.latitude, to.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

            new GetCoordinates().execute(getFrom.get(0).toString().replace(" ", "+"));
            new GetCoordinates().execute(getTo.get(0).toString().replace(" ", "+"));

            System.out.println("Hello");

    }

    //************************
    // Task used to get route
    //************************
    private class GetCoordinates extends AsyncTask<String, Void, String> {
        ProgressBar bar = new ProgressBar(MapsActivity.this);

        @Override
        protected String doInBackground(String... strings) {
            String response;
            try {
                String address = strings[0];
                HttpDataHandler http = new HttpDataHandler();
                String url = String.format("https://maps.googleapis.com/maps/api/geocode/json?address=%s", address + "&key=" + getString(R.string.google_maps_key));
                response = http.getaHttpData(url);
                return response;
            } catch (Exception ex) {
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected void onPostExecute(String s) {

            try {
                JSONObject jsonObject = new JSONObject(s);
                if (jsonObject.getString("status").equals("ZERO_RESULTS"))
                {
                    Toast toast = Toast.makeText(getApplicationContext(),"No Results",Toast.LENGTH_SHORT);
                    toast.show();
                return;
                }
                String lat = ((JSONArray) jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry")
                        .getJSONObject("location").get("lat").toString();
                String lng = ((JSONArray) jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry")
                        .getJSONObject("location").get("lng").toString();

                if (destCheck == 0) {

                    place1 = new MarkerOptions().position(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng))).title("Location 1");
                    destCheck++;

                } else {
                    place2 = new MarkerOptions().position(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng))).title("Location 2");
                    destCheck++;


                }

                if (destCheck == 2) {
                    new FetchURL(MapsActivity.this).execute(getUrl(place1.getPosition(), place2.getPosition(), "driving"), "driving");

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    //*******************************
    // Grabs URL used for getting route
    //*******************************
    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        String mode = "mode=" + directionMode;
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        String output = "json";
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);
        return url;
    }

    //*******************************
    //Upon completion of get route task, place plylnie on map, send data to server
    //*******************************
    @Override
    public void onTaskDone(Object... values) {

        List<LatLng> points;

        if (currentPolyline != null)
            currentPolyline.remove();

        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);

        points = currentPolyline.getPoints();

        GetMarkers(points);
        for (int i = 0; i < markers.size(); i++) {

            createMarkerObject(markers.get(i));
            LocationMarker locationMarker = createMarkerObject(markers.get(i));

            if (startup == 0) {
                ApiResponse<LocationMarker> apiResponse = (
                        new LocationMarkerService().addLocationMarker(locationMarker));
                prec = apiResponse.getData().getPrecipitationChance();
                precArray.add(prec);


                //**********************
                //Get weather for all markers on route
                //***********************
                getWeather(new VolleyCallback() {
                               @Override
                               public void onSuccess(JSONObject result) throws JSONException {
                                   JSONObject main = result.getJSONObject("main");
                                   String name = result.getString("name");
                                   JSONArray weatherArray = result.getJSONArray("weather");
                                   JSONObject weatherObject = weatherArray.getJSONObject(0);
                                   JSONObject wind = result.getJSONObject("wind");

                                   String weatherMain = weatherObject.getString("main");
                                   String weatherDescription = weatherObject.getString("description");
                                   int temp = main.getInt("temp");
                                   int tempHigh = main.getInt("temp_max");
                                   int tempLow = main.getInt("temp_min");
                                   int windSpeed = wind.getInt("speed");
                                   int humidity = main.getInt("humidity");

                                   if (total == 0) {
                                       changeLogos(weatherMain, temp);
                                       total++;
                                   }

                                   WeatherTransitionData weatherTransitionData = new WeatherTransitionData();
                                   weatherTransitionData.setTemperature(String.valueOf(temp));
                                   weatherTransitionData.setImage(weatherMain);
                                   weatherTransitionData.setDescription(weatherDescription);
                                   weatherTransitionData.setHumidity(String.valueOf(humidity));
                                   weatherTransitionData.setLocation(name);
                                   weatherTransitionData.setTemperatureHigh(String.valueOf(tempHigh));
                                   weatherTransitionData.setTemperatureLow(String.valueOf(tempLow));
                                   weatherTransitionData.setWindDirection(String.valueOf(2));
                                   weatherTransitionData.setWindVelocity(String.valueOf(windSpeed));
                                   weatherTransitionData.setMarkerId(markerId);
                                   if (precArray.size() != 0)
                                       weatherTransitionData.setPrecipitation(String.valueOf(precArray.get(total - 1)));
                                   total++;

                                   weatherDataTransition = new WeatherDataTransition(weatherTransitionData);
                                   practice.add(count, weatherDataTransition);
                                   count++;

                                   System.out.println("Hello");

                               }
                           }
                        , markers.get(i).latitude, markers.get(i).longitude);


            }
            markerId++;
        }

        if (practice.size()!= 0)
        {
            int currentTemp = Integer.parseInt(practice.get(0).getMarkerData().getTemperature());
        String weatherDescription = practice.get(0).getMarkerData().getImage();
        changeLogos(weatherDescription, currentTemp);
    }
        startup = 1;

    }

    //*************************
    //Create a location marker to send to the server
    //*************************
    public LocationMarker createMarkerObject(LatLng latLng) {
        tempLocationMarker.setUserID(uuid);
        tempLocationMarker.setLatitude((float) latLng.latitude);
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
            List<Address> listAddresses = geocoder.getFromLocation((float) latLng.latitude, (float) latLng.longitude, 1);
            if (null != listAddresses && listAddresses.size() > 0) {
                String _Location = listAddresses.get(0).getAddressLine(0);
                tempLocationMarker.setLocation(_Location);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        tempLocationMarker.setLongitude((float) latLng.longitude);
        tempLocationMarker.setMarkerID(markerId);

return tempLocationMarker;

    }



    //**********************
    //Callback method for getting weather
    //**********************
    private void getWeather(final VolleyCallback callback, Double latitude, Double longitude){//, //Double latitude2, Double longitude2) {
        String url = String.format("https://api.openweathermap.org/data/2.5/weather?lat="+latitude+"&lon="+longitude+"&units=imperial"+"&appid="+"6ca4e03fef7b04c1fb154a6cd7770d0c");
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            callback.onSuccess(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        System.out.println("Hello");
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ERROR", "Error occurred ", error);

                    }
                });

        queue.add(jsonObjectRequest);

    }


    //**********************************************
    //Gets arrival time, not currently used as there is no use
    //**********************************************
    private void getArrivalTime(final VolleyCallback callback, Double latitude, Double longitude){//, //Double latitude2, Double longitude2) {
        String url = String.format("https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins="+place1.getPosition().latitude+","+place2.getPosition().longitude+"&destinations="+latitude+","+longitude+"&key=AIzaSyCtXFElW8rOpvG7EWTa3jl9EIufKGwZSi0");

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            callback.onSuccess(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        System.out.println("Hello");
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ERROR", "Error occurred ", error);

                    }
                });

        queue.add(jsonObjectRequest);

    }


    //********************************
    //Goes with ArrivalTime
    //********************************
    public Date newDate(Date currentTime, String addedTime)
    {
        Long longTime = currentTime.getTime();
        String values[] = addedTime.split(" ");
        long hours;
        long minutes;
        if (values.length == 2)
        {
             hours = 0;
             minutes = Long.valueOf(values[0]);
        }
        else {
             hours = Long.valueOf(values[0]);
             minutes = Long.valueOf(values[2]);
        }
            Date time = new Date();
            long convHours = hours * 3600000;
            long convminutes = minutes * 60000;
            long fullConversion = convHours + convminutes;
            long newTime = longTime + fullConversion;
            Date newDate = new Date(newTime);

        return newDate;
    }


    //*********************************
    //Method developed to place markers on route at specific locations based on distance
    //*********************************
    public ArrayList<LatLng> GetMarkers(List<LatLng> points) {
        double totalDistance = 0;

        if(startup == 0)
        {
        for (int i = 0; i < points.size(); i++) {
            if (i == points.size() - 1) {
                String stringOne = points.get(i).toString();
                LatLng valueOne = parseString(stringOne);
                markers.add(valueOne);
                MarkerOptions location = new MarkerOptions().position(valueOne).title(String.valueOf(markerCount));
                mMap.addMarker(location);

            } else {

                String stringOne = points.get(i).toString();
                String stringTwo = points.get(i + 1).toString();

                LatLng valueOne = parseString(stringOne);
                LatLng valueTwo = parseString(stringTwo);

                if (i == 0) {
                    markers.add(valueOne);
                    MarkerOptions location = new MarkerOptions().position(valueOne).title("0");
                    mMap.addMarker(location);
                    markerCount++;

                }

                totalDistance = totalDistance + calculationByDistance(valueOne, valueTwo);
                if (totalDistance >= 400) {


                    markers.add(valueTwo);
                    MarkerOptions location = new MarkerOptions().position(valueTwo).title(String.valueOf(markerCount));
                    mMap.addMarker(location);
                    markerCount++;
                    totalDistance = 0;
                }
            }

        }

        }
        else
        {
            for (int i = 0; i < markers.size(); i++)
            {
                MarkerOptions location = new MarkerOptions().position(markers.get(i)).title(String.valueOf(i));
                mMap.addMarker(location);

            }
        }
        return markers;
    }

    //*********************
    //Parses lat/lng string recieved from google
    //*********************
    public LatLng parseString(String value) {
        String[] values;

        value = value.replace("lat/lng: (", "");
        value = value.replace(")", "");

        values = value.split(",");

        LatLng marker = new LatLng(Double.parseDouble(values[0]), Double.parseDouble(values[1]));

        return marker;
    }

    //************************
    //Runnign total of route distance, resets at poitns
    //************************
    public double calculationByDistance(LatLng StartP, LatLng EndP) {
        int Radius = 6371; // radius of earth in Km
        double latitude1 = StartP.latitude;
        double latitude2 = EndP.latitude;
        double longitude1 = StartP.longitude;
        double longitude2 = EndP.longitude;
        double conv_lat = Math.toRadians(latitude2 - latitude1);
        double conv_long = Math.toRadians(longitude2 - longitude1);
        double a = Math.sin(conv_lat / 2) * Math.sin(conv_lat / 2)
                + Math.cos(Math.toRadians(latitude1))
                * Math.cos(Math.toRadians(latitude2)) * Math.sin(conv_long / 2)
                * Math.sin(conv_long / 2);
        double conversion = 2 * Math.asin(Math.sqrt(a));
        double result = Radius * conversion;

        return result;
    }

    //*********************
    //Check all permissions
    //**********************
    private void checkPermissions()
    {
        ArrayList<String> required_perms = new ArrayList<String>();
        ArrayList<String> requested_perms = new ArrayList<String>();
        if(addPermission(requested_perms, Manifest.permission.INTERNET))
        {
            required_perms.add("INTERNET");
        }
        if(addPermission(requested_perms, Manifest.permission.ACCESS_COARSE_LOCATION))
        {
            required_perms.add("ACCESS COARSE LOCATION");
        }
        if(addPermission(requested_perms, Manifest.permission.ACCESS_FINE_LOCATION))
        {
            required_perms.add("ACCESS FINE LOCATION");
        }
        if(requested_perms.size() > 0)
        {
            if(required_perms.size() > 0)
            {
                if(Build.VERSION.SDK_INT >= 23)
                {
                    requestPermissions(requested_perms.toArray(new String[requested_perms.size()]), CODE_ASK_PERMISSIONS);
                    return;
                }
            }
            if(Build.VERSION.SDK_INT >= 23)
            {
                requestPermissions(requested_perms.toArray(new String[requested_perms.size()]), CODE_ASK_PERMISSIONS);
                return;
            }
        }
    }

    private boolean addPermission(List<String> permissions, String perm_check) {
        if (Build.VERSION.SDK_INT >= 23)
        {
            if (checkSelfPermission(perm_check) != PackageManager.PERMISSION_GRANTED)
            {
                permissions.add(perm_check);
                if (!shouldShowRequestPermissionRationale(perm_check))
                {
                    return true;
                }
            }
        }
        return false;
    }
    @Override
    public void onRequestPermissionsResult(int request_code, String[] permissions, int[] grantResults) {
        /*switch (requestCode) {
            case CODE_ASK_PERMISSIONS: {*/
        if(request_code == CODE_ASK_PERMISSIONS)
        {
            Map<String, Integer> perms = new HashMap<>();
            perms.put(Manifest.permission.INTERNET, PackageManager.PERMISSION_GRANTED);
            perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
            perms.put(Manifest.permission.ACCESS_COARSE_LOCATION, PackageManager.PERMISSION_GRANTED);
            for (int i = 0; i < permissions.length; i++)
            {
                perms.put(permissions[i], grantResults[i]);
            }

            if (perms.get(Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED
                    && perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    && perms.get(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            {
                permissions_granted = true;
            }
            else
            {

            }
        }
        else
        {
            super.onRequestPermissionsResult(request_code, permissions, grantResults);
        }
    }

    //****************************
    //Changes features in toolbar
    //****************************
    public void changeLogos(String description, int temp) {

        TextView degrees = findViewById(R.id.degrees);
        ImageView weather = findViewById(R.id.weathertype);
        degrees.setText(String.valueOf(temp) + "°");

        switch (description) {
            case "Thunderstorm":
                weather.setImageDrawable(getResources().getDrawable(R.drawable.thunderstorm));
                break;
            case "Drizzle":
                weather.setImageDrawable(getResources().getDrawable(R.drawable.rainy));
                break;
            case "Rain":
                weather.setImageDrawable(getResources().getDrawable(R.drawable.rainy));
                break;
            case "Snow":
                weather.setImageDrawable(getResources().getDrawable(R.drawable.snowy));
                break;
            case "Clear":
                weather.setImageDrawable(getResources().getDrawable(R.drawable.sunny1));
                break;
            case "Clouds":
                weather.setImageDrawable(getResources().getDrawable(R.drawable.cloudy));
                break;
            case "Fog":
                weather.setImageDrawable(getResources().getDrawable(R.drawable.fog));
                break;
            default:
                break;
        }
    }

}
