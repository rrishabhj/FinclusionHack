package com.rishabh.github.finclusionhack.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by cypac on 26/10/16.
 */

public class AccountInfo implements Parcelable {
    public int Id;
    public String AadhaarNo;
    public String AadhaarName;
    public String FirstName;
    public String MiddleName;
    public String LastName;
    public String DOB;
    public String Email;
    public String MobileNo;
    public String Gender;
    public String Address;
    public String State;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.Id);
        dest.writeString(this.AadhaarNo);
        dest.writeString(this.AadhaarName);
        dest.writeString(this.FirstName);
        dest.writeString(this.MiddleName);
        dest.writeString(this.LastName);
        dest.writeString(this.DOB);
        dest.writeString(this.Email);
        dest.writeString(this.MobileNo);
        dest.writeString(this.Gender);
        dest.writeString(this.Address);
        dest.writeString(this.State);
    }

    public AccountInfo() {
    }

    protected AccountInfo(Parcel in) {
        this.Id = in.readInt();
        this.AadhaarNo = in.readString();
        this.AadhaarName = in.readString();
        this.FirstName = in.readString();
        this.MiddleName = in.readString();
        this.LastName = in.readString();
        this.DOB = in.readString();
        this.Email = in.readString();
        this.MobileNo = in.readString();
        this.Gender = in.readString();
        this.Address = in.readString();
        this.State = in.readString();
    }

    public static final Creator<AccountInfo> CREATOR = new Creator<AccountInfo>() {
        @Override
        public AccountInfo createFromParcel(Parcel source) {
            return new AccountInfo(source);
        }

        @Override
        public AccountInfo[] newArray(int size) {
            return new AccountInfo[size];
        }
    };
}
