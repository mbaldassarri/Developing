package com.iot.smarthome.service.iot;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

import static com.iot.smarthome.Constants.API_LIGHT_PATH;

public interface ApiService {

        @FormUrlEncoded
        @POST(API_LIGHT_PATH)
        Call<Status>changeStatus(@Field("status") String status);

        @GET(API_LIGHT_PATH)
        Call<Status>getStatus();
}
