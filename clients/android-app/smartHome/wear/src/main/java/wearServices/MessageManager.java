package wearServices;

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

import static com.iot.smarthome.Constants.ONE;
import static com.iot.smarthome.Constants.ZERO;

/**
 * Created by Marco on 17/12/2016.
 */

public class MessageManager implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;
    private String nodeId;
    private Context context;
    private LightStatus lightStatus = LightStatus.getInstance();
    private int currentStatus = 0;

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


    public void sendMessageRequestStatus() {


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
                            Log.i("NODES", node.getDisplayName() + " " + node.getId());
                            nodeId = node.getId();
                            //requestDataToPhone();
                            Wearable.MessageApi.sendMessage(mGoogleApiClient, nodeId, Constants.REQUEST_MESSAGE, null).await();
                            if (!result.getStatus().isSuccess()) {
                                Log.e("test", "error");
                            } else {
                                Log.i("test", "success! Message sent to: " + node.getDisplayName());
                            }
                        }
                    }

                }
                mGoogleApiClient.disconnect();
            }
        }).start();
    }


    public void changeStatus(final int currentStatus) {

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
                            Log.i("NODES", node.getDisplayName() + " " + node.getId());
                            nodeId = node.getId();
                            //requestDataToPhone();
                            lightStatus.setLightStatus(getOppositeStatus(currentStatus));
                            Log.i("STATUS_CHANGED", lightStatus.getLightStatusString() + "   " + Constants.CHANGE_LIGHT_STATUS);
                            Wearable.MessageApi.sendMessage(mGoogleApiClient, nodeId, Constants.CHANGE_LIGHT_STATUS, lightStatus.getLightStatusString().getBytes()).await();
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

    private String getOppositeStatus(int currentStatus) {
        if (currentStatus == ONE) {
            return String.valueOf(ZERO);
        } else {
            return String.valueOf(ONE);
        }

    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        sendMessageRequestStatus();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

}
