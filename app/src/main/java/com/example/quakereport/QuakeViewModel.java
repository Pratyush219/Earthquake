package com.example.quakereport;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class QuakeViewModel extends AndroidViewModel implements VolleyCallbackListener {
    MutableLiveData<List<Earthquake>> mReport;

    public QuakeViewModel(@NonNull @NotNull Application application) {
        super(application);
    }

    public void init(){
        if(mReport == null){
            mReport = new MutableLiveData<>();
            EarthquakeRepository.getInstance(this.getApplication())
                    .extractEarthquakes(this);
        }
    }

    public LiveData<List<Earthquake>> getReport(){
        return mReport;
    }

    @Override
    public void onSuccess(ArrayList<Earthquake> earthquakes) {
        mReport.setValue(earthquakes);
    }

    @Override
    public void onFailure() {
        mReport.setValue(null);
        Toast.makeText(getApplication(), "Data not fetched", Toast.LENGTH_SHORT).show();
    }
}
