package com.cognitev.nearbyapp.business;

import com.cognitev.base.dto.BaseResponse;
import com.cognitev.nearbyapp.model.repo.FourSquareRepoImpl;

/**
 * Created by Eslam Hussein on 10/12/17.
 */

public class FourSquareBusiness {


    private FourSquareRepo fourSquareCloudRepo = new FourSquareRepoImpl();

    public BaseResponse getVenues(String lat, String lng) throws Exception {
        try {
            return fourSquareCloudRepo.getVenues(lat, lng);

        } catch (Exception e) {
            throw e;
        }
    }

    public BaseResponse getPhoto(String venueId) throws Exception {
        try {
            return fourSquareCloudRepo.getPhoto(venueId);

        } catch (Exception e) {
            throw e;
        }
    }
}
