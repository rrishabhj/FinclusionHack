package com.rishabh.github.finclusionhack.models;

/**
 * Created by cypac on 26/10/16.
 */
public class LocationInfo {
    public String LocationName;
    public String LocationCode;
    public String LocationType;
    public String LocationOwner;
    public String LocationOwnerName;
    public String Description;
    public String ImageUrl;
    public Double Distance;
    public String Status;
    public LocationAddress LocationAddress;

    public static class LocationAddress {
        public String CountryName;
        public String PostalCode;
        public String AddressDetail;
        public Double Longtitude;
        public Double Latitude;
    }
}
