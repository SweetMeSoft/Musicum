package com.erick.musicum.lists;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.erick.musicum.R;
import com.erick.musicum.common.DispararIntents;
import com.erick.musicum.common.ModificarVistas;
import com.erick.musicum.common.Navegar;
import com.erick.musicum.common.ObtenerCursores;
import com.erick.musicum.common.ObtenerDatos;
import com.erick.musicum.common.objects.Constantes;
import com.erick.musicum.lists.adaptadores.AdaptadorAlbumesArtista;


public class AlbumesArtista extends AppCompatActivity {

    private final AppCompatActivity activity = this;

    private Cursor cursor;
    private CoordinatorLayout contenido;
    private FloatingActionButton fabPlayAll;
    private RecyclerView rclrVwLista;
    private RecyclerView.Adapter adaptador;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressBar cargando;
    private String artista;
    private CollapsingToolbarLayout collapsingToolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albumes_artista);
        iniciarActividad();

        Intent intent = getIntent();
        artista = intent.getStringExtra("Artista");

        contenido = (CoordinatorLayout) findViewById(R.id.content);
        cargando = (ProgressBar) contenido.findViewById(R.id.loading_spinner);
        rclrVwLista = (RecyclerView) contenido.findViewById(R.id.my_recycler_view);
        fabPlayAll = (FloatingActionButton) contenido.findViewById(R.id.fabPlayAll);
        collapsingToolbar = (CollapsingToolbarLayout) contenido.findViewById(R.id.collapsing_toolbar);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbar.setTitle(artista);
        fabPlayAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Datos.getMusicService().setCanciones(ObtenerDatos.crearListaActual(ObtenerCursores.listaCancionesArtista(activity, Preferencias.obtenerRandom(), artista)));
                DispararIntents.irACancion(getApplicationContext(), 0);
                Navegar.irAInicio(activity, 0);
            }
        });

        rclrVwLista.setVisibility(View.GONE);
        rclrVwLista.setHasFixedSize(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ModificarVistas.sumarMargenes(rclrVwLista, 0, 0, 0, ObtenerDatos.alturaNavigationBar(this));
            collapsingToolbar.setPadding(0, ObtenerDatos.alturaStatusBar(this), 0, 0);
        }

        new CargarLista(this).execute("");
    }

    private void iniciarActividad() {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void clickTodasCanciones(View view) {
        Intent intent = new Intent(this, CancionesArtista.class);
        intent.putExtra("artista", artista);
        this.startActivity(intent);
    }

    public class CargarLista extends AsyncTask<String, Void, Void> {

        private final Activity context;

        public CargarLista(final Activity context) {
            this.context = context;
        }

        @Override
        protected Void doInBackground(String... params) {
            cursor = ObtenerCursores.listaAlbumesArtista(context, Constantes.NO_RANDOM_ALBUMES_ARTISTA, artista, params[0]);
            mLayoutManager = new GridLayoutManager(context, 2);
            adaptador = new AdaptadorAlbumesArtista(context, cursor);
            try {
                Thread.sleep(getResources().getInteger(R.integer.tiempo_espera));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
