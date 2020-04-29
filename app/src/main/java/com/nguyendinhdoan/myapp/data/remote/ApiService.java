package com.nguyendinhdoan.myapp.data.remote;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("getCustomToken")
    Observable<ResponseBody> getCustomToken(@Query("access_token") String accessToken);
}
