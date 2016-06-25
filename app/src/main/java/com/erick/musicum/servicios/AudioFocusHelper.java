package com.erick.musicum.servicios;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;

public class AudioFocusHelper implements AudioManager.OnAudioFocusChangeListener {

    private boolean esperaReinicio = false;
    private float volumen = 1.0f;
    private MediaPlayer mediaPlayer;
    private Context context;

    public AudioFocusHelper(final Context context, final MediaPlayer mediaPlayer) {
        this.context = context;
        this.mediaPlayer = mediaPlayer;
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        audioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
    }

    @Override
    public void onAudioFocusChange(int focusChange) {
        switch (focusChange) {
            case AudioManager.AUDIOFOCUS_GAIN:
                if (mediaPlayer == null) {
                    Intent intent = new Intent(context, MusicService.class);
                    context.startService(intent);
                } else {
                    if (!mediaPlayer.isPlaying() && esperaReinicio) {
                        mediaPlayer.start();
                        esperaReinicio = false;
                    }
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (volumen < 1.0f) {
                            volumen += 0.1f;
                            mediaPlayer.setVolume(volumen, volumen);
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
                break;

            case AudioManager.AUDIOFOCUS_LOSS:
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    esperaReinicio = true;
                }
                mediaPlayer.release();
                mediaPlayer = null;
                break;

            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    esperaReinicio = true;
                }
                break;

            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                if (mediaPlayer.isPlaying()) {
                    volumen = 1.0f;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while (volumen > 0.1f) {
                                volumen -= 0.1f;
                                mediaPlayer.setVolume(volumen, volumen);
                                try {
                                    Thread.sleep(50);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }).start();
                }
                break;
        }
    }
}