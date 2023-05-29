package co.edu.uniquindio.storify.model;

import co.edu.uniquindio.storify.exceptions.*;
import java.io.Serializable;
import java.util.*;

/**
 * Clase que representa una tienda de música.
 */
public class TiendaMusica implements Serializable {
    private HashMap<String,Usuario> usuarios;
    private ArbolBinario<Artista> artistas;

    /**
     * Constructor de la clase TiendaMusica.
     * Crea una nueva instancia de TiendaMusica inicializando los atributos usuarios y artistas.
     */
    public TiendaMusica() {
        this.usuarios = new HashMap<>();
        this.artistas = new ArbolBinario<>();
    }

    /**
     * Obtiene el mapa de usuarios de la tienda.
     *
     * @return El mapa de usuarios de la tienda.
     */
    public Map<String, Usuario> getUsuarios() {
        return usuarios;
    }

    /**
     * Establece el mapa de usuarios de la tienda.
     *
     * @param usuarios El mapa de usuarios a establecer.
     */
    public void setUsuarios(Map<String, Usuario> usuarios) {
        if (usuarios instanceof HashMap) {
            this.usuarios = (HashMap<String, Usuario>) usuarios;
        } else {
            this.usuarios = new HashMap<>(usuarios);
        }
    }

    /**
     * Obtiene el árbol binario de artistas de la tienda.
     *
     * @return El árbol binario de artistas de la tienda.
     */
    public ArbolBinario<Artista> getArtistas() {
        return artistas;
    }

    /**
     * Establece el árbol binario de artistas de la tienda.
     *
     * @param artistas El árbol binario de artistas a establecer.
     */
    public void setArtistas(ArbolBinario<Artista> artistas) {
        this.artistas = artistas;
    }

    /**
     * Método para iniciar sesión en la tienda.
     *
     * @param username El nombre de usuario del usuario que desea iniciar sesión.
     * @param password La contraseña del usuario que desea iniciar sesión.
     * @return El objeto Usuario si la autenticación es exitosa, o null si no es exitosa.
     */
    public Usuario iniciarSesion(String username, String password) {
        Usuario usuario = usuarios.get(username);

        if (usuario != null && usuario.getPassword().equals(password)) {
            return usuario;
        }

        return null;
    }

    /**
     * Método para crear un nuevo usuario en la tienda.
     *
     * @param username              El nombre de usuario del nuevo usuario.
     * @param password              La contraseña del nuevo usuario.
     * @param email                 El correo electrónico del nuevo usuario.
     * @param listaCancionesFavoritas La lista de canciones favoritas del nuevo usuario.
     * @throws ExistingUserException Si el nombre de usuario ya está registrado.
     */
    public void crearUsuario(String username, String password, String email, ListaCircular<Cancion> listaCancionesFavoritas) throws ExistingUserException {
        for (Map.Entry<String, Usuario> usuario : usuarios.entrySet()) {
            String clave = usuario.getKey();
            if(clave.equals(username)){
                throw new ExistingUserException("Usuario ya registrado");
            }
        }
        Usuario nuevoUsuario = new Usuario(username, password,email,listaCancionesFavoritas);
        agregarUsuario(nuevoUsuario);
    }
    /**
     * Crea un nuevo artista y lo agrega al gestor de música.
     *
     * @param codigo         el código del artista
     * @param nombre         el nombre del artista
     * @param nacionalidad   la nacionalidad del artista
     * @param esGrupo        indica si el artista es un grupo musical
     * @param listaCanciones la lista de canciones del artista
     * @throws ExistingArtistException si el artista ya está registrado
     */
    public void crearArtista(String codigo, String nombre, String nacionalidad, boolean esGrupo, ListaDobleEnlazada<Cancion> listaCanciones) throws ExistingArtistException {
        Artista nuevoArtista = new Artista(codigo, nombre, nacionalidad, esGrupo, listaCanciones);
        if (nuevoArtista.equals(artistas.buscar(nuevoArtista))) {
            throw new ExistingArtistException("Artista ya registrado");
        }
        agregarArtista(nuevoArtista);
    }

