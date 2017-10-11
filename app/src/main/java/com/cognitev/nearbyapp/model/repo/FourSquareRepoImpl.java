package com.cognitev.nearbyapp.model.repo;

import com.cognitev.base.dto.BaseResponse;
import com.cognitev.nearbyapp.business.FourSquareRepo;
import com.cognitev.nearbyapp.model.cloud.FourSquareCloudRepoImpl;

/**
 * Created by Eslam Hussein on 10/12/17.
 */

public class FourSquareRepoImpl implements FourSquareRepo {


    private FourSquareRepo repo = new FourSquareCloudRepoImpl();


    @Override
    public BaseResponse getVenues(String lat, String lng) throws Exception {
        return repo.getVenues(lat, lng);
    }

    @Override
    public BaseResponse getPhoto(String venueId) throws Exception {
        return repo.getPhoto(venueId);
    }
}
