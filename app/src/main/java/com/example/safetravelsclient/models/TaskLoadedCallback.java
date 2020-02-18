package com.example.safetravelsclient.models;


import org.json.JSONObject;

public interface TaskLoadedCallback {
    void onTaskDone(Object... values);
}