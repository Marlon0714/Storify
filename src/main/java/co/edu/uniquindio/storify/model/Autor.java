package co.edu.uniquindio.storify.model;

import java.io.Serializable;
import java.util.Objects;

public class Autor implements Serializable, Comparable<Autor> {
    private String codigo;
    private String nombre;
    private String nacionalidad;
    private boolean esGrupo;
    private ListaDobleEnlazada<Cancion> listaCanciones;

    public Autor(String codigo, String nombre, String nacionalidad, boolean esGrupo, ListaDobleEnlazada<Cancion> listaCanciones) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
        this.esGrupo = esGrupo;
        this.listaCanciones = listaCanciones;
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
        if (!(o instanceof Autor)) return false;
        Autor autor = (Autor) o;
        return esGrupo == autor.esGrupo && Objects.equals(getCodigo(), autor.getCodigo()) && Objects.equals(getNombre(), autor.getNombre()) && Objects.equals(getNacionalidad(), autor.getNacionalidad()) && Objects.equals(listaCanciones, autor.listaCanciones);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCodigo(), getNombre(), getNacionalidad(), esGrupo, listaCanciones);
    }

    @Override
    public int compareTo(Autor o) {
        return this.nombre.compareTo(o.getNombre());
    }
}
