package com.example.pragya.ilovezappos;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by pragya on 2/4/2017.
 */

public interface ZapposAPIClass {

    @GET("Search?key=b743e26728e16b81da139182bb2094357c31d331")
    Call<ZapposItemList> loadItems(@Query("term") String term);
}