    /**
     * Crea una nueva canción y la agrega al gestor de música.
     *
     * @param nombre      el nombre de la canción
     * @param codigoArtista el código del artista al que pertenece la canción
     * @param album       el álbum al que pertenece la canción
     * @param caratula    la carátula de la canción
     * @param anio        el año de lanzamiento de la canción
     * @param duracion    la duración de la canción en segundos
     * @param genero      el género de la canción
     * @param urlYoutube  la URL de YouTube de la canción
     * @throws ExistingSongException si la canción ya está registrada
     */
    public void crearCancion(String nombre, String codigoArtista, String album, String caratula, int anio, int duracion, String genero, String urlYoutube) throws ExistingSongException {
        Artista artista = buscarArtista(codigoArtista);
        if (artista == null) {
            return;
        }
        for (Cancion cancion : artista.getCanciones()) {
            if (cancion.getNombre().equals(nombre)) {
                throw new ExistingSongException("Cancion ya registrada");
            }
        }
        Cancion nuevaCancion = new Cancion(generarCodigoUnico(), nombre, codigoArtista, album, caratula, anio, duracion, genero, urlYoutube);
        artista.agregarCancion(nuevaCancion);
        //System.out.println(urlYoutube);
    }

    /**
     * Genera un código único para identificar una canción.
     *
     * @return el código único generado
     */
    private String generarCodigoUnico() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    /**
     * Busca un artista por su código.
     *
     * @param codigo el código del artista a buscar
     * @return el artista encontrado o null si no se encuentra
     */
    public Artista buscarArtista(String codigo){
        for(Artista artista : artistas){
            if(codigo.equals(artista.getCodigo())){
                return artista;
            }
        }
        return null;
    }

    /**
     * Busca una canción por su código.
     *
     * @param codigo el código de la canción a buscar
     * @return la canción encontrada o null si no se encuentra
     */
    public Cancion buscarCancion(String codigo) {
        for(Artista artista : artistas){
            for(Cancion cancion: artista.getListaCanciones()){
                if(cancion.getCodigo().equals(codigo)){
                    return cancion;
                }
            }
        }
        return null;
    }

    /**
     * Busca una canción por su código y el código del artista.
     *
     * @param codigo el código de la canción a buscar
     * @param codigoArtista el código del artista a buscar
     * @return la canción encontrada o null si no se encuentra
     */
    private Cancion buscarCancion(String codigo, String codigoArtista) {
        Artista artista = buscarArtista(codigoArtista);
        if(artista == null){
            return null;
        }
        for(Cancion cancion: artista.getCanciones()){
            if(cancion.getCodigo().equals(codigo)){
                return cancion;
            }
        }
        return null;
    }

    /**
     * Elimina un usuario de la lista de usuarios.
     *
     * @param usuario el usuario a eliminar
     */
    public void eliminarUsuario(Usuario usuario) {
        usuarios.remove(usuario.getUsername());
    }

    /**
     * Elimina un artista de la lista de artistas.
     *
     * @param artista el artista a eliminar
     */
    public void eliminarArtista(Artista artista) {
        artistas.eliminar(artista);
    }

    /**
     * Elimina una canción de la lista de canciones de un artista.
     *
     * @param cancion la canción a eliminar
     */
    public void eliminarCancion(Cancion cancion) {
        Artista artista = buscarArtista(cancion.getCodigoArtista());
        if (artista == null) {
            return;
        }
        for(Usuario usuario : usuarios.values()){
            ListaCircular<Cancion> listaCancionesFavoritas = usuario.getListaCancionesFavoritas();
            for (Cancion cancionA:listaCancionesFavoritas) {
                if(cancionA.getCodigo().equals(cancion.getCodigo())){
                    usuario.eliminarCancion(cancion);
                }
            }
        }
        artista.eliminarCancion(cancion);
    }

    /**
     * Agrega un usuario a la lista de usuarios.
     *
     * @param usuario el usuario a agregar
     */
    public void agregarUsuario(Usuario usuario) {
        usuarios.put(usuario.getUsername(), usuario);
    }

