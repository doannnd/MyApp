package com.nguyendinhdoan.myapp.data.models;

import lombok.Data;

@Data
public class CategoryEvent {
    public CategoryEvent(CategoryDTO categoryDTO) {
        this.categoryDTO = categoryDTO;
    }

    private CategoryDTO categoryDTO;
}
