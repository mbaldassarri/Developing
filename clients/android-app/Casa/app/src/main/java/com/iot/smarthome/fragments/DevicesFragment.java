package com.iot.smarthome.fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.ToggleButton;

import com.iot.smarthome.R;
import com.iot.smarthome.service.iot.HTTPRequests;

import static android.app.Activity.RESULT_OK;
import static com.iot.smarthome.Constants.OFF;
import static com.iot.smarthome.Constants.ON;
import static com.iot.smarthome.Constants.ONE;
import static com.iot.smarthome.Constants.ZERO;

/**
 * A simple {@link Fragment} subclass.
 */
public class DevicesFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {

    private ToggleButton toggle;
    private HTTPRequests request;
    private ProgressBar progressBar;


    public DevicesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        progressBar.setVisibility(View.VISIBLE);
        request.getStatus();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_devices, container, false);
        toggle = view.findViewById(R.id.toggleButton);
        progressBar = view.findViewById(R.id.progressBar);
        toggle.setOnCheckedChangeListener(this);
        request = new HTTPRequests(getActivity());
        if (!isConnectionAvailable()) showNoConnectionDialog();

        return view;
    }

    public void statusChanged(String status) {
        progressBar.setVisibility(View.INVISIBLE);
        if (status.equals(ONE)) {
            toggle.setChecked(true);
        }
        if (status.equals(ZERO)) {
            toggle.setChecked(false);
        }
    }
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        progressBar.setVisibility(View.VISIBLE);
        if (isChecked) {
            request.sendStatus(ON);
        } else {
            request.sendStatus(OFF);
        }
    }



    private boolean isConnectionAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        return info != null && info.isConnected();
    }

    private void showNoConnectionDialog() {
        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.network_unavailable_title)
                .setMessage(R.string.network_unavailable)
                .setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().setResult(RESULT_OK);
                    }
                }).show();
    }

}
