package com.millennialapps.musicum.common.cache;

import android.graphics.Bitmap;

import com.millennialapps.musicum.common.objects.Datos;

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
