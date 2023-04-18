package co.edu.uniquindio.storify.model;

import java.io.Serializable;
import java.util.List;

public class Usuario implements Serializable {
    private String username;
    private String password;
    private String email;
    private List<Cancion> cancionesFavoritas;

    public Usuario(String username, String password, String email, List<Cancion> cancionesFavoritas) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.cancionesFavoritas = cancionesFavoritas;
    }

    public Usuario() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Cancion> getCancionesFavoritas() {
        return cancionesFavoritas;
    }

    public void setCancionesFavoritas(List<Cancion> cancionesFavoritas) {
        this.cancionesFavoritas = cancionesFavoritas;
    }
}
