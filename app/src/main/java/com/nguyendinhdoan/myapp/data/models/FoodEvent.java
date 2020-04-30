package com.nguyendinhdoan.myapp.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Data;

@Data
public class FoodEvent implements Parcelable {

    public FoodEvent(FoodDTO foodDTO) {
        this.foodDTO = foodDTO;
    }

    private FoodDTO foodDTO;

    protected FoodEvent(Parcel in) {
        foodDTO = in.readParcelable(FoodDTO.class.getClassLoader());
    }

    public static final Creator<FoodEvent> CREATOR = new Creator<FoodEvent>() {
        @Override
        public FoodEvent createFromParcel(Parcel in) {
            return new FoodEvent(in);
        }

        @Override
        public FoodEvent[] newArray(int size) {
            return new FoodEvent[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(foodDTO, flags);
    }
}
