package com.cognitev.nearbyapp.business;

import com.cognitev.base.dto.BaseResponse;


/**
 * Created by Eslam Hussein on 10/12/17.
 */

public interface FourSquareRepo {


    BaseResponse getVenues(String lat, String lng) throws Exception;


    BaseResponse getPhoto(String venueId) throws Exception;

}
