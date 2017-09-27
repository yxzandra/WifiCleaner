package com.hostienda.wificleaner;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hostienda.wificleaner.activities.HomeActivity;
import com.hostienda.wificleaner.classes.Redes;
import com.hostienda.wificleaner.dialog.DescriptionWifiAddDFragment;
import com.hostienda.wificleaner.dialog.DescriptionWifiConectDFragment;
import com.hostienda.wificleaner.dialog.DescriptionWifiDFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private ArrayList<Redes> arrayList;
    private Context context;

    public RecyclerAdapter(Context context, ArrayList<Redes> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_child, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.cardViewNombreRed.setText(arrayList.get(position).getNombre());
        holder.cardViewTipoRed.setText(arrayList.get(position).getSeguridad());
        if (arrayList.get(position).getTipoRed()== 0 && !arrayList.get(position).getNombre().equals("0x")){
            holder.cardViewImagen.setBackgroundResource(R.drawable.ic_wifi_lock_verde);
        }if (arrayList.get(position).getTipoRed()== 1){
            holder.cardViewImagen.setBackgroundResource(R.drawable.ic_wifi_lock_lila);
        }if (arrayList.get(position).getTipoRed()== 2){
            holder.cardViewImagen.setBackgroundResource(R.drawable.ic_wifi_blanco);
        }
        final Redes elemento = arrayList.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = ((HomeActivity) context).getSupportFragmentManager();
                if (elemento.isGuardada()) {
                    if (elemento.getEstado().equals("Cercana")){
                        DescriptionWifiConectDFragment descriptionWifiConectDFragment = new DescriptionWifiConectDFragment();
                        descriptionWifiConectDFragment.newInstance(elemento);
                        // Show DialogFragment
                        descriptionWifiConectDFragment.show(fm, "Dialog Fragment");
                        Log.e("Estoy aqui", "guardada y cercana");
                    }else {
                        DescriptionWifiDFragment descriptionWifiDFragment = new DescriptionWifiDFragment();
                        descriptionWifiDFragment.newInstance(elemento);
                        // Show DialogFragment
                        descriptionWifiDFragment.show(fm, "Dialog Fragment");
                    }
                }else{
                    DescriptionWifiAddDFragment descriptionWifiConectDFragment = new DescriptionWifiAddDFragment();
                    descriptionWifiConectDFragment.newInstance(elemento);
                    // Show DialogFragment
                    descriptionWifiConectDFragment.show(fm, "Dialog Fragment");
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }




    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.cardViewNombreRed)
        TextView cardViewNombreRed;
        @BindView(R.id.cardViewImagen)
        ImageView cardViewImagen;
        @BindView(R.id.cardViewTipoRed)
        TextView cardViewTipoRed;

        ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View view) {}



    }
}
