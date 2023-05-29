package co.edu.uniquindio.storify.model;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;

/**
 * Esta clase representa una canción en el sistema Storify.
 */
public class Cancion implements Serializable, Comparator<Cancion> {
    private String codigo;
    private String nombre;
    private String album;
    private String caratula;
    private String codigoArtista;
    private int anio;
    private int duracion;
    private String genero;
    private String urlYoutube;

    /**
     * Crea un nuevo objeto Cancion con los atributos especificados.
     *
     * @param codigo        el código de la canción.
     * @param nombre        el nombre de la canción.
     * @param codigoArtista el código del artista asociado a la canción.
     * @param album         el álbum de la canción.
     * @param caratula      la carátula de la canción.
     * @param anio          el año de lanzamiento de la canción.
     * @param duracion      la duración de la canción en segundos.
     * @param genero        el género de la canción.
     * @param urlYoutube    la URL de YouTube de la canción.
     */
    public Cancion(String codigo, String nombre, String codigoArtista, String album, String caratula, int anio, int duracion, String genero, String urlYoutube) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.codigoArtista = codigoArtista;
        this.album = album;
        this.caratula = caratula;
        this.anio = anio;
        this.duracion = duracion;
        this.genero = genero;
        this.urlYoutube = urlYoutube;
    }

    /**
     * Crea un nuevo objeto Cancion sin inicializar los atributos.
     */
    public Cancion() {
    }

    /**
     * Obtiene el código de la canción.
     *
     * @return el código de la canción.
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Obtiene el nombre de la canción.
     *
     * @return el nombre de la canción.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene el álbum de la canción.
     *
     * @return el álbum de la canción.
     */
    public String getAlbum() {
        return album;
    }

    /**
     * Obtiene el código del artista asociado a la canción.
     *
     * @return el código del artista asociado a la canción.
     */
    public String getCodigoArtista() {
        return codigoArtista;
    }

    /**
     * Obtiene la carátula de la canción.
     *
     * @return la carátula de la canción.
     */
    public String getCaratula() {
        return caratula;
    }

    /**
     * Obtiene el año de lanzamiento de la canción.
     *
     * @return el año de lanzamiento de la canción.
     */
    public int getAnio() {
        return anio;
    }

    /**
     * Obtiene la duración de la canción en segundos.
     *
     * @return la duración de la canción en segundos.
     */
    public int getDuracion() {
        return duracion;
    }

    /**
     * Obtiene el género de la canción.
     *
     * @return el género de la canción.
     */
    public String getGenero() {
        return genero;
    }

    /**
     * Obtiene la URL de YouTube de la canción.
     *
     * @return la URL de YouTube de la canción.
     */
    public String getUrlYoutube() {
        return urlYoutube;
    }

    /**
     * Establece el código de la canción.
     *
     * @param codigo el código de la canción.
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * Establece el nombre de la canción.
     *
     * @param nombre el nombre de la canción.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Establece el álbum de la canción.
     *
     * @param album el álbum de la canción.
     */
    public void setAlbum(String album) {
        this.album = album;
    }

    /**
     * Establece la carátula de la canción.
     *
     * @param caratula la carátula de la canción.
     */
    public void setCaratula(String caratula) {
        this.caratula = caratula;
    }

    /**
     * Establece el código del artista asociado a la canción.
     *
     * @param codigoArtista el código del artista asociado a la canción.
     */
    public void setCodigoArtista(String codigoArtista) {
        this.codigoArtista = codigoArtista;
    }

    /**
     * Establece el año de lanzamiento de la canción.
     *
     * @param anio el año de lanzamiento de la canción.
     */
    public void setAnio(int anio) {
        this.anio = anio;
    }

    /**
     * Establece la duración de la canción en segundos.
     *
     * @param duracion la duración de la canción en segundos.
     */
    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    /**
     * Establece el género de la canción.
     *
     * @param genero el género de la canción.
     */
    public void setGenero(String genero) {
        this.genero = genero;
    }

    /**
     * Establece la URL de YouTube de la canción.
     *
     * @param urlYoutube la URL de YouTube de la canción.
     */
    public void setUrlYoutube(String urlYoutube) {
        this.urlYoutube = urlYoutube;
    }

    /**
     * Devuelve una representación en forma de cadena del objeto Cancion.
     *
     * @return una cadena que representa a la canción.
     */
    @Override
    public String toString() {
        return "Cancion{" +
                "codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", codigo Artista='" + codigoArtista + '\'' +
                ", album='" + album + '\'' +
                ", caratula='" + caratula + '\'' +
                ", anio=" + anio +
                ", duracion=" + duracion +
                ", genero='" + genero + '\'' +
                ", urlYoutube='" + urlYoutube + '\'' +
                '}';
    }

    /**
     * Compara este objeto Cancion con otro objeto especificado.
     *
     * @param o el objeto a comparar.
     * @return true si los objetos son iguales, false en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cancion)) return false;
        Cancion cancion = (Cancion) o;
        return getAnio() == cancion.getAnio() && getDuracion() == cancion.getDuracion() && Objects.equals(getNombre(), cancion.getNombre()) && Objects.equals(getAlbum(), cancion.getAlbum()) && Objects.equals(getCaratula(), cancion.getCaratula()) && Objects.equals(getGenero(), cancion.getGenero()) && Objects.equals(getUrlYoutube(), cancion.getUrlYoutube());
    }

    /**
     * Devuelve el valor hash del objeto Cancion.
     *
     * @return el valor hash del objeto Cancion.
     */
    @Override
    public int hashCode() {
        return Objects.hash(getCodigo(), getNombre(), getAlbum(), getCaratula(), getAnio(), getDuracion(), getGenero(), getUrlYoutube());
    }

    /**
     * Compara dos objetos Cancion.
     *
     * @param cancion1 el primer objeto Cancion a comparar.
     * @param cancion2 el segundo objeto Cancion a comparar.
     * @return un valor negativo si la primera canción es menor que la segunda,
     *         cero si son iguales, o un valor positivo si la primera canción es mayor que la segunda.
     */
    @Override
    public int compare(Cancion cancion1, Cancion cancion2) {
        // Aquí defines el criterio de comparación entre dos canciones
        // Por ejemplo, puedes comparar por nombre
        return cancion1.getNombre().compareTo(cancion2.getNombre());
    }
}
