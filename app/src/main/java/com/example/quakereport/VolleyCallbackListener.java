package com.example.quakereport;

import java.util.ArrayList;

public interface VolleyCallbackListener {
    void onSuccess(ArrayList<Earthquake> earthquakes);
    void onFailure();
}
