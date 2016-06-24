package com.erick.musicum.listas;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.erick.musicum.R;
import com.erick.musicum.comun.DispararIntents;
import com.erick.musicum.comun.ModificarVistas;
import com.erick.musicum.comun.Navegar;
import com.erick.musicum.comun.ObtenerCursores;
import com.erick.musicum.comun.ObtenerDatos;
import com.erick.musicum.comun.objetos.Constantes;
import com.erick.musicum.comun.objetos.Datos;
import com.erick.musicum.comun.objetos.Preferencias;
import com.erick.musicum.listas.adaptadores.AdaptadorCancionesArtista;
import com.erick.musicum.servicios.MusicService;


public class CancionesArtista extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {

    private final AppCompatActivity activity = this;

    private boolean mIsTheTitleVisible = false;

    private Cursor cursor;
    private FloatingActionButton fabPlayAll;
    private RecyclerView rclrVwLista;
    private RecyclerView.Adapter adaptador;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressBar cargando;
    private ImageView imgVwPortada;
    private String data;
    private String artista;
    private TextView txtData;
    private TextView txtToolbar;
    private AppBarLayout appbar;
    private TextView txtArtista;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canciones_artista);

        iniciarActividad();

        Intent intent = getIntent();
        artista = intent.getStringExtra("artista");
        artista = artista.replace("'", "''");

        cargando = (ProgressBar) findViewById(R.id.loading_spinner);
        appbar = (AppBarLayout) findViewById(R.id.appbar);
        imgVwPortada = (ImageView) findViewById(R.id.imgVwPortada);
        rclrVwLista = (RecyclerView) findViewById(R.id.my_recycler_view);
        fabPlayAll = (FloatingActionButton) findViewById(R.id.fabPlayAll);
        txtData = (TextView) findViewById(R.id.txtData);
        txtArtista = (TextView) findViewById(R.id.txtArtista);
        txtToolbar = (TextView) findViewById(R.id.txtToolbar);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        cursor = ObtenerCursores.listaCancionesArtista(this, Constantes.NO_RANDOM_CANCIONES_ARTISTA, artista);
        if (cursor.getCount() == 1) {
            data = cursor.getCount() + " canción.";
        } else {
            data = cursor.getCount() + " canciones.";
        }
        mLayoutManager = new LinearLayoutManager(this);
        adaptador = new AdaptadorCancionesArtista(this, cursor, artista);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        ModificarVistas.comenzarAnimacionAlpha(this, txtToolbar, View.INVISIBLE);

        rclrVwLista.setVisibility(View.GONE);
        appbar.addOnOffsetChangedListener(this);
        txtArtista.setText(artista);
        txtData.setText(data);
        txtToolbar.setText(artista);

        rclrVwLista.setHasFixedSize(true);
        rclrVwLista.setPadding(0, 0, 0, ObtenerDatos.alturaNavigationBar(this));
        rclrVwLista.setLayoutManager(mLayoutManager);
        rclrVwLista.setAdapter(adaptador);

        imgVwPortada.setTag(0);

        fabPlayAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Datos.getMusicService().setCanciones(ObtenerDatos.crearListaActual(ObtenerCursores.listaCancionesArtista(activity, Preferencias.obtenerRandom(), artista)));
                DispararIntents.irACancion(getApplicationContext(), 0);
                Navegar.irAInicio(activity, 0);
            }
        });

        Navegar.mostrarVista(this, rclrVwLista, cargando);

        artista = artista.replace("''", "'");
    }

    private void iniciarActividad() {

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
        if (percentage >= Constantes.PORCENTAJE_MOSTRAR_TITULO) {
            if (!mIsTheTitleVisible) {
                ModificarVistas.comenzarAnimacionAlpha(this, txtToolbar, View.VISIBLE);
                mIsTheTitleVisible = true;
            }
        } else {
            if (mIsTheTitleVisible) {
                ModificarVistas.comenzarAnimacionAlpha(this, txtToolbar, View.INVISIBLE);
                mIsTheTitleVisible = false;
            }
        }
    }


}
