package com.moringaschool.closetapp.interfaces;


import com.moringaschool.closetapp.models.Example;
import com.moringaschool.closetapp.models.Response;
import com.moringaschool.closetapp.models.female.FemaleShoe;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ReveryApi {
    @GET("get_filtered_garments")
    Call<Response> getAllGarments(
            @Header("one_time_code") String one_time_code,
            @Header("timestamp") String timestamp
    );
    @GET("get_filtered_garments")

    Call<Response> getAllGarmentsGender(
            @Query("gender") String gender,
            @Header("one_time_code") String one_time_code,
            @Header("timestamp") String timestamp
    );

    @GET("get_filtered_garments")
    Call<Response> getTops(
            @Query("category") String category,
            @Header("one_time_code") String one_time_code,
            @Header("timestamp") String timestamp
    );
    @GET("get_filtered_garments")
    Call<Response> getBottoms(
            @Query("category") String category,
            @Header("one_time_code") String one_time_code,
            @Header("timestamp") String timestamp
    );
    @GET("get_selected_shoes")
    Call<Example> getShoes(
            @Query("gender") String gender,
            @Header("one_time_code") String one_time_code,
            @Header("timestamp") String timestamp
    );
    @GET("get_selected_shoes")
    Call<FemaleShoe> getFemaleShoes(
            @Query("gender") String gender,
            @Header("one_time_code") String one_time_code,
            @Header("timestamp") String timestamp
    );



}
