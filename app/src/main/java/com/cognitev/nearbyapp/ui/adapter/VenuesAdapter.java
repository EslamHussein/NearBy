package com.cognitev.nearbyapp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cognitev.nearbyapp.R;
import com.cognitev.nearbyapp.ui.dto.VenueItemView;

import java.util.List;

/**
 * Created by Eslam Hussein on 10/12/17.
 */

public class VenuesAdapter extends RecyclerView.Adapter<VenuesAdapter.VenuesViewHolder> {

    private List<VenueItemView> mData;
    private Context context;

    public VenuesAdapter(List<VenueItemView> mData, Context context) {
        this.mData = mData;
        this.context = context;
    }

    @Override
    public VenuesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.venue_item_view, parent, false);

        return new VenuesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(VenuesViewHolder holder, int position) {

        VenueItemView venue = mData.get(position);
        holder.venueNameTextView.setText(venue.getName());
        holder.venueAddressTextView.setText(venue.getAddress());
        Glide.with(context).load(venue.getPhoto()).into(holder.venueImageView);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class VenuesViewHolder extends RecyclerView.ViewHolder {
        public TextView venueNameTextView, venueAddressTextView;
        public ImageView venueImageView;

        public VenuesViewHolder(View itemView) {
            super(itemView);

            venueNameTextView = itemView.findViewById(R.id.text_view_venue_name);
            venueImageView = itemView.findViewById(R.id.image_view_venue);
            venueAddressTextView = itemView.findViewById(R.id.text_view_venue_address);
        }
    }
}
