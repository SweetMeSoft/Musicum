package com.erick.musicum.common;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.TypedValue;

import com.erick.musicum.R;
import com.erick.musicum.common.cache.Cache;
import com.erick.musicum.common.cache.SimpleDiskCache;
import com.erick.musicum.common.objetos.Constantes;
import com.erick.musicum.common.objetos.Datos;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by ErickSteven on 07/07/2015.
 */
public class ObtenerDatos {


    private static SimpleDiskCache diskCache;

    public static ArrayList<Long> crearListaActual(Cursor cursor) {
        ArrayList<Long> canciones = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            int columnaID = cursor.getColumnIndex(Constantes.CAN_ID);
            do {
                long thisId = cursor.getLong(columnaID);
                canciones.add(thisId);
            }
            while (cursor.moveToNext());
        }
        Datos.setCursorActual(cursor);
        return canciones;
    }

    public static void randomizarListaActual(final Context context) {
        /*int position = MusicService.getIndexActual();
        long id = Datos.getMusicSrv().getCanciones().get(position);
        Datos.getMusicSrv().setCanciones(crearListaActual(ObtenerCursores.seleccionarLista(context, Preferencias.obtenerTipoLista())));
        Datos.getCursorActual().moveToFirst();
        switch (Preferencias.obtenerRandom()) {
            case 0:
                for (int i = 0; i < Datos.getCursorActual().getCount(); i++) {
                    if (Datos.getCursorActual().getLong(Datos.getCursorActual().getColumnIndex(Constantes.CAN_ID)) == id) {
                        position = i;
                        break;
                    }
                    Datos.getCursorActual().moveToNext();
                }
                MusicService.setIndexActual(position);
                //Datos.setIdActual();
                Datos.getReproductor().mostrarInfoCancion();
                break;
            case 1:
                Datos.getMusicSrv().getCanciones().remove(id);
                Datos.getMusicSrv().getCanciones().add(position, id);
                break;
        }*/
    }

    public static Bitmap obtenerBitmap(final Context context, final long idAlbum, final int tamano) {
        setDiskCache(context);
        String imageKey = idAlbum + "" + tamano;

        Bitmap bitmap = Cache.getBitmapFromMemCache(imageKey);
        if (bitmap != null) {
            return bitmap;
        } else {
            bitmap = diskCache.getBitmap(imageKey);
            if (bitmap != null) {
                return bitmap;
            } else {
                if (idAlbum != 0) {
                    Uri trackUri = ContentUris.withAppendedId(Constantes.uriAlbumesArt, idAlbum);
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), trackUri);
                    } catch (IOException e) {
                        return null;
                    }
                    bitmap = ReducirImagenes.reducirBitmap(tamano, tamano, bitmap);
                } else {
                    bitmap = ReducirImagenes.reducirDrawable(context, tamano, tamano, R.drawable.default_music);
                }
                Cache.addBitmapToMemoryCache(imageKey, bitmap);
                diskCache.put(imageKey, bitmap);
                return bitmap;
            }
        }
    }

    public static Bitmap obtenerBitmapBlur(Activity actividad, long idAlbum, int tamano) {
        setDiskCache(actividad);
        String imageKey = "blur" + idAlbum + "" + tamano;

        Bitmap bitmap = Cache.getBitmapFromMemCache(imageKey);
        if (bitmap != null) {
            return bitmap;
        } else {
            bitmap = diskCache.getBitmap(imageKey);
            if (bitmap != null) {
                return bitmap;
            } else {
                if (idAlbum != 0) {
                    Uri trackUri = ContentUris.withAppendedId(Constantes.uriAlbumesArt, idAlbum);
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(actividad.getContentResolver(), trackUri);
                    } catch (IOException e) {
                        return null;
                    }
                    bitmap = FormatearDatos.obtenerBlurBitmap(ReducirImagenes.reducirBitmap(tamano, tamano, bitmap), actividad);
                } else {
                    bitmap = FormatearDatos.obtenerBlurBitmap(ReducirImagenes.reducirDrawable(actividad, tamano, tamano, R.drawable.default_music), actividad);
                }
                Cache.addBitmapToMemoryCache(imageKey, bitmap);
                diskCache.put(imageKey, bitmap);
                return bitmap;
            }
        }
    }

    public static int alturaStatusBar(Activity actividad) {
        int altura = 0;
        int resourceId = actividad.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            altura = actividad.getResources().getDimensionPixelSize(resourceId);
        }
        return altura;
    }

    public static int alturaNavigationBar(Activity actividad) {
        int id = actividad.getResources().getIdentifier("config_showNavigationBar", "bool", "android");
        int altura = 0;
        int resourceId = actividad.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0 && (id > 0 && actividad.getResources().getBoolean(id))) {
            altura = actividad.getResources().getDimensionPixelSize(resourceId);
        }
        //return altura;
        return 0;
    }

    public static int alturaToolbar(final Context context) {
        return (int) context.getResources().getDimension(android.support.design.R.dimen.abc_action_bar_default_height_material);
    }

    public static int atributo(final Context context, final int atributo) {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(atributo, typedValue, true);
        return typedValue.data;
    }

    private static void setDiskCache(Context context){
        if(diskCache == null){
            diskCache = new SimpleDiskCache(context);
        }
    }
}
