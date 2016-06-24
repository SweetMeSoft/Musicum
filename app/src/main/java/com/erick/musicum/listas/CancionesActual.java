package com.erick.musicum.listas;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.erick.musicum.R;
import com.erick.musicum.comun.Menus;
import com.erick.musicum.comun.ModificarVistas;
import com.erick.musicum.comun.Navegar;
import com.erick.musicum.comun.ObtenerArrays;
import com.erick.musicum.comun.ObtenerDatos;
import com.erick.musicum.comun.ObtenerIds;
import com.erick.musicum.comun.objetos.Cancion;
import com.erick.musicum.comun.objetos.Datos;
import com.erick.musicum.listas.adaptadores.AdaptadorCancionesActual;
import com.erick.musicum.servicios.MusicService;

import java.util.ArrayList;

public class CancionesActual extends Fragment {

    private RelativeLayout vistaContenido;
    private ProgressBar vistaCargando;
    private RecyclerView rclrVwLista;
    private RecyclerView.Adapter adaptador;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Cancion> canciones = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_lista_actual, container, false);
        vistaContenido = (RelativeLayout) rootView.findViewById(R.id.content);
        vistaCargando = (ProgressBar) rootView.findViewById(R.id.loading_spinner);
        rclrVwLista = (RecyclerView) vistaContenido.findViewById(R.id.song_list);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ModificarVistas.sumarMargenes(rclrVwLista, 0, ObtenerDatos.alturaStatusBar(getActivity()), 0, ObtenerDatos.alturaNavigationBar(getActivity()));
        }

        rclrVwLista.setHasFixedSize(true);
        vistaContenido.setVisibility(View.GONE);
        Datos.setFragmentCancionesActual(this);

        new CargarLista(getActivity()).execute("");

        setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Menus.barraBusqueda(getActivity(), menu, inflater, R.menu.menu_busqueda, new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String texto) {
                new CargarLista(getActivity()).execute(texto);
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    public RecyclerView.Adapter getAdaptador() {
        return adaptador;
    }


    public class CargarLista extends AsyncTask<String, Void, Void> {

        private final Activity activity;

        public CargarLista(final Activity activity) {
            this.activity = activity;
        }

        @Override
        protected Void doInBackground(String... params) {
            mLayoutManager = new LinearLayoutManager(getActivity());
            mLayoutManager.scrollToPosition(ObtenerIds.indexActual(activity));
            canciones = ObtenerArrays.cancionesActual(activity, params[0]);
            adaptador = new AdaptadorCancionesActual(activity, canciones);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            rclrVwLista.setLayoutManager(mLayoutManager);
            rclrVwLista.setAdapter(adaptador);
            Navegar.mostrarVista(activity, vistaContenido, vistaCargando);
        }
    }
}