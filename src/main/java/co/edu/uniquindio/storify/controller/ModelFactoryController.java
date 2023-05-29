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

    /**
     * Obtiene la instancia de la tienda de música actual.
     * @return La instancia de la tienda de música.
     */
    public TiendaMusica getTiendaMusica() {
        return tiendaMusica;
    }

    /**
     * Establece la instancia de la tienda de música.
     * @param tiendaMusica La instancia de la tienda de música a establecer.
     */
    public void setTiendaMusica(TiendaMusica tiendaMusica) {
        this.tiendaMusica = tiendaMusica;
    }

    /**
     * Obtiene el usuario que ha iniciado sesión actualmente.
     * @return El usuario que ha iniciado sesión.
     */
    public Usuario getUsuariologgeado() {
        return usuariologgeado;
    }

    /**
     * Establece el usuario que ha iniciado sesión.
     * @param usuariologgeado El usuario que ha iniciado sesión.
     */
    public void setUsuariologgeado(Usuario usuariologgeado) {
        this.usuariologgeado = usuariologgeado;
    }

    /**
     * Crea una nueva canción en la tienda de música.
     * @param cancionAux La canción a crear.
     * @throws ExistingSongException Si la canción ya existe.
     */
    public void crearCancion(Cancion cancionAux) throws ExistingSongException {
        cargarRecursoBinario();
        tiendaMusica.crearCancion(
                cancionAux.getNombre(),
                cancionAux.getCodigoArtista(),
                cancionAux.getAlbum(),
                cancionAux.getCaratula(),
                cancionAux.getAnio(),
                cancionAux.getDuracion(),
                cancionAux.getGenero(),
                cancionAux.getUrlYoutube()
        );
        System.out.println(cancionAux.getUrlYoutube());
        guardarRecursoBinario();
    }

    /**
     * Crea un nuevo artista en la tienda de música.
     * @param artistaAux El artista a crear.
     * @throws ExistingArtistException Si el artista ya existe.
     */
    public void crearArtista(Artista artistaAux) throws ExistingArtistException {
        cargarRecursoBinario();
        tiendaMusica.crearArtista(
                artistaAux.getCodigo(),
                artistaAux.getNombre(),
                artistaAux.getNacionalidad(),
                artistaAux.esGrupo(),
                artistaAux.getListaCanciones()
        );
        guardarRecursoBinario();
    }

    /**
     * Regresa a la interfaz gráfica anterior.
     * @param button El botón que desencadena la acción.
     * @throws IOException Si ocurre un error durante la carga de la interfaz gráfica.
     */
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

    /**
     * Elimina una canción de la tienda de música.
     *
     * @param cancionSeleccion La canción que se desea eliminar.
     */
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
    /**
     *Metodo para inicializar la instancia de la clase
     */
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

    }

    /**
     * Carga los datos de la tienda de música desde un recurso binario.
     */
    private void cargarRecursoBinario() {
        tiendaMusica = Persistencia.cargarRecursoBinario();
    }

    /**
     * Guarda los datos de la tienda de música en un recurso binario.
     */
    private void guardarRecursoBinario() {
        Persistencia.guardarRecursoBinario(tiendaMusica);
    }

    /**
     * Inicia sesión en la tienda de música.
     *
     * @param username El nombre de usuario.
     * @param password La contraseña.
     * @throws MalformedURLException Si la URL del estilo personalizado es incorrecta.
     */
    public void iniciarSesion(String username, String password) throws MalformedURLException {
        if (username.equals("admin") && password.equals("$aDmiN")) {
            usuariologgeado = new Administrador();
        } else {
            usuariologgeado = tiendaMusica.iniciarSesion(username, password);
        }
        if (usuariologgeado == null) {
            showErrorDialog("Usuario y/o contraseña incorrectos");
        }
    }

    /**
     * Elimina una canción de la lista de canciones favoritas del usuario.
     *
     * @param cancionSeleccion La canción seleccionada.
     */
    public void eliminarFavorita(Cancion cancionSeleccion) {
        tiendaMusica.eliminarCancion(usuariologgeado, cancionSeleccion);
        guardarRecursoBinario();
    }

    /**
     * Obtiene la lista de canciones favoritas del usuario.
     *
     * @return La lista de canciones favoritas.
     */
    public ListaCircular<Cancion> tomarListaCancionesFavoritas() {
        if (usuariologgeado instanceof Administrador) {
            return new ListaCircular<>();
        } else {
            return usuariologgeado.getListaCancionesFavoritas();
        }
    }

    /**
     * Obtiene el artista de una canción.
     *
     * @param cancionA La canción.
     * @return El artista de la canción.
     */
    public Artista obtenerAutor(Cancion cancionA) {
        return tiendaMusica.buscarArtista(cancionA.getCodigoArtista());
    }

    /**
     * Crea un nuevo usuario en la tienda de música.
     *
     * @param username           El nombre de usuario.
     * @param email              El correo electrónico.
     * @param passwd             La contraseña.
     * @param cancionesFavoritas La lista de canciones favoritas.
     * @throws ExistingUserException Si el nombre de usuario ya está registrado.
     */
    public void crearUsuario(String username, String email, String passwd, ListaCircular<Cancion> cancionesFavoritas) throws ExistingUserException {
        tiendaMusica.crearUsuario(username, passwd, email, cancionesFavoritas);
        guardarRecursoBinario();
    }

    /**
     * Busca canciones que cumplan con alguna de las características especificadas.
     *
     * @param cancionABuscar La canción a buscar.
     * @return La lista de canciones que cumplen con las características.
     */
    public ListaDobleEnlazada<Cancion> buscarCancionOR(Cancion cancionABuscar) {
        return tiendaMusica.buscarCancionesOR(cancionABuscar);
    }

    /**
     * Busca canciones que cumplan con todas las características especificadas.
     *
     * @param cancionAux La canción a buscar.
     * @return La lista de canciones que cumplen con las características.
     */
    public ListaDobleEnlazada<Cancion> buscarCancionAND(Cancion cancionAux) {
        return tiendaMusica.buscarCancionesAND(cancionAux);
    }

    /**
     * Busca canciones de un artista específico.
     *
     * @param artista El nombre del artista.
     * @return La lista de canciones del artista.
     */
    public ListaDobleEnlazada<Cancion> buscarCancionesArtista(String artista) {
        return tiendaMusica.buscarCancionesArtista(artista);
    }

    /**
     * Busca el código de un artista por su nombre.
     *
     * @param nombre El nombre del artista.
     * @return El código del artista.
     */
    public String buscarArt(String nombre) {
        Artista artistaAux = new Artista();
        artistaAux.setNombre(nombre);
        tiendaMusica.getArtistas().buscar(artistaAux);
        return artistaAux.getCodigo();
    }

    /**
     * Agrega una canción a la lista de canciones favoritas del usuario.
     *
     * @param cancion La canción a agregar.
     */
    public void agregarFavorita(Cancion cancion) {
        tiendaMusica.guardarCancion(usuariologgeado, cancion);
        guardarRecursoBinario();
    }

    /**
     * Obtiene el género de música más popular en la tienda.
     *
     * @return El género más popular.
     */
    public String generoPopular() {
        return tiendaMusica.consultarGeneroMasCanciones();
    }

    /**
     * Obtiene el artista más popular en la tienda.
     *
     * @return El artista más popular.
     */
    public Artista artistaPopular() {
        return tiendaMusica.obtenerArtistaMasPopular();
    }

    /**
     * Deshace la última acción realizada por el usuario.
     */
    public void deshacer() {
        tiendaMusica.deshacer(usuariologgeado);
    }

    /**
     * Rehace la última acción deshecha por el usuario.
     */
    public void rehacer() {
        tiendaMusica.rehacer(usuariologgeado);
    }

    /**
     * Busca una canción por su código.
     *
     * @param codigoCancion El código de la canción.
     * @return La canción encontrada.
     */
    public Cancion buscarCancion(String codigoCancion) {
        return tiendaMusica.buscarCancion(codigoCancion);
    }

    /**
     * Muestra un diálogo de error con el mensaje especificado.
     *
     * @param message El mensaje de error.
     * @throws MalformedURLException Si la URL del estilo personalizado es incorrecta.
     */
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

    /**
     * Carga los artistas y canciones desde un archivo y los agrega a la tienda de música existente.
     *
     * @param archivo El archivo de donde se cargarán los datos.
     */
    public void cargarArtistasYCancionesDesdeArchivo(String archivo) {
        // Agregar los artistas y canciones cargados a la TiendaMusica existente
        ArbolBinario<Artista> artistasCargados = Persistencia.cargarArtistas(archivo, tiendaMusica.getArtistas());
        tiendaMusica.setArtistas(Persistencia.cargarCanciones(archivo, artistasCargados));
        guardarRecursoBinario();
    }

    /**
     * Agrega los nombres de los artistas al ComboBox especificado.
     *
     * @param a El ComboBox donde se agregarán los nombres de los artistas.
     */
    public void artistas(ComboBox a) {
        ArbolBinario<Artista> arb = tiendaMusica.getArtistas();
        for (Artista artist : arb) {
            a.getItems().add(artist.getNombre());
        }
    }

    /**
     * Obtiene el árbol de artistas de la tienda de música.
     *
     * @return El árbol de artistas.
     */
    public ArbolBinario<Artista> getArtistas() {
        return tiendaMusica.getArtistas();
    }

    /**
     * Obtiene la lista de canciones de la tienda de música.
     *
     * @return La lista de canciones.
     */
    public ListaDobleEnlazada<Cancion> obtenerCanciones() {
        return tiendaMusica.obtenerCanciones();
    }
}
