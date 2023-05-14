package co.edu.uniquindio.storify.exceptions;

public class ExistingUserException extends Throwable {
    public ExistingUserException(String message){
        super(message);
    }
}
