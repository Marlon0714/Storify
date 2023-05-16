package co.edu.uniquindio.storify.model;

import co.edu.uniquindio.storify.exceptions.*;
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

    //Crear Usuario
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
    //Crear Artista
    public void crearArtista(String codigo, String nombre, String nacionalidad, boolean esGrupo, ListaDobleEnlazada<Cancion> listaCanciones) throws ExistingArtistException {
        Artista nuevoArtista = new Artista(codigo, nombre,nacionalidad,esGrupo,listaCanciones);
        if(nuevoArtista.equals(artistas.buscar(nuevoArtista))){
            throw new ExistingArtistException("Artista ya registrado");
        }
        agregarArtista(nuevoArtista);
    }
    //Crear Cancion
    public void crearCancion(String codigo, String nombre,String codigoArtista, String album, String caratula, int anio, int duracion, String genero, String urlYoutube) throws ExistingSongException {
        Artista artista = buscarArtista(codigoArtista);
        if(artista == null){
            return;
        }
        Cancion nuevaCancion = new Cancion(codigo, nombre,codigoArtista,album,caratula,anio,duracion,genero,urlYoutube);
        if(nuevaCancion.equals(buscarCancion(codigo, codigoArtista))){
            throw new ExistingSongException("Cancion ya registrada");
        }
        artista.agregarCancion(nuevaCancion);
    }

    public Artista buscarArtista(String codigo){
        for(Artista artista : artistas){
            if(codigo.equals(artista.getCodigo())){
                return artista;
            }
        }
        return null;
    }

    public Cancion buscarCancion(String codigo) {
        for(Artista artista : artistas){
            for(Cancion cancion: artista.getCanciones()){
                if(cancion.getCodigo().equals(codigo)){
                    return cancion;
                }
            }
        }
        return null;
    }

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

    //Eliminar Usuario
    public void eliminarUsuario(Usuario usuario) {
        usuarios.remove(usuario.getUsername());
    }
    //Eliminar Artista
    public void eliminarArtista(Artista artista) {
        artistas.eliminar(artista);
    }
    //Eliminar Cancion
    public void eliminarCancion(Cancion cancion) {
        Artista artista = buscarArtista(cancion.getCodigoArtista());
        if (artista == null) {
            return;
        }
        artista.eliminarCancion(cancion);
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
    public ListaDobleEnlazada<Cancion> buscarCancionesOR(Cancion cancionABuscar) {
        // Implementación de búsqueda de canciones
        // Crear una lista para almacenar las canciones encontradas
        ListaDobleEnlazada<Cancion> cancionesEncontradas = new ListaDobleEnlazada<>();

        // Crear dos hilos para buscar en el lado izquierdo y derecho del árbol binario
        Thread hiloIzquierdo = new Thread(() -> {
            ListaDobleEnlazada<Cancion> cancionesEncontradasIzquierdo = buscarCancionesORHelper(artistas.getRaiz().getIzq(), cancionABuscar,cancionesEncontradas);
            // Procesar las canciones encontradas en el lado izquierdo, si es necesario
            synchronized (cancionesEncontradas) {
                cancionesEncontradas.addAll(cancionesEncontradasIzquierdo);
            }
        });

        Thread hiloDerecho = new Thread(() -> {
            ListaDobleEnlazada<Cancion> cancionesEncontradasDerecho = buscarCancionesORHelper(artistas.getRaiz().getDer(), cancionABuscar,cancionesEncontradas);
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

    private ListaDobleEnlazada<Cancion> buscarCancionesORHelper(ArbolBinario.Nodo<Artista> nodo, Cancion cancionABuscar, ListaDobleEnlazada<Cancion> cancionesEncontradas) {
        if (nodo == null) {
            return cancionesEncontradas;
        }

        // Buscar canciones en el artista actual
        for (Cancion cancion : nodo.getValor().getListaCanciones()) {
            // Verificar si la canción coincide con al menos uno de los atributos proporcionados
            if (cancion.getNombre().equalsIgnoreCase(cancionABuscar.getNombre()) || cancion.getGenero().equalsIgnoreCase(cancionABuscar.getGenero())
                    || cancion.getAlbum().equalsIgnoreCase(cancionABuscar.getAlbum()) || cancion.getAnio() == (cancionABuscar.getAnio())) {
                cancionesEncontradas.insertar(cancion);
                break;
            }

        }
        cancionesEncontradas = buscarCancionesORHelper(nodo.getIzq(), cancionABuscar, cancionesEncontradas);
        cancionesEncontradas = buscarCancionesORHelper(nodo.getDer(), cancionABuscar, cancionesEncontradas);
        return cancionesEncontradas;
    }

    // Buscar canciones que coinciden con todos los atributos proporcionados
    public ListaDobleEnlazada<Cancion> buscarCancionesAND(Cancion cancionABuscar) {
        // Implementación de búsqueda de canciones
        // Crear una lista para almacenar las canciones encontradas
        ListaDobleEnlazada<Cancion> cancionesEncontradas = new ListaDobleEnlazada<>();

        // Crear dos hilos para buscar en el lado izquierdo y derecho del árbol binario
        Thread hiloIzquierdo = new Thread(() -> {
            ListaDobleEnlazada<Cancion> cancionesEncontradasIzquierdo = buscarCancionesANDHelper(artistas.getRaiz().getIzq(), cancionABuscar,cancionesEncontradas);
            // Procesar las canciones encontradas en el lado izquierdo, si es necesario
            synchronized (cancionesEncontradas) {
                cancionesEncontradas.addAll(cancionesEncontradasIzquierdo);
            }
        });

        Thread hiloDerecho = new Thread(() -> {
            ListaDobleEnlazada<Cancion> cancionesEncontradasDerecho = buscarCancionesANDHelper(artistas.getRaiz().getDer(), cancionABuscar,cancionesEncontradas);
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

    private ListaDobleEnlazada<Cancion> buscarCancionesANDHelper(ArbolBinario.Nodo<Artista> nodo, Cancion cancionABuscar, ListaDobleEnlazada<Cancion> cancionesEncontradas) {
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

            if (nombreNOCoincide || generoNOCoincide || albumNOCoincide || anioNOCoincide) {
                coincide = false;
            }
            if(coincide){
                cancionesEncontradas.insertar(cancion);
            }
        }
        cancionesEncontradas = buscarCancionesANDHelper(nodo.getIzq(), cancionABuscar, cancionesEncontradas);
        cancionesEncontradas = buscarCancionesANDHelper(nodo.getDer(), cancionABuscar, cancionesEncontradas);
        return cancionesEncontradas;
    }

    // Buscar canciones de un artista
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

    // Guardar una canción en la lista del usuario
    public void guardarCancion(Usuario usuario, Cancion cancion) {
        usuario.agregarCancion(cancion);
    }

    // Eliminar una canción de la lista del usuario
    public void eliminarCancion(Usuario usuario, Cancion cancion) {
        usuario.eliminarCancion(cancion);
    }

    public void deshacer(Usuario usuario) {usuario.deshacer();}

    public void rehacer(Usuario usuario) {
        usuario.rehacer();
    }

    // Ordenar la lista de canciones de un usuario por cualquier atributo de la canción
    public void ordenarCanciones(Usuario usuario, Comparator<Cancion> comparador) {
        usuario.ordenarCanciones(comparador);
    }

    // Consultar el género con más canciones en la Tienda
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
            }
        }

        return artistaMasPopular;
    }

}
