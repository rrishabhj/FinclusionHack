package com.rishabh.github.finclusionhack.adaptor;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rishabh.github.finclusionhack.R;
import com.rishabh.github.finclusionhack.models.LocationInfo;

import java.util.List;

/**
 * Created by cypac on 26/10/16.
 */

public class LocationsListAdapter extends RecyclerView.Adapter<LocationsListAdapter.ViewHolder> {
    private List<LocationInfo> mLocations;
    private LocationsListListener mLocationsListListener;

    public interface LocationsListListener {
        void onLocationSelected(LocationInfo locationInfo);
    }


    public LocationsListAdapter(LocationsListListener listener) {
        mLocationsListListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_location_info, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        LocationInfo locationInfo = mLocations.get(position);

        holder.mNameTextView.setText(locationInfo.LocationOwnerName);
        holder.mAddressTextView.setText(locationInfo.LocationAddress.AddressDetail);
        holder.mLocationInfo = locationInfo;
    }

    @Override
    public int getItemCount() {
        return mLocations == null ? 0 : mLocations.size();
    }

    public final class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mNameTextView;
        TextView mAddressTextView;

        private LocationInfo mLocationInfo;

        public ViewHolder(View itemView) {
            super(itemView);

            mNameTextView = (TextView) itemView.findViewById(R.id.name_textview);
            mAddressTextView = (TextView) itemView.findViewById(R.id.address_textview);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mLocationsListListener != null)
                mLocationsListListener.onLocationSelected(mLocationInfo);
        }
    }

    public void showLocations(List<LocationInfo> locationInfos) {
        if (locationInfos != null) {
            mLocations = locationInfos;
            this.notifyDataSetChanged();
        }
    }

}
