package com.millennialapps.musicum.lists.adaptadores;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.millennialapps.musicum.R;
import com.millennialapps.musicum.common.DispararIntents;
import com.millennialapps.musicum.common.Navegar;
import com.millennialapps.musicum.common.ObtenerArrays;
import com.millennialapps.musicum.common.objects.Constantes;
import com.millennialapps.musicum.common.objects.Preferencias;

import java.io.File;
import java.util.List;

public class AdaptadorCarpetas extends RecyclerView.Adapter<AdaptadorCarpetas.ViewHolder> {

    private final Activity activity;
    public int folders = -1;
    private List<File> lstArchivos;

    public AdaptadorCarpetas(final Activity activity, List<File> cursor) {
        this.activity = activity;
        this.lstArchivos = cursor;
    }

    @Override
    public AdaptadorCarpetas.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_carpeta, parent, false);
        ViewHolder holder = new ViewHolder(view, activity, this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        File archivo = lstArchivos.get(position);
        final String rutaPadre = archivo.getParent();
        if (!rutaPadre.equals(Constantes.ROOT) && position == 0) {
            holder.imgVwTipo.setImageResource(R.drawable.atras);
            holder.txtNombre.setText(archivo.getName());
            holder.txtCarpeta.setText(rutaPadre.substring(0, rutaPadre.lastIndexOf("/") + 1));
            if (folders < position) {
                folders = position;
            }
        } else {
            if (archivo.isDirectory()) {
                holder.imgVwTipo.setImageResource(R.drawable.directorio);
                if (folders < position) {
                    folders = position;
                }
            } else {
                holder.imgVwTipo.setImageResource(R.drawable.archivo);
            }
            holder.txtNombre.setText(archivo.getName());
            holder.txtCarpeta.setText(rutaPadre);
        }
    }

    @Override
    public int getItemCount() {
        return lstArchivos.size();
    }

    public List<File> getLstArchivos() {
        return lstArchivos;
    }

    public void setLstArchivos(List<File> lstArchivos) {
        this.lstArchivos = lstArchivos;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final Activity activity;
        public TextView txtCarpeta;
        public TextView txtNombre;
        public ImageView imgVwTipo;
        private AdaptadorCarpetas adaptador;

        public ViewHolder(View view, final Activity activity, AdaptadorCarpetas adaptador) {
            super(view);
            this.activity = activity;
            this.adaptador = adaptador;
            txtCarpeta = (TextView) view.findViewById(R.id.txtCarpeta);
            txtNombre = (TextView) view.findViewById(R.id.txtTitulo);
            imgVwTipo = (ImageView) view.findViewById(R.id.imgVwTipo);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            String ruta = txtCarpeta.getText().toString();
            String nombre = "/" + txtNombre.getText().toString();
            int position = getLayoutPosition();
            if (new File(ruta + nombre).isDirectory()) {
                adaptador.setLstArchivos(ObtenerArrays.listaCarpetas(activity, ruta + nombre));
                adaptador.notifyDataSetChanged();
                Preferencias.guardarDato(activity, Constantes.VALOR_RUTA, ruta + nombre);

            } else {
                if (position == 0) {
                    final String rutaPadre = ruta.substring(0, ruta.lastIndexOf("/") + 1);
                    adaptador.setLstArchivos(ObtenerArrays.listaCarpetas(activity, rutaPadre));
                    adaptador.notifyDataSetChanged();
                    Preferencias.guardarDato(activity, Constantes.VALOR_RUTA, rutaPadre);
                } else {
                    //Datos.getMusicService().setCanciones(ObtenerDatos.crearListaActual(ObtenerCursores.listaCancionesCarpeta(activity, Preferencias.obtenerRandom(), ruta + "/")));
                    final int posicion = position - adaptador.folders - 1;
                    DispararIntents.irACancion(activity, posicion);
                    Navegar.irAInicio(activity, posicion);
                }
            }
            adaptador.folders = -1;
        }

    }
}



