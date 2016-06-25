package com.erick.musicum.fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.erick.musicum.R;
import com.erick.musicum.common.ModificarVistas;
import com.erick.musicum.common.ObtenerDatos;
import com.erick.musicum.common.objetos.Datos;

/**
 * Created by ErickSteven on 22/06/2015.
 */
public class FragmentMain extends Fragment {


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_main, container, false);
        FragmentManager fragmentManager = getChildFragmentManager();

        FrameLayout frameControles = (FrameLayout) rootView.findViewById(R.id.containerControles);
        FrameLayout frameReproductor = (FrameLayout) rootView.findViewById(R.id.containerReproductor);

        fragmentManager.beginTransaction().replace(R.id.containerPortadas, new Portada()).commit();
        fragmentManager.beginTransaction().replace(R.id.containerReproductor, Datos.getReproductor()).commit();
        fragmentManager.beginTransaction().replace(R.id.containerControles, Datos.getControles()).commit();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ModificarVistas.sumarMargenes(frameReproductor, 0, ObtenerDatos.alturaStatusBar(getActivity()), 0, 0);
            ModificarVistas.setMargenes(frameControles, 0, 0, 0, ObtenerDatos.alturaNavigationBar(getActivity()));
        }

        Datos.setFragmentMain(this);
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }

}
