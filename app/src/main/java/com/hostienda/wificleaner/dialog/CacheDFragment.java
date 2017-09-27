package com.hostienda.wificleaner.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.hostienda.wificleaner.R;
import com.hostienda.wificleaner.activities.HomeActivity;

import java.lang.reflect.Method;

public class CacheDFragment extends DialogFragment {
    Context activity;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_clean_cache, null))

                // Positive button
                .setPositiveButton(getString(R.string.aceptar), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        cleanCache();
                        ((HomeActivity) getActivity()).redesConfiguradas();
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

    public void cleanCache() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            activity = getActivity().getApplicationContext();

            PackageManager pm = activity.getPackageManager();
            // Get all methods on the PackageManager
            Method[] methods = pm.getClass().getDeclaredMethods();
            for (Method m : methods) {
                if (m.getName().equals("freeStorage")) {
                    // Found the method I want to use
                    try {
                        long desiredFreeStorage = Long.MAX_VALUE; // Request for 8GB of free space
                        m.invoke(pm, desiredFreeStorage, null);
                    } catch (Exception e) {
                        // Method invocation failed. Could be a permission problem
                    }
                    break;
                }
            }
            Toast.makeText(getActivity(), getActivity().getString(R.string.toast_clean), Toast.LENGTH_SHORT).show();
        }else {
            startActivity(new Intent(Settings.ACTION_INTERNAL_STORAGE_SETTINGS));
        }
    }
}
