package com.erick.musicum.listas.adaptadores;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.erick.musicum.R;
import com.erick.musicum.common.objetos.Constantes;
import com.erick.musicum.common.objetos.Dialogos;
import com.erick.musicum.listas.CancionesGenero;

public class AdaptadorGeneros extends RecyclerView.Adapter<AdaptadorGeneros.ViewHolder> {

    private Cursor cursor;
    private final int colID;
    private final int colGenero;
    private Activity actividad;

    public AdaptadorGeneros(Cursor cursor, Activity actividad) {
        this.cursor = cursor;
        this.actividad = actividad;
        colID = cursor.getColumnIndex(Constantes.GEN_ID);
        colGenero = cursor.getColumnIndex(Constantes.GEN_NOMBRE);
    }

    @Override
    public AdaptadorGeneros.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_genero, parent, false);
        ViewHolder vh = new ViewHolder(v, actividad);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        cursor.moveToPosition(position);
        holder.txtGenero.setText(cursor.getString(colGenero));
        holder.idGenero = cursor.getLong(colID);
        //holder.txtCantidad.setText("Canciones: "+cursor.getString(0));
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        public TextView txtGenero;
        public TextView txtCantidad;
        private long idGenero;
        private Activity actividad;

        public ViewHolder(View view, Activity activity) {
            super(view);
            this.actividad = activity;
            txtGenero = (TextView) view.findViewById(R.id.txtGenero);
            txtCantidad = (TextView) view.findViewById(R.id.txtCantidad);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(actividad, CancionesGenero.class);
            intent.putExtra("genero", txtGenero.getText().toString());
            actividad.startActivity(intent);
        }

        @Override
        public boolean onLongClick(View v) {
            Dialogos.agregarALista(actividad, R.string.txt_agregar_genero_a_lista, txtGenero.getText().toString(), idGenero, Constantes.TIP_AGR_GENERO);
            return false;
        }
    }
}



