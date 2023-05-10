package co.edu.uniquindio.storify.model;

import java.io.Serializable;
import java.util.Objects;

public class Artista implements Serializable, Comparable<Artista> {
    private String codigo;
    private String nombre;
    private String nacionalidad;
    private boolean esGrupo;
    private ListaDobleEnlazada<Cancion> listaCanciones;

    public Artista(String codigo, String nombre, String nacionalidad, boolean esGrupo, ListaDobleEnlazada<Cancion> listaCanciones) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
        this.esGrupo = esGrupo;
        this.listaCanciones = listaCanciones;
    }

    public Artista() {
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public boolean isEsGrupo() {
        return esGrupo;
    }

    public ListaDobleEnlazada<Cancion> getListaCanciones() {
        return listaCanciones;
    }

    public void setListaCanciones(ListaDobleEnlazada<Cancion> listaCanciones) {
        this.listaCanciones = listaCanciones;
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

    public void setEsGrupo(boolean esGrupo) {
        this.esGrupo = esGrupo;
    }

    public ListaDobleEnlazada<Cancion> getCanciones() {
        return listaCanciones;
    }

    public void agregarCancion(Cancion cancion) {
        listaCanciones.insertar(cancion);
    }

    public void eliminarCancion(Cancion cancion) {
        listaCanciones.eliminar(cancion);
    }

    @Override
    public String toString() {
        return "Artista{" +
                "codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", nacionalidad='" + nacionalidad + '\'' +
                ", esGrupo=" + esGrupo +
                ", canciones=" + listaCanciones +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Artista)) return false;
        Artista artista = (Artista) o;
        return esGrupo == artista.esGrupo && Objects.equals(getCodigo(), artista.getCodigo()) && Objects.equals(getNombre(), artista.getNombre()) && Objects.equals(getNacionalidad(), artista.getNacionalidad()) && Objects.equals(listaCanciones, artista.listaCanciones);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCodigo(), getNombre(), getNacionalidad(), esGrupo, listaCanciones);
    }

    @Override
    public int compareTo(Artista o) {
        return this.nombre.compareTo(o.getNombre());
    }
}
