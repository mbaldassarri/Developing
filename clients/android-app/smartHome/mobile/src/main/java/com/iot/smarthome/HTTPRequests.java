package com.iot.smarthome;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

/**
 * Created by Marco on 18/12/2016.
 */

public class HTTPRequests {

    private Context context;
    private String jsonStatus;
    private Integer status;
    private OnStatusChanged listener;

    public HTTPRequests(Context activity) {
        this.context = activity;
        jsonStatus = new String("status");
        status = new Integer(0);

        if (activity instanceof OnStatusChanged)
            setListener((OnStatusChanged) activity);
    }

    public void setListener(OnStatusChanged listener) {
        this.listener = listener;
    }

    public Integer getStatus() {
        Ion.with(context)
                .load("http://insert-ip-server-here:port/method")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (result != null) {
                            listener.statusChanged(result.get(jsonStatus).getAsInt());
                        }
                    }
                });
        return status;
    }

    public void sendStatus(String status) {

        Ion.with(context)
                .load("http://insert-ip-server-here:port/method")
                .setBodyParameter("status", status)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        if (result != null) {
                            Log.i("RESULT", result + e);
                        } else {
                            Log.i("RESULT", e.toString());
                        }
                    }
                });
    }

    public interface OnStatusChanged {
        void statusChanged(int status);
    }
}
