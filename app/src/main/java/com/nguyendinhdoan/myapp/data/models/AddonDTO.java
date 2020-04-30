package com.nguyendinhdoan.myapp.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Data;

@Data
public class AddonDTO implements Parcelable {
    private String name;
    private long price;

    public AddonDTO() {
    }

    protected AddonDTO(Parcel in) {
        name = in.readString();
        price = in.readLong();
    }

    public static final Creator<AddonDTO> CREATOR = new Creator<AddonDTO>() {
        @Override
        public AddonDTO createFromParcel(Parcel in) {
            return new AddonDTO(in);
        }

        @Override
        public AddonDTO[] newArray(int size) {
            return new AddonDTO[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeLong(price);
    }
}
