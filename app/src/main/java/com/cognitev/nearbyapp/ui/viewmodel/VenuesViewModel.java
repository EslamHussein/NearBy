package com.cognitev.nearbyapp.ui.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.cognitev.nearbyapp.business.FourSquareBusiness;
import com.cognitev.nearbyapp.ui.dto.VenueItemView;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Eslam Hussein on 10/12/17.
 */

public class VenuesViewModel extends ViewModel {
    public static final String TAG = VenuesViewModel.class.getName();


    private FourSquareBusiness squareBusiness;
    private MutableLiveData<List<VenueItemView>> repoListObservable;
//    private MutableLiveData<Error> errorObservable = new MutableLiveData<>();


    public VenuesViewModel() {

        //30.0444200,31.2357120
        squareBusiness = new FourSquareBusiness();
        repoListObservable = new MutableLiveData<>();
        try {
            squareBusiness.getVenues("30.0444200", "31.2357120").observeOn(AndroidSchedulers.mainThread()).
                    subscribeOn(Schedulers.io()).subscribe(new Observer<List<VenueItemView>>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {

                }

                @Override
                public void onNext(@NonNull List<VenueItemView> venueItemViews) {
                    repoListObservable.setValue(venueItemViews);
                }

                @Override
                public void onError(@NonNull Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }


    }

    public LiveData<List<VenueItemView>> getRepoListObservable() {
        return repoListObservable;
    }
}