    /**
     * Agrega un artista a la lista de artistas.
     *
     * @param artista el artista a agregar
     */
    public void agregarArtista(Artista artista) {
        artistas.insertar(artista);
    }
    /**
     * Busca canciones que coinciden con al menos uno de los atributos proporcionados.
     *
     * @param cancionABuscar la canción con los atributos a buscar
     * @return una lista de canciones que coinciden con al menos uno de los atributos proporcionados
     */
    public ListaDobleEnlazada<Cancion> buscarCancionesOR(Cancion cancionABuscar) {
        // Implementación de búsqueda de canciones
        // Crear una lista para almacenar las canciones encontradas
        ListaDobleEnlazada<Cancion> cancionesEncontradas = new ListaDobleEnlazada<>();

        // Verificar si la canción coincide con al menos uno de los atributos en el nodo raíz
        for (Cancion cancion : artistas.getRaiz().getValor().getListaCanciones()) {
            if (cancion.getNombre().equalsIgnoreCase(cancionABuscar.getNombre())
                    || cancion.getGenero().equalsIgnoreCase(cancionABuscar.getGenero())
                    || cancion.getAlbum().equalsIgnoreCase(cancionABuscar.getAlbum())
                    || cancion.getAnio() == cancionABuscar.getAnio()
                    || cancion.getCodigoArtista().equalsIgnoreCase(cancionABuscar.getCodigoArtista())) {
                cancionesEncontradas.insertar(cancion);
                break;
            }
        }

        // Crear dos hilos para buscar en el lado izquierdo y derecho del árbol binario
        Thread hiloIzquierdo = new Thread(() -> {
            ListaDobleEnlazada<Cancion> cancionesEncontradasIzquierdo = buscarCancionesORHelper(artistas.getRaiz().getIzq(), cancionABuscar);
            // Procesar las canciones encontradas en el lado izquierdo, si es necesario
            synchronized (cancionesEncontradas) {
                cancionesEncontradas.addAll(cancionesEncontradasIzquierdo);
            }
        });

        Thread hiloDerecho = new Thread(() -> {
            ListaDobleEnlazada<Cancion> cancionesEncontradasDerecho = buscarCancionesORHelper(artistas.getRaiz().getDer(), cancionABuscar);
            // Procesar las canciones encontradas en el lado derecho, si es necesario
            synchronized (cancionesEncontradas) {
                cancionesEncontradas.addAll(cancionesEncontradasDerecho);
            }
        });

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

    /**
     * Método recursivo auxiliar utilizado para buscar canciones en un árbol binario de artistas
     * que cumplan con al menos uno de los atributos proporcionados.
     *
     * @param nodo            El nodo actual del árbol binario.
     * @param cancionABuscar  La canción que se desea buscar.
     * @return Una lista doblemente enlazada que contiene las canciones encontradas.
     */
    private ListaDobleEnlazada<Cancion> buscarCancionesORHelper(ArbolBinario.Nodo<Artista> nodo, Cancion cancionABuscar) {
        ListaDobleEnlazada<Cancion> cancionesEncontradas = new ListaDobleEnlazada<>();

        // Caso base: el nodo es nulo, no hay más elementos para buscar
        if (nodo == null) {
            return cancionesEncontradas;
        }

        // Buscar canciones en el artista actual
        for (Cancion cancion : nodo.getValor().getListaCanciones()) {
            // Verificar si la canción coincide con al menos uno de los atributos proporcionados
            if (cancion.getNombre().equalsIgnoreCase(cancionABuscar.getNombre())
                    || cancion.getGenero().equalsIgnoreCase(cancionABuscar.getGenero())
                    || cancion.getAlbum().equalsIgnoreCase(cancionABuscar.getAlbum())
                    || cancion.getAnio() == cancionABuscar.getAnio()
                    || cancion.getCodigoArtista().equalsIgnoreCase(cancionABuscar.getCodigoArtista())) {
                cancionesEncontradas.insertar(cancion);
                break;
            }
        }

        // Realizar la búsqueda en el subárbol izquierdo
        ListaDobleEnlazada<Cancion> cancionesEncontradasIzquierdo = buscarCancionesORHelper(nodo.getIzq(), cancionABuscar);
        // Realizar la búsqueda en el subárbol derecho
        ListaDobleEnlazada<Cancion> cancionesEncontradasDerecho = buscarCancionesORHelper(nodo.getDer(), cancionABuscar);

        // Agregar las canciones encontradas en ambos subárboles a la lista final
        cancionesEncontradas.addAll(cancionesEncontradasIzquierdo);
        cancionesEncontradas.addAll(cancionesEncontradasDerecho);

        return cancionesEncontradas;
    }

    /**
     * Busca canciones que coincidan con los atributos proporcionados utilizando una operación lógica AND.
     *
     * @param cancionABuscar La canción con los atributos a buscar.
     * @return Una lista doblemente enlazada que contiene las canciones encontradas.
     */
    public ListaDobleEnlazada<Cancion> buscarCancionesAND(Cancion cancionABuscar) {
        // Implementación de búsqueda de canciones
        // Crear una lista para almacenar las canciones encontradas
        ListaDobleEnlazada<Cancion> cancionesEncontradas = new ListaDobleEnlazada<>();

        // Buscar canciones en el artista actual
        for (Cancion cancion : artistas.getRaiz().getValor().getListaCanciones()) {
            // Verificar si la canción coincide con los atributos proporcionados
            boolean coincide = true;
            boolean nombreNOCoincide = cancionABuscar.getNombre() != null && !cancionABuscar.getNombre().equalsIgnoreCase(cancion.getNombre());
            boolean generoNOCoincide = cancionABuscar.getGenero() != null && !cancionABuscar.getGenero().equalsIgnoreCase(cancion.getGenero());
            boolean albumNOCoincide = cancionABuscar.getAlbum() != null && !cancionABuscar.getAlbum().equalsIgnoreCase(cancion.getAlbum());
            boolean anioNOCoincide = cancionABuscar.getAnio() != -1 && cancionABuscar.getAnio() != cancion.getAnio();
            boolean artistaNOCoincide = cancionABuscar.getCodigoArtista() != null && !cancionABuscar.getCodigoArtista().equalsIgnoreCase(cancion.getCodigoArtista());

            if (nombreNOCoincide || generoNOCoincide || albumNOCoincide || anioNOCoincide || artistaNOCoincide) {
                coincide = false;
            }
            if (coincide) {
                cancionesEncontradas.insertar(cancion);
            }
        }

        // Crear dos hilos para buscar en el lado izquierdo y derecho del árbol binario
        Thread hiloIzquierdo = new Thread(() -> {
            ListaDobleEnlazada<Cancion> cancionesEncontradasIzquierdo = buscarCancionesANDHelper(artistas.getRaiz().getIzq(), cancionABuscar);
            // Procesar las canciones encontradas en el lado izquierdo, si es necesario
            synchronized (cancionesEncontradas) {
                cancionesEncontradas.addAll(cancionesEncontradasIzquierdo);
            }
        });

        Thread hiloDerecho = new Thread(() -> {
            ListaDobleEnlazada<Cancion> cancionesEncontradasDerecho = buscarCancionesANDHelper(artistas.getRaiz().getDer(), cancionABuscar);
            // Procesar las canciones encontradas en el lado derecho, si es necesario
            synchronized (cancionesEncontradas) {
                cancionesEncontradas.addAll(cancionesEncontradasDerecho);
            }
        });

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

    /**
     * Método auxiliar utilizado por el método buscarCancionesAND para buscar canciones en un subárbol.
     *
     * @param nodo            El nodo raíz del subárbol.
     * @param cancionABuscar  La canción con los atributos a buscar.
     * @return Una lista doblemente enlazada que contiene las canciones encontradas en el subárbol.
     */
    private ListaDobleEnlazada<Cancion> buscarCancionesANDHelper(ArbolBinario.Nodo<Artista> nodo, Cancion cancionABuscar) {
        ListaDobleEnlazada<Cancion> cancionesEncontradas = new ListaDobleEnlazada<>();
        if (nodo == null) {
            return cancionesEncontradas;
        }

        // Buscar canciones en el artista actual
        for (Cancion cancion : nodo.getValor().getListaCanciones()) {
            // Verificar si la canción coincide con los atributos proporcionados
            boolean coincide = true;
            boolean nombreNOCoincide = cancionABuscar.getNombre() != null && !cancionABuscar.getNombre().equalsIgnoreCase(cancion.getNombre());
            boolean generoNOCoincide = cancionABuscar.getGenero() != null && !cancionABuscar.getGenero().equalsIgnoreCase(cancion.getGenero());
            boolean albumNOCoincide = cancionABuscar.getAlbum() != null && !cancionABuscar.getAlbum().equalsIgnoreCase(cancion.getAlbum());
            boolean anioNOCoincide = cancionABuscar.getAnio() != -1 && cancionABuscar.getAnio() != cancion.getAnio();
            boolean artistaNOCoincide = cancionABuscar.getCodigoArtista() != null && !cancionABuscar.getCodigoArtista().equalsIgnoreCase(cancion.getCodigoArtista());

            if (nombreNOCoincide || generoNOCoincide || albumNOCoincide || anioNOCoincide || artistaNOCoincide) {
                coincide = false;
            }
            if (coincide) {
                cancionesEncontradas.insertar(cancion);
            }
        }
        ListaDobleEnlazada<Cancion> cancionesEncontradasIzquierdo = buscarCancionesANDHelper(nodo.getIzq(), cancionABuscar);
        ListaDobleEnlazada<Cancion> cancionesEncontradasDerecho = buscarCancionesANDHelper(nodo.getDer(), cancionABuscar);

        cancionesEncontradas.addAll(cancionesEncontradasIzquierdo);
        cancionesEncontradas.addAll(cancionesEncontradasDerecho);

        return cancionesEncontradas;
    }


    /**
     * Método para buscar canciones de un artista.
     *
     * @param nombreArtista El nombre del artista cuyas canciones se desean buscar.
     * @return Una lista doblemente enlazada de tipo Cancion que contiene las canciones del artista,
     *         o una lista vacía si el artista no fue encontrado.
     */
    public ListaDobleEnlazada<Cancion> buscarCancionesArtista(String nombreArtista) {
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

    /**
     * Método para guardar una canción en la lista del usuario.
     *
     * @param usuario  El usuario al que se le desea guardar la canción.
     * @param cancion  La canción que se desea guardar.
     */
    public void guardarCancion(Usuario usuario, Cancion cancion) {
        usuario.agregarCancion(cancion);
    }

    /**
     * Método para eliminar una canción de la lista del usuario.
     *
     * @param usuario El usuario al que se le desea eliminar la canción.
     * @param cancion La canción que se desea eliminar.
     */
    public void eliminarCancion(Usuario usuario, Cancion cancion) {
        usuario.eliminarCancion(cancion);
    }

    /**
     * Método para deshacer la última acción realizada por el usuario.
     *
     * @param usuario El usuario que desea deshacer la acción.
     */
    public void deshacer(Usuario usuario) {
        usuario.deshacer();
    }

    /**
     * Método para rehacer la última acción deshecha por el usuario.
     *
     * @param usuario El usuario que desea rehacer la acción.
     */
    public void rehacer(Usuario usuario) {
        usuario.rehacer();
    }

    /**
     * Método para ordenar la lista de canciones de un usuario por cualquier atributo de la canción.
     *
     * @param usuario    El usuario cuya lista de canciones se desea ordenar.
     * @param comparador El comparador utilizado para ordenar las canciones.
     */
    public void ordenarCanciones(Usuario usuario, Comparator<Cancion> comparador) {
        usuario.ordenarCanciones(comparador);
    }

    /**
     * Método para consultar el género con más canciones en la Tienda.
     *
     * @return El género con más canciones en la Tienda.
     */
    public String consultarGeneroMasCanciones() {
        Map<String, Integer> generoCount = new HashMap<>();

        // Contar las canciones por género
        for (Artista artista : artistas) {
            for (Cancion cancion : artista.getListaCanciones()) {
                String genero = cancion.getGenero();
                generoCount.put(genero, generoCount.getOrDefault(genero, 0) + 1);
            }
        }

        // Encontrar el género con más canciones
        String generoMasCanciones = null;
        int maxCount = 0;

        for (Map.Entry<String, Integer> entry : generoCount.entrySet()) {
            String genero = entry.getKey();
            int count = entry.getValue();

            if (count > maxCount) {
                maxCount = count;
                generoMasCanciones = genero;
            }
        }

        return generoMasCanciones;
    }

    /**
     * Método para obtener el artista más popular en la Tienda.
     *
     * @return El artista más popular en la Tienda.
     */
    public Artista obtenerArtistaMasPopular() {
        Map<Artista, Integer> contadorArtistas = new HashMap<>();

        // Recorrer la lista de usuarios
        for (Usuario usuario : usuarios.values()) {
            // Obtener la lista de canciones favoritas del usuario
            ListaCircular<Cancion> listaCancionesFavoritas = usuario.getListaCancionesFavoritas();

            // Recorrer la lista de canciones favoritas del usuario
            for (Cancion cancion : listaCancionesFavoritas) {
                // Obtener el artista de la canción
                Artista artista = buscarArtista(cancion.getCodigoArtista());

                // Incrementar el contador del artista
                contadorArtistas.put(artista, contadorArtistas.getOrDefault(artista, 0) + 1);
            }
        }

        // Buscar el artista con el contador más alto
        Artista artistaMasPopular = null;
        int maxContador = 0;

        for (Map.Entry<Artista, Integer> entry : contadorArtistas.entrySet()) {
            Artista artista = entry.getKey();
            int contador = entry.getValue();

            if (contador > maxContador) {
                maxContador = contador;
                artistaMasPopular = artista;
                System.out.println(artista + " " + contador);
            }
        }

        return artistaMasPopular;
    }

    /**
     * Método para obtener todas las canciones de la Tienda.
     *
     * @return Una lista doblemente enlazada de tipo Cancion que contiene todas las canciones de la Tienda.
     */
    public ListaDobleEnlazada<Cancion> obtenerCanciones() {
        ListaDobleEnlazada<Cancion> canciones = new ListaDobleEnlazada<>();
        for (Artista artista : artistas) {
            canciones.addAll(artista.getListaCanciones());
        }
        return canciones;
    }
}