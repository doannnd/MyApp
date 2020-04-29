package com.nguyendinhdoan.myapp.utils;

import androidx.annotation.StringDef;
import androidx.annotation.StringRes;

public class Constance {

    @StringDef({TABLE.USER})
    public @interface TABLE {
        String USER = "Users";
        String POPULAR_CATEGORIES = "MostPopular";
        String BEST_DEAL = "BestDeals";
        String CATEGORY_MENU = "Category";
    }

}
