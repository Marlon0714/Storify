package co.edu.uniquindio.storify.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Autor implements Serializable {
    private String codigo;
    private String nombre;
    private String nacionalidad;
    private boolean esGrupo;
    private List<Cancion> canciones;

    public Autor(String codigo, String nombre, String nacionalidad, boolean esGrupo) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
        this.esGrupo = esGrupo;
        this.canciones = new ArrayList<>();
    }

    public Autor() {
    }


    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public boolean esGrupo() {
        return esGrupo;
    }

    public List<Cancion> getCanciones() {
        return canciones;
    }

    public void agregarCancion(Cancion cancion) {
        canciones.add(cancion);
    }

    public void eliminarCancion(Cancion cancion) {
        canciones.remove(cancion);
    }

    private String generarCodigo() {
        // Implementación de la generación del código único
        return "codigoAleatorio";
    }

    @Override
    public String toString() {
        return "Artista{" +
                "codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", nacionalidad='" + nacionalidad + '\'' +
                ", esGrupo=" + esGrupo +
                ", canciones=" + canciones +
                '}';
    }
}
