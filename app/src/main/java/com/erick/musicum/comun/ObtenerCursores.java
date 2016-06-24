package com.erick.musicum.comun;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.erick.musicum.comun.objetos.Columnas;
import com.erick.musicum.comun.objetos.Constantes;
import com.erick.musicum.comun.objetos.Datos;
import com.erick.musicum.comun.objetos.Preferencias;
import com.erick.musicum.servicios.MusicService;
import com.erick.musicum.sqlite.SQLiteManager;

/**
 * Created by erick.musicum on 03/10/2015.
 */
public class ObtenerCursores {

    private static String obtenerRandom(int random) {
        switch (random) {
            case Constantes.NO_RANDOM_CANCIONES:
            case Constantes.NO_RANDOM_CANCIONES_GENERO:
            case Constantes.NO_RANDOM_CANCIONES_ARTISTA:
                return Constantes.CAN_ORDEN;
            case Constantes.NO_RANDOM_ARTISTAS:
                return Constantes.ART_ORDEN;
            case Constantes.NO_RANDOM_ALBUMES:
                return Constantes.ALB_ORDEN;
            case Constantes.NO_RANDOM_GENEROS:
                return Constantes.GEN_ORDEN;
            case Constantes.NO_RANDOM_LISTAS:
            case Constantes.NO_RANDOM_CANCIONES_LISTA:
                return Constantes.LIS_ORDEN;
            case Constantes.NO_RANDOM_CANCIONES_ALBUM:
                return Constantes.CAN_TRACK;
            case Constantes.NO_RANDOM_CANCIONES_CARPETA:
                return Constantes.CAN_DATA;
            case Constantes.NO_RANDOM_ALBUMES_ARTISTA:
                return Constantes.ALB_ANO;
            default:
                return Constantes.RANDOM;
        }
    }

