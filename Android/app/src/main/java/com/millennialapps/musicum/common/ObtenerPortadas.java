package com.millennialapps.musicum.common;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

/**
 * Created by ErickSteven on 26/12/2015.
 */

public class ObtenerPortadas extends AsyncTask<Activity, Void, Bitmap> {

    private long idAlbum;
    private ImageView imv;
    private String path;
    private int tamano;
    private boolean blur;

    public ObtenerPortadas(long idAlbum, ImageView imv, int tamano, boolean blur) {
        this.idAlbum = idAlbum;
        this.imv = imv;
        this.path = imv.getTag().toString();
        this.tamano = tamano;
        this.blur = blur;
    }

    @Override
    protected Bitmap doInBackground(Activity... params) {
        WeakReference<Bitmap> wrBitmap;
        Bitmap bitmap;
        try {
            if (imv.getTag().toString().equals(path)) {
                if (blur) {
                    bitmap = ObtenerDatos.obtenerBitmapBlur(params[0], idAlbum, tamano);
                    if (bitmap != null) {
                        wrBitmap = new WeakReference<>(bitmap);
                    } else {
                        return null;
                    }
                } else {
                    bitmap = ObtenerDatos.obtenerBitmap(params[0], idAlbum, tamano);
                    if (bitmap != null) {
                        wrBitmap = new WeakReference<>(bitmap);
                    } else {
                        return null;
                    }
                }
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
        return wrBitmap.get();
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);
        if (!imv.getTag().toString().equals(path)) {
            return;
        }
        if (imv != null) {
            if (result != null) {
                imv.setImageBitmap(result);
                imv.setAlpha(0f);
                imv.setVisibility(View.VISIBLE);
                imv.animate().alpha(1f).setDuration(200).setListener(null);
            }
        }
    }
}