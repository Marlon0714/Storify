package co.edu.uniquindio.storify.exceptions;

/**
 * Excepción que se lanza cuando se intenta agregar una canción existente.
 */
public class ExistingSongException extends Throwable {

    /**
     * Crea una instancia de la excepción con el mensaje especificado.
     *
     * @param message El mensaje de la excepción.
     */
    public ExistingSongException(String message) {
        super(message);
    }
}
