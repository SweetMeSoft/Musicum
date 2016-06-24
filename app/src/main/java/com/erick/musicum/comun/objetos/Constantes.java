package com.erick.musicum.comun.objetos;

import android.net.Uri;
import android.provider.MediaStore;

/**
 * Created by ErickSteven on 05/08/2015.
 */
public interface Constantes {

    int VERSION_DB = 1;
    String NOMBRE_DB = "music.sqlite";
    String TABLA_LISTAS = "mus_listas";
    String TABLA_LISTA_CANCION = "mus_lista_cancion";
    String TABLA_LISTA_ACTUAL = "mus_lista_actual";
    String _ID = "_id";

    Uri uriCanciones = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    Uri uriArtistas = MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI;
    Uri uriAlbumes = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
    Uri uriAlbumesArt = Uri.parse("content://media/external/audio/albumart");
    Uri uriGeneros = MediaStore.Audio.Genres.EXTERNAL_CONTENT_URI;
    String RANDOM = "Random()";

    String CAN_ID = MediaStore.Audio.Media._ID;
    String CAN_TITULO = MediaStore.Audio.Media.TITLE;
    String CAN_ARTISTA = MediaStore.Audio.Media.ARTIST;
    String CAN_ALBUM = MediaStore.Audio.Media.ALBUM;
    String CAN_DURACION = MediaStore.Audio.Media.DURATION;
    String CAN_TRACK = MediaStore.Audio.Media.TRACK;
    String CAN_DATA = MediaStore.Audio.Media.DATA;
    String CAN_ORDEN = MediaStore.Audio.Media.DEFAULT_SORT_ORDER;

    String ART_ID = MediaStore.Audio.Artists._ID;
    String ART_NOMBRE = MediaStore.Audio.Artists.ARTIST;
    String ART_CANCIONES = MediaStore.Audio.Artists.NUMBER_OF_TRACKS;
    String ART_ALBUMES = MediaStore.Audio.Artists.NUMBER_OF_ALBUMS;
    String ART_ORDEN = MediaStore.Audio.Artists.DEFAULT_SORT_ORDER;

    String ALB_ID = MediaStore.Audio.Albums._ID;
    String ALB_NOMBRE = MediaStore.Audio.Albums.ALBUM;
    String ALB_ARTISTA = MediaStore.Audio.Albums.ARTIST;
    String ALB_CANCIONES = MediaStore.Audio.Albums.NUMBER_OF_SONGS;
    String ALB_ANO = MediaStore.Audio.Albums.FIRST_YEAR;
    String ALB_ORDEN = MediaStore.Audio.Albums.DEFAULT_SORT_ORDER;

    String GEN_ID = MediaStore.Audio.Genres._ID;
    String GEN_NOMBRE = MediaStore.Audio.Genres.NAME;
    String GEN_ORDEN = MediaStore.Audio.Genres.DEFAULT_SORT_ORDER;

    String LIS_ID = "lis_id";
    String LIS_NOMBRE = "lis_nombre";
    String LIS_ORDEN = LIS_ID;

    String SENTENCIA_CREAR_TABLA_LISTAS = "CREATE TABLE " + TABLA_LISTAS
            + " (" + _ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
            + LIS_ID + " INTEGER NOT NULL, "
            + LIS_NOMBRE + " TEXT NOT NULL DEFAULT 'Lista de reproducción');";
    String SENTENCIA_CREAR_TABLA_LISTA_CANCIONES = "CREATE TABLE " + TABLA_LISTA_CANCION
            + " (" + CAN_ID + " INTEGER NOT NULL, "
            + LIS_ID + " INTENGER NOT NULL DEFAULT 1, "
            + CAN_TITULO + " TEXT NOT NULL, "
            + CAN_ARTISTA + " TEXT NOT NULL, "
            + CAN_ALBUM + " TEXT NOT NULL, "
            + CAN_DURACION + " TEXT NOT NULL)";
    String SENTENCIA_CREAR_TABLA_LISTA_ACTUAL = "CREATE TABLE " + TABLA_LISTA_ACTUAL
            + " (" + CAN_ID + " INTEGER NOT NULL)";

    String PREFERENCIAS = "MusicumPreferencias";
    String VALOR_PRIMERA_VEZ = "PrimeraVez";
    String VALOR_RANDOM = "Random";
    String VALOR_ID_LISTA = "IdLista";
    String VALOR_TIPO_LISTA = "TipoLista";
    String VALOR_ARTISTA = "Artista";
    String VALOR_ALBUM = "Album";
    String VALOR_GENERO = "Genero";
    String VALOR_ID_ACTUAL = "IdCancion";
    String VALOR_POSICION_ACTUAL = "PosicionActual";
    String VALOR_INDEX_ACTUAL = "IndexActual";
    String VALOR_RUTA = "Ruta";
    String VALOR_ESTADO = "Estado";

    int MEMORIA_CACHE = (int) ((Runtime.getRuntime().maxMemory() / 1024) / 8);

    String[] TIPOS_ARCHIVOS = new String[]{
            ".mp3",
            ".wav",
            ".aac",
            ".ogg"
    };
    String ROOT = "/";

    int TIP_AGR_CANCION = 1;
    int TIP_AGR_ARTISTA = 2;
    int TIP_AGR_ALBUM = 3;
    int TIP_AGR_GENERO = 4;
    int TIP_AGR_CARPETA = 5;

    int NO_RANDOM_CANCIONES = 0;
    int NO_RANDOM_ARTISTAS = 1;
    int NO_RANDOM_ALBUMES = 2;
    int NO_RANDOM_GENEROS = 3;
    int NO_RANDOM_LISTAS = 4;
    int NO_RANDOM_CANCIONES_ARTISTA = 5;
    int NO_RANDOM_CANCIONES_ALBUM = 6;
    int NO_RANDOM_CANCIONES_GENERO = 7;
    int NO_RANDOM_CANCIONES_CARPETA = 8;
    int NO_RANDOM_CANCIONES_LISTA = 9;
    int NO_RANDOM_ALBUMES_ARTISTA = 10;

    int FRAG_MAIN = 1;
    int FRAG_LISTA_ACTUAL = 2;
    int FRAG_TODAS_CANCIONES = 3;
    int FRAG_ARTISTAS = 4;
    int FRAG_ALBUMES = 5;
    int FRAG_GENEROS = 6;
    int FRAG_CARPETAS = 7;
    int FRAG_LISTAS = 8;

    float PORCENTAJE_MOSTRAR_TITULO = 0.9f;

    int ESTADO_DETENIDO = 0;
    int ESTADO_REPRODUCIENDO = 1;
    int ESTADO_PAUSA = 2;

    String WIDGET_PLAY = "widget_play";
    String WIDGET_NEXT = "widget_next";
    String WIDGET_PREV = "widget_prev";
    String WIDGET_ABRIR = "widget_abrir";

    String ACCION_INICIAR = "com.erick.musicum.INICIAR";
    String ACCION_REPRODUCIR = "com.erick.musicum.REPRODUCIR";
    String ACCION_PAUSAR = "com.erick.musicum.ACCION_PAUSAR";
    String ACCION_SIGUIENTE = "com.erick.musicum.SIGUIENTE";
    String ACCION_ANTERIOR = "com.erick.musicum.ANTERIOR";
    String ACCION_CREAR_LISTA = "com.erick.musicum.ACCION_CREAR_LISTA";
    String ACCION_IR_A_CANCION = "com.erick.musicum.ACCION_IR_A_CANCION";

    String BUNDLE_KEY_INDEX_ACTUAL = "BUNDLE_KEY_INDEX_ACTUAL";
}
