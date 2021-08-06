package com.example.quakereport;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    private QuakeViewModel mQuakeViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        QuakeAdapter adapter = new QuakeAdapter(this);
        RecyclerView earthquakeListView = findViewById(R.id.list);
        ProgressBar progressBar = findViewById(R.id.progress_circular);
        // Instantiate the ViewModel
        mQuakeViewModel = new ViewModelProvider(this).get(QuakeViewModel.class);
        // Update liveData
        mQuakeViewModel.init();
        earthquakeListView.setAdapter(adapter);
        earthquakeListView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        // Observe changes to live data and update UI
        mQuakeViewModel.getReport().observe(this, earthquakes -> {
            if(earthquakes != null) adapter.setEarthquakes(mQuakeViewModel.getReport().getValue());
            progressBar.setVisibility(View.GONE);
            earthquakeListView.setVisibility(View.VISIBLE);
        });
    }

}