package com.cognitev.nearbyapp.ui.livedata;

import android.Manifest;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import com.cognitev.utils.MyPreferences;

/**
 * Created by Eslam Hussein on 10/13/17.
 */

public class LocationLiveData extends LiveData<Location> implements LocationListener {
    private LocationManager locationManager;
    private Context context;
    private Location mCurrentLocation;

    public LocationLiveData(Context context) {
        this.context = context;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }


    @Override
    protected void onActive() {
        super.onActive();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {

        if (mCurrentLocation == null) {
            mCurrentLocation = location;
            setValue(mCurrentLocation);
        } else {
            if (MyPreferences.getInstance().getIsRealTime()) {
                float diff = location.distanceTo(mCurrentLocation);
                if (diff > 50) {
                    mCurrentLocation = location;
                    setValue(mCurrentLocation);
                }
            }
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        System.out.println(provider);
    }

    @Override
    public void onProviderEnabled(String provider) {
        System.out.println(provider);

    }

    @Override
    public void onProviderDisabled(String provider) {
        System.out.println(provider);


    }
}
