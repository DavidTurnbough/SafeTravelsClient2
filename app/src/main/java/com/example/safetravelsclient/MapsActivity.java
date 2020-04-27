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
//import com.android.volley.toolbox.Volley;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
//import com.android.volley.toolbox.JsonObjectRequest;

//import android.support.annotation.Nullable;
//import android.support.v7.app.AppCompatActivity;
import android.os.Parcelable;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.android.volley.toolbox.Volley;
import com.example.safetravelsclient.models.HttpDataHandler;
import com.example.safetravelsclient.models.TaskLoadedCallback;
import com.example.safetravelsclient.models.adapter.WeatherListAdapter;
import com.example.safetravelsclient.models.fields.WeatherTransitionData;
import com.example.safetravelsclient.models.interfaces.VolleyCallback;
import com.example.safetravelsclient.models.services.ApiResponse;
import com.example.safetravelsclient.models.services.LocationMarker;
import com.example.safetravelsclient.models.services.LocationMarkerService;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.example.safetravelsclient.models.FetchURL;

import com.example.safetravelsclient.models.transition.WeatherDataTransition;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.UUID;

import static java.lang.Thread.sleep;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback, LocationListener {
    ArrayList<LatLng> markers = new ArrayList<>();

    private WeatherDataTransition weatherDataTransition = new WeatherDataTransition();
    int destCheck = 0;
    private GoogleMap mMap;
    private MarkerOptions place1, place2;
    static int startup = 0;
    private Date arrivalTime;
    private String location;
    private LocationManager locationManager;
    private String provider;
    //private FusedLocationProviderClient mFused;
    private final int FINE_LOCATION_PERMISSION = 9999;

    public LocationMarker tempLocationMarker = new LocationMarker();


    Button to_weather_list;
    private WeatherListAdapter weather_list_adapter;

    public Date newDate;
    public WeatherDataTransition practice[] = new WeatherDataTransition[10];


    int markerPointer = 0;
    TextView temp_text;
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
    static UUID uuid = UUID.randomUUID();

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        button = findViewById(R.id.directions);
        temp_text = findViewById(R.id.temp_text);

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

      //  this.startActivity(new Intent(getApplicationContext(), WeatherListActivity.class));
        System.out.println(practice.length);
        Intent intent = new Intent(getApplicationContext(), WeatherListActivity.class);
      //  intent.putExtras()
        intent.putExtra("WeatherData", practice);
        this.startActivity(intent);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        toolbar.setTitle(null);
        toolbar.setBackground(getResources().getDrawable(R.drawable.rounded_corners));

        //mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        Log.d("mylog", "Added Markers");

    }

    private void getDeviceLocation() {

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
                    location = "Current Location";
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
                popupWindow.dismiss();


            }

        });

    }

    public void startActivityOnClick(View view) {
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
        //  mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
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
        System.out.println("Startup: " + startup);
        //-----
        //Check user lcoation for marker update
        //------

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(provider, 400, 1, this);
        System.out.println("This is the uuid: " + uuid);

        if (startup != 0)
        getRouteData(new VolleyCallback() {
                           @Override
                           public void onSuccess(JSONObject result) throws JSONException {
                               JSONArray rows = result.getJSONArray("rows");
                               JSONObject rowsObject = rows.getJSONObject(0);
                               JSONArray elements = rowsObject.getJSONArray("elements");
                               JSONObject elementsObject = elements.getJSONObject(0);
                               JSONObject duration = elementsObject.getJSONObject("duration");
                               String addedTime = duration.getString("text");

                               Date currentTime = new Date();
                               SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss.S", Locale.US);


                               //ApiResponse<LocationMarker> apiResponse = (
                                    //   new LocationMarkerService().addLocationMarker(tempLocationMarker)

                              // );


                           }
                       }
                );

       // tempLocationMarker.setPrecipitationChance(1);
       // tempLocationMarker.setTemperature(1);

    }








    private void getRouteData(final VolleyCallback callback){//, //Double latitude2, Double longitude2) {
       UUID testId = new UUID(0,0);
        String url = String.format("https://weatherways-server.herokuapp.com/api/markers/"+testId);   // +uuid);

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try{
                            JSONObject object = response.getJSONObject(0);
                            String value = object.getString("id");
                            System.out.println(value);
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

        // Add JsonArrayRequest to the RequestQueue
        queue.add(jsonArrayRequest);
    }




    public void updateRoute(JSONArray jsonArray) throws JSONException, IOException {
       // ArrayList<LatLng> markers = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++)
        {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Double latitude = jsonObject.getDouble("Latitude");
            Double longitude = jsonObject.getDouble("Longitude");
            LatLng latLng = new LatLng(latitude,longitude);
            markers.add(latLng);
            getWeather(markers.get(i).latitude,markers.get(i).longitude);

        }
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        LatLng from = markers.get(0);
        LatLng to = markers.get(markers.size() - 1);

        List<Address> getFrom =  geocoder.getFromLocation(from.latitude, from.longitude,1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        List<Address> getTo =  geocoder.getFromLocation(to.latitude, to.longitude,1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

        new GetCoordinates().execute(getFrom.get(0).toString().replace(" ", "+"));
        new GetCoordinates().execute(getTo.get(0).toString().replace(" ", "+"));



        System.out.println("Hello");
    }





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
            // bar.set
        }


        @Override
        protected void onPostExecute(String s) {

            s = "{   \"results\" : [      {         \"address_components\" : [            {               \"long_name\" : \"Tulsa\",               \"short_name\" : \"Tulsa\",               \"types\" : [ \"locality\", \"political\" ]            },            {               \"long_name\" : \"Tulsa County\",               \"short_name\" : \"Tulsa County\",               \"types\" : [ \"administrative_area_level_2\", \"political\" ]            },            {               \"long_name\" : \"Oklahoma\",               \"short_name\" : \"OK\",               \"types\" : [ \"administrative_area_level_1\", \"political\" ]            },            {               \"long_name\" : \"United States\",               \"short_name\" : \"US\",               \"types\" : [ \"country\", \"political\" ]            }         ],         \"formatted_address\" : \"Tulsa, OK, USA\",         \"geometry\" : {            \"bounds\" : {               \"northeast\" : {                  \"lat\" : 36.336506,                  \"lng\" : -95.6815429               },               \"southwest\" : {                  \"lat\" : 35.968097,                  \"lng\" : -96.074478               }            },            \"location\" : {               \"lat\" : 36.1539816,               \"lng\" : -95.99277500000001            },            \"location_type\" : \"APPROXIMATE\",            \"viewport\" : {               \"northeast\" : {                  \"lat\" : 36.336506,                  \"lng\" : -95.6815429               },               \"southwest\" : {                  \"lat\" : 35.968097,                  \"lng\" : -96.074478               }            }         },         \"place_id\" : \"ChIJjy7R3biStocR92rZG8gQaec\",         \"types\" : [ \"locality\", \"political\" ]      }   ],   \"status\" : \"OK\"}";


            Log.d("ONPOST", "S : " + s);

            try {
                JSONObject jsonObject = new JSONObject(s);

                String lat = ((JSONArray) jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry")
                        .getJSONObject("location").get("lat").toString();
                String lng = ((JSONArray) jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry")
                        .getJSONObject("location").get("lng").toString();

                if (destCheck == 0) {

                    place1 = new MarkerOptions().position(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng))).title("Location 1");
                    mMap.addMarker(place1);
                    destCheck++;

                } else {
                    place2 = new MarkerOptions().position(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng))).title("Location 2");
                    mMap.addMarker(place2);
                    destCheck++;


                }

                if (destCheck == 2) {
                    new FetchURL(MapsActivity.this).execute(getUrl(place1.getPosition(), place2.getPosition(), "driving"), "driving");

                }

                // System.out.println("Cooridnates: " + lat + ", " + lng);
            } catch (Exception e) {
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

        for (int i = 0; i < markers.size(); i++) {
            createMarkerObject(markers.get(i));
            //LocationMarker locationMarker = createMarkerObject(markers.get(i));


            //System.out.println(apiResponse.getRawResponse());

           // practice[0] =
             getWeather(markers.get(i).latitude,markers.get(i).longitude);
          //  transition_weather.add(0,getWeather(markers.get(i).latitude,markers.get(i).longitude));

            markerId++;
            markerPointer++;
        }



    }


    public void createMarkerObject(LatLng latLng) {
       // final LocationMarker locationMarker = new LocationMarker();
        tempLocationMarker.setUserID(uuid);
        tempLocationMarker.setLatitude((float) latLng.latitude);
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
            List<Address> listAddresses = geocoder.getFromLocation((float) latLng.latitude, (float) latLng.longitude, 1);
            if (null != listAddresses && listAddresses.size() > 0) {
                String _Location = listAddresses.get(0).getAddressLine(0);
                tempLocationMarker.setLocation(_Location);
                //System.out.println("Address: " + _Location);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        tempLocationMarker.setLongitude((float) latLng.longitude);
        tempLocationMarker.setMarkerID(markerId);

        getArrivalTime(new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) throws JSONException {
                JSONArray rows = result.getJSONArray("rows");
                JSONObject rowsObject = rows.getJSONObject(0);
                JSONArray elements = rowsObject.getJSONArray("elements");
                JSONObject elementsObject = elements.getJSONObject(0);
                JSONObject duration = elementsObject.getJSONObject("duration");
                String addedTime = duration.getString("text");

                Date currentTime = new Date();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss.S", Locale.US);

                String time = simpleDateFormat.format(currentTime);
                newDate = newDate(currentTime,addedTime);
                tempLocationMarker.setArrivalTime(newDate);
                ApiResponse<LocationMarker> apiResponse = (
                        new LocationMarkerService().addLocationMarker(tempLocationMarker)

                );


            }
        }
        , latLng.latitude, latLng.longitude);

      //  tempLocationMarker.setPrecipitationChance(1);
       // tempLocationMarker.setTemperature(1);

       // try {
          //  wait(1000);
       // } /catch (InterruptedException e) {
           // e.printStackTrace();
       // }
        //locationMarker.s
        //if (!newDate[0].equals(null)){
       // locationMarker.setArrivalTime(newDate[0]);
        //locationMarker.setUserID();



    }






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

                        /* JSONArray rows = response.getJSONArray("rows");
                         JSONObject rowsObject = rows.getJSONObject(0);
                         JSONArray elements = rowsObject.getJSONArray("elements");
                         JSONObject elementsObject = elements.getJSONObject(0);
                         JSONObject duration = elementsObject.getJSONObject("duration");
                         String addedTime = duration.getString("text");

                         Date currentTime = new Date();
                         SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss.S", Locale.US);

                         String time = simpleDateFormat.format(currentTime);
                         newDate[0] = newDate(currentTime,addedTime);
*/
                        System.out.println("Hello");
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ERROR", "Error occurred ", error);

                    }
                });


        //System.out.println(newDate[0]);
        queue.add(jsonObjectRequest);
        //return weatherDataTransition;

    }



    public Date newDate(Date currentTime, String addedTime)
    {
        Long longTime = currentTime.getTime();
        String values[] = addedTime.split(" ");
        long hours = Long.valueOf(values[0]);
        long minutes = Long.valueOf(values[2]);
        Date time = new Date();
        long convHours = hours * 3600000;
        long convminutes = minutes * 60000;
        long fullConversion = convHours + convminutes;
        long newTime = longTime + fullConversion;
        Date newDate = new Date(newTime);
        return newDate;
    }



    public ArrayList<LatLng> GetMarkers(List<LatLng> points) {
        double totalDistance = 0;
        for (int i = 0; i < points.size() - 1; i++) {
            String stringOne = points.get(i).toString();
            String stringTwo = points.get(i + 1).toString();

            LatLng valueOne = parseString(stringOne);
            LatLng valueTwo = parseString(stringTwo);

            totalDistance = totalDistance + calculationByDistance(valueOne, valueTwo);
            if (totalDistance >= 96.5606) {
                markers.add(valueTwo);
                MarkerOptions location = new MarkerOptions().position(valueTwo).title("Location");
                mMap.addMarker(location);
                totalDistance = 0;
            }
        }

        return markers;
        //System.out.println("The total distance is: " + totalDistance);
    }

    public LatLng parseString(String value) {
        String[] values;

        value = value.replace("lat/lng: (", "");
        value = value.replace(")", "");

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

//http://api.openweathermap.org/data/2.5/weather?zip=64804,us&appid=6ca4e03fef7b04c1fb154a6cd7770d0c
    private void getWeather(Double latitude, Double longitude) {
        startup = 1;
        String url = String.format("https://api.openweathermap.org/data/2.5/weather?lat="+latitude+"&lon="+longitude+"&units=imperial"+"&appid="+"6ca4e03fef7b04c1fb154a6cd7770d0c");
        RequestQueue queue = Volley.newRequestQueue(this);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            //JSONArray array = response.getJSONArray("list");
                            //JSONObject object = response.getJSONObject("coord");
                            JSONObject main = response.getJSONObject("main");
                            JSONArray weatherArray = response.getJSONArray("weather");
                            JSONObject weatherObject = weatherArray.getJSONObject(0);
                            JSONObject wind = response.getJSONObject("wind");

                            String weatherMain = weatherObject.getString("main");
                            String weatherDescription = weatherObject.getString("description");
                            int temp = main.getInt("temp");
                            int tempHigh = main.getInt("temp_max");
                            int tempLow = main.getInt("temp_min");
                            int windSpeed = wind.getInt("speed");
                            //int windDeg = wind.getInt("deg");
                            int humidity = main.getInt("humidity");

                            WeatherTransitionData weatherTransitionData = new WeatherTransitionData();
                            weatherTransitionData.setImage(weatherMain);
                            weatherTransitionData.setDescription(weatherDescription);
                            weatherTransitionData.setHumidity(String.valueOf(humidity));
                            weatherTransitionData.setLocation("Location");
                            weatherTransitionData.setTemperatureHigh(String.valueOf(tempHigh));
                            weatherTransitionData.setTemperatureLow(String.valueOf(tempLow));
                            weatherTransitionData.setWindDirection(String.valueOf(2));
                            weatherTransitionData.setWindVelocity(String.valueOf(windSpeed));
                            weatherTransitionData.setMarkerId(markerId);

                           // weatherTransitionData.setPrecipitation(String.valueOf(0));
                           // UUID id = new UUID(0,0);
                            //weatherTransitionData.setId(id);
                            //weatherTransitionData.setTime(String.valueOf(2));
                             weatherDataTransition = new WeatherDataTransition(weatherTransitionData);
                             practice[0] = weatherDataTransition;
                           // transition_weather.add(weatherDataTransition);
                            // weather_list.add(weatherDataTransition);
                           // weather_list.add(weatherDataTransitio
                           // weather_list.add(weatherTransitionData);
                          //  weather_list.add(weatherDataTransition);
                            System.out.println("Hello");


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                            Log.e("ERROR", "Error occurred ", error);

                        }
                });



        queue.add(jsonObjectRequest);

        //return weatherDataTransition;

    }

    public void changeLogos(String description, int temp) {
        TextView degrees = findViewById(R.id.degrees);
        ImageView weather = findViewById(R.id.weathertype);

   // temp = Math.round(temp)   ;
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

