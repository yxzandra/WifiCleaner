package com.hostienda.wificleaner.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hostienda.wificleaner.R;
import com.hostienda.wificleaner.classes.Redes;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DescriptionWifiDFragment extends DialogFragment {
    @BindView(R.id.textEstado)
    TextView textEstado;
    @BindView(R.id.textIntensidad)
    TextView textIntensidad;
    @BindView(R.id.textVelocidad)
    TextView textVelocidad;
    @BindView(R.id.textFrecuencia)
    TextView textFrecuencia;
    @BindView(R.id.textRespuestaIntensidad)
    TextView textRespuestaIntensidad;
    @BindView(R.id.textSeguridad)
    TextView textSeguridad;
    @BindView(R.id.textRespuestaSeguridad)
    TextView textRespuestaSeguridad;
    @BindView(R.id.textRespuestaEstado)
    TextView textRespuestaEstado;
    @BindView(R.id.textRespuestaFrecuencia)
    TextView textRespuestaFrecuencia;
    @BindView(R.id.textRespuestaVelocidad)
    TextView textRespuestaVelocidad;
    @BindView(R.id.textView)
    TextView textRespuestaNombre;

    Redes red;
    View view;


    public DescriptionWifiDFragment() {

    }

    public DescriptionWifiDFragment newInstance(Redes red) {
        this.red = red;
        Log.i("Test", red.getNombre());
        return new  DescriptionWifiDFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        view = View.inflate(getContext(), R.layout.dialog_info_wifi, null);
        builder.setView(view);
        ButterKnife.bind(this, view);
        textRespuestaNombre.setText(red.getNombre());
        textRespuestaEstado.setText(red.getEstado());

        textRespuestaIntensidad.setText(" "+String.valueOf(red.getPotencia()));
        textRespuestaVelocidad.setText(red.getVelocidad());
        textRespuestaFrecuencia.setText(red.getFrecuencia());
        textRespuestaSeguridad.setText(red.getSeguridad());
        return builder.create();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        return super.onCreateView(inflater, container, savedInstanceState);
    }


}