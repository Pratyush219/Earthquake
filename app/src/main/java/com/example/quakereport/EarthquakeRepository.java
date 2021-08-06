package com.example.quakereport;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EarthquakeRepository {
    ArrayList<Earthquake> earthquakes = new ArrayList<>();
    final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2012-01-01&endtime=2012-12-01&minmagnitude=6";
    private static JSONObject root = null;
    private static int length = 0;
    private static EarthquakeRepository instance;
    private final Application mApplication;

    private EarthquakeRepository(Application context) {
        this.mApplication = context;
    }

    public static EarthquakeRepository getInstance(Application context){
        if(instance == null) {
            instance = new EarthquakeRepository(context);
        }
        return instance;
    }
//    public MutableLiveData<ArrayList<Earthquake>> getEarthquakes(){
//        setQuakeList();
//        MutableLiveData<ArrayList<Earthquake>> data = new MutableLiveData<>();
//        data.setValue(this.quakeList);
//        Log.i("ValueType", this.quakeList.size()+"");
//        return data;
//    }
//
//    public void setQuakeList() {
//        extractEarthquakes();
//    }

    public void extractEarthquakes(VolleyCallbackListener listener) {

        // Create an empty ArrayList that we can start adding earthquakes to

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
        // build up a list of Earthquake objects with the corresponding data.
        RequestQueue requestQueue = VolleySingleton.getInstance(mApplication).getRequestQueue();
        StringRequest stringRequest = new StringRequest(Request.Method.GET,USGS_REQUEST_URL,
                response -> {
                    if(response!=null) {
                        try {
                            root = new JSONObject(response);
                            Log.i("Status","Root initialized");
//                            Toast.makeText(context, "Entered try-catch block!", Toast.LENGTH_SHORT).show();
                            JSONArray features = root.getJSONArray("features");
                            Log.i("Size: ",""+features.length());
                            length = features.length();
                            Log.i("JSONArray", ""+length);
                            Log.i("Network request","Started");
                            Log.i("EarthQuakesList","Size = " + earthquakes.size());
                            for(int i = 0; i < features.length();i++){
                                Log.i("Status", "Entered for loop " + (i+1)+"th time!");
                                JSONObject earthquakeObject = features.getJSONObject(i);
                                JSONObject details = earthquakeObject.getJSONObject("properties");
                                double magnitude = details.getDouble("mag");
                                String place = details.getString("place");
                                long time = details.getLong("time");
                                String url = details.getString("url");
                                Date date = new Date(time);
                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

                                String finalDate = dateFormat.format(date);
                                SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
                                String formattedTIme = timeFormat.format(date);
                                Log.i("Date: ",finalDate);
                                earthquakes.add(new Earthquake(magnitude, place, finalDate, formattedTIme, url));
                            }
                            Log.i("SingleDate",earthquakes.get(1).getDate());
                            Log.i("EarthQuakesList","Size = " + earthquakes.size());
                            listener.onSuccess(earthquakes);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                error -> {
                    Toast.makeText(mApplication,"Err...Nikal bhai",Toast.LENGTH_SHORT).show();
                    listener.onFailure();

                });
        requestQueue.add(stringRequest);
    }
}
