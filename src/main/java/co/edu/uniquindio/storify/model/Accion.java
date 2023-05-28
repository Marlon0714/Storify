package co.edu.uniquindio.storify.model;

import java.io.Serializable;

/**
 * La clase Accion representa una acción realizada sobre una canción.
 * Cada acción tiene un tipo (inserción o eliminación) y un elemento (la canción asociada a la acción).
 */
public class Accion implements Serializable {

    /**
     * El tipo de la acción puede ser INSERCION o ELIMINACION.
     */
    public enum Tipo {
        INSERCION,
        ELIMINACION
    }

    private Tipo tipo;
    private Cancion elemento;

    /**
     * Crea una nueva instancia de la clase Accion con el tipo y elemento especificados.
     *
     * @param tipo     El tipo de la acción.
     * @param elemento La canción asociada a la acción.
     */
    public Accion(Tipo tipo, Cancion elemento) {
        this.tipo = tipo;
        this.elemento = elemento;
    }

    /**
     * Obtiene el tipo de la acción.
     *
     * @return El tipo de la acción.
     */
    public Tipo getTipo() {
        return tipo;
    }

    /**
     * Obtiene el elemento (canción) asociado a la acción.
     *
     * @return La canción asociada a la acción.
     */
    public Cancion getElemento() {
        return elemento;
    }

    /**
     * Establece el tipo de la acción.
     *
     * @param tipo El tipo de la acción.
     */
    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    /**
     * Establece el elemento (canción) asociado a la acción.
     *
     * @param elemento La canción asociada a la acción.
     */
    public void setElemento(Cancion elemento) {
        this.elemento = elemento;
    }
}