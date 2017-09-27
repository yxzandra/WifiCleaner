package com.hostienda.wificleaner.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hostienda.wificleaner.R;
import com.hostienda.wificleaner.activities.HomeActivity;
import com.hostienda.wificleaner.classes.Redes;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DescriptionWifiAddDFragment extends DialogFragment {

    Redes red;
    View view;
    WifiConfiguration wc;
    WifiInfo wifiInfo;
    WifiManager wifi;

    @BindView(R.id.respuestaNombre)
    TextView respuestaNombre;
    @BindView(R.id.respuestaIntensidad)
    TextView respuestaIntensidad;
    @BindView(R.id.respuestaSeguridad)
    TextView respuestaSeguridad;
    @BindView(R.id.respuestaEstado)
    TextView respuestaEstado;
    @BindView(R.id.respuestaFrecuencia)
    TextView respuestaFrecuencia;
    @BindView(R.id.respuestaVelocidad)
    TextView respuestaVelocidad;
    @BindView(R.id.input_password)
    EditText inputPassword;
    @BindView(R.id.valor_checkBox)
    CheckBox valorCheckBox;
    @BindView(R.id.boton_cancelar)
    Button botonCancelar;
    @BindView(R.id.boton_conectar)
    Button botonConectar;

    public DescriptionWifiAddDFragment() {

    }

    public DescriptionWifiAddDFragment newInstance(Redes red) {
        this.red = red;
        Log.i("Test", red.getNombre());
        return new DescriptionWifiAddDFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        view = View.inflate(getContext(), R.layout.dialog_add_wifi, null);

        builder.setView(view);
        ButterKnife.bind(this, view);
        respuestaNombre.setText(red.getNombre());
        respuestaEstado.setText(red.getEstado());
        respuestaIntensidad.setText(" "+String.valueOf(red.getPotencia()));
        respuestaVelocidad.setText(red.getVelocidad());
        respuestaFrecuencia.setText(red.getFrecuencia());
        respuestaSeguridad.setText(red.getSeguridad());

        valorCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    inputPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else {
                    inputPassword.setInputType(129);
                }
            }
        });

        inputPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() > 7) {
                    botonConectar.setTextColor(getResources().getColor(R.color.secondary_text));
                    botonConectar.setEnabled(true);
                    botonConectar.setClickable(true);
                }
            }
        });

        return builder.create();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @OnClick({R.id.boton_cancelar, R.id.boton_conectar})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.boton_cancelar:
                getDialog().dismiss();
                ((HomeActivity) getActivity()).redesConfiguradas();
                break;
            case R.id.boton_conectar:
                conectarRed(red.getNombre(), red.getSeguridad(), inputPassword.getText().toString());

                break;
        }
    }
    public void conectarRed(String nombre, String segurida, String s) {

        wifi = (WifiManager) getContext().getSystemService(Context.WIFI_SERVICE);

        wc = new WifiConfiguration();
        wc.SSID = String.format("\"%s\"", nombre);

        if (segurida.equals("WEP")) {
            Log.v("rht", "Configuring WEP");
            wc.wepKeys[0]= String.format("\"%s\"", s);
            wc.wepTxKeyIndex = 0;
            wc.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            wc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);

        }else if (segurida.equals("WPA PSK") || segurida.equals("WPA2 PSK") || segurida.equals("WPA2 EAP")){
            Log.v("rht", "Configuring WPA y WPA2");
            wc.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            wc.preSharedKey = String.format("\"%s\"", s);
        }else{
            Log.v("rht", "Configuring OPEN network");
            wc.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
        }

        wifi.addNetwork(wc);
        List<WifiConfiguration> list = wifi.getConfiguredNetworks();
        for( WifiConfiguration i : list ) {
            if(i.SSID != null && i.SSID.equals("\"" + nombre + "\"")) {
                wifi.disconnect();
                wifi.enableNetwork(i.networkId, true);
                Log.e("Agregar wifi", String.valueOf(i.networkId));
                wifi.reconnect();
                break;
            }
        }
        Toast.makeText(getContext(), R.string.authenticating, Toast.LENGTH_SHORT).show();
        ((HomeActivity) getActivity()).initConect();
        getDialog().dismiss();

    }
}