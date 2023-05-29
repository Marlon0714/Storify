package co.edu.uniquindio.storify.exceptions;

/**
 * Excepción lanzada cuando se intenta agregar un artista que ya existe.
 */
public class ExistingArtistException extends Throwable {

    /**
     * Crea una instancia de la excepción con un mensaje personalizado.
     *
     * @param message El mensaje de error.
     */
    public ExistingArtistException(String message) {
        super(message);
    }
}

