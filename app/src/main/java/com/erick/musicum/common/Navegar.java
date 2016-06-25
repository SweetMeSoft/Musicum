package com.erick.musicum.common;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.erick.musicum.R;
import com.erick.musicum.common.objects.Constantes;
import com.erick.musicum.common.objects.Datos;
import com.erick.musicum.main.MainActivity;

/**
 * Created by ErickSteven on 26/07/2015.
 */
public class Navegar {

    public static void irAInicio(final Activity actividad, final int posicion) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                Datos.getMusicService().setIndexActual(posicion);
                Datos.getMusicService().start();
                Intent intent = new Intent(actividad.getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                FragmentManager fragmentManager = Datos.getMainActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.container, Datos.getFragmentMain()).commitAllowingStateLoss();
                if (!(actividad instanceof MainActivity)) {
                    actividad.finish();
                }
            }
        }).start();
    }

    public static void irACancion(long idCancion, Cursor cursor) {
        cursor.moveToFirst();
        int posicion = 0;
        do {
            if (cursor.getLong(cursor.getColumnIndex(Constantes.CAN_ID)) == idCancion) {
                break;
            } else {
                posicion++;
            }
        } while (cursor.moveToNext());
        //new Intent()
    }

    public static void mostrarVista(final Context context, final View vistaAMostrar, final View vistaAOcultar) {
        int animationDuration = context.getResources().getInteger(android.R.integer.config_mediumAnimTime);
        vistaAMostrar.setAlpha(0f);
        vistaAMostrar.setVisibility(View.VISIBLE);
        vistaAMostrar.animate().alpha(1f).setDuration(animationDuration).setListener(null);
        vistaAOcultar.animate().alpha(0f).setDuration(animationDuration).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                vistaAOcultar.setVisibility(View.GONE);
            }
        });
    }

}
