package co.edu.uniquindio.storify.persistencia;

import java.beans.Encoder;
import java.beans.Expression;
import java.beans.PersistenceDelegate;
import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDateTime;

public class GuardarRecursoXML extends Thread{
    String rutaArchivo;
    Object objeto;

    public GuardarRecursoXML(String rutaArchivo, Object objeto){
        this.rutaArchivo = rutaArchivo;
        this.objeto = objeto;
    }

    @Override
    public void run(){
        XMLEncoder codificadorXML;

        try {
            codificadorXML = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(rutaArchivo)));
            codificadorXML.setPersistenceDelegate(LocalDateTime.class,
                    new PersistenceDelegate() {
                        @Override
                        protected Expression instantiate(Object localDateTime, Encoder encoder) {
                            return new Expression(localDateTime,
                                    LocalDateTime.class,
                                    "parse",
                                    new Object[]{localDateTime.toString()});
                        }
                    });
            codificadorXML.writeObject(objeto);
            codificadorXML.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
