package com.iot.smarthome.service.iot;

import android.content.Context;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Marco on 18/12/2016.
 */

public class HTTPRequests {

    private Context context;
    private String jsonStatus;
    private Integer status;
    private OnStatusChanged listener;
    private Retrofit retrofit;
    private ApiService apiService;

    public HTTPRequests(Context activity) {
        this.context = activity;
        jsonStatus = new String("status");
        status = new Integer(0);

        if (activity instanceof OnStatusChanged) {
            setListener((OnStatusChanged) activity);
        }
        this.retrofit = new Retrofit.Builder()
                .baseUrl("https://casa-dashboard.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.apiService = retrofit.create(ApiService.class);
    }

    private void setListener(OnStatusChanged listener) {
        this.listener = listener;
    }

    public Integer getStatus() {
        Call<Status> getStatusCall = this.apiService.getStatus();
        getStatusCall.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                String status = response.body().getStatus();
                if(status != null) listener.statusChanged(status);
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {
                Log.i("GET STATUS", " exeption\n" + t);
            }
        });
        return status;
    }

    public void sendStatus(String status) {

        Call<Status> changeStatusCall = this.apiService.changeStatus(status);
        changeStatusCall.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                Log.i("STATUS", call.request().body().toString());
                listener.statusChanged(call.request().body().toString());
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {
                Log.i("POST STATUS", call.request().body().toString() + " exeption\n" + t);
            }
        });

    }

    public interface OnStatusChanged {
        void statusChanged(String status);
    }
}
