package com.nguyendinhdoan.myapp.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Data;

@Data
public class CategoryEvent implements Parcelable {
    public CategoryEvent(CategoryDTO categoryDTO) {
        this.categoryDTO = categoryDTO;
    }

    private CategoryDTO categoryDTO;

    protected CategoryEvent(Parcel in) {
        categoryDTO = in.readParcelable(CategoryDTO.class.getClassLoader());
    }

    public static final Creator<CategoryEvent> CREATOR = new Creator<CategoryEvent>() {
        @Override
        public CategoryEvent createFromParcel(Parcel in) {
            return new CategoryEvent(in);
        }

        @Override
        public CategoryEvent[] newArray(int size) {
            return new CategoryEvent[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(categoryDTO, flags);
    }
}
