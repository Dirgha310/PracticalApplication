package com.arinspect.practicalapplication.network;

import com.arinspect.practicalapplication.model.RowsList;

import retrofit2.Call;
import retrofit2.http.GET;

//request function to call data from webservice
public interface RequestAPI {

    @GET("s/2iodh4vg0eortkl/facts.json")
    Call<RowsList> getData();
}
