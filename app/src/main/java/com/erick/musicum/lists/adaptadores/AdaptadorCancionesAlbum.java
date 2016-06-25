package com.erick.musicum.lists.adaptadores;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.erick.musicum.R;
import com.erick.musicum.common.DispararIntents;
import com.erick.musicum.common.FormatearDatos;
import com.erick.musicum.common.Navegar;
import com.erick.musicum.common.objects.Constantes;

public class AdaptadorCancionesAlbum extends RecyclerView.Adapter<AdaptadorCancionesAlbum.ViewHolder> {

    private Cursor cursor;
    private int columnaTitulo;
    private int columnaDuracion;
    private int columnaTrack;
    private AppCompatActivity actividad;
    private String artista;
    private String album;

    public AdaptadorCancionesAlbum(Cursor cursor, AppCompatActivity actividad, String artista, String album) {
        this.cursor = cursor;
        this.actividad = actividad;
        this.artista = artista;
        this.album = album;
        columnaTitulo = cursor.getColumnIndex(Constantes.CAN_TITULO);
        columnaDuracion = cursor.getColumnIndex(Constantes.CAN_DURACION);
        columnaTrack = cursor.getColumnIndex(Constantes.CAN_TRACK);
    }

    @Override
    public AdaptadorCancionesAlbum.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cancion_album, parent, false);
        ViewHolder vh = new ViewHolder(v, actividad, artista, album, cursor);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        cursor.moveToPosition(position);
        holder.txtNombre.setText(cursor.getString(columnaTitulo));
        holder.txtDuracion.setText(FormatearDatos.millisToString(cursor.getLong(columnaDuracion)));
        holder.txtTrack.setText(FormatearDatos.obtenerTrack(cursor.getString(columnaTrack)));
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public RelativeLayout container;
        public TextView txtNombre;
        public TextView txtDuracion;
        public TextView txtTrack;
        private AppCompatActivity actividad;
        private String artista;
        private String album;
        private Cursor cursor;

        public ViewHolder(View v, AppCompatActivity actividad, String artista, String album, Cursor cursor) {
            super(v);
            this.actividad = actividad;
            this.artista = artista;
            this.album = album;
            this.cursor = cursor;
            container = (RelativeLayout) itemView.findViewById(R.id.cancion_album);
            txtNombre = (TextView)v.findViewById(R.id.txtTitulo);
            txtDuracion = (TextView)v.findViewById(R.id.txtDuracion);
            txtTrack = (TextView)v.findViewById(R.id.txtTrack);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getLayoutPosition();
            //Datos.getMusicService().setCanciones(ObtenerDatos.crearListaActual(cursor));
            DispararIntents.irACancion(actividad, position);
            Navegar.irAInicio(actividad, position);
        }
    }
}





