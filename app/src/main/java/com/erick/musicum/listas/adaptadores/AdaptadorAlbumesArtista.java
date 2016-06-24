package com.erick.musicum.listas.adaptadores;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.erick.musicum.R;
import com.erick.musicum.comun.ObtenerPortadas;
import com.erick.musicum.comun.objetos.Constantes;
import com.erick.musicum.comun.objetos.Datos;
import com.erick.musicum.listas.CancionesAlbum;

public class AdaptadorAlbumesArtista extends RecyclerView.Adapter<AdaptadorAlbumesArtista.ViewHolder> {

    private final Activity activity;
    private Cursor cursor;
    private final int colAlbum;
    private final int colArtista;

    public AdaptadorAlbumesArtista(final Activity activity, final Cursor cursor) {
        this.cursor = cursor;
        this.activity = activity;
        colAlbum = this.cursor.getColumnIndex(Constantes.ALB_NOMBRE);
        colArtista = this.cursor.getColumnIndex(Constantes.ALB_ARTISTA);
    }

    @Override
    public AdaptadorAlbumesArtista.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_album, parent, false);
        ViewHolder vh = new ViewHolder(v, activity);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        cursor.moveToPosition(position);
        holder.imgVwPortada.setImageBitmap(Datos.getPortadaDefault());
        holder.imgVwPortada.setTag(position);
        holder.txtTitulo.setText(cursor.getString(colAlbum));
        holder.txtArtista.setText(cursor.getString(colArtista));
        new ObtenerPortadas(cursor.getInt(cursor.getColumnIndex(Constantes.ALB_ID)), holder.imgVwPortada, 200, false)
                .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, activity);
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView txtTitulo;
        public TextView txtArtista;
        public ImageView imgVwPortada;
        public RelativeLayout container;
        private Context context;

        public ViewHolder(View v, final Context context) {
            super(v);
            this.context = context;
            container = (RelativeLayout) itemView.findViewById(R.id.artista);
            txtTitulo = (TextView) v.findViewById(R.id.txtTitulo);
            txtArtista = (TextView) v.findViewById(R.id.txtArtista);
            imgVwPortada = (ImageView) v.findViewById(R.id.imgVwPortada);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, CancionesAlbum.class);
            intent.putExtra("album", txtTitulo.getText().toString());
            intent.putExtra("artista", txtArtista.getText().toString());
            context.startActivity(intent);
        }
    }

}



