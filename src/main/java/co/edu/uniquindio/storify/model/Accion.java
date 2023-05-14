package co.edu.uniquindio.storify.model;

import java.io.Serializable;

public class Accion implements Serializable {
    public enum Tipo {
        INSERCION,
        ELIMINACION
    }

    private Tipo tipo;
    private Cancion elemento;

    public Accion(Tipo tipo, Cancion elemento) {
        this.tipo = tipo;
        this.elemento = elemento;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public Cancion getElemento() {
        return elemento;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public void setElemento(Cancion elemento) {
        this.elemento = elemento;
    }
}
