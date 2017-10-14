package com.cognitev.nearbyapp.business;

import com.cognitev.nearbyapp.model.cloud.FourSquareCloudRepoImpl;
import com.cognitev.nearbyapp.model.dto.photo.PhotoItem;
import com.cognitev.nearbyapp.model.dto.photo.PhotoResponse;
import com.cognitev.nearbyapp.model.dto.venue.Group;
import com.cognitev.nearbyapp.model.dto.venue.Item;
import com.cognitev.nearbyapp.model.dto.venue.Venue;
import com.cognitev.nearbyapp.model.dto.venue.VenueResponse;
import com.cognitev.nearbyapp.ui.dto.VenueItemView;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;

public class FourSquareBusiness {
    public static final String TAG = FourSquareBusiness.class.getName();


    private FourSquareRepo fourSquareCloudRepo = new FourSquareCloudRepoImpl();

    public Observable<VenueItemView> getVenues(final String lat, final String lng) {


        return fourSquareCloudRepo.getVenues(lat, lng).flatMap(new Function<VenueResponse, Observable<Group>>() {
            @Override
            public Observable<Group> apply(@NonNull VenueResponse venueResponse) throws Exception {

                return Observable.fromIterable(venueResponse.getResponse().getGroups());
            }
        }).flatMap(new Function<Group, Observable<Item>>() {
            @Override
            public Observable<Item> apply(@NonNull Group group) throws Exception {
                return Observable.fromIterable(group.getItems());
            }
        }).flatMap(new Function<Item, Observable<VenueItemView>>() {

            @Override
            public Observable<VenueItemView> apply(@NonNull Item item) throws Exception {
                Venue venue = item.getVenue();
                VenueItemView venueItemView = new VenueItemView(venue.getId(), venue.getName(), venue.getLocation().getAddress(), "");
                Observable<PhotoItem> photoItemObservable = getPhoto(venue);
                return Observable.zip(photoItemObservable, Observable.just(venueItemView), new BiFunction<PhotoItem, VenueItemView, VenueItemView>() {
                    @Override
                    public VenueItemView apply(@NonNull PhotoItem photoItem, @NonNull VenueItemView venueItemView) throws Exception {
                        String tempPhoto = photoItem.getPrefix() + "100x100" + photoItem.getSuffix();
                        venueItemView.setPhoto(tempPhoto);
                        return venueItemView;
                    }
                });
            }
        });

    }

    private Observable<PhotoItem> getPhoto(final Venue venue) throws Exception {

        return fourSquareCloudRepo.getPhoto(venue.getId()).flatMap(new Function<PhotoResponse, Observable<PhotoItem>>() {
            @Override
            public Observable<PhotoItem> apply(@NonNull PhotoResponse photoResponse) throws Exception {
                return Observable.fromIterable(photoResponse.getResponse().getPhotos().getItems().subList(0, 4)).take(1);
            }
        });
    }
}
