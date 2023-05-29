package co.edu.uniquindio.storify.persistencia;

import java.io.*;

/**
 * Clase que representa un hilo para guardar un objeto en formato binario.
 */
public class GuardarRecursoBinario extends Thread {
    String rutaArchivo;
    Object object;

    /**
     * Constructor de la clase GuardarRecursoBinario.
     *
     * @param rutaArchivo La ruta del archivo en el que se va a guardar el objeto.
     * @param object      El objeto a guardar.
     */
    public GuardarRecursoBinario(String rutaArchivo, Object object) {
        this.rutaArchivo = rutaArchivo;
        this.object = object;
    }

    /**
     * Método que se ejecuta cuando se inicia el hilo.
     * Guarda el objeto en formato binario en el archivo especificado.
     */
    public void run() {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(rutaArchivo));
            oos.writeObject(object);
        } catch (Exception e) {
            try {
                throw e;
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
