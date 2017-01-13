package com.iot.smarthome.mobileServices;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;
import com.iot.smarthome.Constants;
import com.iot.smarthome.LightStatus;

/**
 * Created by Marco on 16/12/2016.
 */

public class DataLayerListenerService extends WearableListenerService {


    private MessageManager messageManager;

    private LightStatus lightStatus = LightStatus.getInstance();

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        super.onMessageReceived(messageEvent);
        if (messageEvent.getPath().equalsIgnoreCase(Constants.REQUEST_MESSAGE)) {

            messageManager = new MessageManager(this);
            messageManager.sendStatus();
        }

        if (messageEvent.getPath().equalsIgnoreCase(Constants.CHANGE_LIGHT_STATUS)) {

            Log.i("DATA", "OK DATA RECEIVED FROM WEARABLE");
            String status = new String(messageEvent.getData());
            Log.i("STATUS", status);
            lightStatus.setLightStatus(status);
            Intent intent = new Intent();
            intent.setAction(Constants.ACTION_TEXT_CHANGED);
            intent.putExtra(Constants.BROADCAST_STATUS, status);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

        }
    }

}
