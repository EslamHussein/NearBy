package com.cognitev.nearbyapp.model.cloud;

import com.cognitev.base.dto.BaseResponse;
import com.cognitev.base.repo.cloud.BaseCloudRepo;
import com.cognitev.base.repo.cloud.CloudConfig;
import com.cognitev.nearbyapp.business.FourSquareRepo;
import com.cognitev.utils.DateUtils;

/**
 * Created by Eslam Hussein on 10/12/17.
 */

public class FourSquareCloudRepoImpl extends BaseCloudRepo implements FourSquareRepo {


    @Override
    public BaseResponse getVenues(String lat, String lng) throws Exception {
        try {
            String version = DateUtils.getCurrentDate();
            StringBuilder latLng = new StringBuilder().append(lat).append(",").append(lng);
            return execute(create(FourSquareApiServices.class).getVenues(latLng.toString(),
                    CloudConfig.CLIENT_ID, CloudConfig.CLIENT_SECRET, version));

        } catch (Throwable throwable) {
            throw new Exception(throwable);
        }
    }

    @Override
    public BaseResponse getPhoto(String venueId) throws Exception {
        try {
            String version = DateUtils.getCurrentDate();
            return execute(create(FourSquareApiServices.class).getPhoto(venueId,
                    CloudConfig.CLIENT_ID, CloudConfig.CLIENT_SECRET, version));

        } catch (Throwable throwable) {
            throw new Exception(throwable);
        }
    }
}
