package com.hostienda.wificleaner.classes;

import android.support.v4.app.ActivityCompat;

import com.hostienda.wificleaner.activities.PrincipalActivity;

import permissions.dispatcher.PermissionUtils;


public class PermissionsDispatcher  {

    //private static final int REQUEST_SHOWCAMERA = 0;

    //private static final String[] PERMISSION_SHOWCAMERA = new String[] {"android.permission.CAMERA"};

    private static final int REQUEST = 1;
    //private static final String[] PERMISSIONS = new String[] {"android.permission.WRITE_EXTERNAL_STORAGE","android.permission.ACCESS_COARSE_LOCATION","android.permission.ACCESS_FINE_LOCATION","android.permission.CALL_PHONE","android.permission.READ_PHONE_STATE","android.permission.CAMERA"};
    private static final String[] PERMISSIONS = new String[]  {"android.permission.ACCESS_COARSE_LOCATION","android.permission.ACCESS_FINE_LOCATION","android.permission.INTERNET","android.permission.ACCESS_WIFI_STATE",};

    private PermissionsDispatcher() {
    }

    public static void showDialogPermissions(PrincipalActivity target) {
        if (PermissionUtils.hasSelfPermissions(target, PERMISSIONS)) {
            //target.initLogin();
            target.initAplication();

        } else {
            ActivityCompat.requestPermissions(target, PERMISSIONS, REQUEST);
        }
    }

    public static void onRequestPermissionsResult(PrincipalActivity target, int requestCode, int[] grantResults) {
        switch (requestCode) {
            case REQUEST:
                if (!PermissionUtils.hasSelfPermissions(target, PERMISSIONS)) {
                    target.finish();
                }else
                {
                    //target.initLogin();
                    target.initAplication();
                }
                break;
            default:
                break;
        }
    }
}