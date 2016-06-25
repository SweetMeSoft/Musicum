package com.erick.musicum.lists.adaptadores;

import android.app.Activity;
import android.database.Cursor;
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
import com.erick.musicum.common.ObtenerCursores;
import com.erick.musicum.common.objects.Constantes;
import com.erick.musicum.common.objects.Preferencias;

public class AdaptadorCancionesArtista extends RecyclerView.Adapter<AdaptadorCancionesArtista.ViewHolder> {

    private final Activity activity;
    private final int colTitulo;
    private final int colDuracion;
    private final int colAlbum;
    private Cursor cursor;
    private String artista;

    public AdaptadorCancionesArtista(final Activity activity, Cursor cursor, String artista) {
        this.cursor = cursor;
        this.activity = activity;
        this.artista = artista;
        colTitulo = cursor.getColumnIndex(Constantes.CAN_TITULO);
        colDuracion = cursor.getColumnIndex(Constantes.CAN_DURACION);
        colAlbum = cursor.getColumnIndex(Constantes.CAN_ALBUM);
    }

    @Override
    public AdaptadorCancionesArtista.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cancion_artista, parent, false);
        ViewHolder vh = new ViewHolder(view, activity, artista, cursor);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        cursor.moveToPosition(position);
        holder.txtTitulo.setText(cursor.getString(colTitulo));
        holder.txtDuracion.setText(FormatearDatos.millisToString(cursor.getLong(colDuracion)));
        holder.txtAlbum.setText(cursor.getString(colAlbum));
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public RelativeLayout container;
        public TextView txtTitulo;
        public TextView txtDuracion;
        public TextView txtAlbum;
        private Activity activity;
        private String artista;
        private Cursor cursor;

        public ViewHolder(View view, final Activity activity, String artista, Cursor cursor) {
            super(view);
            this.activity = activity;
            this.artista = artista;
            this.cursor = cursor;
            txtTitulo = (TextView)view.findViewById(R.id.txtTitulo);
            txtDuracion = (TextView)view.findViewById(R.id.txtDuracion);
            txtAlbum = (TextView)view.findViewById(R.id.txtLista);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getLayoutPosition();
            cursor.moveToPosition(position);
            long index = cursor.getLong(cursor.getColumnIndex(Constantes.CAN_ID));
            cursor = ObtenerCursores.listaCancionesArtista(activity, Preferencias.obtenerRandom(activity), artista);
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                if(cursor.getLong(cursor.getColumnIndex(Constantes.CAN_ID)) == index){
                    position = i;
                    break;
                }
                cursor.moveToNext();
            }
            //Datos.getMusicService().setCanciones(ObtenerDatos.crearListaActual(cursor));
            DispararIntents.irACancion(activity, position);
            Navegar.irAInicio(activity, position);
        }
    }
}





