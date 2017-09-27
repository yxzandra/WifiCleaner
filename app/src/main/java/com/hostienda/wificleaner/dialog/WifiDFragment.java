package com.hostienda.wificleaner.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.hostienda.wificleaner.R;
import com.hostienda.wificleaner.activities.HomeActivity;

import java.util.List;

public class WifiDFragment extends DialogFragment {
    Context activity;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_clean_wifi, null))

                // Positive button
                .setPositiveButton(getString(R.string.aceptar), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        redesConf2();
                    }
                })

                // Negative Button
                .setNeutralButton(getString(R.string.cancelar), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,	int which) {
                        dialog.cancel();
                        ((HomeActivity) getActivity()).redesConfiguradas();
                    }
                });

        return builder.create();
    }

    public void redesConf2() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            activity = getActivity().getApplicationContext();

            WifiManager wifiMgr = (WifiManager) activity.getSystemService(Context.WIFI_SERVICE);

            List<WifiConfiguration> list = wifiMgr.getConfiguredNetworks();
            Log.e("Borrar wifi",list.toString());

            if (list != null) {
                for (WifiConfiguration i : list) {
                    Log.e("Borrar wifi", String.valueOf(i.networkId));
                    wifiMgr.removeNetwork(i.networkId);
                    wifiMgr.saveConfiguration();
                }
                Toast.makeText(getActivity(), getActivity().getString(R.string.toast_clean), Toast.LENGTH_SHORT).show();
                ((HomeActivity) getActivity()).redesConfiguradas();
            } else {
                Toast.makeText(getActivity(), getActivity().getString(R.string.TextWifiApagado), Toast.LENGTH_SHORT).show();
            }
        }else {
            startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
        }
    }
}
