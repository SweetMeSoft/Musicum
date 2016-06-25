package com.erick.musicum.lists.adaptadores;

import android.app.Activity;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.erick.musicum.R;
import com.erick.musicum.common.DispararIntents;
import com.erick.musicum.common.FormatearDatos;
import com.erick.musicum.common.Navegar;
import com.erick.musicum.common.objetos.Constantes;

public class AdaptadorCancionesGenero extends RecyclerView.Adapter<AdaptadorCancionesGenero.ViewHolder> {

    private final Activity activity;
    private Cursor cursor;
    private int columnaTitulo;
    private int columnaArtista;
    private int columnaAlbum;
    private int columnaDuracion;
    private String genero;

    public AdaptadorCancionesGenero(final Activity activity, Cursor cursor, String genero) {
        this.activity = activity;
        this.cursor = cursor;
        this.genero = genero;
        columnaTitulo = cursor.getColumnIndex(Constantes.CAN_TITULO);
        columnaArtista = cursor.getColumnIndex(Constantes.CAN_ARTISTA);
        columnaAlbum = cursor.getColumnIndex(Constantes.CAN_ALBUM);
        columnaDuracion = cursor.getColumnIndex(Constantes.CAN_DURACION);
    }

    @Override
    public AdaptadorCancionesGenero.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cancion_genero, parent, false);
        ViewHolder holder = new ViewHolder(view, activity, genero);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        cursor.moveToPosition(position);
        holder.txtTitulo.setText(cursor.getString(columnaTitulo));
        holder.txtArtista.setText(cursor.getString(columnaArtista));
        holder.txtAlbum.setText(cursor.getString(columnaAlbum));
        holder.txtDuracion.setText(FormatearDatos.millisToString(cursor.getLong(columnaDuracion)));
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
        private String genero;

        public ViewHolder(View v, final Activity activity, String genero) {
            super(v);
            this.genero = genero;
            this.activity = activity;
            txtTitulo = (TextView)v.findViewById(R.id.txtTitulo);
            txtArtista = (TextView)v.findViewById(R.id.txtArtista);
            txtAlbum = (TextView)v.findViewById(R.id.txtAlbum);
            txtDuracion = (TextView)v.findViewById(R.id.txtDuracion);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getLayoutPosition();
            //Datos.getMusicService().setCanciones(ObtenerDatos.crearListaActual(ObtenerCursores.listaCancionesGenero(activity, Preferencias.obtenerRandom(), genero)));
            DispararIntents.irACancion(activity, position);
            Navegar.irAInicio(activity, position);
        }
    }
}





