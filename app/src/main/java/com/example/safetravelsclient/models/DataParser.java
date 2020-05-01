//*******************************
//Written By William Henness
//********************************

package com.example.safetravelsclient.models;


import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataParser{
public List<List<HashMap<String,String>>> parse(JSONObject jObject) throws JSONException {

        List<List<HashMap<String, String>>> routes = new ArrayList<>() ;
        JSONArray jRoutes;
        JSONArray jLegs;
        JSONArray jSteps;


        JSONArray array = jObject.getJSONArray("routes");
        JSONObject routesTwo = array.getJSONObject(0);
        JSONArray legs = routesTwo.getJSONArray("legs");
        JSONObject steps = legs.getJSONObject(0);
        JSONObject distance = steps.getJSONObject("distance");

        try {

        jRoutes = jObject.getJSONArray("routes");

        for(int i=0;i<jRoutes.length();i++){
        jLegs = ( (JSONObject)jRoutes.get(i)).getJSONArray("legs");
        List path = new ArrayList<>();

        for(int j=0;j<jLegs.length();j++){
        jSteps = ( (JSONObject)jLegs.get(j)).getJSONArray("steps");

        for(int k=0;k<jSteps.length();k++){
        String polyline = "";
        polyline = (String)((JSONObject)((JSONObject)jSteps.get(k)).get("polyline")).get("points");
        List<LatLng> list = decodePoly(polyline);

        for(int l=0;l<list.size();l++){
        HashMap<String, String> hm = new HashMap<>();
        hm.put("lat", Double.toString((list.get(l)).latitude) );
        hm.put("lng", Double.toString((list.get(l)).longitude) );
        path.add(hm);
        }
        }
        routes.add(path);
        }
        }

        } catch (JSONException e) {
        e.printStackTrace();
        }catch (Exception e){
        }


        return routes;
        }

        //**********************************
        //Method to decode polyline
        // Source:  http://jeffreysambells.com/2010/05/27/decoding-polylines-from-google-maps-direction-api-with-java
        //*************************************
        private List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<>();
        int index = 0;
        int length = encoded.length();
        int latitude = 0;
        int longitude = 0;

        while (index < length) {
        int value;
        int shift = 0;
        int result = 0;
        do {
        value = encoded.charAt(index++) - 63;
        result |= (value & 0x1f) << shift;
        shift += 5;
        } while (value >= 0x20);
        int _latitude = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
        latitude += _latitude;

        shift = 0;
        result = 0;
        do {
        value = encoded.charAt(index++) - 63;
        result |= (value & 0x1f) << shift;
        shift += 5;
        } while (value >= 0x20);
        int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
        longitude += dlng;

        LatLng p = new LatLng((((double) latitude / 1E5)),
        (((double) longitude / 1E5)));
        poly.add(p);
        }

        return poly;
        }
        }
