package com.iot.smarthome;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.ToggleButton;
import com.iot.smarthome.mobileServices.MessageManager;
import static com.iot.smarthome.Constants.ACTION_TEXT_CHANGED;
import static com.iot.smarthome.Constants.OFF;
import static com.iot.smarthome.Constants.ON;

public class MainActivity extends AppCompatActivity implements HTTPRequests.OnStatusChanged {

    ToggleButton toggle;
    private Button button;
    private MessageManager messageManager;
    private LightStatus lightStatus = LightStatus.getInstance();
    private HTTPRequests request;
    private ProgressBar progressBar;
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String status = intent.getStringExtra(Constants.BROADCAST_STATUS);
            Log.i("STATUS", status);
            if (Integer.parseInt(status) == Constants.ZERO) {
                toggle.setChecked(false);
            } else {
                toggle.setChecked(true);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);
        toggle = (ToggleButton) findViewById(R.id.toggleButton);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setEnabled(true);
        toggle.setEnabled(false);
        messageManager = new MessageManager(getApplication());
        request = new HTTPRequests(this);
        request.getStatus();

        if (isConnectionAvailable()) {

        } else {
            showNoConnectionDialog();
        }

        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    request.sendStatus(ON);
                    lightStatus.setLightStatus(ON);
                } else {
                    request.sendStatus(OFF);
                    lightStatus.setLightStatus(OFF);
                }

                messageManager.sendStatus();
            }
        });

        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, new IntentFilter(ACTION_TEXT_CHANGED));
    }


    private boolean isConnectionAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        return info != null && info.isConnected();
    }


    private void showNoConnectionDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.network_unavailable_title)
                .setMessage(R.string.network_unavailable)
                .setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setResult(RESULT_OK);
                    }
                }).show();
    }

    @Override
    public void statusChanged(int status) {
        toggle.setEnabled(true);
        lightStatus.setLightStatus(status);
        messageManager.sendStatus();
        if (status == 1) {
            toggle.setChecked(true);
        }
        if (status == 0) {
            toggle.setChecked(false);
        }
    }
}
