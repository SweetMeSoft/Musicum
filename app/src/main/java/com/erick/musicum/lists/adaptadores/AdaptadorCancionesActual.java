package com.erick.musicum.lists.adaptadores;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.erick.musicum.R;
import com.erick.musicum.common.Navegar;
import com.erick.musicum.common.ObtenerDatos;
import com.erick.musicum.common.objects.Cancion;
import com.erick.musicum.common.objects.Constantes;
import com.erick.musicum.common.objects.Datos;
import com.erick.musicum.common.objects.Dialogos;

import java.util.ArrayList;

public class AdaptadorCancionesActual extends RecyclerView.Adapter<AdaptadorCancionesActual.ViewHolder> {

    private final Activity activity;

    private ArrayList<Cancion> canciones;
    private int color;

    public AdaptadorCancionesActual(final Activity activity, ArrayList<Cancion> canciones) {
        this.activity = activity;
        this.canciones = canciones;

        color = ObtenerDatos.atributo(activity, R.attr.color_texto);
    }

    @Override
    public AdaptadorCancionesActual.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cancion, parent, false);
        ViewHolder holder = new ViewHolder(view, activity, this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Cancion cancion = canciones.get(position);
        holder.idCancion = cancion.getId();
        holder.txtTitulo.setText(cancion.getTitulo());
        holder.txtArtista.setText(cancion.getArtista());
        holder.txtAlbum.setText(cancion.getAlbum());
        holder.txtFin.setText(cancion.getDuracion());
        /*if (cancion.getId() == Datos.getMusicSrv().getCanciones().get(MusicService.getIndexActual())) {
            holder.txtTitulo.setTextColor(activity.getResources().getColor(R.color.colorPrimary));
            holder.txtArtista.setTextColor(activity.getResources().getColor(R.color.colorPrimary));
            holder.txtAlbum.setTextColor(activity.getResources().getColor(R.color.colorPrimary));
            holder.txtFin.setTextColor(activity.getResources().getColor(R.color.colorPrimary));
        } else {*/
            holder.txtTitulo.setTextColor(color);
            holder.txtArtista.setTextColor(color);
            holder.txtAlbum.setTextColor(color);
            holder.txtFin.setTextColor(color);
        //}
    }

    @Override
    public int getItemCount() {
        return canciones.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        public TextView txtTitulo;
        public TextView txtArtista;
        public TextView txtAlbum;
        public TextView txtFin;
        public AdaptadorCancionesActual adaptador;
        private Activity activity;
        private long idCancion;

        public ViewHolder(View v, final Activity activity, AdaptadorCancionesActual adaptador) {
            super(v);
            this.activity = activity;
            this.adaptador = adaptador;
            txtTitulo = (TextView) v.findViewById(R.id.txtTitulo);
            txtArtista = (TextView) v.findViewById(R.id.txtArtista);
            txtAlbum = (TextView) v.findViewById(R.id.txtAlbum);
            txtFin = (TextView) v.findViewById(R.id.txtDuracion);
            v.setOnClickListener(this);
            v.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            final int position = getLayoutPosition();
            Navegar.irACancion(idCancion, Datos.getCursorActual());
            adaptador.notifyDataSetChanged();
        }
        @Override
        public boolean onLongClick(View view) {
            Dialogos.agregarALista(activity, R.string.txt_agregar_cancion_a_lista, txtTitulo.getText().toString(), idCancion, Constantes.TIP_AGR_CANCION);
            return true;
        }

    }
}



