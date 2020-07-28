package com.millennialapps.musicum.lists.adaptadores;

import android.app.Activity;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.millennialapps.musicum.R;
import com.millennialapps.musicum.common.FormatearDatos;
import com.millennialapps.musicum.common.Navegar;
import com.millennialapps.musicum.common.ObtenerCursores;
import com.millennialapps.musicum.common.objects.Constantes;
import com.millennialapps.musicum.common.objects.Dialogos;
import com.millennialapps.musicum.common.objects.Preferencias;

public class AdaptadorTodasCanciones extends RecyclerView.Adapter<AdaptadorTodasCanciones.ViewHolder> {

    private final int colID;
    private final int colTitulo;
    private final int colArtista;
    private final int colAlbum;
    private final int colDuracion;
    private Activity activity;
    private Cursor cursor;

    public AdaptadorTodasCanciones(final Activity activity, final Cursor cursor) {
        this.activity = activity;
        this.cursor = cursor;
        colID = cursor.getColumnIndex(Constantes.CAN_ID);
        colTitulo = cursor.getColumnIndex(Constantes.CAN_TITULO);
        colArtista = cursor.getColumnIndex(Constantes.CAN_ARTISTA);
        colAlbum = cursor.getColumnIndex(Constantes.CAN_ALBUM);
        colDuracion = cursor.getColumnIndex(Constantes.CAN_DURACION);
    }

    @Override
    public AdaptadorTodasCanciones.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todas_canciones, parent, false);
        ViewHolder vh = new ViewHolder(v, activity, cursor);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        cursor.moveToPosition(position);
        holder.idCancion = cursor.getLong(colID);
        holder.txtTitulo.setText(cursor.getString(colTitulo));
        holder.txtArtista.setText(cursor.getString(colArtista));
        holder.txtAlbum.setText(cursor.getString(colAlbum));
        holder.txtFin.setText(FormatearDatos.millisToString(cursor.getLong(colDuracion)));
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        private final Activity activity;
        public TextView txtTitulo;
        public TextView txtArtista;
        public TextView txtAlbum;
        public TextView txtFin;
        private Cursor cursor;
        private long idCancion;

        public ViewHolder(View view, final Activity activity, Cursor cursor) {
            super(view);
            this.activity = activity;
            this.cursor = cursor;
            txtTitulo = (TextView) view.findViewById(R.id.txtTitulo);
            txtArtista = (TextView) view.findViewById(R.id.txtArtista);
            txtAlbum = (TextView) view.findViewById(R.id.txtAlbum);
            txtFin = (TextView) view.findViewById(R.id.txtDuracion);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getLayoutPosition();
            cursor.moveToPosition(position);
            long index = cursor.getLong(cursor.getColumnIndex(Constantes.CAN_ID));
            cursor = ObtenerCursores.todasCanciones(activity, Preferencias.obtenerRandom(activity));
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                if(cursor.getLong(cursor.getColumnIndex(Constantes.CAN_ID)) == index){
                    position = i;
                    break;
                }
                cursor.moveToNext();
            }
            //Datos.getMusicService().setCanciones(ObtenerDatos.crearListaActual(cursor));
            Navegar.irAInicio(activity, position);
        }

        @Override
        public boolean onLongClick(View v) {
            Dialogos.agregarALista(activity, R.string.txt_agregar_cancion_a_lista, txtTitulo.getText().toString(), idCancion, Constantes.TIP_AGR_CANCION);
            return false;
        }
    }
}



