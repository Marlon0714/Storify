package co.edu.uniquindio.storify.model;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;

/**
 * Clase que representa a un usuario en la tienda de música.
 */
public class Usuario implements Serializable {
    private String username;
    private String password;
    private String email;
    private ListaCircular<Cancion> listaCancionesFavoritas;
    private Pila<Accion> acciones;
    private Pila<Accion> accionesDesechas;

    /**
     * Constructor de la clase Usuario.
     *
     * @param username              El nombre de usuario del usuario.
     * @param password              La contraseña del usuario.
     * @param email                 El correo electrónico del usuario.
     * @param listaCancionesFavoritas La lista de canciones favoritas del usuario.
     */
    public Usuario(String username, String password, String email, ListaCircular<Cancion> listaCancionesFavoritas) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.listaCancionesFavoritas = listaCancionesFavoritas;
        this.acciones = new Pila<>();
        this.accionesDesechas = new Pila<>();
    }

    /**
     * Constructor vacío de la clase Usuario.
     */
    public Usuario() {
    }

    /**
     * Obtiene el nombre de usuario del usuario.
     *
     * @return El nombre de usuario del usuario.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Establece el nombre de usuario del usuario.
     *
     * @param username El nombre de usuario a establecer.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Obtiene la contraseña del usuario.
     *
     * @return La contraseña del usuario.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Establece la contraseña del usuario.
     *
     * @param password La contraseña a establecer.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Obtiene el correo electrónico del usuario.
     *
     * @return El correo electrónico del usuario.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el correo electrónico del usuario.
     *
     * @param email El correo electrónico a establecer.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtiene la lista de canciones favoritas del usuario.
     *
     * @return La lista de canciones favoritas del usuario.
     */
    public ListaCircular<Cancion> getListaCancionesFavoritas() {
        return listaCancionesFavoritas;
    }

    /**
     * Establece la lista de canciones favoritas del usuario.
     *
     * @param listaCancionesFavoritas La lista de canciones favoritas a establecer.
     */
    public void setListaCancionesFavoritas(ListaCircular<Cancion> listaCancionesFavoritas) {
        this.listaCancionesFavoritas = listaCancionesFavoritas;
    }

    /**
     * Obtiene la pila de acciones del usuario.
     *
     * @return La pila de acciones del usuario.
     */
    public Pila<Accion> getAcciones() {
        return acciones;
    }

    /**
     * Establece la pila de acciones del usuario.
     *
     * @param acciones La pila de acciones a establecer.
     */
    public void setAcciones(Pila<Accion> acciones) {
        this.acciones = acciones;
    }

    /**
     * Obtiene la pila de acciones desechadas del usuario.
     *
     * @return La pila de acciones desechadas del usuario.
     */
    public Pila<Accion> getAccionesDesechas() {
        return accionesDesechas;
    }

    /**
     * Establece la pila de acciones desechadas del usuario.
     *
     * @param accionesDesechas La pila de acciones desechadas a establecer.
     */
    public void setCancionesEliminadas(Pila<Accion> accionesDesechas) {
        this.accionesDesechas = accionesDesechas;
    }

    /**
     * Agrega una canción a la lista de canciones favoritas del usuario.
     * También registra la acción de inserción en la pila de acciones.
     *
     * @param cancion La canción a agregar.
     */
    public void agregarCancion(Cancion cancion) {
        listaCancionesFavoritas.insertar(cancion);
        Accion accion = new Accion(Accion.Tipo.INSERCION, cancion);
        acciones.push(accion);
    }

    /**
     * Elimina una canción de la lista de canciones favoritas del usuario.
     * También registra la acción de eliminación en la pila de acciones.
     *
     * @param cancion La canción a eliminar.
     */
    public void eliminarCancion(Cancion cancion) {
        listaCancionesFavoritas.eliminar(cancion);
        Accion accion = new Accion(Accion.Tipo.ELIMINACION, cancion);
        acciones.push(accion);
    }

    /**
     * Ordena la lista de canciones favoritas del usuario utilizando el comparador especificado.
     *
     * @param comparador El comparador utilizado para ordenar las canciones.
     */
    public void ordenarCanciones(Comparator<Cancion> comparador) {
        listaCancionesFavoritas.ordenar(comparador);
    }

    /**
     * Deshace la última acción realizada por el usuario.
     * Si la acción fue una inserción, se elimina la canción agregada.
     * Si la acción fue una eliminación, se agrega la canción eliminada.
     * La acción deshecha se registra en la pila de acciones desechadas.
     */
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

    /**
     * Rehace la última acción deshecha por el usuario.
     * Si la acción deshecha fue una inserción, se agrega la canción nuevamente.
     * Si la acción deshecha fue una eliminación, se elimina la canción nuevamente.
     * La acción rehacer se registra en la pila de acciones.
     */
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

    /**
     * Compara si este objeto Usuario es igual a otro objeto.
     *
     * @param o El objeto a comparar.
     * @return true si los objetos son iguales, false de lo contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;
        Usuario usuario = (Usuario) o;
        return getUsername().equals(usuario.getUsername()) && getPassword().equals(usuario.getPassword()) && getEmail().equals(usuario.getEmail());
    }

    /**
     * Calcula el código hash para este objeto Usuario.
     *
     * @return El código hash calculado.
     */
    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getPassword(), getEmail());
    }
}
