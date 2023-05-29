package co.edu.uniquindio.storify.controller;

import co.edu.uniquindio.storify.exceptions.ExistingArtistException;
import co.edu.uniquindio.storify.exceptions.ExistingSongException;
import co.edu.uniquindio.storify.exceptions.ExistingUserException;
import co.edu.uniquindio.storify.model.*;
import co.edu.uniquindio.storify.persistencia.Persistencia;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class ModelFactoryController {
    TiendaMusica tiendaMusica;

    Usuario usuariologgeado = new Usuario();

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

    public void crearCancion(Cancion cancionAux) throws ExistingSongException {
        cargarRecursoBinario();
        tiendaMusica.crearCancion(cancionAux.getNombre(),cancionAux.getCodigoArtista(),cancionAux.getAlbum(),cancionAux.getCaratula(),cancionAux.getAnio(),cancionAux.getDuracion(),cancionAux.getGenero(),cancionAux.getUrlYoutube());
        //System.out.println(cancionAux.getUrlYoutube());
        guardarRecursoBinario();
    }

    public void crearArtista(Artista artistaAux) throws ExistingArtistException {
        cargarRecursoBinario();
        tiendaMusica.crearArtista(artistaAux.getCodigo(),artistaAux.getNombre(),artistaAux.getNacionalidad(),artistaAux.esGrupo(),artistaAux.getListaCanciones());
        guardarRecursoBinario();
    }

    public void regresarGui(Button button) throws IOException {
        if(usuariologgeado instanceof Administrador){
            URL url = new File("src/main/java/co/edu/uniquindio/storify/view/gui2.fxml").toURI().toURL();
            Parent root1 = FXMLLoader.load(url);
            Scene scene1 = new Scene(root1, 906 , 694);
            Stage stage1 = new Stage();
            URL url1 = new File("src/main/java/co/edu/uniquindio/storify/view/styles/estilos.css").toURI().toURL();
            scene1.getStylesheets().add(url1.toExternalForm());
            stage1.setTitle("Admin");
            stage1.setScene(scene1);
            stage1.show();


            Stage stage = (Stage) button.getScene().getWindow();
            stage.close();
        }else{
            URL url = new File("src/main/java/co/edu/uniquindio/storify/view/gui1.fxml").toURI().toURL();
            Parent root1 = FXMLLoader.load(url);
            Scene scene1 = new Scene(root1, 906 , 694);
            Stage stage1 = new Stage();
            URL url1 = new File("src/main/java/co/edu/uniquindio/storify/view/styles/estilos.css").toURI().toURL();
            scene1.getStylesheets().add(url1.toExternalForm());
            stage1.setTitle(usuariologgeado.getUsername());
            stage1.setScene(scene1);
            stage1.show();


            Stage stage = (Stage) button.getScene().getWindow();
            stage.close();
        }
    }

    public void eliminarCancion(Cancion cancionSeleccion) {
        tiendaMusica.eliminarCancion(cancionSeleccion);
        guardarRecursoBinario();
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
        cargarRecursoBinario();
        //cargarArtistasYCancionesDesdeArchivo("src/main/java/co/edu/uniquindio/storify/persistencia/ArtistasyCanciones.txt");

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
            ListaCircular<Cancion> listb= new ListaCircular<>();
            list1.insertar(cancionB);
            songs.insertar(cancionA);
            list2.insertar(cancionC);
            list.insertar(cancionB);
            list.insertar(cancionC);
            listb.insertar(cancionB);
            Usuario a = new Usuario("a","a","a",list);
            Usuario b = new Usuario("b","b","b",listb);
            tiendaMusica.agregarUsuario(a);
            tiendaMusica.agregarUsuario(b);
            tiendaMusica.guardarCancion(a,cancionA);
            tiendaMusica.guardarCancion(b,cancionC);
            tiendaMusica.agregarArtista(artista);
            tiendaMusica.agregarArtista(artista1);
            tiendaMusica.agregarArtista(artista2);

            ArbolBinario<Artista> arb = tiendaMusica.getArtistas();
            for(Artista artist : arb){
                System.out.println(artist.getNombre());
            }
            guardarRecursoBinario();
        }
        /***ListaDobleEnlazada<Cancion> songs = new ListaDobleEnlazada<>();
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
        Usuario b = new Usuario("b","b","b",list);
        tiendaMusica.agregarUsuario(b);
        tiendaMusica.eliminarCancion(b,cancionB);
        tiendaMusica.deshacer(b);
        tiendaMusica.rehacer(b);
        tiendaMusica.agregarArtista(artista);
        tiendaMusica.agregarArtista(artista1);
        tiendaMusica.agregarArtista(artista2);

        for (Map.Entry<String, Usuario> usuario : tiendaMusica.getUsuarios().entrySet()) {
            System.out.println(usuario.getValue().getUsername());
            for(Cancion c : usuario.getValue().getListaCancionesFavoritas()) {
                System.out.println(c.getNombre());
            }

        }
***/
    }

    private void cargarRecursoBinario() {
        tiendaMusica = Persistencia.cargarRecursoBinario();
    }

    private void guardarRecursoBinario() {
        Persistencia.guardarRecursoBinario(tiendaMusica);
    }
    public void iniciarSesion(String username, String password) throws MalformedURLException {
        if(username.equals("admin") && password.equals("$aDmiN") ){
            usuariologgeado = new Administrador();
        }else{
            usuariologgeado = tiendaMusica.iniciarSesion(username,password);
        }
        if(usuariologgeado == null){
            showErrorDialog("Usuario y/o contraseña incorrectos");
        }

    }
    public void eliminarFavorita(Cancion cancionSeleccion) {
        tiendaMusica.eliminarCancion(usuariologgeado,cancionSeleccion);
        guardarRecursoBinario();
    }
    public ListaCircular<Cancion> tomarListaCancionesFavoritas() {
        if(usuariologgeado instanceof Administrador){
            return new ListaCircular<>();
        }
        else{
            return usuariologgeado.getListaCancionesFavoritas();
        }
    }
    public Artista obtenerAutor(Cancion cancionA){
        return tiendaMusica.buscarArtista(cancionA.getCodigoArtista());
    }

    public void crearUsuario(String username, String email, String passwd, ListaCircular<Cancion> cancionesFavoritas) throws ExistingUserException {
        tiendaMusica.crearUsuario(username,passwd,email,cancionesFavoritas);
        guardarRecursoBinario();
    }

    public ListaDobleEnlazada<Cancion> buscarCancionOR(Cancion cancionABuscar){
        return tiendaMusica.buscarCancionesOR(cancionABuscar);
    }

    public ListaDobleEnlazada<Cancion> buscarCancionAND(Cancion cancionAux) {
        return tiendaMusica.buscarCancionesAND(cancionAux);
    }

    public ListaDobleEnlazada<Cancion> buscarCancionesArtista(String artista){
        return tiendaMusica.buscarCancionesArtista(artista);
    }

    public String buscarArt(String nombre) {
        Artista artistaAux = new Artista();
        artistaAux.setNombre(nombre);
        tiendaMusica.getArtistas().buscar(artistaAux);
        return artistaAux.getCodigo();
    }

    public void agregarFavorita(Cancion cancion){
        tiendaMusica.guardarCancion(usuariologgeado,cancion);
        guardarRecursoBinario();
    }
    public String generoPopular(){
        return tiendaMusica.consultarGeneroMasCanciones();
    }

    public Artista artistaPopular(){
        return tiendaMusica.obtenerArtistaMasPopular();
    }

    public void deshacer(){
        tiendaMusica.deshacer(usuariologgeado);
    }

    public void rehacer(){
        tiendaMusica.rehacer(usuariologgeado);
    }

    public Cancion buscarCancion(String codigoCancion) {
        return tiendaMusica.buscarCancion(codigoCancion);
    }
    public void showErrorDialog(String message) throws MalformedURLException {
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

    public void cargarArtistasYCancionesDesdeArchivo(String archivo) {
        // Agregar los artistas y canciones cargados a la TiendaMusica existente
        ArbolBinario<Artista> artistasCargados = Persistencia.cargarArtistas(archivo, tiendaMusica.getArtistas());
        tiendaMusica.setArtistas(Persistencia.cargarCanciones(archivo, artistasCargados));
        guardarRecursoBinario();
    }
    public void artistas(ComboBox a){
        ArbolBinario<Artista> arb = tiendaMusica.getArtistas();
        for(Artista artist : arb){
            a.getItems().add(artist.getNombre());
        }
    }
    public ArbolBinario<Artista> getArtistas(){
        return tiendaMusica.getArtistas();
    }
    public ListaDobleEnlazada<Cancion>obtenerCanciones(){
        return tiendaMusica.obtenerCanciones();
    }
}
