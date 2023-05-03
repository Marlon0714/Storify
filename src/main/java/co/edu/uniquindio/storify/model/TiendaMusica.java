package co.edu.uniquindio.storify.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class TiendaMusica implements Serializable {
    private HashMap<String,Usuario> usuarios;
    private ArbolBinario artistas;
    public TiendaMusica() {}

    public Map<String, Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Map<String, Usuario> usuarios) {
        if (usuarios instanceof HashMap) {
            this.usuarios = (HashMap<String, Usuario>) usuarios;
        } else {
            this.usuarios = new HashMap<>(usuarios);
        }
    }

    public ArbolBinario getArtistas() {
        return artistas;
    }

    public void setArtistas(ArbolBinario artistas) {
        this.artistas = artistas;
    }
}
