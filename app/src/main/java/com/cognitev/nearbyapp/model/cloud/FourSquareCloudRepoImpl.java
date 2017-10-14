package com.cognitev.nearbyapp.model.cloud;

import com.cognitev.base.repo.cloud.BaseCloudRepo;
import com.cognitev.base.repo.cloud.CloudConfig;
import com.cognitev.nearbyapp.business.FourSquareRepo;
import com.cognitev.nearbyapp.model.dto.photo.PhotoResponse;
import com.cognitev.nearbyapp.model.dto.venue.VenueResponse;
import com.cognitev.utils.DateUtils;

import io.reactivex.Observable;

/**
 * Created by Eslam Hussein on 10/12/17.
 */

public class FourSquareCloudRepoImpl extends BaseCloudRepo implements FourSquareRepo {


    @Override
    public Observable<VenueResponse> getVenues(String lat, String lng) {
        String version = DateUtils.getCurrentDate();
        StringBuilder latLng = new StringBuilder().append(lat).append(",").append(lng);

        return create(FourSquareApiServices.class).getVenues(latLng.toString(),
                CloudConfig.CLIENT_ID, CloudConfig.CLIENT_SECRET, version);


    }

    @Override
    public Observable<PhotoResponse> getPhoto(String venueId) throws Exception {
        String version = DateUtils.getCurrentDate();
        return create(FourSquareApiServices.class).getPhoto(venueId,
                CloudConfig.CLIENT_ID, CloudConfig.CLIENT_SECRET, version);

    }
}
