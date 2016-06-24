package com.erick.musicum.servicios;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.KeyEvent;

import com.erick.musicum.comun.DispararIntents;
import com.erick.musicum.comun.Navegar;
import com.erick.musicum.comun.ObtenerIds;
import com.erick.musicum.comun.objetos.Constantes;
import com.erick.musicum.comun.objetos.Datos;

/**
 * Created by ErickSteven on 29/07/2015.
 */
public class RemoteControlReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_MEDIA_BUTTON.equals(intent.getAction())) {
            final KeyEvent event = intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);
            if (event != null && event.getAction() == KeyEvent.ACTION_DOWN) {
                if(!Datos.isInClicks()){
                    Datos.setClicks(Datos.getClicks() + 1);
                    Hilo hilo = new Hilo();
                    hilo.execute(context);
                }else{
                    if(Datos.getClicks() < 3) {
                        Datos.setClicks(Datos.getClicks() + 1);
                    }
                }
            }
        }
    }

    public class Hilo extends AsyncTask<Context, Void, Context>{

        @Override
        protected Context doInBackground(Context... params) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return params[0];
        }
        @Override
        protected void onPostExecute(Context context){

            switch (Datos.getClicks()) {
                //Play y Pause
                case 1:
                    Datos.setClicks(0);
                    DispararIntents.reproducir(context);
                    break;
                //Siguiente canción
                case 2:
                    Datos.setClicks(0);
                    Datos.getPortada().getmPager().setCurrentItem(ObtenerIds.indexActual(context), true);
                    break;
                //Anterior canción
                case 3:
                    Datos.setClicks(0);
                    Datos.getPortada().getmPager().setCurrentItem(ObtenerIds.indexActual(context), true);
                    break;
            }
        }
    }

}