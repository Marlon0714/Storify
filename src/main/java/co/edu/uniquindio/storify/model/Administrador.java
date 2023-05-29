package co.edu.uniquindio.storify.model;

import java.io.Serializable;

/**
 * La clase Administrador representa un usuario con privilegios de administrador en el sistema.
 * Hereda de la clase Usuario.
 */
public class Administrador extends Usuario implements Serializable {

    /**
     * Crea una nueva instancia de la clase Administrador.
     * Llama al constructor de la clase padre (Usuario) sin argumentos.
     */
    public Administrador() {
        super();
    }
}
