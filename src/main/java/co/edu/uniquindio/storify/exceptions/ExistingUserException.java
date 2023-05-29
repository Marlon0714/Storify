package co.edu.uniquindio.storify.exceptions;

/**
 * Excepción lanzada cuando se intenta crear un usuario que ya existe.
 */
public class ExistingUserException extends Throwable {
    /**
     * Crea una nueva instancia de la excepción con el mensaje especificado.
     *
     * @param message El mensaje de la excepción.
     */
    public ExistingUserException(String message){
        super(message);
    }
}

