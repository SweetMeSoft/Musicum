package com.erick.musicum.comun.cache;

import android.graphics.Bitmap;

import com.erick.musicum.comun.objetos.Datos;

/**
 * Created by ErickSteven on 31/12/2015.
 */
public class Cache {

    public static void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            Datos.getmMemoryCache().put(key, bitmap);
        }
    }

    public static Bitmap getBitmapFromMemCache(String key) {
        return Datos.getmMemoryCache().get(key);
    }

}
