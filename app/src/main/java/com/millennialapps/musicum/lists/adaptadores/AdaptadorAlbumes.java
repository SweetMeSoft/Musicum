package com.millennialapps.musicum.lists.adaptadores;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.millennialapps.musicum.R;
import com.millennialapps.musicum.common.ObtenerPortadas;
import com.millennialapps.musicum.common.objects.Constantes;
import com.millennialapps.musicum.common.objects.Datos;
import com.millennialapps.musicum.common.objects.Dialogos;
import com.millennialapps.musicum.lists.CancionesAlbum;

public class AdaptadorAlbumes extends RecyclerView.Adapter<AdaptadorAlbumes.ViewHolder> {

    private final int colID;
    private final int colNombre;
    private final int colArtista;
    private Cursor cursor;
    private Activity activity;
    private int lastPosition = -1;

    public AdaptadorAlbumes(Cursor cursor, final Activity activity) {
        this.cursor = cursor;
        this.activity = activity;
        this.colID = cursor.getColumnIndex(Constantes.ALB_ID);
        this.colNombre = cursor.getColumnIndex(Constantes.ALB_NOMBRE);
        this.colArtista = cursor.getColumnIndex(Constantes.ALB_ARTISTA);
    }

    @Override
    public AdaptadorAlbumes.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_album, parent, false);
        ViewHolder vh = new ViewHolder(v, activity);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        cursor.moveToPosition(position);

        holder.idAlbum = cursor.getLong(colID);
        holder.imgVwPortada.setImageBitmap(Datos.getPortadaDefault());
        holder.imgVwPortada.setTag(position);
        holder.txtAlbum.setText(cursor.getString(colNombre));
        holder.txtArtista.setText(cursor.getString(colArtista));

        new ObtenerPortadas(cursor.getInt(colID), holder.imgVwPortada, 200, false).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, activity);
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        public TextView txtAlbum;
        public TextView txtArtista;
        public ImageView imgVwPortada;
        private Activity activity;
        private long idAlbum;

        public ViewHolder(View view, Activity activity) {
            super(view);
            this.activity = activity;
            txtAlbum = (TextView) view.findViewById(R.id.txtTitulo);
            txtArtista = (TextView) view.findViewById(R.id.txtArtista);
            imgVwPortada = (ImageView) view.findViewById(R.id.imgVwPortada);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(activity, CancionesAlbum.class);
            intent.putExtra("album", txtAlbum.getText().toString());
            intent.putExtra("artista", txtArtista.getText().toString());
            activity.startActivity(intent);
        }

        @Override
        public boolean onLongClick(View v) {
            Dialogos.agregarALista(activity, R.string.txt_agregar_album_a_lista, txtAlbum.getText().toString(), idAlbum, Constantes.TIP_AGR_ALBUM);
            return false;
        }

    }

}



