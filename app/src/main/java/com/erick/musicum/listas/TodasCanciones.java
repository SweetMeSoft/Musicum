package com.erick.musicum.listas;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.erick.musicum.R;
import com.erick.musicum.comun.ModificarVistas;
import com.erick.musicum.comun.Navegar;
import com.erick.musicum.comun.ObtenerCursores;
import com.erick.musicum.comun.ObtenerDatos;
import com.erick.musicum.listas.adaptadores.AdaptadorTodasCanciones;


public class TodasCanciones extends Fragment {

    private RecyclerView rclrVwLista;
    private RecyclerView.Adapter adaptador;
    private RecyclerView.LayoutManager mLayoutManager;
    private RelativeLayout vistaContenido;
    private ProgressBar vistaCargando;
    private Cursor cursor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_todas_canciones, container, false);
        vistaContenido = (RelativeLayout) rootView.findViewById(R.id.content);
        vistaCargando = (ProgressBar) rootView.findViewById(R.id.loading_spinner);
        rclrVwLista = (RecyclerView) vistaContenido.findViewById(R.id.lista_todas_canciones);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ModificarVistas.sumarMargenes(rclrVwLista, 0, ObtenerDatos.alturaStatusBar(getActivity()), 0, ObtenerDatos.alturaNavigationBar(getActivity()));
        }

        rclrVwLista.setHasFixedSize(true);
        vistaContenido.setVisibility(View.GONE);

        new CargarLista(getActivity()).execute();
        setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.removeItem(R.menu.menu_busqueda);
        inflater.inflate(R.menu.menu_busqueda, menu);

        MenuItem searchItem = menu.findItem(R.id.menu_search);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = null;

        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
            SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String texto) {
                    new CargarLista(getActivity()).execute(texto);
                    return true;
                }

                @Override
                public boolean onQueryTextSubmit(String query) {
                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    public class CargarLista extends AsyncTask<String, Void, Void> {

        private final Activity activity;

        public CargarLista(final Activity activity) {
            this.activity = activity;
        }

        @Override
        protected Void doInBackground(String... params) {
            mLayoutManager = new LinearLayoutManager(getActivity());
            if (params.length == 0) {
                cursor = ObtenerCursores.todasCanciones(getActivity(), 0);
            } else {
                cursor = ObtenerCursores.busquedaCanciones(getActivity(), params[0]);
            }
            adaptador = new AdaptadorTodasCanciones(activity, cursor);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            rclrVwLista.setLayoutManager(mLayoutManager);
            rclrVwLista.setAdapter(adaptador);
            Navegar.mostrarVista(getActivity(), vistaContenido, vistaCargando);
        }
    }
}
