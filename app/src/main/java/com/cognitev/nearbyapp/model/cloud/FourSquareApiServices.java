package com.cognitev.nearbyapp.model.cloud;


import com.cognitev.nearbyapp.model.dto.photo.PhotoResponse;
import com.cognitev.nearbyapp.model.dto.venue.VenueResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Eslam Hussein on 10/11/17.
 */

public interface FourSquareApiServices {

    @GET("explore")
    Observable<VenueResponse> getVenues(@Query("ll") String latLng, @Query("client_id") String clientId,
                                        @Query("client_secret") String clientSecret, @Query("v") String version);


    @GET("{venue_id}/photos")
    Observable<PhotoResponse> getPhoto(@Path("venue_id") String venueId, @Query("client_id") String clientId,
                                       @Query("client_secret") String clientSecret, @Query("v") String version);

}
