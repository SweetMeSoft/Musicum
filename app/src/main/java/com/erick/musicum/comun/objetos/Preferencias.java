package com.erick.musicum.comun.objetos;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import com.erick.musicum.comun.ObtenerIds;

import java.util.Map;
import java.util.Set;

/**
 * Created by ErickSteven on 03/10/2015.
 */
public class Preferencias {

    public static void guardarDato(final Context context, String llave, String valor){
        SharedPreferences.Editor editor = context.getSharedPreferences(Constantes.PREFERENCIAS, Context.MODE_PRIVATE).edit();
        editor.putString(llave, valor);
        editor.commit();
    }
    public static void guardarDato(final Context context, String llave, boolean valor){
        SharedPreferences.Editor editor = context.getSharedPreferences(Constantes.PREFERENCIAS, Context.MODE_PRIVATE).edit();
        editor.putBoolean(llave, valor);
        editor.commit();
    }
    public static void guardarDato(final Context context, String llave, int valor){
        SharedPreferences.Editor editor = context.getSharedPreferences(Constantes.PREFERENCIAS, Context.MODE_PRIVATE).edit();
        editor.putInt(llave, valor);
        editor.commit();
    }
    public static void guardarDato(final Context context, String llave, long valor){
        SharedPreferences.Editor editor = context.getSharedPreferences(Constantes.PREFERENCIAS, Context.MODE_PRIVATE).edit();
        editor.putLong(llave, valor);
        editor.commit();
    }
    public static void eliminarPreferencias(final Context context){
        SharedPreferences.Editor editor = context.getSharedPreferences(Constantes.PREFERENCIAS, Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.commit();
    }

    public static String obtenerDato(final Context context, String llave) {
        return context.getSharedPreferences(Constantes.PREFERENCIAS, Context.MODE_PRIVATE).getString(llave, null);
    }
    public static int obtenerRandom(final Context context) {
        return context.getSharedPreferences(Constantes.PREFERENCIAS, Context.MODE_PRIVATE).getInt(Constantes.VALOR_RANDOM, 0);
    }
    public static int obtenerIDLista(final Context context) {
        return context.getSharedPreferences(Constantes.PREFERENCIAS, Context.MODE_PRIVATE).getInt(Constantes.VALOR_ID_LISTA, 0);
    }
    public static boolean obtenerPrimeraVez(final Context context) {
        return context.getSharedPreferences(Constantes.PREFERENCIAS, Context.MODE_PRIVATE).getBoolean(Constantes.VALOR_PRIMERA_VEZ, true);
    }
    public static int obtenerTipoLista(final Context context) {
        return context.getSharedPreferences(Constantes.PREFERENCIAS, Context.MODE_PRIVATE).getInt(Constantes.VALOR_TIPO_LISTA, 0);
    }
    public static String obtenerArtista(final Context context) {
        return context.getSharedPreferences(Constantes.PREFERENCIAS, Context.MODE_PRIVATE).getString(Constantes.VALOR_ARTISTA, "");
    }
    public static String obtenerAlbum(final Context context) {
        return context.getSharedPreferences(Constantes.PREFERENCIAS, Context.MODE_PRIVATE).getString(Constantes.VALOR_ALBUM, "");
    }
    public static String obtenerGenero(final Context context) {
        return context.getSharedPreferences(Constantes.PREFERENCIAS, Context.MODE_PRIVATE).getString(Constantes.VALOR_GENERO, "");
    }
    public static long obtenerIdActual(final Context context) {
        long id = context.getSharedPreferences(Constantes.PREFERENCIAS, Context.MODE_PRIVATE).getLong(Constantes.VALOR_ID_ACTUAL, 0);
        if(id != 0){
            return id;
        }else {
            return ObtenerIds.primerId(context);
        }
    }
    public static int obtenerIndexActual(final Context context) {
        return context.getSharedPreferences(Constantes.PREFERENCIAS, Context.MODE_PRIVATE).getInt(Constantes.VALOR_INDEX_ACTUAL, 0);
    }
    public static long obtenerPosicionActual(final Context context) {
        return context.getSharedPreferences(Constantes.PREFERENCIAS, Context.MODE_PRIVATE).getLong(Constantes.VALOR_POSICION_ACTUAL, 0);
    }
    public static String obtenerValorRuta(final Context context) {
        return context.getSharedPreferences(Constantes.PREFERENCIAS, Context.MODE_PRIVATE).getString(Constantes.VALOR_RUTA, Constantes.ROOT);
    }
    public static int obtenerEstado(final Context context) {
        return context.getSharedPreferences(Constantes.PREFERENCIAS, Context.MODE_PRIVATE).getInt(Constantes.VALOR_ESTADO, Constantes.ESTADO_DETENIDO);
    }
}
