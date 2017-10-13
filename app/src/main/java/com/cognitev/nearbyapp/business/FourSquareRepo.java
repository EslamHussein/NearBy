package com.cognitev.nearbyapp.business;

import com.cognitev.nearbyapp.model.dto.photo.PhotoResponse;
import com.cognitev.nearbyapp.model.dto.venue.VenueResponse;

import io.reactivex.Observable;


/**
 * Created by Eslam Hussein on 10/12/17.
 */

public interface FourSquareRepo {


    Observable<VenueResponse> getVenues(String lat, String lng) throws Throwable;

    Observable<PhotoResponse> getPhoto(String venueId) throws Throwable;

}
