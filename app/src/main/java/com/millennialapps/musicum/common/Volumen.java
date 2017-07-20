package com.millennialapps.musicum.common;

import android.media.MediaPlayer;

/**
 * Created by ErickSteven on 3/4/2016.
 */
public class Volumen {

    public static boolean modificando = false;
    public static float volumen = 1.0f;

    public static void subirVolumen(final MediaPlayer mediaPlayer) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                modificando = true;
                while (volumen < 1.0f) {
                    volumen += 0.1f;
                    mediaPlayer.setVolume(volumen, volumen);
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                modificando = false;
            }
        }).start();
    }

    public static void bajarVolumen(final MediaPlayer mediaPlayer) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                modificando = true;
                while (volumen > 0.1f) {
                    volumen -= 0.1f;
                    mediaPlayer.setVolume(volumen, volumen);
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                mediaPlayer.pause();
                modificando = false;
            }
        }).start();

    }

}
