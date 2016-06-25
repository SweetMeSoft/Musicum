package com.erick.musicum.common.objetos;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by ErickSteven on 6/1/2016.
 */
public class Directorio {

    private String ruta;
    private final ArrayList<File> archivos = new ArrayList<>();

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public ArrayList<File> getArchivos() {
        return archivos;
    }
}
