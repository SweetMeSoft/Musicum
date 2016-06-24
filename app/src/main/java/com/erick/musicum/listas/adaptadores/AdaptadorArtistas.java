package com.erick.musicum.listas.adaptadores;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.erick.musicum.R;
import com.erick.musicum.comun.objetos.Constantes;
import com.erick.musicum.comun.objetos.Datos;
import com.erick.musicum.comun.objetos.Dialogos;
import com.erick.musicum.listas.AlbumesArtista;

public class AdaptadorArtistas extends RecyclerView.Adapter<AdaptadorArtistas.ViewHolder> {

    private final Activity context;
    private Cursor cursor;
    private final int colID;
    private final int colArtista;
    private final int colCanciones;
    private final int colAlbumes;

    public AdaptadorArtistas(final Activity context, final Cursor cursor) {
        this.cursor = cursor;
        this.context = context;
        colID = cursor.getColumnIndex(Constantes.ART_ID);
        colArtista = cursor.getColumnIndex(Constantes.ART_NOMBRE);
        colCanciones = cursor.getColumnIndex(Constantes.ART_CANCIONES);
        colAlbumes = cursor.getColumnIndex(Constantes.ART_ALBUMES);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_artista, parent, false);
        ViewHolder holder = new ViewHolder(view, context);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        cursor.moveToPosition(position);
        holder.id = cursor.getLong(colID);
        holder.txtArtista.setText(cursor.getString(colArtista));
        holder.txtArtista.setEllipsize(TextUtils.TruncateAt.MIDDLE);
        if(cursor.getString(colCanciones).equals("1")) {
            if(cursor.getString(colAlbumes).equals("1")) {
                holder.txtCantidad.setText("Tiene " + cursor.getString(colCanciones) + " canción en " + cursor.getString(colAlbumes) + " álbum");
            }else{
                holder.txtCantidad.setText("Tiene " + cursor.getString(colCanciones) + " canción en " + cursor.getString(colAlbumes) + " álbumes");
            }
        }else{
            if(cursor.getString(colAlbumes).equals("1")) {
                holder.txtCantidad.setText("Tiene " + cursor.getString(colCanciones) + " canciones en " + cursor.getString(colAlbumes) + " álbum");
            }else{
                holder.txtCantidad.setText("Tiene " + cursor.getString(colCanciones) + " canciones en " + cursor.getString(colAlbumes) + " álbumes");
            }
        }
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        private Activity context;
        public TextView txtArtista;
        public TextView txtCantidad;
        private long id;

        public ViewHolder(View view, final Activity context) {
            super(view);
            this.context = context;
            txtArtista = (TextView) view.findViewById(R.id.txtArtista);
            txtCantidad = (TextView) view.findViewById(R.id.txtCantidad);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, AlbumesArtista.class);
            intent.putExtra("Artista", txtArtista.getText());
            context.startActivity(intent);
        }

        @Override
        public boolean onLongClick(View view) {
            Dialogos.agregarALista(context, R.string.txt_agregar_artista_a_lista, txtArtista.getText().toString(), id, Constantes.TIP_AGR_ARTISTA);
            return true;
        }

    }
}



