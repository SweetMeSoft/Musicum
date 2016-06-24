package com.erick.musicum.comun;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;

import com.erick.musicum.R;
import com.erick.musicum.comun.objetos.Datos;

/**
 * Created by ErickSteven on 30/12/2015.
 */
public class ModificarVistas {

    public static void setMargenes(View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            if (left < 0) {
                left = params.leftMargin;
            }
            if (top < 0) {
                top = params.topMargin;
            }
            if (right < 0) {
                right = params.rightMargin;
            }
            if (bottom < 0) {
                bottom = params.bottomMargin;
            }
            params.setMargins(left, top, right, bottom);
            view.requestLayout();
        }
    }

    public static void sumarMargenes(View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            params.setMargins(params.leftMargin + left, params.topMargin + top, params.rightMargin + right, params.bottomMargin + bottom);
            view.requestLayout();
        }
    }

    public static void sumarDimensiones(View view, int alto, int ancho) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            params.height = params.height + alto;
            params.width = params.width + ancho;
            view.requestLayout();
        }
    }


    public static void comenzarAnimacionAlpha(final Context context, final View view, final int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f) : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(context.getResources().getInteger(R.integer.tiempo_animacion_alpha));
        alphaAnimation.setFillAfter(true);
        view.startAnimation(alphaAnimation);
    }

}
