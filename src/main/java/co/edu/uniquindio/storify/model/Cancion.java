package co.edu.uniquindio.storify.model;

import java.io.Serializable;

public class Cancion implements Serializable {
    private String codigo;
    private String nombre;
    private String album;
    private String caratula;
    private int anio;
    private int duracion;
    private String genero;
    private String urlYoutube;

    public Cancion(String codigo, String nombre, String album, String caratula, int anio, int duracion, String genero, String urlYoutube) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.album = album;
        this.caratula = caratula;
        this.anio = anio;
        this.duracion = duracion;
        this.genero = genero;
        this.urlYoutube = urlYoutube;
    }

    public Cancion(){}

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getAlbum() {
        return album;
    }

    public String getCaratula() {
        return caratula;
    }

    public int getAnio() {
        return anio;
    }

    public int getDuracion() {
        return duracion;
    }

    public String getGenero() {
        return genero;
    }

    public String getUrlYoutube() {
        return urlYoutube;
    }

    @Override
    public String toString() {
        return "Cancion{" +
                "codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", album='" + album + '\'' +
                ", caratula='" + caratula + '\'' +
                ", anio=" + anio +
                ", duracion=" + duracion +
                ", genero='" + genero + '\'' +
                ", urlYoutube='" + urlYoutube + '\'' +
                '}';
    }
}
