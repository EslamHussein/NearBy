package com.cognitev.nearbyapp.model.dto.venue;

import com.cognitev.nearbyapp.model.dto.Meta;

/**
 * Created by Eslam Hussein on 10/12/17.
 */

public class VenueResponse {

    private Meta meta;
    private Groups response;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Groups getResponse() {
        return response;
    }

    public void setResponse(Groups response) {
        this.response = response;
    }
}
