package com.erick.musicum.common;

import android.content.Context;
import android.content.Intent;

import com.erick.musicum.common.objects.Constantes;
import com.erick.musicum.services.MusicService;

/**
 * Created by ErickSteven on 5/2/2016.
 */
public class DispararIntents {

    public static void reproducir(Context context) {
        context.startService(new Intent(context, MusicService.class).setAction(Constantes.ACCION_REPRODUCIR));
    }

    public static void siguiente(Context context) {
        context.startService(new Intent(context, MusicService.class).setAction(Constantes.ACCION_SIGUIENTE));
    }

    public static void anterior(Context context) {
        context.startService(new Intent(context, MusicService.class).setAction(Constantes.ACCION_ANTERIOR));
    }

    public static void irACancion(final Context context, final int posicion) {
        context.startService(new Intent(context, MusicService.class).setAction(Constantes.ACCION_IR_A_CANCION)
                .putExtra(Constantes.BUNDLE_KEY_INDEX_ACTUAL, posicion));
    }

    public static void iniciarServicio(Context context) {
        context.startService(new Intent(context, MusicService.class).setAction(Constantes.ACCION_INICIAR));
    }
}
