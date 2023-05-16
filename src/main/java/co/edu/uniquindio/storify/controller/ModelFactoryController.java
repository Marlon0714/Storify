package co.edu.uniquindio.storify.controller;

import co.edu.uniquindio.storify.model.*;
import co.edu.uniquindio.storify.persistencia.Persistencia;
import javafx.scene.control.Alert;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class ModelFactoryController {
    TiendaMusica tiendaMusica;

    Usuario usuariologgeado;

    public TiendaMusica getTiendaMusica() {
        return tiendaMusica;
    }

    public void setTiendaMusica(TiendaMusica tiendaMusica) {
        this.tiendaMusica = tiendaMusica;
    }

    public Usuario getUsuariologgeado() {
        return usuariologgeado;
    }

    public void setUsuariologgeado(Usuario usuariologgeado) {
        this.usuariologgeado = usuariologgeado;
    }


    private static class SingletonHolder {
        private static final  ModelFactoryController eINSTANCE = new ModelFactoryController();
    }
    /**
     *Metodo para obtener la instancia de la clase
     */
    public static ModelFactoryController getInstance() {
        return SingletonHolder.eINSTANCE;
    }

    public ModelFactoryController(){
        usuariologgeado = new Usuario();
        cargarRecursoXML();
        if(tiendaMusica == null){
            System.out.println("es null");
            tiendaMusica = new TiendaMusica();
            ListaDobleEnlazada<Cancion> songs = new ListaDobleEnlazada<>();
            ListaDobleEnlazada<Cancion> list1 = new ListaDobleEnlazada<>();
            ListaDobleEnlazada<Cancion> list2 = new ListaDobleEnlazada<>();
            Artista artista = new Artista("1","Red Hot Chilli Papers","Estados Unidos",true,songs);
            Artista artista1 = new Artista("2","Magic!","Canadá",true,list1);
            Artista artista2 = new Artista("3","Maroon 5","Estados Unidos",true,list2);
            Cancion cancionA = new Cancion("1","Otherside","1","Californication","src/main/java/co/edu/uniquindio/storify/persistencia/Carátulas/RedHotChiliPeppersCalifornication.jpg",2014,456,"Rock","https://youtu.be/rn_YodiJO6k");
            Cancion cancionB = new Cancion("2","Rude","2","Don't Kill the Magic","src/main/java/co/edu/uniquindio/storify/persistencia/Carátulas/DontKillTheMagic.png",2014,345,"Pop","https://youtu.be/PIh2xe4jnpk");
            Cancion cancionC = new Cancion("3","Animals","3","V","src/main/java/co/edu/uniquindio/storify/persistencia/Carátulas/V.jpg",2015,440,"Rock","https://youtu.be/qpgTC9MDx1o");
            ListaCircular<Cancion> list = new ListaCircular<>();
            list1.insertar(cancionB);
            songs.insertar(cancionA);
            list2.insertar(cancionC);
            list.insertar(cancionA);
            list.insertar(cancionB);
            list.insertar(cancionC);
            Usuario a = new Usuario("a","a","a",list);
            tiendaMusica.agregarUsuario(a);
            tiendaMusica.guardarCancion(a,cancionA);
            tiendaMusica.agregarArtista(artista);
            tiendaMusica.agregarArtista(artista1);
            tiendaMusica.agregarArtista(artista2);

            ArbolBinario<Artista> arb = tiendaMusica.getArtistas();
            for(Artista artist : arb){
                System.out.println(artist.getNombre());
            }
            guardarRecursoXML();
        }

    }

    private void cargarRecursoXML() {
        tiendaMusica = Persistencia.cargarRecursoBinario();
    }

    private void guardarRecursoXML() {
        Persistencia.guardarRecursoBinario(tiendaMusica);
    }
    public void iniciarSesion(String username, String password) throws MalformedURLException {
        usuariologgeado = tiendaMusica.iniciarSesion(username,password);
        if(usuariologgeado == null){
            showErrorDialog("Usuario y/o contraseña incorrectos");
        }

    }
    public void eliminarFavorita(Cancion cancionSeleccion) {
        tiendaMusica.eliminarCancion(usuariologgeado,cancionSeleccion);
    }
    public ListaCircular<Cancion> tomarListaCancionesFavoritas() {
        return usuariologgeado.getListaCancionesFavoritas();
    }
    public Artista obtenerAutor(Cancion cancionA){
        return tiendaMusica.buscarArtista(cancionA.getCodigoArtista());
    }

    public Cancion buscarCancion(String codigoCancion) {
        return tiendaMusica.buscarCancion(codigoCancion);
    }

    private void showErrorDialog(String message) throws MalformedURLException {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error de autenticación");
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Aplicar el estilo personalizado al diálogo de error
        URL url1 = new File("src/main/java/co/edu/uniquindio/storify/view/styles/estilos.css").toURI().toURL();
        alert.getDialogPane().getStylesheets().add((url1.toExternalForm()));
        alert.getDialogPane().getStyleClass().add("dialog-pane");

        alert.showAndWait();
    }
}
