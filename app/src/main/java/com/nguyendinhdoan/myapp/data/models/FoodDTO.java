package com.nguyendinhdoan.myapp.data.models;

import java.util.List;

import lombok.Data;

@Data
public class FoodDTO {
    private String id;
    private String name;
    private String image;
    private String description;
    private long price;
    private List<AddonDTO> addonList;
}
