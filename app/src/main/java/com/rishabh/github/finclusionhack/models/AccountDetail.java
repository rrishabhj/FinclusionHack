package com.rishabh.github.finclusionhack.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by cypac on 27/10/16.
 */

public class AccountDetail {
    @SerializedName("DataObject")
    public AccountInfo AccountInfo;

    public String Status;
    public String Message;
}
