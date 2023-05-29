package co.edu.uniquindio.storify.persistencia;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Clase que representa un hilo para cargar un recurso binario desde un archivo.
 */
public class CargarRecursoBinario extends Thread {
    private Object aux;
    private String rutaArchivo;

    /**
     * Constructor de la clase CargarRecursoBinario.
     *
     * @param rutaArchivo La ruta del archivo binario a cargar.
     */
    public CargarRecursoBinario(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    /**
     * Método que se ejecuta al iniciar el hilo.
     * Carga el recurso binario desde el archivo.
     */
    @Override
    public void run(){
        Object aux = null;
        ObjectInputStream ois = null;
        try {

            // Se crea un ObjectInputStream para leer el archivo binario
            ois = new ObjectInputStream(new FileInputStream(rutaArchivo));

            aux = ois.readObject();

        } catch (Exception e2) {
            try {
                throw e2;
            } catch (Exception e) {

                e.printStackTrace();
            }
        } finally {
            if (ois != null)
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        this.aux = aux;
    }

    /**
     * Obtiene el objeto cargado desde el archivo binario.
     *
     * @return El objeto cargado desde el archivo binario.
     */
    public Object getAux() {
        return aux;
    }
}

