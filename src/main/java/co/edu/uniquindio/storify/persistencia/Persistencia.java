package co.edu.uniquindio.storify.persistencia;

import co.edu.uniquindio.storify.model.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Persistencia {
    private static final String RUTA_ARCHIVO_TIENDA_MUSICA_XML = null;
    static ArbolBinario<Artista> artistas = new ArbolBinario<>();

    public static ArbolBinario<Artista> cargarArtistas(String archivo) {

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
                        Artista artista = new Artista(codigoArtista, nombreArtista, nacionalidad, esGrupo, null);
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

    public static void cargarCanciones(String archivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            boolean seccionCanciones = false;

            while ((linea = br.readLine()) != null) {
                if (linea.startsWith("#Canciones")) {
                    seccionCanciones = true;
                } else if (seccionCanciones) {
                    String[] datosCancion = linea.split(";");
                    if (datosCancion.length == 7) {
                        String nombreArtista = datosCancion[0];
                        String nombreCancion = datosCancion[1];
                        String nombreAlbum = datosCancion[2];
                        int anio = Integer.parseInt(datosCancion[3]);
                        int duracion = Integer.parseInt(datosCancion[4]);
                        String genero = datosCancion[5];
                        String urlCancion = datosCancion[6];

                        // Obtener el artista correspondiente de tu estructura de datos
                        Artista artista = new Artista();
                        artista.setNombre(nombreArtista);
                        artista = artistas.buscar(artista);
                        if (artista != null) {
                            // Crear el objeto Cancion y agregarlo a la lista de canciones del artista
                            Cancion cancion = new Cancion();
                            artista.getListaCanciones().insertar(cancion);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static TiendaMusica cargarRecursoCasaXML() {
        TiendaMusica tiendaMusica = null;
        try {

            CargarRecursoXML hiloCargarRecursoSerializado = new CargarRecursoXML(RUTA_ARCHIVO_TIENDA_MUSICA_XML);

            hiloCargarRecursoSerializado.start();
            hiloCargarRecursoSerializado.join();

            tiendaMusica = (TiendaMusica) hiloCargarRecursoSerializado.getObjetoXML();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return tiendaMusica;
    }
    public static void guardarRecursoCasaXML(TiendaMusica tiendaMusica) {
        try {

            GuardarRecursoXML hiloGuardarRecursoXML = new GuardarRecursoXML(RUTA_ARCHIVO_TIENDA_MUSICA_XML, tiendaMusica);

            hiloGuardarRecursoXML.start();
            hiloGuardarRecursoXML.join();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}