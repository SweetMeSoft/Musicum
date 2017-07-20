package com.millennialapps.musicum.common;

import android.content.Context;
import android.database.Cursor;

import com.millennialapps.musicum.common.objects.Columnas;
import com.millennialapps.musicum.common.objects.Constantes;
import com.millennialapps.musicum.common.objects.Preferencias;
import com.millennialapps.musicum.sqlite.SQLiteManager;

/**
 * Created by ErickSteven on 2/1/2016.
 */
public class ObtenerIds {

    public static long idAlbum(final Context context, final String artista, final String album) {
        long id = 0;
        Cursor cursor = context.getContentResolver().query(Constantes.uriAlbumes, Columnas.albumes,
                Constantes.ALB_NOMBRE + " LIKE '" + album + "' AND " + Constantes.ALB_ARTISTA + " LIKE '" + artista + "'", null, Constantes.ALB_ORDEN);
        if (cursor != null && cursor.moveToFirst()) {
            id = cursor.getLong(cursor.getColumnIndex(Constantes.ALB_ID));
        }
        return id;
    }

    public static long idAlbum(final Context context, final Long idCancion) {
        long id = 0;
        Cursor cursorCancion = context.getContentResolver().query(Constantes.uriCanciones, Columnas.canciones,
                Constantes.CAN_ID + " = " + idCancion, null, Constantes.CAN_ORDEN);
        if (cursorCancion != null && cursorCancion.moveToFirst()) {
            String artista = cursorCancion.getString(cursorCancion.getColumnIndex(Constantes.CAN_ARTISTA));
            String album = cursorCancion.getString(cursorCancion.getColumnIndex(Constantes.CAN_ALBUM));
            Cursor cursorAlbum = context.getContentResolver().query(Constantes.uriAlbumes, Columnas.albumes,
                    Constantes.ALB_NOMBRE + " LIKE '" + album + "' AND " + Constantes.ALB_ARTISTA + " LIKE '" + artista + "'", null, Constantes.ALB_ORDEN);
            if (cursorAlbum != null && cursorAlbum.moveToFirst()) {
                id = cursorAlbum.getLong(cursorAlbum.getColumnIndex(Constantes.ALB_ID));
            }
        }
        return id;
    }

    public static long idGenero(final Context context, final String genero) {
        long id = 0;
        Cursor cursor = context.getContentResolver().query(Constantes.uriGeneros, Columnas.generos,
                Constantes.GEN_NOMBRE + " = '" + genero + "'", null, Constantes.GEN_ORDEN);
        if (cursor != null && cursor.moveToFirst()) {
            id = cursor.getLong(cursor.getColumnIndex(Constantes.GEN_ID));
        }
        return id;
    }

    public static long idLista(final Context context, final String lista) {
        long id = 0;
        Cursor cursor = new SQLiteManager(context).getDb().query(Constantes.TABLA_LISTAS, Columnas.listas,
                Constantes.LIS_NOMBRE + " LIKE '" + lista + "'", null, null, null, Constantes.LIS_ORDEN, null);
        if (cursor != null && cursor.moveToFirst()) {
            id = cursor.getLong(cursor.getColumnIndex(Constantes.LIS_ID));
        }
        return id;
    }

    public static String nombreArtista(final Context context, final long idArtista) {
        final Cursor cursor = context.getContentResolver().query(Constantes.uriArtistas, Columnas.artistas,
                Constantes.ART_ID + " = " + idArtista, null, Constantes.ART_ORDEN);
        if (cursor != null && cursor.moveToFirst()) {
            final String nombreArtista = cursor.getString(cursor.getColumnIndex(Constantes.ART_NOMBRE));
            cursor.close();
            return nombreArtista;
        }
        return "";
    }

    public static String nombreAlbum(final Context context, final long idAlbum) {
        final Cursor cursor = context.getContentResolver().query(Constantes.uriAlbumes, Columnas.albumes,
                Constantes.ALB_ID + " = " + idAlbum, null, Constantes.ALB_ORDEN);
        if (cursor != null && cursor.moveToFirst()) {
            final String nombreAlbum = cursor.getString(cursor.getColumnIndex(Constantes.ALB_NOMBRE));
            cursor.close();
            return nombreAlbum;
        }
        return "";
    }

    public static String nombreArtistaAlbum(final Context context, final long idAlbum) {
        final Cursor cursor = context.getContentResolver().query(Constantes.uriAlbumes, Columnas.albumes,
                Constantes.ALB_ID + " = " + idAlbum, null, Constantes.ALB_ORDEN);
        if (cursor != null && cursor.moveToFirst()) {
            final String nombreArtistaAlbum = cursor.getString(cursor.getColumnIndex(Constantes.ALB_ARTISTA));
            cursor.close();
            return nombreArtistaAlbum;
        }
        return "";
    }

    public static long primerId(final Context context) {
        long id = 0;
        final Cursor cursor = ObtenerCursores.todasCanciones(context, Constantes.NO_RANDOM_CANCIONES);
        if(cursor != null && cursor.moveToFirst()){
            id = cursor.getLong(cursor.getColumnIndex(Constantes.CAN_ID));
        }
        return id;
    }

    public static int indexActual(final Context context) {
        return Preferencias.obtenerIndexActual(context);
    }

}
