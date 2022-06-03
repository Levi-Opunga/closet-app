package com.moringaschool.closetapp.interfaces;


import com.moringaschool.closetapp.models.Response;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ReveryApi {
    @GET("get_filtered_garments ")
        Call<Response> getAllGarments(@Query("gender") String gender);

}
