package com.erick.musicum.common.objects;

import com.erick.musicum.common.FormatearDatos;

/**
 * Created by ErickSteven on 5/1/2016.
 */
public class Cancion {

    private long id;
    private String titulo;
    private String artista;
    private String album;
    private String duracion;

    public Cancion() {
    }

    public Cancion(long id, String titulo, String artista, String album, String duracion) {
        this.id = id;
        this.titulo = titulo;
        this.artista = artista;
        this.album = album;
        this.duracion = duracion;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(long duracion) {
        this.duracion = FormatearDatos.millisToString(duracion);
    }
}
