package com.millennialapps.musicum.common;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.millennialapps.musicum.widget.Widget4x1;

/**
 * Created by ErickSteven on 10/1/2016.
 */
public class Widgets {

    public static void update(Context context) {
        Intent intent = new Intent(context, Widget4x1.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int ids[] = AppWidgetManager.getInstance(context).getAppWidgetIds(new ComponentName(context, Widget4x1.class));
        //intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        context.sendBroadcast(intent);
    }
}
