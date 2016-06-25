package com.erick.musicum.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.RemoteViews;

import com.erick.musicum.R;
import com.erick.musicum.common.DispararIntents;
import com.erick.musicum.common.ObtenerDatos;
import com.erick.musicum.common.ObtenerIds;
import com.erick.musicum.common.objetos.Columnas;
import com.erick.musicum.common.objetos.Constantes;
import com.erick.musicum.common.objetos.Datos;
import com.erick.musicum.common.objetos.Preferencias;
import com.erick.musicum.main.InitialPage;

public class Widget4x1 extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget4x1);

        final long idCancion = Preferencias.obtenerIdActual(context);
        final long idAlbum = ObtenerIds.idAlbum(context, idCancion);
        final Bitmap bitmap = ObtenerDatos.obtenerBitmap(context, idAlbum, 300);
        final Cursor cursor = context.getContentResolver().query(Constantes.uriCanciones, Columnas.canciones, Constantes.CAN_ID + " = " + idCancion, null, null);//ObtenerCursores.cancionActual(context);
        if (cursor.moveToFirst()) {
            if (bitmap != null) {
                remoteViews.setImageViewBitmap(R.id.imgWidget, bitmap);
            } else {
                remoteViews.setImageViewBitmap(R.id.imgWidget, Datos.getPortadaDefault());
            }
            remoteViews.setTextViewText(R.id.txtTitulo, cursor.getString(cursor.getColumnIndex(Constantes.CAN_TITULO)));
            remoteViews.setTextViewText(R.id.txtArtista, cursor.getString(cursor.getColumnIndex(Constantes.CAN_ARTISTA)));
        }
        if (Preferencias.obtenerEstado(context) != Constantes.ESTADO_REPRODUCIENDO) {
            remoteViews.setImageViewResource(R.id.imgBtnPlay, R.drawable.play);
        } else {
            remoteViews.setImageViewResource(R.id.imgBtnPlay, R.drawable.pause);
        }

        remoteViews.setImageViewResource(R.id.imgBtnNext, R.drawable.next);
        remoteViews.setImageViewResource(R.id.imgBtnPrev, R.drawable.prev);

        Intent openIntent = new Intent(context, InitialPage.class);
        openIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds);
        openIntent.setAction(Constantes.WIDGET_ABRIR);
        openIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        openIntent.setData(Uri.parse(openIntent.toUri(Intent.URI_INTENT_SCHEME)));
        remoteViews.setOnClickPendingIntent(R.id.imgWidget, PendingIntent.getActivity(context, 0, openIntent, PendingIntent.FLAG_UPDATE_CURRENT));

        Intent playIntent = new Intent(context, Widget4x1.class);
        playIntent.setAction(Constantes.WIDGET_PLAY);
        remoteViews.setOnClickPendingIntent(R.id.imgBtnPlay, PendingIntent.getBroadcast(context, 0, playIntent, 0));

        Intent nextIntent = new Intent(context, Widget4x1.class);
        nextIntent.setAction(Constantes.WIDGET_NEXT);
        remoteViews.setOnClickPendingIntent(R.id.imgBtnNext, PendingIntent.getBroadcast(context, 0, nextIntent, 0));

        Intent prevIntent = new Intent(context, Widget4x1.class);
        prevIntent.setAction(Constantes.WIDGET_PREV);
        remoteViews.setOnClickPendingIntent(R.id.imgBtnPrev, PendingIntent.getBroadcast(context, 0, prevIntent, 0));

        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        switch (action) {
            case AppWidgetManager.ACTION_APPWIDGET_DELETED:
                final int appWidgetId = intent.getExtras().getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
                        AppWidgetManager.INVALID_APPWIDGET_ID);
                if (appWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
                    this.onDeleted(context, new int[]{appWidgetId});
                }
                break;
            case Constantes.WIDGET_ABRIR:

                break;
            case Constantes.WIDGET_PLAY:
                DispararIntents.reproducir(context);
                break;
            case Constantes.WIDGET_NEXT:
                DispararIntents.siguiente(context);
                break;
            case Constantes.WIDGET_PREV:
                DispararIntents.anterior(context);
                break;
        }
        super.onReceive(context, intent);
    }
}