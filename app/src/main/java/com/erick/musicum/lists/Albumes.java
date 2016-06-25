package com.erick.musicum.lists;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.erick.musicum.lists.adaptadores.AdaptadorAlbumes;

/**
 * Created by ErickSteven on 27/06/2015.
 */
public class Albumes extends Fragment {

    private Cursor cursor;
    private ProgressBar cargando;
    private RecyclerView rclrVwLista;
    private RecyclerView.Adapter adaptador;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_albumes, container, false);

        cargando = (ProgressBar) rootView.findViewById(R.id.loading_spinner);
        rclrVwLista = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);

        rclrVwLista.setVisibility(View.GONE);
        rclrVwLista.setHasFixedSize(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ModificarVistas.sumarMargenes(rclrVwLista, 0, 0, 0, ObtenerDatos.alturaNavigationBar(getActivity()));
            ModificarVistas.sumarMargenes(rclrVwLista, 0, ObtenerDatos.alturaStatusBar(getActivity()), 0, 0);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    public class CargarLista extends AsyncTask<String, Void, Void> {

        private final Context context;

        public CargarLista(Context context) {
            this.context = context;
        }

        @Override
        protected Void doInBackground(String... params) {
            mLayoutManager = new GridLayoutManager(getActivity(), 2);
            cursor = ObtenerCursores.listaAlbumes(context, Constantes.NO_RANDOM_ALBUMES, params[0]);
            adaptador = new AdaptadorAlbumes(cursor, getActivity());
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
