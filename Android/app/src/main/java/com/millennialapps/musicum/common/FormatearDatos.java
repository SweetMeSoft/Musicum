package com.millennialapps.musicum.common;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

import java.util.concurrent.TimeUnit;

/**
 * Created by ErickSteven on 08/07/2015.
 */
public class FormatearDatos {

    public static String millisToString(long millis) {
        String resultado;
        long minutos = TimeUnit.MILLISECONDS.toMinutes(millis);
        long segundos = TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis));
        if (segundos < 10) {
            resultado = String.format("%d:0%d", minutos, segundos);
        } else {
            resultado = String.format("%d:%d", minutos, segundos);
        }
        return resultado;
    }

    public static String obtenerKbps(int bitrate) {
        String resultado;
        int total = bitrate / 1000;
        resultado = total + " Kbps";
        return resultado;
    }

    public static String obtenerTrack(String track) {
        if(track.length() >= 4){
            track = track.substring(track.length()-3);
        }
        track = track.replaceFirst ("^0*", "");
        return track;
    }

    public static Bitmap obtenerBlurBitmap(Bitmap bitmap, Activity actividad) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            RenderScript rs = RenderScript.create(actividad.getApplicationContext());
            ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
            Allocation allIn = Allocation.createFromBitmap(rs, bitmap);
            Allocation allOut = Allocation.createFromBitmap(rs, outBitmap);
            blurScript.setRadius(25.f);
            blurScript.setInput(allIn);
            blurScript.forEach(allOut);
            allOut.copyTo(outBitmap);
            rs.destroy();
            return outBitmap;
        } else {
            return bitmap;
        }
    }

}
