package com.nguyendinhdoan.myapp.data.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Size;

import java.util.List;

import lombok.Data;

@Data
public class CategoryDTO implements Parcelable {
    private String menuId;
    private String name;
    private String image;
    private List<FoodDTO> foods;

    public CategoryDTO() {
    }

    protected CategoryDTO(Parcel in) {
        menuId = in.readString();
        name = in.readString();
        image = in.readString();
        foods = in.createTypedArrayList(FoodDTO.CREATOR);
    }

    public static final Creator<CategoryDTO> CREATOR = new Creator<CategoryDTO>() {
        @Override
        public CategoryDTO createFromParcel(Parcel in) {
            return new CategoryDTO(in);
        }

        @Override
        public CategoryDTO[] newArray(int size) {
            return new CategoryDTO[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(menuId);
        dest.writeString(name);
        dest.writeString(image);
        dest.writeTypedList(foods);
    }
}
