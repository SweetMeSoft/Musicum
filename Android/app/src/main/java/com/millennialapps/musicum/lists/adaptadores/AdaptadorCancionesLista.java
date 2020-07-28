package com.millennialapps.musicum.lists.adaptadores;

import android.app.Activity;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.millennialapps.musicum.R;
import com.millennialapps.musicum.common.DispararIntents;
import com.millennialapps.musicum.common.Navegar;
import com.millennialapps.musicum.common.objects.Constantes;

public class AdaptadorCancionesLista extends RecyclerView.Adapter<AdaptadorCancionesLista.ViewHolder> {

    private final Activity activity;
    private final Cursor cursor;
    private String nombreLista;

    public AdaptadorCancionesLista(final Activity activity, final Cursor cursor, String nombreLista) {
        this.activity = activity;
        this.nombreLista = nombreLista;
        this.cursor = cursor;
    }

    @Override
    public AdaptadorCancionesLista.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cancion_lista, parent, false);
        ViewHolder holder = new ViewHolder(view, activity, nombreLista, cursor);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        cursor.moveToPosition(position);
        holder.txtTitulo.setText(cursor.getString(cursor.getColumnIndex(Constantes.CAN_TITULO)));
        holder.txtArtista.setText(cursor.getString(cursor.getColumnIndex(Constantes.CAN_ARTISTA)));
        holder.txtAlbum.setText(cursor.getString(cursor.getColumnIndex(Constantes.CAN_ALBUM)));
        holder.txtDuracion.setText(cursor.getString(cursor.getColumnIndex(Constantes.CAN_DURACION)));
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final Activity activity;
        public TextView txtTitulo;
        public TextView txtArtista;
        public TextView txtAlbum;
        public TextView txtDuracion;
        private String lista;
        private Cursor cursor;

        public ViewHolder(View view, final Activity activity, String lista, Cursor cursor) {
            super(view);
            this.activity = activity;
            this.lista = lista;
            this.cursor = cursor;
            txtTitulo = (TextView) view.findViewById(R.id.txtTitulo);
            txtArtista = (TextView) view.findViewById(R.id.txtArtista);
            txtAlbum = (TextView) view.findViewById(R.id.txtAlbum);
            txtDuracion = (TextView) view.findViewById(R.id.txtDuracion);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getLayoutPosition();
            //Datos.getMusicService().setCanciones(ObtenerDatos.crearListaActual(cursor));
            DispararIntents.irACancion(activity, position);
            Navegar.irAInicio(activity, position);
        }
    }
}





