package com.cognitev.nearbyapp.model.dto.venue;

import java.util.List;

/**
 * Created by Eslam Hussein on 10/12/17.
 */

public class Groups {

    private int totalResults;

    private List<Group> groups;

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }
}
