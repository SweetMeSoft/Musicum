package com.erick.musicum.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.erick.musicum.R;
import com.erick.musicum.comun.DispararIntents;
import com.erick.musicum.comun.ObtenerCursores;
import com.erick.musicum.comun.ObtenerDatos;
import com.erick.musicum.comun.cache.SimpleDiskCache;
import com.erick.musicum.comun.objetos.Constantes;
import com.erick.musicum.comun.objetos.Datos;
import com.erick.musicum.comun.objetos.Preferencias;
import com.erick.musicum.servicios.MusicService;
import com.erick.musicum.sqlite.SQLiteManager;

public class InitialPage extends AppCompatActivity {

    private TextView txtAccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_page);

        txtAccion = (TextView) findViewById(R.id.txtAccion);
        txtAccion.setSelected(true);

        Preferencias.guardarDato(this, Constantes.VALOR_ESTADO, Constantes.ESTADO_DETENIDO);

        CargarConfiguraciones oc = new CargarConfiguraciones();
        oc.execute(this);
    }

    class CargarConfiguraciones extends AsyncTask<Activity, String, Void> {

        public CargarConfiguraciones() {
        }

        @Override
        protected Void doInBackground(Activity... params) {
            Preferencias.eliminarPreferencias(params[0]);
            params[0].deleteDatabase(Constantes.NOMBRE_DB);

            //Datos.setSqLiteManager(new SQLiteManager(params[0]));

            if (Preferencias.obtenerPrimeraVez(params[0])) {
                publishProgress("Analizando biblioteca... Esto puede tomar unos momentos, pero solo se hará la primera vez...");
                Preferencias.guardarDato(params[0], Constantes.VALOR_PRIMERA_VEZ, false);
            } else {
                publishProgress("Recuperando datos...");
            }

            DispararIntents.iniciarServicio(params[0]);

            publishProgress("Cargando configuraciones...");

            publishProgress("Cargando imágenes...");
            Datos.setPortadaDefault(ObtenerDatos.obtenerBitmap(params[0], 0, 300));
            Datos.setPortadaDefaultBlur(ObtenerDatos.obtenerBitmapBlur(params[0], 0, 300));
            publishProgress("Extrayendo datos...");
            return null;
        }

        @Override
        protected void onProgressUpdate(String... progress) {
            txtAccion.setText(progress[0]);
        }

        @Override
        protected void onPostExecute(Void result) {
            Intent actividaPrincipal = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(actividaPrincipal);
        }
    }

}
