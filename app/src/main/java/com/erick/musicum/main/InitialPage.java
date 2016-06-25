package com.erick.musicum.main;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.erick.musicum.R;
import com.erick.musicum.common.DispararIntents;
import com.erick.musicum.common.ObtenerDatos;
import com.erick.musicum.common.objects.Constantes;
import com.erick.musicum.common.objects.Datos;
import com.erick.musicum.common.objects.Preferencias;

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
