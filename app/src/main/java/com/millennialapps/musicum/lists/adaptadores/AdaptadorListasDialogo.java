package com.millennialapps.musicum.lists.adaptadores;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.millennialapps.musicum.R;
import com.millennialapps.musicum.common.ManejarListas;
import com.millennialapps.musicum.common.ObtenerCursores;
import com.millennialapps.musicum.common.ObtenerIds;
import com.millennialapps.musicum.common.objects.Constantes;

public class AdaptadorListasDialogo extends BaseAdapter {

    private static LayoutInflater inflater = null;
    final int colID;
    final int colNombre;
    private Context context;
    private Cursor cursor;
    private AlertDialog dialogo;
    private long id;
    private int tipoAgregacion;

    public AdaptadorListasDialogo(Context context, Cursor cursor, long id, final int tipoAgregacion, final AlertDialog dialogo) {
        this.context = context;
        this.cursor = cursor;
        this.dialogo = dialogo;
        this.id = id;
        this.tipoAgregacion = tipoAgregacion;
        this.colID = cursor.getColumnIndex(Constantes.LIS_ID);
        this.colNombre = cursor.getColumnIndex(Constantes.LIS_NOMBRE);
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return cursor.getCount();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        cursor.moveToPosition(position);
        if (convertView == null) {
            view = inflater.inflate(R.layout.item_lista, null);
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cursor.moveToPosition(position);
                dialogo.dismiss();
                ManejarListas.elegirAgregacion(context, tipoAgregacion, id, cursor.getLong(colID));
                cursor.close();
            }
        });

        TextView txtLista = (TextView) view.findViewById(R.id.txtLista);
        TextView txtCantidad = (TextView) view.findViewById(R.id.txtCantidad);
        ImageView imgVwPortada = (ImageView) view.findViewById(R.id.imgVwPortada);

        final Cursor cancionesCursor = ObtenerCursores.listaCancionesLista(context, Constantes.NO_RANDOM_CANCIONES_LISTA,
                ObtenerIds.idLista(context, cursor.getString(colNombre)), "");
        txtLista.setText(cursor.getString(colNombre));
        txtCantidad.setText(cancionesCursor.getCount() + " canciones.");
        cancionesCursor.close();
        return view;
    }

}