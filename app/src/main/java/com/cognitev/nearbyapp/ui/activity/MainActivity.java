package com.cognitev.nearbyapp.ui.activity;

import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cognitev.nearbyapp.R;
import com.cognitev.nearbyapp.ui.adapter.VenuesAdapter;
import com.cognitev.nearbyapp.ui.dto.VenueItemView;
import com.cognitev.nearbyapp.ui.viewmodel.VenuesViewModel;

import java.util.List;


public class MainActivity extends LifecycleActivity {

    LocationManager locationManager;
    private RecyclerView venuesRecyclerView;
    private VenuesAdapter venuesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        venuesRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_venues);

        locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);


        VenuesViewModel venuesViewModel =
                ViewModelProviders.of(this).get(VenuesViewModel.class);
        observeViewModel(venuesViewModel);

    }

    private void observeViewModel(VenuesViewModel venuesViewModel) {

        venuesViewModel.getRepoListObservable().observe(this, new Observer<List<VenueItemView>>() {
            @Override
            public void onChanged(@Nullable List<VenueItemView> venueItemViews) {
                venuesAdapter = new VenuesAdapter(venueItemViews, MainActivity.this);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                venuesRecyclerView.setLayoutManager(mLayoutManager);
                venuesRecyclerView.setItemAnimator(new DefaultItemAnimator());
                venuesRecyclerView.setAdapter(venuesAdapter);

            }
        });

    }


}
