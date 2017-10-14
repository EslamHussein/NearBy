package com.cognitev.base.error;

import com.cognitev.nearbyapp.ui.dto.VenueItemView;

import java.util.List;

/**
 * Created by Eslam Hussein on 10/14/17.
 */

public class VenuesUiView {


    private int progressBarViability;
    private UiError uiError;
    private List<VenueItemView> venueItemViews;

    public int getProgressBarViability() {
        return progressBarViability;
    }

    public void setProgressBarViability(int progressBarViability) {
        this.progressBarViability = progressBarViability;
    }

    public UiError getUiError() {
        return uiError;
    }

    public void setUiError(UiError uiError) {
        this.uiError = uiError;
    }

    public List<VenueItemView> getVenueItemViews() {
        return venueItemViews;
    }

    public void setVenueItemViews(List<VenueItemView> venueItemViews) {
        this.venueItemViews = venueItemViews;
    }
}
