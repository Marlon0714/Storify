package co.edu.uniquindio.storify.model;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;

public class Usuario implements Serializable {
    private String username;
    private String password;
    private String email;
    private ListaCircular<Cancion> listaCancionesFavoritas;
    private Pila<Accion> acciones;
    private Pila<Accion> accionesDesechas;

    public Usuario(String username, String password, String email, ListaCircular<Cancion> listaCancionesFavoritas) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.listaCancionesFavoritas = listaCancionesFavoritas;
        this.acciones = new Pila<>();
        this.accionesDesechas = new Pila<>();
    }

    public Usuario() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ListaCircular<Cancion> getListaCancionesFavoritas() {
        return listaCancionesFavoritas;
    }

    public void setListaCancionesFavoritas(ListaCircular<Cancion> listaCancionesFavoritas) {
        this.listaCancionesFavoritas = listaCancionesFavoritas;
    }

    public Pila<Accion> getAcciones() {
        return acciones;
    }

    public void setAcciones(Pila<Accion> acciones) {
        this.acciones = acciones;
    }

    public Pila<Accion> getAccionesDesechas() {
        return accionesDesechas;
    }

    public void setCancionesEliminadas(Pila<Accion> accionesDesechas) {
        this.accionesDesechas = accionesDesechas;
    }

    public void agregarCancion(Cancion cancion) {
        listaCancionesFavoritas.insertar(cancion);
        Accion accion = new Accion(Accion.Tipo.INSERCION, cancion);
        acciones.push(accion);
    }

    public void eliminarCancion(Cancion cancion) {
        listaCancionesFavoritas.eliminar(cancion);
        Accion accion = new Accion(Accion.Tipo.ELIMINACION,cancion);
        acciones.push(accion);
    }

    public void ordenarCanciones(Comparator<Cancion> comparador) {
        listaCancionesFavoritas.ordenar(comparador);
    }

    public void deshacer() {
        if (!acciones.isEmpty()) {
            Accion ultimaAccion = acciones.pop();
            if (ultimaAccion.getTipo() == Accion.Tipo.INSERCION) {
                eliminarCancion(ultimaAccion.getElemento());
            } else if (ultimaAccion.getTipo() == Accion.Tipo.ELIMINACION) {
                agregarCancion(ultimaAccion.getElemento());
            }
            accionesDesechas.push(ultimaAccion);
        }
    }

    public void rehacer() {
        if (!accionesDesechas.isEmpty()) {
            Accion ultimaAccion = accionesDesechas.pop();
            if (ultimaAccion.getTipo() == Accion.Tipo.INSERCION) {
                agregarCancion(ultimaAccion.getElemento());
            } else if (ultimaAccion.getTipo() == Accion.Tipo.ELIMINACION) {
                eliminarCancion(ultimaAccion.getElemento());
            }
            acciones.push(ultimaAccion);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;
        Usuario usuario = (Usuario) o;
        return getUsername().equals(usuario.getUsername()) && getPassword().equals(usuario.getPassword()) && getEmail().equals(usuario.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getPassword(), getEmail());
    }
}