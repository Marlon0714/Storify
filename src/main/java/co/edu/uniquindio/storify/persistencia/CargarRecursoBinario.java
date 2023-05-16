package co.edu.uniquindio.storify.persistencia;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class CargarRecursoBinario extends Thread {
    Object aux;
    String rutaArchivo;

    public CargarRecursoBinario(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    @Override
    public void run(){
        Object aux = null;
        ObjectInputStream ois = null;
        try {

            // Se crea un ObjectInputStream
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

    public Object getAux() {
        return aux;
    }
}
