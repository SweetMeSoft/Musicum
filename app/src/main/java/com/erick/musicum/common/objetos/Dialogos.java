package com.erick.musicum.common.objetos;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.erick.musicum.R;
import com.erick.musicum.common.ManejarListas;
import com.erick.musicum.common.ObtenerCursores;
import com.erick.musicum.lists.adaptadores.AdaptadorListasDialogo;

/**
 * Created by ErickSteven on 3/1/2016.
 */
public class Dialogos {

    public static AlertDialog.Builder nuevaLista(final Context context, final EditText edTxt,
                                                 DialogInterface.OnClickListener eventoOk, DialogInterface.OnClickListener eventoCancelar) {
        AlertDialog.Builder alerta = new AlertDialog.Builder(context);
        alerta.setTitle(context.getResources().getString(R.string.txt_nueva_lista));
        alerta.setMessage(context.getResources().getString(R.string.txt_ingrese_nombre_lista));
        alerta.setPositiveButton(context.getResources().getString(R.string.btn_guardar), eventoOk);
        alerta.setNegativeButton(context.getResources().getString(R.string.btn_cancelar), eventoCancelar);
        alerta.setView(edTxt);
        alerta.show();
        return alerta;
    }

    public static AlertDialog.Builder agregarALista(final Activity activity, final int titulo, final String tituloCancion, final long id, final int tipoAgregacion) {
        final AlertDialog.Builder alerta = new AlertDialog.Builder(activity);
        alerta.setTitle(activity.getResources().getString(titulo));
        alerta.setMessage(tituloCancion);
        alerta.setPositiveButton(R.string.nueva_lista, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final EditText edTxt = new EditText(activity);
                Dialogos.nuevaLista(activity, edTxt,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                long nuevoId = ManejarListas.nuevaLista(activity, edTxt);
                                ManejarListas.elegirAgregacion(activity, tipoAgregacion, id, nuevoId);
                            }
                        }, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        }
                );
            }
        });

        LayoutInflater inflater = activity.getLayoutInflater();
        View rootView = inflater.inflate(R.layout.dialog_listas, null);
        alerta.setView(rootView);

        final AlertDialog miAlerta = alerta.create();

        final Cursor cursor = ObtenerCursores.listaListas(activity, Constantes.NO_RANDOM_LISTAS);
        ListView lstListas = (ListView) rootView.findViewById(R.id.lstListas);
        AdaptadorListasDialogo adaptador = new AdaptadorListasDialogo(activity, cursor, id, tipoAgregacion, miAlerta);
        lstListas.setAdapter(adaptador);
        miAlerta.show();

        return alerta;
    }
}
