package com.cognitev.nearbyapp.ui.activity;

import android.Manifest;
import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.cognitev.base.error.UiError;
import com.cognitev.nearbyapp.BuildConfig;
import com.cognitev.nearbyapp.R;
import com.cognitev.nearbyapp.ui.adapter.VenuesAdapter;
import com.cognitev.nearbyapp.ui.dto.VenueItemView;
import com.cognitev.nearbyapp.ui.viewmodel.VenuesViewModel;
import com.cognitev.utils.Defs;
import com.cognitev.utils.MyPreferences;
import com.cognitev.utils.TextUtils;

import java.util.List;


public class MainActivity extends LifecycleActivity {

    public static final String TAG = MainActivity.class.getName();
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    LocationManager locationManager;
    private View errorView, loadingView;
    private TextView errorTextView;
    private ImageView errorImageView;
    private SwitchCompat appModeSwitch;
    private RecyclerView venuesRecyclerView;
    private VenuesAdapter venuesAdapter;
    private VenuesViewModel venuesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setToolBar();

        appModeSwitch = findViewById(R.id.app_mode_switch);
        errorView = findViewById(R.id.error_relative_layout_view);
        loadingView = findViewById(R.id.loading_relative_layout_view);
        errorTextView = findViewById(R.id.error_text_view);
        errorImageView = findViewById(R.id.error_image_view);
        venuesRecyclerView = findViewById(R.id.recycler_view_venues);

        appModeSwitch.setChecked(MyPreferences.getInstance().getIsRealTime());
        venuesRecyclerView.setVisibility(View.GONE);
        errorView.setVisibility(View.GONE);
        loadingView.setVisibility(View.VISIBLE);
        appModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MyPreferences.getInstance().setIsRealTime(isChecked);
            }
        });

        locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);

        venuesViewModel =
                ViewModelProviders.of(this).get(VenuesViewModel.class);

    }

    private void setToolBar() {
        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        tb.setTitle(TextUtils.getString(R.string.app_name));
    }

    private void observeViewModel() {


        venuesViewModel.getProgressLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean) {
                    showProgressBar();
                } else {
                    hideProgressBar();
                }
            }
        });

        venuesViewModel.getVenuesLiveData().observe(this, new Observer<List<VenueItemView>>() {
            @Override
            public void onChanged(@Nullable List<VenueItemView> venueItemViews) {
                showVenues(venueItemViews);
            }
        });

        venuesViewModel.getErrorObservable().observe(this, new Observer<UiError>() {
            @Override
            public void onChanged(@Nullable UiError uiError) {

                switch (uiError.getErrorsDisplayTypes()) {
                    case Defs.IN_SCREEN:
                        showErrorView(uiError);
                        break;
                    case Defs.SNACK_BAR:
                        showErrorSnackBar(uiError);
                        break;
                }

            }
        });

    }

    @SuppressWarnings("MissingPermission")
    private void getLastLocation() {
        venuesViewModel.loadLocation(this);
        observeViewModel();
    }

    private void showProgressBar() {
        loadingView.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        loadingView.setVisibility(View.GONE);
    }

    private void showVenues(List<VenueItemView> venueItemViews) {
        venuesAdapter = new VenuesAdapter(venueItemViews, MainActivity.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        venuesRecyclerView.setLayoutManager(mLayoutManager);
        venuesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        venuesRecyclerView.setAdapter(venuesAdapter);

        venuesRecyclerView.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);

    }


    private void showErrorView(UiError uiError) {
        venuesRecyclerView.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
        errorTextView.setText(uiError.getMessage());
    }

    private void showErrorSnackBar(UiError uiError) {
        venuesRecyclerView.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);
        showSnackBar(uiError.getMessage());
    }

    private void showSnackBar(final String text) {
        View container = findViewById(R.id.main_activity_container);
        if (container != null) {
            Snackbar.make(container, text, Snackbar.LENGTH_LONG).show();
        }
    }

    private void showSnackBar(final int mainTextStringId, final int actionStringId,
                              View.OnClickListener listener) {
        Snackbar.make(findViewById(android.R.id.content),
                getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(actionStringId), listener).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!checkPermissions()) {
            requestPermissions();
        } else {
            getLastLocation();
        }
    }

    /**
     * Return the current state of the permissions needed.
     */
    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                REQUEST_PERMISSIONS_REQUEST_CODE);
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION);

        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.");

            showSnackBar(R.string.permission_rationale, android.R.string.ok,
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startLocationPermissionRequest();
                        }
                    });

        } else {
            Log.i(TAG, "Requesting permission");
            startLocationPermissionRequest();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.i(TAG, "onRequestPermissionResult");
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                Log.i(TAG, "User interaction was cancelled.");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted.
                getLastLocation();
            } else {
                showSnackBar(R.string.permission_denied_explanation, R.string.settings,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent();
                                intent.setAction(
                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package",
                                        BuildConfig.APPLICATION_ID, null);
                                intent.setData(uri);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        });
            }
        }
    }

}
