package com.erick.musicum.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.widget.Toast;

import com.erick.musicum.R;
import com.erick.musicum.common.ObtenerCursores;
import com.erick.musicum.common.ObtenerIds;
import com.erick.musicum.common.Volumen;
import com.erick.musicum.common.objects.Constantes;
import com.erick.musicum.common.objects.Datos;
import com.erick.musicum.common.objects.Preferencias;
import com.erick.musicum.main.MainActivity;

public class MusicService extends Service implements MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener {

    private final IBinder musicBind = new MusicBinder();
    private MediaPlayer mediaPlayer;

    @Override
    public void onCreate() {
        initMediaPlayer();
    }

    private final void initMediaPlayer() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnErrorListener(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getAction() != null) {
            switch (intent.getAction()) {
                case Constantes.ACCION_INICIAR:
                    initMediaPlayer();
                    new AudioFocusHelper(getApplicationContext(), mediaPlayer);
                    Datos.setCursorActual(getCursor());
                    Datos.setMusicService(this);
                    prepararCancion();
                    break;
                case Constantes.ACCION_REPRODUCIR:
                    if (Datos.getEstado(getApplicationContext()) == Constantes.ESTADO_REPRODUCIENDO) {
                        pause();
                    } else {
                        start();
                    }
                    break;
                case Constantes.ACCION_SIGUIENTE:
                    cambiarCancion(getIndexSiguiente());
                    break;
                case Constantes.ACCION_ANTERIOR:
                    cambiarCancion(getIndexAnterior());
                    break;
                case Constantes.ACCION_IR_A_CANCION:
                    Bundle bundle = intent.getExtras();
                    cambiarCancion(bundle.getInt(Constantes.BUNDLE_KEY_INDEX_ACTUAL));
                    break;
            }
        }
        return START_STICKY;
    }

    private void cambiarCancion(final int nuevoIndex) {
        if (mediaPlayer == null) {
            initMediaPlayer();
        }
        Datos.setPuedeCorrer(false);
        setIndexActual(nuevoIndex);
        if (Datos.getReproductor() != null) {
            Datos.getReproductor().mostrarInfoCancion(getApplicationContext());
        }
        prepararCancion();
        Datos.setPuedeCorrer(true);
    }

    @Override
    public void onDestroy() {
        Toast.makeText(getApplicationContext(), "Servicio terminado", Toast.LENGTH_LONG).show();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Datos.setPuedeCorrer(true);
        return musicBind;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Datos.setPuedeCorrer(false);
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {

        Intent notIntent = new Intent(this, MainActivity.class);
        notIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendInt = PendingIntent.getActivity(this, 0, notIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentIntent(pendInt)
                .setSmallIcon(R.drawable.play)
                .setTicker(getCursor().getString(getCursor().getColumnIndex(Constantes.CAN_TITULO)))
                .setOngoing(true)
                .setContentTitle(getCursor().getString(getCursor().getColumnIndex(Constantes.CAN_TITULO)))
                .setContentText(getCursor().getString(getCursor().getColumnIndex(Constantes.CAN_ARTISTA)));
        Notification not = builder.build();
        startForeground(1, not);

        if (Datos.getEstado(getApplicationContext()) == Constantes.ESTADO_REPRODUCIENDO) {
            mediaPlayer.start();
        }
        //Widgets.update(getApplicationContext());
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (getCurrentPosition() > 0) {
            cambiarCancion(getIndexSiguiente());
            if (Datos.getPortada() != null) {
                Datos.getPortada().getmPager().setCurrentItem(ObtenerIds.indexActual(getApplicationContext()), true);
                if (Datos.getFragmentCancionesActual() != null) {
                    Datos.getFragmentCancionesActual().getAdaptador().notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        mp.reset();
        return false;
    }

    public void prepararCancion() {

        mediaPlayer.reset();
        getCursor().moveToPosition(ObtenerIds.indexActual(getApplicationContext()));
        Uri trackUri = ContentUris.withAppendedId(Constantes.uriCanciones, getCursor().getLong(getCursor().getColumnIndex(Constantes.CAN_ID)));
        try {
            mediaPlayer.setDataSource(getApplicationContext(), trackUri);
        } catch (Exception e) {
        }
        mediaPlayer.prepareAsync();
    }

    public int getCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
    }

    public int getDuration() {
        return mediaPlayer.getDuration();
    }

    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    public void pause() {
        Volumen.bajarVolumen(mediaPlayer);
        setEstado(Constantes.ESTADO_PAUSA);
    }

    public void seek(int posn) {
        mediaPlayer.seekTo(posn);
    }

    public void start() {
        if (Datos.getEstado(getApplicationContext()) != Constantes.ESTADO_REPRODUCIENDO) {
            mediaPlayer.start();
        }
        Datos.setPuedeCorrer(true);
        setEstado(Constantes.ESTADO_REPRODUCIENDO);
        if (Volumen.volumen < 1.0) {
            Volumen.subirVolumen(mediaPlayer);
        }
    }

    public void setEstado(int estado) {
        if (Datos.getControles() != null) {
            switch (estado) {
                case Constantes.ESTADO_DETENIDO:
                case Constantes.ESTADO_PAUSA:
                    Datos.getControles().getImgBtnPlay().setImageResource(R.drawable.play);
                    Datos.getMainActivity().getFabHeader().setImageResource(R.drawable.play);
                    break;
                case Constantes.ESTADO_REPRODUCIENDO:
                    Datos.getControles().getImgBtnPlay().setImageResource(R.drawable.pause);
                    Datos.getMainActivity().getFabHeader().setImageResource(R.drawable.pause);
                    break;
            }
        }
        Preferencias.guardarDato(getApplicationContext(), Constantes.VALOR_ESTADO, estado);
    }

    public void setIndexActual(int indexActual) {
        Preferencias.guardarDato(getApplicationContext(), Constantes.VALOR_INDEX_ACTUAL, indexActual);
    }

    public int getIndexAnterior() {
        if (ObtenerIds.indexActual(getApplicationContext()) == 0) {
            return getCursor().getCount() - 1;
        } else {
            return ObtenerIds.indexActual(getApplicationContext()) - 1;
        }
    }

    public int getIndexSiguiente() {
        if (ObtenerIds.indexActual(getApplicationContext()) == getCursor().getCount() - 1) {
            return 0;
        } else {
            return ObtenerIds.indexActual(getApplicationContext()) + 1;
        }
    }

    public long getPosicionActual() {
        return Preferencias.obtenerPosicionActual(getApplicationContext());
    }

    public void setPosicionActual(long posicionActual) {
        Preferencias.guardarDato(getApplicationContext(), Constantes.VALOR_POSICION_ACTUAL, posicionActual);
    }

    public Cursor getCursor(){
        return ObtenerCursores.listaActual(getApplicationContext());
    }

    public class MusicBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }

}
