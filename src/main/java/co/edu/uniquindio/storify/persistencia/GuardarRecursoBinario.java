package co.edu.uniquindio.storify.persistencia;

import java.io.*;

public class GuardarRecursoBinario extends Thread{
    String rutaArchivo;
    Object object;

    public GuardarRecursoBinario(String rutaArchivo, Object object){
        this.rutaArchivo = rutaArchivo;
        this.object = object;
    }

    public void run(){
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
