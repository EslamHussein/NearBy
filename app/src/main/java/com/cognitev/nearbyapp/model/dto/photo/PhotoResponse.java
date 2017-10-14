package com.cognitev.nearbyapp.model.dto.photo;

import com.cognitev.nearbyapp.model.dto.Meta;

/**
 * Created by Eslam Hussein on 10/12/17.
 */

public class PhotoResponse {

    private Meta meta;
    private Response response;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }
}
