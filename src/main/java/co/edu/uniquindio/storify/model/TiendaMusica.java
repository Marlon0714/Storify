package co.edu.uniquindio.storify.model;

import java.io.Serializable;
import java.util.*;


public class TiendaMusica implements Serializable {
    private HashMap<String,Usuario> usuarios;
    private ArbolBinario<Artista> artistas;
    public TiendaMusica() {
        this.usuarios = new HashMap<>();
        this.artistas = new ArbolBinario<>();
    }

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

    public ArbolBinario<Artista> getArtistas() {
        return artistas;
    }

    public void setArtistas(ArbolBinario<Artista> artistas) {
        this.artistas = artistas;
    }

    // Iniciar sesión
    public Usuario iniciarSesion(String username, String password) {
        // Buscar el usuario por nombre de usuario
        Usuario usuario = usuarios.get(username);

        // Verificar si el usuario existe y si la contraseña es correcta
        if (usuario != null && usuario.getPassword().equals(password)) {
            // Devolver el usuario si la autenticación es exitosa
            return usuario;
        }

        // Devolver null si el usuario no existe o si la autenticación falla
        return null;
    }

    // Agregar usuario
    public void agregarUsuario(Usuario usuario) {
        usuarios.put(usuario.getUsername(), usuario);
    }

    // Agregar artista
    public void agregarArtista(Artista artista) {
        artistas.insertar(artista);
    }

    // Buscar canciones que coinciden con al menos uno de los atributos proporcionados
    public ListaDobleEnlazada<Cancion> buscarCancionesOR(String[] atributos) {
        // Implementación de búsqueda de canciones
        // Crear una lista para almacenar las canciones encontradas
        ListaDobleEnlazada<Cancion> cancionesEncontradas = new ListaDobleEnlazada<>();

        // Crear dos hilos para buscar en el lado izquierdo y derecho del árbol binario
        Thread hiloIzquierdo = new Thread(() -> buscarCancionesORHelper(artistas.getRaiz(), atributos, cancionesEncontradas));
        Thread hiloDerecho = new Thread(() -> buscarCancionesORHelper(artistas.getRaiz(), atributos, cancionesEncontradas));

        // Iniciar los hilos y esperar a que terminen
        hiloIzquierdo.start();
        hiloDerecho.start();
        try {
            hiloIzquierdo.join();
            hiloDerecho.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            //Thread.currentThread().interrupt();
        }

        return cancionesEncontradas;
    }

    private void buscarCancionesORHelper(ArbolBinario.Nodo<Artista> nodo, String[] atributos, ListaDobleEnlazada<Cancion> cancionesEncontradas) {
        if (nodo == null) {
            return;
        }

        Artista artista = nodo.getValor();

        // Buscar canciones en el artista actual
        ListaDobleEnlazada<Cancion> cancionesArtista = artista.getListaCanciones();
        for (Cancion cancion : cancionesArtista) {
            // Verificar si la canción coincide con al menos uno de los atributos proporcionados
            for (String atributo : atributos) {
                if (cancion.getNombre().equalsIgnoreCase(atributo) || cancion.getGenero().equalsIgnoreCase(atributo)
                        || cancion.getAlbum().equalsIgnoreCase(atributo)) {
                    cancionesEncontradas.insertar(cancion);
                    break;
                }
            }
        }
        buscarCancionesORHelper(nodo.getIzq(), atributos, cancionesEncontradas);
        buscarCancionesORHelper(nodo.getDer(), atributos, cancionesEncontradas);
    }

    // Buscar canciones que coinciden con todos los atributos proporcionados
    public ListaDobleEnlazada<Cancion> buscarCancionesAND(String[] atributos) {
        // Implementación de búsqueda de canciones
        return null;
    }

    // Buscar artista
    public ListaDobleEnlazada<Cancion> buscarArtista(String nombreArtista) {
        // Implementación de búsqueda de canciones
        // Buscar el artista por nombre en el árbol binario
        Artista artistaABuscar = new Artista();
        artistaABuscar.setNombre(nombreArtista);
        Artista artista = artistas.buscar(artistaABuscar);

        // Verificar si el artista fue encontrado
        if (artista != null) {
            // Devolver la lista de canciones del artista
            return artista.getCanciones();
        } else {
            // Si el artista no fue encontrado, devolver una lista vacía
            return new ListaDobleEnlazada<>();
        }
    }

    // Guardar una canción en la lista del usuario
    public void guardarCancion(Usuario usuario, Cancion cancion) {
        usuario.agregarCancion(cancion);
    }

    // Eliminar una canción de la lista del usuario
    public void eliminarCancion(Usuario usuario, Cancion cancion) {
        usuario.eliminarCancion(cancion);
    }

    // Ordenar la lista de canciones de un usuario por cualquier atributo de la canción
    public void ordenarCanciones(Usuario usuario, Comparator<Cancion> comparador) {
        usuario.ordenarCanciones(comparador);
    }


    // Guardar datos
    public void guardarDatos() {
        // Implementación de guardado de datos en disco duro
    }

    // Cargar datos
    public void cargarDatos() {
        // Implementación de carga de datos desde disco duro
    }

}