    public static Cursor listaDefault(final Context context) {
        Cursor cursor = context.getContentResolver().query(Constantes.uriCanciones,
                Columnas.canciones, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        return cursor;
    }

    public static Cursor seleccionarLista(final Context context, final int seleccion) {
        Cursor cursor;
        switch (seleccion) {
            case 1:
                cursor = todasCanciones(context, Preferencias.obtenerRandom(context));
                break;
            case 2:
                cursor = listaCancionesAlbum(context, Preferencias.obtenerRandom(context), Preferencias.obtenerArtista(context), Preferencias.obtenerAlbum(context), "");
                break;
            case 3:
                cursor = listaCancionesArtista(context, Preferencias.obtenerRandom(context), Preferencias.obtenerArtista(context));
                break;
            case 4:
                cursor = listaCancionesGenero(context, Preferencias.obtenerRandom(context), Preferencias.obtenerGenero(context));
                break;
            default:
                cursor = todasCanciones(context, Preferencias.obtenerRandom(context));
                break;
        }
        cursor.moveToFirst();
        return cursor;
    }

    public static Cursor listaInicial(int random) {
        /*switch (random) {
            case 0:
                cursor = Datos.getSqLiteManager().getDb().query(Constantes.TABLA_LISTA_CANCION_PSE + ", " + Constantes.TABLA_CANCIONES_PSE,
                        columnasCancionesInicialesPse, Constantes.CAN_ID_CAN + " = " + Constantes.CAN_ID_LCA + " AND " + Constantes.LCA_ID_LISTA + " = "
                                + Preferencias.obtenerIDLista(), null, null, null, Constantes.CAN_TITULO_CAN);
                break;
            case 1:
                cursor = Datos.getSqLiteManager().getDb().query(Constantes.TABLA_LISTA_CANCION_PSE + ", " + Constantes.TABLA_CANCIONES_PSE,
                        columnasCancionesInicialesPse, Constantes.CAN_ID_CAN + " = " + Constantes.CAN_ID_LCA + " AND " + Constantes.LCA_ID_LISTA + " = "
                                + Preferencias.obtenerIDLista(), null, null, null, "Random()");
                break;
            default:
                cursor = null;
                break;
        }*/
        Cursor cursor = null;
        return cursor;
    }

    public static Cursor cancionActual(final Context context) {
        Cursor cursor = context.getContentResolver().query(Constantes.uriCanciones, Columnas.canciones, Constantes.CAN_ID
                + " = " + Preferencias.obtenerIdActual(context), null, null, null);
        return cursor;
    }

    public static Cursor todasCanciones(final Context context, final int random) {
        Cursor cursor;
        cursor = context.getContentResolver().query(Constantes.uriCanciones, Columnas.canciones,
                null, null, obtenerRandom(random));
        return cursor;
    }

    public static Cursor listaArtistas(final Context context, final int random, final String busqueda) {
        Cursor cursor;
        cursor = context.getContentResolver().query(Constantes.uriArtistas, Columnas.artistas,
                Constantes.ART_NOMBRE + " LIKE '%" + busqueda + "%'", null, obtenerRandom(random));
        return cursor;
    }

    public static Cursor listaAlbumes(final Context context, final int random, final String busqueda) {
        Cursor cursor;
        cursor = context.getContentResolver().query(Constantes.uriAlbumes, Columnas.albumes,
                Constantes.ALB_NOMBRE + " LIKE '%" + busqueda + "%'", null, obtenerRandom(random));
        return cursor;
    }

    public static Cursor listaGeneros(final Context context, final int random) {
        Cursor cursor;
        cursor = context.getContentResolver().query(Constantes.uriGeneros, Columnas.generos,
                null, null, obtenerRandom(random));
        return cursor;
    }

    public static Cursor listaCancionesGenero(final Context context, final int random, final String genero) {
        Cursor cursor;
        final long idGenero = ObtenerIds.idGenero(context, genero);
        final Uri uri = MediaStore.Audio.Genres.Members.getContentUri("external", idGenero);
        cursor = context.getContentResolver().query(uri, Columnas.canciones, null, null, obtenerRandom(random));
        return cursor;
    }

    public static Cursor listaCancionesGenero(final Context context, final int random, final long idGenero) {
        Cursor cursor;
        final Uri uri = MediaStore.Audio.Genres.Members.getContentUri("external", idGenero);
        cursor = context.getContentResolver().query(uri, Columnas.canciones, null, null, obtenerRandom(random));
        return cursor;
    }

    public static Cursor listaAlbumesArtista(final Context context, final int random, final String artista, final String busqueda) {
        Cursor cursor = context.getContentResolver().query(Constantes.uriAlbumes, Columnas.albumes,
                Constantes.ALB_ARTISTA + " = '" + artista + "' AND " + Constantes.ALB_NOMBRE + " LIKE '%" + busqueda + "%'", null, obtenerRandom(random));
        return cursor;
    }

    public static Cursor listaCancionesArtista(final Context context, final int random, final String artista) {
        Cursor cursor;
        cursor = context.getContentResolver().query(Constantes.uriCanciones, Columnas.canciones,
                Constantes.CAN_ARTISTA + " = '" + artista + "'", null, obtenerRandom(random));
        return cursor;
    }

    public static Cursor listaCancionesArtista(final Context context, final int random, final long idArtista) {
        final String artista = ObtenerIds.nombreArtista(context, idArtista);
        return listaCancionesArtista(context, random, artista);
    }

    public static Cursor listaCancionesAlbum(final Context context, final int random, final String artista, final String album, final String busqueda) {
        Cursor cursor;
        cursor = context.getContentResolver().query(Constantes.uriCanciones, Columnas.canciones,
                Constantes.CAN_ARTISTA + " LIKE '" + artista + "' AND " + Constantes.CAN_ALBUM + " LIKE '" + album
                        + "' AND " + Constantes.CAN_TITULO + " LIKE '%" + busqueda + "%'", null, obtenerRandom(random));
        return cursor;
    }

    public static Cursor listaCancionesAlbum(final Context context, final int random, final long idAlbum, final String busqueda) {
        final String album = ObtenerIds.nombreAlbum(context, idAlbum);
        final String artista = ObtenerIds.nombreArtistaAlbum(context, idAlbum);
        return listaCancionesAlbum(context, random, artista, album, busqueda);
    }

    public static Cursor cancionesCarpetaSub(final Context context, final String ruta) {
        Cursor cursor = context.getContentResolver().query(Constantes.uriCanciones, new String[]{Constantes.CAN_DATA},
                Constantes.CAN_DATA + " LIKE ?", new String[]{ruta + "%"}, obtenerRandom(Constantes.NO_RANDOM_CANCIONES_CARPETA));
        if (cursor != null && cursor.getCount() > 0) {
            return cursor;
        }
        return null;
    }

    public static Cursor listaCancionesCarpeta(final Context context, final int random, final String ruta) {
        Cursor cursor = context.getContentResolver().query(Constantes.uriCanciones, Columnas.canciones,
                Constantes.CAN_DATA + " LIKE ? AND " + Constantes.CAN_DATA + " NOT LIKE ?",
                new String[]{"%" + ruta + "%", "%" + ruta + "%/%"}, obtenerRandom(random));
        if (cursor == null || cursor.getCount() == 0) {
            return Datos.getCursorActual();
        }
        return cursor;
    }

    public static Cursor listaListas(final Context context, int random) {
        Cursor cursor;
        cursor = new SQLiteManager(context).getDb().query(Constantes.TABLA_LISTAS, Columnas.listas,
                null, null, null, null, obtenerRandom(random), null);
        return cursor;
    }

    public static Cursor listaCancionesLista(final Context context, final int random, final long idLista, final String busqueda) {
        Cursor cursor;
        cursor = new SQLiteManager(context).getDb().query(Constantes.TABLA_LISTA_CANCION, Columnas.listaCancion,
                Constantes.LIS_ID + " = " + idLista + " AND " + Constantes.CAN_TITULO + " LIKE '%" + busqueda + "%'",
                null, null, null, obtenerRandom(random), null);
        return cursor;
    }

    public static Cursor obtenerCancion(final Context context, final Long id) {
        Cursor cursor = context.getContentResolver().query(Constantes.uriCanciones, Columnas.canciones,
                Constantes.CAN_ID + " = " + id, null, Constantes.CAN_ORDEN);
        return cursor;
    }

    public static Cursor busquedaCanciones(final Context context, final String texto) {
        Cursor cursor = context.getContentResolver().query(Constantes.uriCanciones, Columnas.canciones,
                Constantes.CAN_TITULO + " LIKE '%" + texto + "%'", null, Constantes.CAN_ORDEN);
        return cursor;
    }

    public static Cursor listaActual(final Context context) {
        if (Datos.getCursorActual() != null) {
            return Datos.getCursorActual();
        } else {
            Cursor cursor = new SQLiteManager(context).getDb().query(Constantes.TABLA_LISTA_ACTUAL, Columnas.listaActual, null,
                    null, null, null, null, null);
            if(cursor.getCount() > 0) {
                return cursor;
            }else{
                return listaDefault(context);
            }
        }
    }
}
