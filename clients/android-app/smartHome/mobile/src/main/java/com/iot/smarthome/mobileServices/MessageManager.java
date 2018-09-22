package com.iot.smarthome.mobileServices;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;
import com.iot.smarthome.Constants;
import com.iot.smarthome.LightStatus;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Marco on 17/12/2016.
 */

public class MessageManager implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;
    private String nodeId;
    private boolean nodeConnected = false;
    private Context context;
    private LightStatus lightStatus = LightStatus.getInstance();

    public MessageManager(Context context) {
        this.context = context;
        setGoogleAPI();
    }

    private void setGoogleAPI() {
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();
    }

    public void sendStatus() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                //  nodeId = nodes.get(0).getId();
                if (mGoogleApiClient != null && !(mGoogleApiClient.isConnected() || mGoogleApiClient.isConnecting())) {
                    mGoogleApiClient.blockingConnect(20, TimeUnit.MILLISECONDS);
                }

                NodeApi.GetConnectedNodesResult result = Wearable.NodeApi.getConnectedNodes(mGoogleApiClient).await();
                List<Node> nodes = result.getNodes();
                if (nodes.size() > 0) {
                    for (Node node : nodes) {
                        if (node.isNearby()) {
                            nodeId = node.getId();
                            String status = lightStatus.getLightStatusString();
                            Log.i("WEAR_STATUS", status);

                            Wearable.MessageApi.sendMessage(mGoogleApiClient, nodeId, Constants.LIGHT_STATUS, status.getBytes()).await();
                            if (!result.getStatus().isSuccess()) {
                                Log.e("test", "error");
                            } else {
                                Log.i("test", "success!! sent to: " + node.getDisplayName());
                            }
                        }
                    }

                }
                mGoogleApiClient.disconnect();
            }
        }).start();
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        nodeConnected = true;


    }

    @Override
    public void onConnectionSuspended(int i) {
        nodeConnected = false;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        nodeConnected = false;
    }
}
