package com.millennialapps.musicum.common.objects;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.millennialapps.musicum.fragments.Controles;
import com.millennialapps.musicum.fragments.FragmentMain;
import com.millennialapps.musicum.fragments.Portada;
import com.millennialapps.musicum.fragments.Reproductor;
import com.millennialapps.musicum.lists.CancionesActual;
import com.millennialapps.musicum.activities.MainActivity;
import com.millennialapps.musicum.services.MusicService;

/**
 * Created by erick.musicum on 15/06/2015.
 */
public class Datos {

    //private static SQLiteManager sqLiteManager;

    private static final LruCache<String, Bitmap> mMemoryCache = new LruCache<String, Bitmap>(Constantes.MEMORIA_CACHE) {
        @Override
        protected int sizeOf(String key, Bitmap bitmap) {
            return bitmap.getByteCount() / 1024;
        }
    };
    private static Long idPrev;
    private static Long idActual;
    private static Long idNext;
    private static MusicService musicService;
    private static Cursor cursorActual;
    //private static AppCompatActivity actividadActual;
    private static Cursor cursorCancionActual;
    private static boolean musicBound = false;
    private static boolean puedeCorrer = false;
    private static FragmentMain fragmentMain;
    private static CancionesActual fragmentCancionesActual;
    private static Reproductor reproductor;
    private static Controles controles;
    private static MainActivity mainActivity;
    private static Bitmap portadaDefault;
    private static Bitmap portadaDefaultBlur;

    private static int clicks = 0;
    private static boolean inClicks = false;
    private static Portada portada;


    /*public static SQLiteManager getSqLiteManager() {
        return sqLiteManager;
    }

    public static void setSqLiteManager(SQLiteManager sqLiteManager) {
        Datos.sqLiteManager = sqLiteManager;
    }

    public static Long getIdPrev() {
        return idPrev;
    }

    public static void setIdPrev() {
        if (Datos.indexActual == 0) {
            Datos.idPrev = MusicService.getCanciones().get(MusicService.getCanciones().size() - 1);
        } else {
            Datos.idPrev = MusicService.getCanciones().get(Datos.indexActual - 1);
        }
    }

    public static Long getIdActual() {
        return idActual;
    }

    public static void setIdActual() {
        Datos.idActual = MusicService.getCanciones().get(Datos.indexActual);
    }

    public static Long getIdNext() {
        return idNext;
    }

    public static void setIdNext() {
        if (Datos.indexActual == MusicService.getCanciones().size() - 1) {
            Datos.idNext = MusicService.getCanciones().get(0);
        } else {
            Datos.idNext = MusicService.getCanciones().get(Datos.indexActual + 1);
        }
    }*/


    public static MusicService getMusicService() {
        return musicService;
    }

    public static void setMusicService(MusicService musicService) {
        Datos.musicService = musicService;
    }

    public static Cursor getCursorActual() {
        return cursorActual;
    }

    public static void setCursorActual(Cursor cursor) {
        Datos.cursorActual = cursor;
    }

    /*public static AppCompatActivity getActividadActual() {
        return actividadActual;
    }

    public static void setActividadActual(AppCompatActivity actividadActual) {
        Datos.actividadActual = actividadActual;
    }*/


    public static FragmentMain getFragmentMain() {
        return fragmentMain;
    }

    public static void setFragmentMain(FragmentMain fragmentMain) {
        Datos.fragmentMain = fragmentMain;
    }

    public static CancionesActual getFragmentCancionesActual() {
        return fragmentCancionesActual;
    }

    public static void setFragmentCancionesActual(CancionesActual fragmentCancionesActual) {
        Datos.fragmentCancionesActual = fragmentCancionesActual;
    }

    public static Reproductor getReproductor() {
        return reproductor;
    }

    public static void setReproductor(Reproductor reproductor) {
        Datos.reproductor = reproductor;
    }

    public static boolean isMusicBound() {
        return musicBound;
    }

    public static void setMusicBound(boolean musicBound) {
        Datos.musicBound = musicBound;
    }

    public static boolean isPuedeCorrer() {
        return puedeCorrer;
    }

    public static void setPuedeCorrer(boolean puedeCorrer) {
        Datos.puedeCorrer = puedeCorrer;
    }

    public static Controles getControles() {
        return controles;
    }

    public static void setControles(Controles controles) {
        Datos.controles = controles;
    }

    public static MainActivity getMainActivity() {
        return mainActivity;
    }

    public static void setMainActivity(MainActivity mainActivity) {
        Datos.mainActivity = mainActivity;
    }

    //Portadas
    public static Bitmap getPortadaDefault() {
        return portadaDefault;
    }

    public static void setPortadaDefault(Bitmap portadaDefault) {
        Datos.portadaDefault = portadaDefault;
    }

    public static Bitmap getPortadaDefaultBlur() {
        return portadaDefaultBlur;
    }

    public static void setPortadaDefaultBlur(Bitmap portadaDefaultBlur) {
        Datos.portadaDefaultBlur = portadaDefaultBlur;
    }

    public static Cursor getCursorCancionActual() {
        return cursorCancionActual;
    }

    public static void setCursorCancionActual(Cursor cursorCancionActual) {
        Datos.cursorCancionActual = cursorCancionActual;
    }


    public static int getClicks() {
        return clicks;
    }

    public static void setClicks(int clicks) {
        Datos.clicks = clicks;
    }

    public static boolean isInClicks() {
        return inClicks;
    }

    public static void setInClicks(boolean inClicks) {
        Datos.inClicks = inClicks;
    }

    public static Portada getPortada() {
        return portada;
    }

    public static void setPortada(Portada portada) {
        Datos.portada = portada;
    }

    public static LruCache<String, Bitmap> getmMemoryCache() {
        return mMemoryCache;
    }

    public static int getEstado(final Context context) {
        return Preferencias.obtenerEstado(context);
    }

}
