package com.erick.musicum.listas;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.erick.musicum.R;
import com.erick.musicum.comun.ModificarVistas;
import com.erick.musicum.comun.Navegar;
import com.erick.musicum.comun.ObtenerCursores;
import com.erick.musicum.comun.ObtenerDatos;
import com.erick.musicum.comun.objetos.Datos;
import com.erick.musicum.listas.adaptadores.AdaptadorCancionesGenero;


public class CancionesGenero extends AppCompatActivity {

    private Cursor cursor;
    private CoordinatorLayout contenido;
    private RecyclerView rclrVwLista;
    private RecyclerView.Adapter adaptador;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressBar cargando;
    private String genero;
    private TextView txtGenero;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canciones_genero);

        iniciarActividad();

        Intent intent = getIntent();
        genero = intent.getStringExtra("genero");
        genero = genero.replace("'", "''");

        contenido = (CoordinatorLayout) findViewById(R.id.content);
        cargando = (ProgressBar) findViewById(R.id.loading_spinner);
        rclrVwLista = (RecyclerView) contenido.findViewById(R.id.lista_canciones_genero);
        txtGenero = (TextView) contenido.findViewById(R.id.txtGenero);
        final Toolbar toolbar = (Toolbar) contenido.findViewById(R.id.toolbar);

        mLayoutManager = new LinearLayoutManager(this);
        cursor = ObtenerCursores.listaCancionesGenero(this, 0, genero);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        txtGenero.setText(genero);
        contenido.setVisibility(View.GONE);
        rclrVwLista.setHasFixedSize(true);
        rclrVwLista.setLayoutManager(mLayoutManager);
        adaptador = new AdaptadorCancionesGenero(this, cursor, genero);
        rclrVwLista.setAdapter(adaptador);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ModificarVistas.sumarMargenes(toolbar, 0, ObtenerDatos.alturaStatusBar(this), 0, 0);
            ModificarVistas.sumarMargenes(rclrVwLista, 0, ObtenerDatos.alturaStatusBar(this) + ObtenerDatos.alturaToolbar(this), 0, 0);
        }

        Navegar.mostrarVista(this, contenido, cargando);
        genero = genero.replace("''", "'");
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

}
