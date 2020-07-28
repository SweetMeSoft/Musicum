package com.millennialapps.musicum.common.objects;

/**
 * Created by ErickSteven on 2/1/2016.
 */
public interface Columnas {

    String[] canciones = new String[]{
            Constantes.CAN_ID,
            Constantes.CAN_TITULO,
            Constantes.CAN_ARTISTA,
            Constantes.CAN_ALBUM,
            Constantes.CAN_DURACION,
            Constantes.CAN_TRACK,
            Constantes.CAN_DATA
    };
    String[] artistas = new String[]{
            Constantes.ART_ID,
            Constantes.ART_NOMBRE,
            Constantes.ART_CANCIONES,
            Constantes.ART_ALBUMES,
    };
    String[] albumes = new String[]{
            Constantes.ALB_ID,
            Constantes.ALB_NOMBRE,
            Constantes.ALB_ARTISTA,
            Constantes.ALB_CANCIONES,
            Constantes.ALB_ANO,
    };
    String[] generos = new String[]{
            Constantes.GEN_ID,
            Constantes.GEN_NOMBRE,
    };
    String[] listas = new String[]{
            Constantes._ID,
            Constantes.LIS_ID,
            Constantes.LIS_NOMBRE
    };
    String[] listaCancion = new String[]{
            Constantes.LIS_ID,
            Constantes.CAN_ID,
            Constantes.CAN_TITULO,
            Constantes.CAN_ARTISTA,
            Constantes.CAN_ALBUM,
            Constantes.CAN_DURACION
    };
    String[] listaActual = new String[]{
            Constantes.CAN_ID
    };
}
