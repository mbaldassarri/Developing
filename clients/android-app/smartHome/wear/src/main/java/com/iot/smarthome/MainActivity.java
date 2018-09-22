package com.iot.smarthome;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.BoxInsetLayout;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import wearServices.MessageManager;

import static com.iot.smarthome.Constants.ACTION_TEXT_CHANGED;
import static com.iot.smarthome.Constants.OFF;
import static com.iot.smarthome.Constants.ON;

public class MainActivity extends WearableActivity {

    private static final SimpleDateFormat AMBIENT_DATE_FORMAT =
            new SimpleDateFormat("HH:mm", Locale.US);


    private BoxInsetLayout mContainerView;
    private ToggleButton circularButton;
    private TextView mClockView;
    private MessageManager messageManager;
    private LightStatus lightStatus = LightStatus.getInstance();
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            circularButton.setEnabled(true);
            String status = intent.getStringExtra(Constants.BROADCAST_STATUS);
            Log.i("STATUS", status);
            if (Integer.parseInt(status) == Constants.ZERO) {
                circularButton.setText(OFF);
                circularButton.setBackgroundResource(R.drawable.off_shape);
            } else {
                circularButton.setText(ON);
                circularButton.setBackgroundResource(R.drawable.on_shape);
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //asks the Phone the status of the light when this object is instantiated
        messageManager = new MessageManager(this);

        setAmbientEnabled();

        mContainerView = (BoxInsetLayout) findViewById(R.id.container);
        mContainerView.setBackgroundColor(getResources().getColor(R.color.black));
        circularButton = (ToggleButton) findViewById(R.id.toggleButton);
        mClockView = (TextView) findViewById(R.id.clock);
        circularButton.setEnabled(true);
        circularButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    lightStatus.setLightStatus(Constants.ONE);
                } else {
                    lightStatus.setLightStatus(Constants.ZERO);
                }
                messageManager.changeStatus(lightStatus.getLightStatus());
            }
        });

        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, new IntentFilter(ACTION_TEXT_CHANGED));
    }

    @Override
    public void onEnterAmbient(Bundle ambientDetails) {
        super.onEnterAmbient(ambientDetails);
        updateDisplay();
    }

    @Override
    public void onUpdateAmbient() {
        super.onUpdateAmbient();
        updateDisplay();
    }

    @Override
    public void onExitAmbient() {
        updateDisplay();
        super.onExitAmbient();
    }

    private void updateDisplay() {
        if (isAmbient()) {

            //circularButton.setVisibility(View.GONE);
            mClockView.setVisibility(View.VISIBLE);
            mContainerView.setBackgroundColor(getResources().getColor(R.color.black));
            mClockView.setText(AMBIENT_DATE_FORMAT.format(new Date()));
        } else {
            mContainerView.setBackground(null);
            //circularButton.setVisibility(View.VISIBLE);
            mClockView.setVisibility(View.GONE);
        }
    }

}
