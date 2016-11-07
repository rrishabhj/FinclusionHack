package com.rishabh.github.finclusionhack.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by cypac on 26/10/16.
 */

public class LocationsList {
    @SerializedName("ListOfObjects")
    public List<LocationInfo> LocationInfos;

    public String Status;
    public String Message;
    public Integer TotalRecord;
}
