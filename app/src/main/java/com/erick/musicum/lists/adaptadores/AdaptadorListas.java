package com.erick.musicum.lists.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.erick.musicum.R;
import com.erick.musicum.common.ObtenerCursores;
import com.erick.musicum.common.ObtenerIds;
import com.erick.musicum.common.objects.Constantes;
import com.erick.musicum.lists.CancionesLista;

public class AdaptadorListas extends RecyclerView.Adapter<AdaptadorListas.ViewHolder> {

    private final Context context;
    private final int colID;
    private final int colNombre;
    private Cursor cursor;

    public AdaptadorListas(final Context context, final Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
        colID = cursor.getColumnIndex(Constantes.LIS_ID);
        colNombre = cursor.getColumnIndex(Constantes.LIS_NOMBRE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista, parent, false);
        ViewHolder holder = new ViewHolder(view, context);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        cursor.moveToPosition(position);
        final Cursor cancionesCursor = ObtenerCursores.listaCancionesLista(context, Constantes.NO_RANDOM_CANCIONES_LISTA,
                ObtenerIds.idLista(context, cursor.getString(colNombre)), "");
        holder.txtLista.setText(cursor.getString(colNombre));
        holder.txtCantidad.setText(cancionesCursor.getCount() + " canciones.");
        cancionesCursor.close();
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final Context context;
        public TextView txtLista;
        public TextView txtCantidad;

        public ViewHolder(View view, final Context context) {
            super(view);
            this.context = context;
            txtLista = (TextView) view.findViewById(R.id.txtLista);
            txtCantidad = (TextView) view.findViewById(R.id.txtCantidad);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, CancionesLista.class);
            intent.putExtra("lista", txtLista.getText());
            context.startActivity(intent);
        }

    }
}



