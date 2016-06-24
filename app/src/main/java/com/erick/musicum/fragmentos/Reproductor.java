package com.erick.musicum.fragmentos;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.erick.musicum.R;
import com.erick.musicum.comun.FormatearDatos;
import com.erick.musicum.comun.ObtenerCursores;
import com.erick.musicum.comun.ObtenerIds;
import com.erick.musicum.comun.ObtenerPortadas;
import com.erick.musicum.comun.objetos.Constantes;
import com.erick.musicum.comun.objetos.Datos;
import com.erick.musicum.comun.objetos.Preferencias;
import com.erick.musicum.servicios.MusicService;


/**
 * Created by ErickSteven on 15/06/2015.
 */
public class Reproductor extends Fragment implements Runnable {

    private TextView txtTitulo;
    private TextView txtArtista;
    private TextView txtInicio;
    private TextView txtFin;
    private TextView txtData;
    private SeekBar skBProgreso;

    private Thread hilo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View reproductor = inflater.inflate(R.layout.fragment_main_reproductor, container, false);

        txtTitulo = (TextView) reproductor.findViewById(R.id.txtTitulo);
        txtArtista = (TextView) reproductor.findViewById(R.id.txtArtista);
        txtInicio = (TextView) reproductor.findViewById(R.id.txtInicio);
        txtFin = (TextView) reproductor.findViewById(R.id.txtFin);
        txtData = (TextView) reproductor.findViewById(R.id.txtData);
        skBProgreso = (SeekBar) reproductor.findViewById(R.id.skBProgreso);

        Datos.setReproductor(this);
        mostrarInfoCancion(getActivity());

        if (savedInstanceState == null) {
            hilo = new Thread(this);
            hilo.start();
            skBProgreso.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    long segundoActual;
                    if (skBProgreso.isPressed()) {
                        segundoActual = seekBar.getProgress();
                    } else {
                        segundoActual = Datos.getMusicService().getCurrentPosition();
                    }
                    long total = Datos.getCursorCancionActual().getLong(Datos.getCursorCancionActual().
                            getColumnIndex(Constantes.CAN_DURACION));
                    txtInicio.setText(FormatearDatos.millisToString(segundoActual));
                    txtFin.setText(FormatearDatos.millisToString(total - segundoActual));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    Datos.setPuedeCorrer(false);
                    long segundoActual = seekBar.getProgress();
                    long total = Datos.getCursorCancionActual().getLong(Datos.getCursorCancionActual().
                            getColumnIndex(Constantes.CAN_DURACION));
                    txtInicio.setText(FormatearDatos.millisToString(segundoActual));
                    txtFin.setText(FormatearDatos.millisToString(total - segundoActual));
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    Datos.setPuedeCorrer(true);
                    long segundoActual = seekBar.getProgress();
                    long total = Datos.getCursorCancionActual().getLong(Datos.getCursorCancionActual().
                            getColumnIndex(Constantes.CAN_DURACION));
                    txtInicio.setText(FormatearDatos.millisToString(segundoActual));
                    txtFin.setText(FormatearDatos.millisToString(total - segundoActual));

                    if (Datos.getMusicService().isPlaying()) {
                        Datos.getMusicService().seek((int) segundoActual);
                    }
                }
            });
        }
        return reproductor;
    }

    @Override
    public void run() {
        while (true) {
            if (Datos.getEstado(getActivity()) == Constantes.ESTADO_REPRODUCIENDO && Datos.isPuedeCorrer()) {
                skBProgreso.setProgress(Datos.getMusicService().getCurrentPosition());
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void mostrarInfoCancion(Context context) {
        final Cursor cursor = ObtenerCursores.cancionActual(getActivity());
        Datos.setCursorCancionActual(cursor);
        cursor.moveToFirst();
        skBProgreso.setProgress(0);

        final String cancion = cursor.getString(cursor.getColumnIndex(Constantes.CAN_TITULO));
        final String artista = cursor.getString(cursor.getColumnIndex(Constantes.CAN_ARTISTA));
        final String album = cursor.getString(cursor.getColumnIndex(Constantes.CAN_ALBUM));
        final long idAlbum = ObtenerIds.idAlbum(getActivity().getApplicationContext(), artista, album);

        txtTitulo.setText(cancion);
        txtArtista.setText(artista + " - " + album);
        txtInicio.setText("0:00");
        txtFin.setText(FormatearDatos.millisToString(cursor.getLong(cursor.getColumnIndex(Constantes.CAN_DURACION))));
        txtData.setText((ObtenerIds.indexActual(context) + 1) + " de " + Datos.getCursorActual().getCount());
        //txtData.setText(cursor.getString(cursor.getColumnIndex(Constantes.CAN_DATA)));

        skBProgreso.setMax((int) (cursor.getLong(cursor.getColumnIndex(Constantes.CAN_DURACION))));

        Datos.getMainActivity().getImgVwHeader().setTag(idAlbum);
        new ObtenerPortadas(idAlbum, Datos.getMainActivity().getImgVwHeader(), 500, false).execute(getActivity());
        Preferencias.guardarDato(context, Constantes.VALOR_ID_ACTUAL, cursor.getLong(cursor.getColumnIndex(Constantes.CAN_ID)));
        Preferencias.guardarDato(context, Constantes.VALOR_INDEX_ACTUAL, ObtenerIds.indexActual(context));

    }


}
