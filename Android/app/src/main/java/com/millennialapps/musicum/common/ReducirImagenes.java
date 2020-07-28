package com.millennialapps.musicum.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

/**
 * Created by ErickSteven on 26/07/2015.
 */
public class ReducirImagenes {

    public static Bitmap reducirDrawable(final Context context, final int reqHeight, final int reqWidth, final int recursoImagen) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), recursoImagen, options);
        int height = options.outHeight;
        int width = options.outWidth;
        options.inSampleSize = obtenerSampleSize(width, reqWidth, height, reqHeight);
        options.inJustDecodeBounds = false;
        Bitmap outBitmap = Bitmap.createBitmap(reqHeight, reqHeight, Bitmap.Config.ARGB_8888);
        outBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), recursoImagen, options), reqWidth, reqHeight, true);
        return outBitmap;
    }

    public static Bitmap reducirArrayBits(final int reqHeight, final int reqWidth, byte[] imagen){

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(imagen, 0, imagen.length, options);
        int height = options.outHeight;
        int width = options.outWidth;

        options.inSampleSize = obtenerSampleSize(width, reqWidth, height, reqHeight);
        options.inJustDecodeBounds = false;
        Bitmap outBitmap = Bitmap.createBitmap(reqHeight, reqHeight, Bitmap.Config.ARGB_8888);
        outBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeByteArray(imagen, 0, imagen.length, options), reqWidth, reqHeight, true);
        return outBitmap;
//        return Bitmap.createScaledBitmap(BitmapFactory.decodeByteArray(imagen, 0, imagen.length, options), reqWidth, reqHeight, true);
    }
    public static Bitmap reducirBitmap(final int reqHeight, final int reqWidth, Bitmap imagen){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        imagen.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length, options);

        int height = options.outHeight;
        int width = options.outWidth;

        options.inSampleSize = obtenerSampleSize(width, reqWidth, height, reqHeight);
        options.inJustDecodeBounds = false;
        Bitmap outBitmap = Bitmap.createBitmap(reqHeight, reqHeight, Bitmap.Config.ARGB_8888);
        outBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length, options), reqWidth, reqHeight, true);
        return outBitmap;
//        return Bitmap.createScaledBitmap(BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length, options), reqWidth, reqHeight, true);
    }

    private static int obtenerSampleSize(int width, int reqWidth, int height, int reqHeight){
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

}
