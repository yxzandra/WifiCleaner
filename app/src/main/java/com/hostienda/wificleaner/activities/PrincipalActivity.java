package com.hostienda.wificleaner.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import com.hostienda.wificleaner.R;
import com.hostienda.wificleaner.classes.PermissionsDispatcher;
import com.splunk.mint.Mint;

public class PrincipalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        // Mint.setApplicationEnvironment(Mint.appEnvironmentStaging);
        Mint.initAndStartSession(this.getApplication(), "b3395067");
    }

    @Override
    protected void onResume(){
        super.onResume();
        PermissionsDispatcher.showDialogPermissions(this);
        //initConvertidor();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionsDispatcher.onRequestPermissionsResult(PrincipalActivity.this, requestCode, grantResults);
    }

    public void initAplication(){
        Handler myHandler = new Handler();
        myHandler.postDelayed(mMyRunnable, 1500);

    }

    private Runnable mMyRunnable = new Runnable(){
        @Override
        public void run(){
            introActivity();
            finish();
        }
    };
    private void introActivity() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                //Initialize SharedPreferences
                SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

                //Create a new boolean and preference and set it to true
                boolean isFirstStart = getPrefs.getBoolean("FirstStart", true);
                if (isFirstStart){
                    //Launch app intro

                    Intent intent = new Intent(PrincipalActivity.this,IntroActivity.class);
                    startActivity(intent);
                    finish();

                    SharedPreferences.Editor editor = getPrefs.edit();
                    editor.putBoolean("FirstStart",false);
                    editor.apply();
                }else {
                    Intent intent = new Intent(PrincipalActivity.this,HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        t.start();
    }

}
