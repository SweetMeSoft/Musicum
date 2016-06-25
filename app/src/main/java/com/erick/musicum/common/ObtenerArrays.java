package com.erick.musicum.common;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.erick.musicum.common.objetos.Cancion;
import com.erick.musicum.common.objetos.Constantes;
import com.erick.musicum.common.objetos.Datos;
import com.erick.musicum.sqlite.SQLiteManager;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Created by ErickSteven on 5/1/2016.
 */
public class ObtenerArrays {

    public static ArrayList<Cancion> cancionesActual(final Context context, final String titulo) {
        final ArrayList<Cancion> canciones = new ArrayList<>();
        Datos.getCursorActual().moveToFirst();
        final ContentValues valores = new ContentValues();
        do {
            if (Datos.getCursorActual().getString(Datos.getCursorActual().getColumnIndex(Constantes.CAN_TITULO)).contains(titulo)) {
                final Cancion cancion = new Cancion();
                cancion.setId(Datos.getCursorActual().getLong(Datos.getCursorActual().getColumnIndex(Constantes.CAN_ID)));
                cancion.setTitulo(Datos.getCursorActual().getString(Datos.getCursorActual().getColumnIndex(Constantes.CAN_TITULO)));
                cancion.setArtista(Datos.getCursorActual().getString(Datos.getCursorActual().getColumnIndex(Constantes.CAN_ARTISTA)));
                cancion.setAlbum(Datos.getCursorActual().getString(Datos.getCursorActual().getColumnIndex(Constantes.CAN_ALBUM)));
                cancion.setDuracion(Datos.getCursorActual().getLong(Datos.getCursorActual().getColumnIndex(Constantes.CAN_DURACION)));
                canciones.add(cancion);
                valores.put(Constantes.CAN_ID, cancion.getId());
                new SQLiteManager(context).getDb().insert(Constantes.TABLA_LISTA_ACTUAL, null, valores);
            }
        } while (Datos.getCursorActual().moveToNext());
        return canciones;
    }


    public static List<File> listaCarpetas(final Context context, final String root) {
        File raiz = new File(root);
        ArrayList<File> archivos = new ArrayList<>();
        File[] files = raiz.listFiles();

        Arrays.sort(files, new Comparator<File>() {
            @Override
            public int compare(File object1, File object2) {
                return object1.getName().compareTo(object2.getName());
            }
        });
        if (!root.equals(Constantes.ROOT)) {
            archivos.add(0, new File(root, "Atrás"));
        }

        for (File file : files) {
            if (file.isDirectory()) {
                if (file.canRead() && file.canExecute() && !file.isHidden()) {
                    final Cursor cursor = ObtenerCursores.cancionesCarpetaSub(context, file.getPath());
                    if(cursor != null) {
                        cursor.close();
                        archivos.add(file);
                    }
                }
            } else {
                for (String tipo : Constantes.TIPOS_ARCHIVOS) {
                    if (file.getName().toLowerCase().endsWith(tipo)) {
                        archivos.add(file);
                        break;
                    }
                }
            }
        }

        return archivos;
    }

}
