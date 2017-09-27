package com.hostienda.wificleaner.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.wifi.ScanResult;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionMenu;
import com.hostienda.wificleaner.R;
import com.hostienda.wificleaner.RecyclerAdapter;
import com.hostienda.wificleaner.classes.Redes;
import com.hostienda.wificleaner.dialog.CacheDFragment;
import com.hostienda.wificleaner.dialog.WifiDFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hotchemi.android.rate.AppRate;
import hotchemi.android.rate.OnClickButtonListener;


public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FragmentManager fm = getSupportFragmentManager();
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.menuItemCache)
    com.github.clans.fab.FloatingActionButton menuItemCache;
    @BindView(R.id.menuItemWifi)
    com.github.clans.fab.FloatingActionButton menuItemWifi;
    @BindView(R.id.menuFAB)
    FloatingActionMenu menuFAB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        redesConfiguradas();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        AppRate.with(HomeActivity.this)
                .setInstallDays(1)
                .setLaunchTimes(1)
                .setRemindInterval(2)
                .setShowLaterButton(true)
                .setDebug(false)
                .setOnClickButtonListener(new OnClickButtonListener() {
                    @Override
                    public void onClickButton(int which) {
                        Log.d(PrincipalActivity.class.getName(), Integer.toString(which));
                    }
                })
                .monitor();

        AppRate.showRateDialogIfMeetsConditions(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        redesConfiguradas();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_share) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT,getString(R.string.shareText)+getPackageName());
            //intent.putExtra(Intent.EXTRA_TEXT,getString(R.string.shareText)+getPackageName());
            startActivity(Intent.createChooser(intent, "Share With"));

        } else if (id == R.id.nav_apps) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://play.google.com/store/apps/dev?id=6214168289555124073&hl=es"));
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void recyclerViewPrueba(ArrayList<Redes> arrayList) {

        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.setAdapter(new RecyclerAdapter(this, arrayList));

    }

    public void redesConfiguradas() {

        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        wifiManager.setWifiEnabled(true);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if (wifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLED) {

            String networkSSID = wifiInfo.getSSID();

            WifiConfiguration conf = new WifiConfiguration();
            conf.SSID = networkSSID;
            conf.status = WifiConfiguration.Status.ENABLED;
            conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            conf.priority = Integer.MAX_VALUE;
            ArrayList<Redes> listRedes = new ArrayList<>();

            String nombreRed1 = wifiInfo.getSSID().replaceAll("\"", "");

            Redes redes1;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    redes1 = new Redes(nombreRed1, "0", "Conectada", wifiInfo.getRssi(), wifiInfo.getLinkSpeed() + "Mbps", String.valueOf(wifiInfo.getFrequency()), 0, true);
                } else {
                    redes1 = new Redes(nombreRed1, "0", "Conectada", wifiInfo.getRssi(), wifiInfo.getLinkSpeed() + "Mbps", String.valueOf(0), 0, true);
                }

                listRedes.add(redes1);
            List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();

            List<ScanResult> wifiScanList = wifiManager.getScanResults();
            for (int j = 0; j < wifiScanList.size(); j++) {
                if (!wifiScanList.get(j).SSID.equals("")) {
                    String seguridad = "";
                    if (wifiScanList.get(j).capabilities.contains("WPA-PSK")) {
                        seguridad = "WPA PSK";
                    }
                    if (wifiScanList.get(j).capabilities.contains("WPA2-PSK")) {
                        seguridad = "WPA2 PSK";
                    }
                    if (wifiScanList.get(j).capabilities.contains("WPA2-EAP")) {
                        seguridad = "WPA2 EAP";
                    }
                    if (wifiScanList.get(j).capabilities.contains("[WPS][WEP][ESS]")) {
                        seguridad = "WPS";
                    }
                    if (wifiScanList.get(j).capabilities.contains("[WEP][ESS]")) {
                        seguridad = "WEP";
                    }
                    Log.e("lista cercana", wifiScanList.toString());


                    Redes redes = new Redes(wifiScanList.get(j).SSID, seguridad, "Cercana", wifiScanList.get(j).level, "0",String.valueOf(wifiScanList.get(j).frequency), 1,false);
                    if (listRedes.get(0).getNombre().equals(redes.getNombre())) {
                        if (wifiInfo.getSupplicantState() == SupplicantState.COMPLETED){
                            listRedes.get(0).setSeguridad(seguridad);
                            listRedes.get(0).setFrecuencia(String.valueOf(wifiScanList.get(j).frequency));
                        }else {
                            listRedes.remove(0);
                            listRedes.add(redes);
                        }
                    } else {
                        listRedes.add(redes);
                    }
                }
            }


            for (int i = 0; i <= list.size() - 1; i++) {
                Boolean repetida = false;
                String protocolo = "null";
                if (list.get(i).allowedKeyManagement.get(0)) {
                    protocolo = "NONE";
                }
                if (list.get(i).allowedKeyManagement.get(1)) {
                    protocolo = "WPA PSK";
                }
                if (list.get(i).allowedKeyManagement.get(2)) {
                    protocolo = "WPA EAP";
                }
                if (list.get(i).allowedKeyManagement.get(3)) {
                    protocolo = "IEEE8021X";
                }
                String nombreRed = list.get(i).SSID.replaceAll("\"", "");

                for (int j = 0; j < listRedes.size(); j++) {
                    if (listRedes.get(j).getNombre().equals(nombreRed)) {
                        repetida = true;
                        Log.e("lisredes",listRedes.get(j).toString());
                        listRedes.get(j).setGuardada(true);
                        Log.e("lisredes",listRedes.get(j).toString());
                        break;
                    }
                }
                if (!repetida) {
                    Redes redes = new Redes(nombreRed, protocolo, "Guardada", 0, "0"," NONE", 2,true);
                    listRedes.add(redes);
                }

            }


            if (listRedes.get(0).getNombre().equals("0x") || listRedes.get(0).getNombre().equals("<unknown ssid>") ) {
                listRedes.remove(0);
            }

            recyclerViewPrueba(listRedes);
            if (recyclerView.getVisibility() == View.GONE) {
                recyclerView.setVisibility(View.VISIBLE);
                textView.setVisibility(View.GONE);
            }
        } else {
            recyclerView.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.menuItemCache, R.id.menuItemWifi})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.menuItemCache:
                CacheDFragment cachedFragment = new CacheDFragment();
                // Show Alert DialogFragment
                cachedFragment.show(fm, "Alert Dialog Fragment");

                break;
            case R.id.menuItemWifi:
                WifiDFragment wifiDFragment = new WifiDFragment();
                // Show Alert DialogFragment
                wifiDFragment.show(fm, "Alert Dialog Fragment");
        }
    }

    public void initConect(){
        Handler myHandler = new Handler();
        myHandler.postDelayed(mMyRunnable, 3000);

    }

    private Runnable mMyRunnable = new Runnable(){
        @Override
        public void run(){
            WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
            wifiManager.setWifiEnabled(true);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            if (wifiInfo.getSupplicantState() != SupplicantState.COMPLETED) {

                List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();

                for (WifiConfiguration i : list) {
                    if (i.SSID.equals(wifiInfo.getSSID())) {
                        Log.e("Borrar wifi", String.valueOf(i.networkId));
                        wifiManager.removeNetwork(i.networkId);
                        wifiManager.saveConfiguration();
                    }
                }
                Toast.makeText(getBaseContext(), R.string.passwordinvalid, Toast.LENGTH_SHORT).show();
            }
            redesConfiguradas();
        }
    };

}
