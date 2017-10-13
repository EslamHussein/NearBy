package com.cognitev.nearbyapp.model.dto.venue;

/**
 * Created by Eslam Hussein on 10/12/17.
 */

public class Venue {

    private String id;
    private String name;
    private VenueLocation location;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public VenueLocation getLocation() {
        return location;
    }

    public void setLocation(VenueLocation location) {
        this.location = location;
    }
}
