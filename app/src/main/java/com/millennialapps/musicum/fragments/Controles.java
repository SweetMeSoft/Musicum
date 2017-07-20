package com.millennialapps.musicum.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.millennialapps.musicum.R;
import com.millennialapps.musicum.common.objects.Constantes;
import com.millennialapps.musicum.common.objects.Datos;
import com.millennialapps.musicum.common.objects.Preferencias;


/**
 * Created by ErickSteven on 15/06/2015.
 */
public class Controles extends Fragment {

    private ImageButton imgBtnPlay;
    private ImageButton imgBtnSiguiente;
    private ImageButton imgBtnPrev;
    private ImageButton imgBtnRewind;
    private ImageButton imgBtnForward;
    private ImageButton imgBtnRandom;
    private ImageButton imgBtnRepetir;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View controles = inflater.inflate(R.layout.fragment_main_controles, container, false);

        imgBtnPlay = (ImageButton) controles.findViewById(R.id.imgBtnPlay);
        imgBtnSiguiente = (ImageButton) controles.findViewById(R.id.imgBtnSiguiente);
        imgBtnPrev = (ImageButton) controles.findViewById(R.id.imgBtnAnterior);
        imgBtnRewind = (ImageButton) controles.findViewById(R.id.imgBtnRewind);
        imgBtnForward = (ImageButton) controles.findViewById(R.id.imgBtnForward);
        imgBtnRandom = (ImageButton) controles.findViewById(R.id.imgBtnRandom);
        imgBtnRepetir = (ImageButton) controles.findViewById(R.id.imgBtnRepetir);

        if (Datos.getEstado(getActivity()) == Constantes.ESTADO_REPRODUCIENDO) {
            imgBtnPlay.setImageResource(R.drawable.pause);
        }
        switch (Preferencias.obtenerRandom(getActivity())) {
            case 0:
                imgBtnRandom.setImageResource(R.drawable.random0);
                break;
            case 1:
                imgBtnRandom.setImageResource(R.drawable.random1);
                break;
        }

        Datos.setControles(this);

        return controles;
    }

    public void start() {
        Datos.getMusicService().start();
    }

    public void pause() {
        Datos.getMusicService().pause();
    }

    //    public int getDuration() {
//        if(Datos.getMusicSrv() != null && musicBound && isPlaying()){
//            return Datos.getMusicSrv().getDuration();
//        }
//        else{
//            return (int)Datos.getCancionActual().getMillis();
//        }
//    }
//    public int getCurrentPosition() {
//        if(Datos.getMusicSrv() != null && Datos.isMusicBound() && isPlaying()){
//            Datos.getCancionActual().setCurrentPosition(Datos.getMusicSrv().getCurrentPosition());
//        }
//        return (int)Datos.getCancionActual().getCurrentPosition();
//    }
    public void seekTo(int pos) {
        Datos.getMusicService().seek(pos);
    }

    public boolean isPlaying() {
        if (Datos.isMusicBound()) {
            return Datos.getMusicService().isPlaying();
        }
        return false;
    }

    public int getBufferPercentage() {
        return 0;
    }

    public boolean canPause() {
        return true;
    }

    public boolean canSeekBackward() {
        return true;
    }

    public boolean canSeekForward() {
        return true;
    }

    public int getAudioSessionId() {
        return 0;
    }


    public ImageButton getImgBtnPlay() {
        return imgBtnPlay;
    }

    public void setImgBtnPlay(ImageButton imgBtnPlay) {
        this.imgBtnPlay = imgBtnPlay;
    }

    public ImageButton getImgBtnSiguiente() {
        return imgBtnSiguiente;
    }

    public void setImgBtnSiguiente(ImageButton imgBtnSiguiente) {
        this.imgBtnSiguiente = imgBtnSiguiente;
    }

    public ImageButton getImgBtnPrev() {
        return imgBtnPrev;
    }

    public void setImgBtnPrev(ImageButton imgBtnPrev) {
        this.imgBtnPrev = imgBtnPrev;
    }

    public ImageButton getImgBtnRewind() {
        return imgBtnRewind;
    }

    public void setImgBtnRewind(ImageButton imgBtnRewind) {
        this.imgBtnRewind = imgBtnRewind;
    }

    public ImageButton getImgBtnForward() {
        return imgBtnForward;
    }

    public void setImgBtnForward(ImageButton imgBtnForward) {
        this.imgBtnForward = imgBtnForward;
    }

    public ImageButton getImgBtnRandom() {
        return imgBtnRandom;
    }

    public void setImgBtnRandom(ImageButton imgBtnRandom) {
        this.imgBtnRandom = imgBtnRandom;
    }

    public ImageButton getImgBtnRepetir() {
        return imgBtnRepetir;
    }

    public void setImgBtnRepetir(ImageButton imgBtnRepetir) {
        this.imgBtnRepetir = imgBtnRepetir;
    }
}
