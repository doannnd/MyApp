package com.nguyendinhdoan.myapp.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import lombok.Data;

@Data
public class FoodDTO implements Parcelable {
    private String id;
    private String name;
    private String image;
    private String description;
    private long price;
    private List<AddonDTO> addonList;
    private List<SizeDTO> sizeList;

    public FoodDTO() {
    }

    protected FoodDTO(Parcel in) {
        id = in.readString();
        name = in.readString();
        image = in.readString();
        description = in.readString();
        price = in.readLong();
    }

    public static final Creator<FoodDTO> CREATOR = new Creator<FoodDTO>() {
        @Override
        public FoodDTO createFromParcel(Parcel in) {
            return new FoodDTO(in);
        }

        @Override
        public FoodDTO[] newArray(int size) {
            return new FoodDTO[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(image);
        dest.writeString(description);
        dest.writeLong(price);
    }
}
