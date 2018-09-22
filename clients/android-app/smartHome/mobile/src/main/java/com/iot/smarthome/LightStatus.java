package com.iot.smarthome;

/**
 * Created by Marco on 17/12/2016.
 */

public class LightStatus {
    private static LightStatus mLightStatus = new LightStatus();
    int lightStatus = 0;

    private LightStatus() {}

    public static LightStatus getInstance() {
        return mLightStatus;
    }

    public void setLightStatus(int lightStatus) {
        this.lightStatus = lightStatus;
    }

    public void setLightStatus(String lightStatus) {
        this.lightStatus = Integer.valueOf(lightStatus);
    }

    public String getLightStatusString() {
        return String.valueOf(lightStatus);
    }
}
