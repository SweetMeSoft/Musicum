package com.erick.musicum.lists;

import android.app.Activity;
import android.database.Cursor;
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

import com.erick.musicum.R;
import com.erick.musicum.common.Menus;
import com.erick.musicum.common.ModificarVistas;
import com.erick.musicum.common.Navegar;
import com.erick.musicum.common.ObtenerCursores;
import com.erick.musicum.common.ObtenerDatos;
import com.erick.musicum.common.objects.Constantes;
import com.erick.musicum.lists.adaptadores.AdaptadorArtistas;

/**
 * Created by ErickSteven on 27/06/2015.
 */
public class Artistas extends Fragment {

    private Cursor cursor;
    private RecyclerView rclrVwLista;
    private RecyclerView.Adapter adaptador;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressBar cargando;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_artistas, container, false);

        cargando = (ProgressBar) rootView.findViewById(R.id.loading_spinner);
        rclrVwLista = (RecyclerView) rootView.findViewById(R.id.listaArtistas);
        mLayoutManager = new LinearLayoutManager(getActivity());
        rclrVwLista.setVisibility(View.GONE);
        rclrVwLista.setHasFixedSize(true);
        rclrVwLista.setLayoutManager(mLayoutManager);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ModificarVistas.sumarMargenes(rclrVwLista, 0, ObtenerDatos.alturaStatusBar(getActivity()), 0, ObtenerDatos.alturaNavigationBar(getActivity()));
        }

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

    public class CargarLista extends AsyncTask<String, Void, Void> {

        private final Activity context;

        public CargarLista(final Activity context) {
            this.context = context;
        }

        @Override
        protected Void doInBackground(String... params) {
            mLayoutManager = new LinearLayoutManager(getActivity());
            cursor = ObtenerCursores.listaArtistas(context, Constantes.NO_RANDOM_ARTISTAS, params[0]);
            adaptador = new AdaptadorArtistas(context, cursor);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            rclrVwLista.setLayoutManager(mLayoutManager);
            rclrVwLista.setAdapter(adaptador);
            Navegar.mostrarVista(context, rclrVwLista, cargando);
        }
    }
}
