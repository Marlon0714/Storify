package co.edu.uniquindio.storify.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.TreeMap;

public class TiendaMusica implements Serializable {
    private HashMap<String,Usuario> usuarios;
    private TreeMap artistas;
    public TiendaMusica() {}

}
