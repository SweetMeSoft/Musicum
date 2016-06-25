package com.erick.musicum.common;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.widget.EditText;

import com.erick.musicum.R;
import com.erick.musicum.common.objects.Constantes;
import com.erick.musicum.sqlite.SQLiteManager;

import java.util.Random;

/**
 * Created by ErickSteven on 3/1/2016.
 */
public class ManejarListas {

    public static void elegirAgregacion(final Context context, final int tipoAgregacion, final long id, final long idLista) {
        switch (tipoAgregacion) {
            case Constantes.TIP_AGR_CANCION:
                ManejarListas.agregarCancion(context, id, idLista);
                break;
            case Constantes.TIP_AGR_ARTISTA:
                ManejarListas.agregarArtista(context, id, idLista);
                break;
            case Constantes.TIP_AGR_ALBUM:
                ManejarListas.agregarAlbum(context, id, idLista);
                break;
            case Constantes.TIP_AGR_GENERO:
                ManejarListas.agregarGenero(context, id, idLista);
                break;
        }
    }

    public static void agregarCancion(final Context context, final long idCancion, final long idLista) {
        if (!new SQLiteManager(context).cancionEnLista(idCancion, idLista)) {
            final ContentValues valores = new ContentValues();
            final Cursor cursor = ObtenerCursores.obtenerCancion(context, idCancion);
            if (cursor != null && cursor.moveToFirst()) {
                valores.put(Constantes.CAN_ID, idCancion);
                valores.put(Constantes.LIS_ID, idLista);
                valores.put(Constantes.CAN_TITULO, cursor.getString(cursor.getColumnIndex(Constantes.CAN_TITULO)));
                valores.put(Constantes.CAN_ARTISTA, cursor.getString(cursor.getColumnIndex(Constantes.CAN_ARTISTA)));
                valores.put(Constantes.CAN_ALBUM, cursor.getString(cursor.getColumnIndex(Constantes.CAN_ALBUM)));
                valores.put(Constantes.CAN_DURACION, FormatearDatos.millisToString(cursor.getLong(cursor.getColumnIndex(Constantes.CAN_DURACION))));
                new SQLiteManager(context).getDb().insert(Constantes.TABLA_LISTA_CANCION, null, valores);
                valores.clear();
                cursor.close();
            }
        }
    }

    public static void agregarArtista(final Context context, final long idArtista, final long idLista) {
        final Cursor cursor = ObtenerCursores.listaCancionesArtista(context, 0, idArtista);
        if (cursor != null && cursor.moveToFirst()) {
            final ProgressDialog progress = new ProgressDialog(context);
            progress.setMax(cursor.getCount());
            new AgregarCanciones(context, progress, idLista).execute(cursor);
        }
    }

    public static void agregarAlbum(final Context context, final long idAlbum, final long idLista) {
        final Cursor cursor = ObtenerCursores.listaCancionesAlbum(context, 0, idAlbum, "");
        if (cursor != null && cursor.moveToFirst()) {
            final ProgressDialog progress = new ProgressDialog(context);
            progress.setMax(cursor.getCount());
            new AgregarCanciones(context, progress, idLista).execute(cursor);
        }
    }

    public static void agregarGenero(final Context context, final long idGenero, final long idLista) {
        final Cursor cursor = ObtenerCursores.listaCancionesGenero(context, 0, idGenero);
        if (cursor != null && cursor.moveToFirst()) {
            final ProgressDialog progress = new ProgressDialog(context);
            progress.setMax(cursor.getCount());
            new AgregarCanciones(context, progress, idLista).execute(cursor);
        }
    }

    public static void eliminarCancion(final Context context, final long idCancion, final long idLista) {
        new SQLiteManager(context).getDb().delete(Constantes.TABLA_LISTA_CANCION,
                Constantes.CAN_ID + " = " + idCancion + " AND " + Constantes.LIS_ID + " = " + idLista, null);
    }

    public static void eliminarLista(final Context context, final long idLista) {
        new SQLiteManager(context).getDb().delete(Constantes.TABLA_LISTA_CANCION, Constantes.LIS_ID + " = " + idLista, null);
        new SQLiteManager(context).getDb().delete(Constantes.TABLA_LISTAS, Constantes.LIS_ID + " = " + idLista, null);
    }

    public static long nuevaLista(final Context context, final EditText edTxt) {
        ContentValues valores = new ContentValues();
        final Random rand = new Random();
        long idLista = rand.nextLong() + 1;
        valores.put(Constantes.LIS_ID, idLista);
        valores.put(Constantes.LIS_NOMBRE, edTxt.getText().toString());
        new SQLiteManager(context).getDb().insert(Constantes.TABLA_LISTAS, null, valores);
        return idLista;
    }

    public static class AgregarCanciones extends AsyncTask<Cursor, Void, Cursor> {

        private ProgressDialog progress;
        private long idLista;
        private Context context;

        public AgregarCanciones(final Context context, final ProgressDialog progress, final long idLista) {
            this.progress = progress;
            this.idLista = idLista;
            this.context = context;
        }

        @Override
        public void onPreExecute() {
            progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progress.setProgress(0);
            progress.setMessage(context.getResources().getString(R.string.agregando_canciones));
            progress.show();
        }

        @Override
        public void onPostExecute(Cursor result) {
            result.close();
            progress.dismiss();
        }

        @Override
        protected Cursor doInBackground(Cursor... params) {
            do {
                agregarCancion(context, params[0].getLong(params[0].getColumnIndex(Constantes.CAN_ID)), idLista);
                progress.setProgress(progress.getProgress() + 1);
            } while (params[0].moveToNext());
            params[0].close();
            return params[0];
        }

    }
}
