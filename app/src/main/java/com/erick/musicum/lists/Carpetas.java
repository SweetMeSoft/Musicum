package com.erick.musicum.lists;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.erick.musicum.R;
import com.erick.musicum.common.ModificarVistas;
import com.erick.musicum.common.Navegar;
import com.erick.musicum.common.ObtenerArrays;
import com.erick.musicum.common.ObtenerDatos;
import com.erick.musicum.common.objects.Preferencias;
import com.erick.musicum.lists.adaptadores.AdaptadorCarpetas;

/**
 * Created by ErickSteven on 27/06/2015.
 */
public class Carpetas extends Fragment {

    private RecyclerView rclrVwLista;
    private RecyclerView.Adapter adaptador;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressBar cargando;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_carpetas, container, false);

        cargando = (ProgressBar) rootView.findViewById(R.id.loading_spinner);
        rclrVwLista = (RecyclerView) rootView.findViewById(R.id.lista_carpetas);

        rclrVwLista.setVisibility(View.GONE);
        rclrVwLista.setHasFixedSize(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ModificarVistas.sumarMargenes(rclrVwLista, 0, 0, 0, ObtenerDatos.alturaNavigationBar(getActivity()));
            ModificarVistas.sumarMargenes(rclrVwLista, 0, ObtenerDatos.alturaStatusBar(getActivity()), 0, 0);
        }
        new CargarLista(getActivity()).execute("");
        return rootView;
    }

    public class CargarLista extends AsyncTask<String, Void, Void> {

        private final Activity activity;

        public CargarLista(Activity activity) {
            this.activity = activity;
        }

        @Override
        protected Void doInBackground(String... params) {
            mLayoutManager = new LinearLayoutManager(getActivity());
            adaptador = new AdaptadorCarpetas(activity, ObtenerArrays.listaCarpetas(activity, Preferencias.obtenerValorRuta(getActivity())));
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            rclrVwLista.setLayoutManager(mLayoutManager);
            rclrVwLista.setAdapter(adaptador);
            Navegar.mostrarVista(activity, rclrVwLista, cargando);
        }
    }
}
