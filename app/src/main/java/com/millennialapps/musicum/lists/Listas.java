package com.millennialapps.musicum.lists;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.millennialapps.musicum.R;
import com.millennialapps.musicum.common.ManejarListas;
import com.millennialapps.musicum.common.ModificarVistas;
import com.millennialapps.musicum.common.Navegar;
import com.millennialapps.musicum.common.ObtenerCursores;
import com.millennialapps.musicum.common.ObtenerDatos;
import com.millennialapps.musicum.common.objects.Constantes;
import com.millennialapps.musicum.common.objects.Dialogos;
import com.millennialapps.musicum.lists.adaptadores.AdaptadorListas;

/**
 * Created by ErickSteven on 27/06/2015.
 */
public class Listas extends Fragment {

    private Cursor cursor;
    private RecyclerView rclrVwLista;
    private RecyclerView.Adapter adaptador;
    private RecyclerView.LayoutManager mLayoutManager;
    private RelativeLayout contenido;
    private ProgressBar cargando;
    private Button btnNuevaLista;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_listas, container, false);

        contenido = (RelativeLayout) rootView.findViewById(R.id.content);
        cargando = (ProgressBar) rootView.findViewById(R.id.loading_spinner);
        rclrVwLista = (RecyclerView) contenido.findViewById(R.id.lista_listas);
        btnNuevaLista = (Button) contenido.findViewById(R.id.btnNuevaLista);

        contenido.setVisibility(View.GONE);
        rclrVwLista.setHasFixedSize(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ModificarVistas.sumarMargenes(btnNuevaLista, 0, ObtenerDatos.alturaStatusBar(getActivity()), 0, 0);
            ModificarVistas.sumarMargenes(rclrVwLista, 0, 0, 0, ObtenerDatos.alturaNavigationBar(getActivity()));
        }

        btnNuevaLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText edTxt = new EditText(getActivity());
                Dialogos.nuevaLista(getActivity(), edTxt,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                ManejarListas.nuevaLista(getActivity(), edTxt);
                                new CargarLista(getActivity()).execute();
                            }
                        }, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        }
                );
            }
        });

        new CargarLista(getActivity()).execute();

        return rootView;
    }

    public class CargarLista extends AsyncTask<Void, Void, Void> {

        private final Context context;

        public CargarLista(Context context) {
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... params) {
            mLayoutManager = new LinearLayoutManager(getActivity());
            cursor = ObtenerCursores.listaListas(context, Constantes.NO_RANDOM_LISTAS);
            adaptador = new AdaptadorListas(context, cursor);
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
            Navegar.mostrarVista(context, contenido, cargando);
        }
    }
}
