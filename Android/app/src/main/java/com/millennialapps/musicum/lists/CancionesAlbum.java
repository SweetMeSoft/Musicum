package com.millennialapps.musicum.lists;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.millennialapps.musicum.R;
import com.millennialapps.musicum.common.DispararIntents;
import com.millennialapps.musicum.common.Menus;
import com.millennialapps.musicum.common.ModificarVistas;
import com.millennialapps.musicum.common.Navegar;
import com.millennialapps.musicum.common.ObtenerCursores;
import com.millennialapps.musicum.common.ObtenerDatos;
import com.millennialapps.musicum.common.ObtenerIds;
import com.millennialapps.musicum.common.ObtenerPortadas;
import com.millennialapps.musicum.common.objects.Constantes;
import com.millennialapps.musicum.lists.adaptadores.AdaptadorCancionesAlbum;


public class CancionesAlbum extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {

    private AppCompatActivity activity = this;

    private boolean tituloVisible = false;

    private Cursor cursor;
    private FloatingActionButton fabPlayAll;
    private RecyclerView rclrVwLista;
    private RecyclerView.Adapter adaptador;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressBar cargando;
    private ImageView imgVwPortada;
    private String artista;
    private String album;
    private Long idAlbum;
    private TextView txtAlbum;
    private TextView txtArtista;
    private TextView txtToolbar;
    private AppBarLayout appbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canciones_album);

        iniciarActividad();

        Intent intent = getIntent();
        artista = intent.getStringExtra("artista");
        album = intent.getStringExtra("album");
        artista = artista.replace("'", "''");
        album = album.replace("'", "''");
        idAlbum = ObtenerIds.idAlbum(this, artista, album);

        cargando = (ProgressBar) findViewById(R.id.loading_spinner);
        appbar = (AppBarLayout) findViewById(R.id.appbar);
        imgVwPortada = (ImageView) findViewById(R.id.imgVwPortada);
        rclrVwLista = (RecyclerView) findViewById(R.id.my_recycler_view);
        fabPlayAll = (FloatingActionButton) findViewById(R.id.fabPlayAll);
        txtArtista = (TextView) findViewById(R.id.txtArtista);
        txtAlbum = (TextView) findViewById(R.id.txtAlbum);
        txtToolbar = (TextView) findViewById(R.id.txtToolbar);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mLayoutManager = new LinearLayoutManager(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        appbar.addOnOffsetChangedListener(this);
        rclrVwLista.setVisibility(View.GONE);
        rclrVwLista.setHasFixedSize(true);
        rclrVwLista.setPadding(0, 0, 0, ObtenerDatos.alturaNavigationBar(this));
        rclrVwLista.setLayoutManager(mLayoutManager);
        txtAlbum.setText(album);
        txtArtista.setText(artista);
        txtToolbar.setText(album);
        imgVwPortada.setTag(0);

        ModificarVistas.comenzarAnimacionAlpha(this, txtToolbar, View.INVISIBLE);

        mostrarLista("");

        fabPlayAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Datos.getMusicService().setCanciones(ObtenerDatos.crearListaActual(cursor));
                DispararIntents.irACancion(getApplicationContext(), 0);
                Navegar.irAInicio(activity, 0);
            }
        });

        new ObtenerPortadas(idAlbum, imgVwPortada, 500, false).execute(this);

        artista = artista.replace("''", "'");
        album = album.replace("''", "'");
    }

    private void iniciarActividad() {

    }

    private void mostrarLista(final String texto) {
        cursor = ObtenerCursores.listaCancionesAlbum(this, Constantes.NO_RANDOM_CANCIONES_ALBUM, artista, album, texto);
        adaptador = new AdaptadorCancionesAlbum(cursor, activity, artista, album);
        rclrVwLista.setAdapter(adaptador);
        Navegar.mostrarVista(this, rclrVwLista, cargando);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Menus.barraBusqueda(this, menu, getMenuInflater(), R.menu.menu_canciones_lista, new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String texto) {
                mostrarLista(texto);
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
        if (percentage >= Constantes.PORCENTAJE_MOSTRAR_TITULO) {
            if (!tituloVisible) {
                ModificarVistas.comenzarAnimacionAlpha(this, txtToolbar, View.VISIBLE);
                tituloVisible = true;
            }
        } else {
            if (tituloVisible) {
                ModificarVistas.comenzarAnimacionAlpha(this, txtToolbar, View.INVISIBLE);
                tituloVisible = false;
            }
        }
    }

}
