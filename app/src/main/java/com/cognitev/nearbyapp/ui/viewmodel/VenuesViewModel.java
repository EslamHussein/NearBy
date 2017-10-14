package com.cognitev.nearbyapp.ui.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.location.Location;
import android.support.annotation.Nullable;

import com.cognitev.base.error.UiError;
import com.cognitev.nearbyapp.R;
import com.cognitev.nearbyapp.business.FourSquareBusiness;
import com.cognitev.nearbyapp.ui.dto.VenueItemView;
import com.cognitev.nearbyapp.ui.livedata.LocationLiveData;
import com.cognitev.utils.Defs;
import com.cognitev.utils.TextUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Eslam Hussein on 10/12/17.
 */

public class VenuesViewModel extends AndroidViewModel implements Observer<VenueItemView>, android.arch.lifecycle.Observer<Location> {
    public static final String TAG = VenuesViewModel.class.getName();


    private FourSquareBusiness squareBusiness;
    private MutableLiveData<List<VenueItemView>> venuesLiveData;
    private List<VenueItemView> venueItemViews;
    private MutableLiveData<UiError> errorObservable;
    private MutableLiveData<Boolean> progressLiveData;

    private LocationLiveData locationLiveData;

    public VenuesViewModel(Application application) {
        super(application);
        locationLiveData = new LocationLiveData(application.getApplicationContext());
        progressLiveData = new MutableLiveData<>();
        progressLiveData.setValue(false);
        errorObservable = new MutableLiveData<>();
        squareBusiness = new FourSquareBusiness();
        venuesLiveData = new MutableLiveData<>();
    }

    public void loadLocation(LifecycleOwner lifecycleOwner) {
        locationLiveData.observe(lifecycleOwner, this);


    }


    public LiveData<List<VenueItemView>> getVenuesLiveData() {
        return venuesLiveData;
    }

    public MutableLiveData<UiError> getErrorObservable() {
        return errorObservable;
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        if (venuesLiveData.getValue() == null || venuesLiveData.getValue().size() < 0)
            progressLiveData.setValue(true);
        else
            progressLiveData.setValue(false);

    }

    @Override
    public void onNext(@NonNull VenueItemView venueItemView) {
        venueItemViews.add(venueItemView);

    }

    @Override
    public void onError(@NonNull Throwable e) {

        UiError uiError = new UiError();
        if (venuesLiveData.getValue() == null || venuesLiveData.getValue().size() < 0) {
            uiError.setErrorsDisplayTypes(Defs.IN_SCREEN);
        } else {
            uiError.setErrorsDisplayTypes(Defs.SNACK_BAR);
        }
        uiError.setMessage(TextUtils.getString(R.string.something_went_wrong));
        errorObservable.setValue(uiError);
        progressLiveData.setValue(false);

    }

    @Override
    public void onComplete() {
        if (venueItemViews == null || venueItemViews.size() < 0) {
            UiError uiError = new UiError();
            uiError.setErrorsDisplayTypes(Defs.IN_SCREEN);
            uiError.setMessage(TextUtils.getString(R.string.no_data_found));
            errorObservable.setValue(uiError);
        } else {
            venuesLiveData.setValue(venueItemViews);
        }
        progressLiveData.setValue(false);
    }

    @Override
    public void onChanged(@Nullable Location location) {
        venueItemViews = new ArrayList<>();

        squareBusiness.getVenues(String.valueOf(location.getLatitude()),
                String.valueOf(location.getLongitude())).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(VenuesViewModel.this);

    }

    public MutableLiveData<Boolean> getProgressLiveData() {
        return progressLiveData;
    }
}
