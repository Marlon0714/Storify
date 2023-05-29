package co.edu.uniquindio.storify.persistencia;

import co.edu.uniquindio.storify.model.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Clase que proporciona métodos para la persistencia de datos de la tienda de música.
 */
public class Persistencia {
    private static final String RUTA_ARCHIVO_TIENDA_MUSICA_XML = "src/main/java/co/edu/uniquindio/storify/persistencia/persistencia.dat";

    /**
     * Carga los artistas desde un archivo y los agrega a un árbol binario.
     *
     * @param archivo   El archivo que contiene los datos de los artistas.
     * @param artistas  El árbol binario donde se agregarán los artistas.
     * @return El árbol binario actualizado con los artistas cargados.
     */
    public static ArbolBinario<Artista> cargarArtistas(String archivo, ArbolBinario<Artista> artistas) {

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            boolean seccionArtistas = false;

            while ((linea = br.readLine()) != null) {
                if (linea.startsWith("#Artistas")) {
                    seccionArtistas = true;
                } else if (linea.startsWith("#Canciones")) {
                    seccionArtistas = false;
                } else if (seccionArtistas) {
                    String[] datosArtista = linea.split(";");
                    if (datosArtista.length == 4) {
                        String codigoArtista = datosArtista[0];
                        String nombreArtista = datosArtista[1];
                        String nacionalidad = datosArtista[2];
                        boolean esGrupo = Boolean.parseBoolean(datosArtista[3]);

                        // Crear el objeto Artista y almacenarlo en tu estructura de datos
                        Artista artista = new Artista(codigoArtista, nombreArtista, nacionalidad, esGrupo, new ListaDobleEnlazada<>());
                        // Agregar el artista a tu estructura de datos (por ejemplo, un árbol binario)
                        artistas.insertar(artista);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return artistas;
    }

    /**
     * Carga las canciones desde un archivo y las agrega a un árbol binario de artistas.
     *
     * @param archivo   El archivo que contiene los datos de las canciones.
     * @param artistas  El árbol binario de artistas donde se agregarán las canciones.
     * @return El árbol binario de artistas actualizado con las canciones cargadas.
     */
    public static ArbolBinario<Artista> cargarCanciones(String archivo, ArbolBinario<Artista> artistas) {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            boolean seccionCanciones = false;

            while ((linea = br.readLine()) != null) {
                if (linea.startsWith("#Canciones")) {
                    seccionCanciones = true;
                } else if (seccionCanciones) {
                    String[] datosCancion = linea.split(";");
                    if (datosCancion.length == 8) {
                        String nombreArtista = datosCancion[0];
                        String nombreCancion = datosCancion[1];
                        String nombreAlbum = datosCancion[2];
                        int anio = Integer.parseInt(datosCancion[3]);
                        int duracion = Integer.parseInt(datosCancion[4]);
                        String genero = datosCancion[5];
                        String urlCancion = datosCancion[6];
                        String rutaImagen = datosCancion[7];

                        // Obtener el artista correspondiente de tu estructura de datos
                        Artista artista = new Artista();
                        artista.setNombre(nombreArtista);
                        artista = artistas.buscar(artista);
                        if (artista != null) {
                            // Crear el objeto Cancion y agregarlo a la lista de canciones del artista
                            Cancion cancion = new Cancion();
                            cancion.setNombre(nombreCancion);
                            cancion.setAlbum(nombreAlbum);
                            cancion.setAnio(anio);
                            cancion.setDuracion(duracion);
                            cancion.setGenero(genero);
                            cancion.setUrlYoutube(urlCancion);
                            cancion.setCodigoArtista(artista.getCodigo());
                            cancion.setCaratula(rutaImagen);
                            artista.getListaCanciones().insertar(cancion);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return artistas;
    }

    /**
     * Carga el recurso binario de la tienda de música.
     *
     * @return El objeto TiendaMusica cargado desde el recurso binario.
     */
    public static TiendaMusica cargarRecursoBinario() {
        TiendaMusica tiendaMusica = null;
        try {
            CargarRecursoBinario hiloCargarRecursoSerializado = new CargarRecursoBinario(RUTA_ARCHIVO_TIENDA_MUSICA_XML);
            hiloCargarRecursoSerializado.start();
            hiloCargarRecursoSerializado.join();
            tiendaMusica = (TiendaMusica) hiloCargarRecursoSerializado.getAux();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tiendaMusica;
    }

    /**
     * Guarda el recurso binario de la tienda de música.
     *
     * @param tiendaMusica El objeto TiendaMusica que se desea guardar en el recurso binario.
     */
    public static void guardarRecursoBinario(TiendaMusica tiendaMusica) {
        try {
            GuardarRecursoBinario hiloGuardarRecursoBinario = new GuardarRecursoBinario(RUTA_ARCHIVO_TIENDA_MUSICA_XML, tiendaMusica);
            hiloGuardarRecursoBinario.start();
            hiloGuardarRecursoBinario.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
