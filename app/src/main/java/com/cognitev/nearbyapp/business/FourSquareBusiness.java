package com.cognitev.nearbyapp.business;

import android.util.Log;

import com.cognitev.nearbyapp.model.cloud.FourSquareCloudRepoImpl;
import com.cognitev.nearbyapp.model.dto.photo.PhotoResponse;
import com.cognitev.nearbyapp.model.dto.venue.Group;
import com.cognitev.nearbyapp.model.dto.venue.Item;
import com.cognitev.nearbyapp.model.dto.venue.Venue;
import com.cognitev.nearbyapp.model.dto.venue.VenueResponse;
import com.cognitev.nearbyapp.ui.dto.VenueItemView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;

public class FourSquareBusiness {
    public static final String TAG = FourSquareBusiness.class.getName();


    private FourSquareRepo fourSquareCloudRepo = new FourSquareCloudRepoImpl();

    public Observable<List<VenueItemView>> getVenues(final String lat, final String lng) throws Throwable {

        return fourSquareCloudRepo.getVenues(lat, lng).map(new Function<VenueResponse, List<Venue>>() {
            @Override
            public List<Venue> apply(@NonNull VenueResponse venueResponse) throws Exception {
                List<Venue> venues = new ArrayList<Venue>();
                for (Group group : venueResponse.getResponse().getGroups()) {
                    for (Item item : group.getItems()) {
                        venues.add(item.getVenue());
                    }
                }
                return venues;
            }
        }).flatMap(new Function<List<Venue>, Observable<List<VenueItemView>>>() {
            @Override
            public Observable<List<VenueItemView>> apply(@NonNull List<Venue> venues) throws Exception {


                List<Observable<PhotoResponse>> venueObservables = new ArrayList<>();
                for (Venue venue : venues) {
                    try {
                        venueObservables.add(getPhoto(venue));

                    } catch (Throwable e) {

                    }
                }

                Observable<List<Venue>> venueObservableList = Observable.fromArray(venues);
                Observable<List<PhotoResponse>> listObservable = getPhotoObservables(venueObservables);

                Observable<List<VenueItemView>> venueItemsViewObservables
                        = getVenuesItemViewObservable(venueObservableList, listObservable);

                return venueItemsViewObservables;

            }
        });


    }


    private Observable<List<PhotoResponse>> getPhotoObservables(List<Observable<PhotoResponse>> venueObservables) {

        return Observable.zip(venueObservables, new Function<Object[], List<PhotoResponse>>() {
            @Override
            public List<PhotoResponse> apply(@NonNull Object[] objects) throws Exception {

                List<PhotoResponse> itemDetails = new ArrayList<>();
                for (Object arg : objects) {
                    Log.d(TAG, "apply() returned: " + arg.getClass());

                    PhotoResponse photoResponse = (PhotoResponse) arg;
                    itemDetails.add(photoResponse);
                }
                return itemDetails;

            }
        });

    }


    private Observable<List<VenueItemView>> getVenuesItemViewObservable(Observable<List<Venue>> venueObservableList, Observable<List<PhotoResponse>> listObservable) {


        return Observable.zip(venueObservableList, listObservable,
                new BiFunction<List<Venue>, List<PhotoResponse>, List<VenueItemView>>() {
                    @Override
                    public List<VenueItemView> apply(@NonNull List<Venue> venues, @NonNull List<PhotoResponse> photoResponses) throws Exception {

                        ArrayList<VenueItemView> venueItemViews = new ArrayList<VenueItemView>();
                        for (int i = 0; i < venues.size(); i++) {

                            Venue venue = venues.get(i);
                            PhotoResponse photo = photoResponses.get(i);
                            VenueItemView venueItemView = new VenueItemView(venue.getId(),
                                    venue.getName(), venue.getLocation().getAddress(), "");

                            venueItemViews.add(venueItemView);
                        }
                        return venueItemViews;
                    }
                }
        );
    }

    private Observable<PhotoResponse> getPhoto(final Venue venue) throws Throwable {

        return fourSquareCloudRepo.getPhoto(venue.getId());

    }
}
