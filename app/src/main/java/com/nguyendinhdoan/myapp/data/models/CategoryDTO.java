package com.nguyendinhdoan.myapp.data.models;

import android.util.Size;

import java.util.List;

import lombok.Data;

@Data
public class CategoryDTO {
    private String menuId;
    private String name;
    private String image;
    private List<FoodDTO> foodList;
    private List<SizeDTO> sizeList;
}
