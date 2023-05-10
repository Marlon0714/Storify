package co.edu.uniquindio.storify.model;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;

public class Usuario implements Serializable {
    private String username;
    private String password;
    private String email;
    private ListaCircular<Cancion> listaCancionesFavoritas;

    public Usuario(String username, String password, String email, ListaCircular<Cancion> listaCancionesFavoritas) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.listaCancionesFavoritas = listaCancionesFavoritas;
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

    public ListaCircular<Cancion> getCancionesFavoritas() {
        return listaCancionesFavoritas;
    }

    public void setCancionesFavoritas(ListaCircular<Cancion> listaCancionesFavoritas) {
        this.listaCancionesFavoritas = listaCancionesFavoritas;
    }

    public void agregarCancion(Cancion cancion) {
        listaCancionesFavoritas.insertar(cancion);
    }

    public void eliminarCancion(Cancion cancion) {
        listaCancionesFavoritas.eliminar(cancion);
    }
    public void ordenarCanciones(Comparator<Cancion> comparador) {
        listaCancionesFavoritas.ordenar(comparador);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;
        Usuario usuario = (Usuario) o;
        return getUsername().equals(usuario.getUsername()) && getPassword().equals(usuario.getPassword()) && Objects.equals(getEmail(), usuario.getEmail()) && Objects.equals(listaCancionesFavoritas, usuario.listaCancionesFavoritas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getPassword(), getEmail(), listaCancionesFavoritas);
    }
}
