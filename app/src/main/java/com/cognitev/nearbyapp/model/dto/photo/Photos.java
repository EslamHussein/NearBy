package com.cognitev.nearbyapp.model.dto.photo;

import java.util.List;

/**
 * Created by Eslam Hussein on 10/12/17.
 */

public class Photos {
    private List<PhotoItem> items = null;

    public List<PhotoItem> getItems() {
        return items;
    }

    public void setItems(List<PhotoItem> items) {
        this.items = items;
    }
}
