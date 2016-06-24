package com.erick.musicum.main;

import android.support.v7.app.AppCompatActivity;

import com.erick.musicum.comun.objetos.Datos;

/**
 * Created by ErickSteven on 25/1/2016.
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onPause() {
        super.onPause();
        Datos.setPuedeCorrer(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Datos.setPuedeCorrer(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Datos.setPuedeCorrer(true);
    }
}
