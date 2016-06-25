package com.erick.musicum.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.erick.musicum.common.objects.Datos;

/**
 * Created by ErickSteven on 29/07/2015.
 */
public class ConexionAudifonosReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
            if (intent.getIntExtra("state", -1) == 1) {
                Datos.getMusicService().start();
            }
        } else {
            if (intent.getAction().equals(android.media.AudioManager.ACTION_AUDIO_BECOMING_NOISY)) {
                Datos.getMusicService().pause();
            }
        }
    }
}
