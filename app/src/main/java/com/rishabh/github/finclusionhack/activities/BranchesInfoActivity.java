package com.rishabh.github.finclusionhack.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.rishabh.github.finclusionhack.R;
import com.rishabh.github.finclusionhack.adaptor.LocationsListAdapter;
import com.rishabh.github.finclusionhack.models.LocationInfo;
import com.rishabh.github.finclusionhack.models.LocationsList;
import com.rishabh.github.finclusionhack.utils.ApiUtils;
import com.rishabh.github.finclusionhack.utils.Constants;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cypac on 26/10/16.
 */

public class BranchesInfoActivity extends BaseActivity implements LocationsListAdapter.LocationsListListener {
    private RecyclerView mRecyclerView;
    private LocationsListAdapter mLocationsListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations_list);

        getSupportActionBar().setTitle("Branch Locations");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        initializeRecyclerView();

        fetchBranchesList();
    }

    private void initializeRecyclerView() {
        //adapter
        mLocationsListAdapter = new LocationsListAdapter(this);

        //recycler view
        mRecyclerView = (RecyclerView) findViewById(R.id.locations_list_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mLocationsListAdapter);
    }

    @SuppressWarnings("deprecation")
    private void fetchBranchesList() {
        showProgressDialog();
        (new AsyncTask<Void, Void, LocationsList>() {
            @Override
            protected LocationsList doInBackground(Void... params) {
                try {
                    List<NameValuePair> queryParams = new ArrayList<>();
                    queryParams.add(new BasicNameValuePair("type", "BRANCH"));
                    queryParams.add(new BasicNameValuePair("countryCode", Constants.QUICKSTART_COUNTRY_CODE));
                    queryParams.add(new BasicNameValuePair("longitude", Constants.GEO_LONGITUDE));
                    queryParams.add(new BasicNameValuePair("latitude", Constants.GEO_LATITUDE));
                    queryParams.add(new BasicNameValuePair("fromDistanceToMe", "0"));
                    queryParams.add(new BasicNameValuePair("toDistanceToMe", "0"));
                    queryParams.add(new BasicNameValuePair("pageSize", "1000"));
                    queryParams.add(new BasicNameValuePair("pageNum", "1"));

                    HttpGet request = new HttpGet(Constants.API_BASE_URL + "location/branchs?" + URLEncodedUtils.format(queryParams, "UTF-8"));
                    ApiUtils.appendCommonRequestParams(request);

                    HttpResponse response = (new DefaultHttpClient()).execute(request);
                    String jsonResponse = EntityUtils.toString(response.getEntity());
                    Log.d("API", jsonResponse);
                    LocationsList locationsList = ApiUtils.getSharedGson().fromJson(jsonResponse, LocationsList.class);

                    if (!locationsList.Status.equals(Constants.API_SUCCESS_CODE))
                        throw new Error(locationsList.Message);

                    return locationsList;
                } catch (Exception e) {
                    Log.e(BranchesInfoActivity.this.getClass().toString(), e.getLocalizedMessage());
                }
                return null;
            }

            @Override
            protected void onPostExecute(LocationsList locationsList) {
                if(locationsList!=null) {
                    hideProgressDialog();
                    populateBranches(locationsList);
                }
            }
        }).execute();
    }

    private void populateBranches(LocationsList locationsList) {
        mLocationsListAdapter.showLocations(locationsList.LocationInfos);
    }

    /**
     * LocationsListAdapter.LocationsListListener
     */
    @Override
    public void onLocationSelected(LocationInfo locationInfo) {
        Log.e(BranchesInfoActivity.this.getClass().toString(), locationInfo.LocationName);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("geo:" + Constants.MAP_LATITUDE + "," + Constants.MAP_LONGITUDE + "?z= "+ Constants.MAP_ZOOM + "&q=" + Uri.encode(locationInfo.LocationAddress.AddressDetail))); //lat lng or address query
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
