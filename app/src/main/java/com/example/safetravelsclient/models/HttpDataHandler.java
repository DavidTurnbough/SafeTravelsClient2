package com.example.safetravelsclient.models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class HttpDataHandler {

    public HttpDataHandler()
    {

    }

    public String getaHttpData(String requestURL) throws IOException {
        URL url;
        String response = "";

        try {
            url = new URL(requestURL);
            HttpURLConnection connect = (HttpURLConnection) url.openConnection();
            connect.setRequestMethod("GET");
            connect.setReadTimeout(15000);
            connect.setConnectTimeout(15000);
            connect.setDoInput(true);
            connect.setDoOutput(true);
            connect.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            int responseCode = connect.getResponseCode();
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(connect.getInputStream()));
                while ((line = br.readLine()) != null)
                    response += line;

            } else
                response = "";
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}
