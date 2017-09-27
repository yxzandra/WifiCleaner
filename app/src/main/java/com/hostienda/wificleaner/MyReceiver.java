package com.hostienda.wificleaner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {
    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving

        if (intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
            NetworkInfo networkInfo = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            if (networkInfo.isConnected() && (networkInfo.getType()== ConnectivityManager.TYPE_WIFI)) {
                // Wifi is connected
                Log.d("Inetify","Wifi is connected: " + String.valueOf(networkInfo));
                Toast.makeText(context, R.string.wificonectado, Toast.LENGTH_SHORT).show();
            }

        } else if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            NetworkInfo networkInfo = intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
            if ((networkInfo.getDetailedState() == NetworkInfo.DetailedState.DISCONNECTED) && (networkInfo.getType()== ConnectivityManager.TYPE_WIFI)) {
                // Wifi is disconnected
                Log.d("Inetify","Wifi is disconnected: "+String.valueOf(networkInfo));
            }
        }
    }
}
