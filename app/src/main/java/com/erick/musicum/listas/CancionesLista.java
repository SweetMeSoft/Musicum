package com.erick.musicum.listas;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.erick.musicum.R;
import com.erick.musicum.comun.DispararIntents;
import com.erick.musicum.comun.Menus;
import com.erick.musicum.comun.Navegar;
import com.erick.musicum.comun.ObtenerCursores;
import com.erick.musicum.comun.ObtenerDatos;
import com.erick.musicum.comun.ObtenerIds;
import com.erick.musicum.comun.objetos.Constantes;
import com.erick.musicum.comun.objetos.Datos;
import com.erick.musicum.listas.adaptadores.AdaptadorCancionesLista;
import com.erick.musicum.servicios.MusicService;


public class CancionesLista extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {

    private final AppCompatActivity activity = this;

    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.9f;
    private static final int ALPHA_ANIMATIONS_DURATION = 200;
    private boolean mIsTheTitleVisible = false;

    private Cursor cursor;
    private CoordinatorLayout contenido;
    private FloatingActionButton fabPlayAll;
    private RecyclerView rclrVwLista;
    private RecyclerView.Adapter adaptador;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressBar cargando;
    private ImageView imgVwPortada;
    private String lista;
    private long idLista;
    private TextView txtData;
    private TextView txtLista;
    private TextView txtToolbar;
    private AppBarLayout appbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_canciones_lista);

        iniciarActividad();

        Intent intent = getIntent();
        lista = intent.getStringExtra("lista");
        idLista = ObtenerIds.idLista(getApplicationContext(), lista);

        contenido = (CoordinatorLayout) findViewById(R.id.content);
        cargando = (ProgressBar) contenido.findViewById(R.id.loading_spinner);
        appbar = (AppBarLayout) contenido.findViewById(R.id.appbar);
        imgVwPortada = (ImageView) contenido.findViewById(R.id.imgVwPortada);
        rclrVwLista = (RecyclerView) contenido.findViewById(R.id.my_recycler_view);
        fabPlayAll = (FloatingActionButton) contenido.findViewById(R.id.fabPlayAll);
        txtLista = (TextView) contenido.findViewById(R.id.txtLista);
        txtData = (TextView) contenido.findViewById(R.id.txtData);
        txtToolbar = (TextView) contenido.findViewById(R.id.txtToolbar);
        final Toolbar toolbar = (Toolbar) contenido.findViewById(R.id.toolbar);

        mLayoutManager = new LinearLayoutManager(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        startAlphaAnimation(txtToolbar, 0, View.INVISIBLE);

        rclrVwLista.setVisibility(View.GONE);
        rclrVwLista.setHasFixedSize(true);
        appbar.addOnOffsetChangedListener(this);

        imgVwPortada.setTag(0);

        fabPlayAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Datos.getMusicService().setCanciones(ObtenerDatos.crearListaActual(cursor));
                DispararIntents.irACancion(getApplicationContext(), 0);
                Navegar.irAInicio(activity, 0);
            }
        });

        lista = lista.replace("''", "'");

        new CargarLista(this).execute("");
    }

    private void iniciarActividad() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Menus.barraBusqueda(this, menu, getMenuInflater(), R.menu.menu_canciones_lista, new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String texto) {
                new CargarLista(activity).execute(texto);
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }
        });
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

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;

        handleToolbarTitleVisibility(percentage);
    }

    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {
            if (!mIsTheTitleVisible) {
                startAlphaAnimation(txtToolbar, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleVisible = true;
            }
        } else {
            if (mIsTheTitleVisible) {
                startAlphaAnimation(txtToolbar, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleVisible = false;
            }
        }
    }

    public static void startAlphaAnimation(View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }

    public class CargarLista extends AsyncTask<String, Void, Void> {

        private final Activity activity;

        public CargarLista(Activity activity) {
            this.activity = activity;
        }

        @Override
        protected Void doInBackground(String... params) {
            cursor = ObtenerCursores.listaCancionesLista(activity, Constantes.NO_RANDOM_CANCIONES_LISTA, idLista, params[0]);
            adaptador = new AdaptadorCancionesLista(activity, cursor, lista);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            rclrVwLista.setLayoutManager(mLayoutManager);
            rclrVwLista.setAdapter(adaptador);
            txtLista.setText(lista);
            txtData.setText(cursor.getCount() + " canciones.");
            txtToolbar.setText(lista);
            if (cursor.getCount() == 0) {
                fabPlayAll.setEnabled(false);
            }
            Navegar.mostrarVista(activity, rclrVwLista, cargando);
        }
    }

}
