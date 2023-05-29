package co.edu.uniquindio.storify.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Esta clase representa a un artista en el sistema Storify.
 */
public class Artista implements Serializable, Comparable<Artista> {
    private String codigo;
    private String nombre;
    private String nacionalidad;
    private boolean esGrupo;
    private ListaDobleEnlazada<Cancion> listaCanciones;

    /**
     * Crea un nuevo objeto Artista con los atributos especificados.
     *
     * @param codigo          el código del artista.
     * @param nombre          el nombre del artista.
     * @param nacionalidad    la nacionalidad del artista.
     * @param esGrupo         indica si el artista es un grupo o no.
     * @param listaCanciones  la lista de canciones asociadas al artista.
     */
    public Artista(String codigo, String nombre, String nacionalidad, boolean esGrupo, ListaDobleEnlazada<Cancion> listaCanciones) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
        this.esGrupo = esGrupo;
        this.listaCanciones = listaCanciones;
    }

    /**
     * Crea un nuevo objeto Artista sin inicializar los atributos.
     */
    public Artista() {
    }

    /**
     * Establece el código del artista.
     *
     * @param codigo el código del artista.
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * Establece el nombre del artista.
     *
     * @param nombre el nombre del artista.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Establece la nacionalidad del artista.
     *
     * @param nacionalidad la nacionalidad del artista.
     */
    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    /**
     * Verifica si el artista es un grupo.
     *
     * @return true si el artista es un grupo, false de lo contrario.
     */
    public boolean isEsGrupo() {
        return esGrupo;
    }

    /**
     * Obtiene la lista de canciones asociadas al artista.
     *
     * @return la lista de canciones del artista.
     */
    public ListaDobleEnlazada<Cancion> getListaCanciones() {
        return listaCanciones;
    }

    /**
     * Establece la lista de canciones asociadas al artista.
     *
     * @param listaCanciones la lista de canciones del artista.
     */
    public void setListaCanciones(ListaDobleEnlazada<Cancion> listaCanciones) {
        this.listaCanciones = listaCanciones;
    }

    /**
     * Obtiene el código del artista.
     *
     * @return el código del artista.
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Obtiene el nombre del artista.
     *
     * @return el nombre del artista.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene la nacionalidad del artista.
     *
     * @return la nacionalidad del artista.
     */
    public String getNacionalidad() {
        return nacionalidad;
    }

    /**
     * Verifica si el artista es un grupo.
     *
     * @return true si el artista es un grupo, false de lo contrario.
     */
    public boolean esGrupo() {
        return esGrupo;
    }

    /**
     * Establece si el artista es un grupo.
     *
     * @param esGrupo true si el artista es un grupo, false de lo contrario.
     */
    public void setEsGrupo(boolean esGrupo) {
        this.esGrupo = esGrupo;
    }

    /**
     * Agrega una canción a la lista de canciones del artista.
     *
     * @param cancion la canción a agregar.
     */
    public void agregarCancion(Cancion cancion) {
        listaCanciones.insertar(cancion);
    }

    /**
     * Elimina una canción de la lista de canciones del artista.
     *
     * @param cancion la canción a eliminar.
     */
    public void eliminarCancion(Cancion cancion) {
        listaCanciones.eliminar(cancion);
    }

    /**
     * Devuelve una representación en forma de cadena del objeto Artista.
     *
     * @return una cadena que representa al artista.
     */
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

    /**
     * Compara este objeto Artista con otro objeto especificado.
     *
     * @param o el objeto a comparar.
     * @return 0 si los objetos son iguales, un valor negativo si este objeto es menor que el objeto especificado,
     *         un valor positivo si este objeto es mayor que el objeto especificado.
     */
    @Override
    public int compareTo(Artista o) {
        return this.nombre.compareTo(o.getNombre());
    }
}
