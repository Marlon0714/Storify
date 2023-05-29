/**
 * Sample Skeleton for 'creacion.fxml' Controller Class
 */

package co.edu.uniquindio.storify.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

import co.edu.uniquindio.storify.exceptions.ExistingArtistException;
import co.edu.uniquindio.storify.exceptions.ExistingSongException;
import co.edu.uniquindio.storify.model.ArbolBinario;
import co.edu.uniquindio.storify.model.Artista;
import co.edu.uniquindio.storify.model.Cancion;
import co.edu.uniquindio.storify.model.ListaDobleEnlazada;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class creacionController {
    ModelFactoryController modelFactoryController;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCrearArtista"
    private Button btnCrearArtista; // Value injected by FXMLLoader

    @FXML // fx:id="btnCrearCancion"
    private Button btnCrearCancion; // Value injected by FXMLLoader

    @FXML // fx:id="btnRegresar"
    private Button btnRegresar; // Value injected by FXMLLoader

    @FXML // fx:id="btnReproducir"
    private Button btnReproducir; // Value injected by FXMLLoader

    @FXML // fx:id="comboGenero"
    private ComboBox<String> comboGenero; // Value injected by FXMLLoader

    @FXML // fx:id="comboGrupo"
    private ComboBox<String> comboGrupo; // Value injected by FXMLLoader

    @FXML
    private ComboBox<String> comboArtista;

    @FXML // fx:id="txtAlbum"
    private TextField txtAlbum; // Value injected by FXMLLoader

    @FXML // fx:id="txtAnio"
    private TextField txtAnio; // Value injected by FXMLLoader

    @FXML // fx:id="txtArtista"
    private TextField txtArtista; // Value injected by FXMLLoader

    @FXML // fx:id="txtCancion"
    private TextField txtCancion; // Value injected by FXMLLoader

    @FXML // fx:id="txtCaratula"
    private TextField txtCaratula; // Value injected by FXMLLoader

    @FXML // fx:id="txtCodigo"
    private TextField txtCodigo; // Value injected by FXMLLoader

    @FXML // fx:id="txtDuracion"
    private TextField txtDuracion; // Value injected by FXMLLoader

    @FXML // fx:id="txtNacionalidad"
    private TextField txtNacionalidad; // Value injected by FXMLLoader

    @FXML // fx:id="txtUrl"
    private TextField txtUrl; // Value injected by FXMLLoader

    /**
     * Método ejecutado cuando se presiona el botón "Crear Artista".
     * Crea un nuevo artista en la tienda de música con los datos ingresados en los campos correspondientes.
     *
     * @param event El evento de acción.
     * @throws ExistingArtistException Si el artista ya existe en la tienda.
     * @throws MalformedURLException Si ocurre un error al construir la URL.
     */
    @FXML
    void actionCrearArtista(ActionEvent event) throws ExistingArtistException, MalformedURLException {
        if (camposArtistaCompletos()) {
            Artista artistaAux = new Artista();
            artistaAux.setNombre(txtArtista.getText());
            artistaAux.setCodigo(txtCodigo.getText());
            artistaAux.setNacionalidad(txtNacionalidad.getText());
            if (comboGrupo.getSelectionModel().getSelectedIndex() == 1) {
                artistaAux.setEsGrupo(true);
            }
            if (comboGrupo.getSelectionModel().getSelectedIndex() == 2) {
                artistaAux.setEsGrupo(false);
            }
            ListaDobleEnlazada<Cancion> canciones = new ListaDobleEnlazada<>();
            artistaAux.setListaCanciones(canciones);
            modelFactoryController.crearArtista(artistaAux);
            showSuccessDialog(artistaAux.getNombre() + " Añadido con éxito");
            artistas();
        } else {
            showErrorDialog("Debe llenar todos los campos");
        }
    }

    /**
     * Método ejecutado cuando se presiona el botón "Crear Canción".
     * Crea una nueva canción en la tienda de música con los datos ingresados en los campos correspondientes.
     *
     * @param event El evento de acción.
     * @throws ExistingSongException Si la canción ya existe en la tienda.
     * @throws MalformedURLException Si ocurre un error al construir la URL.
     */
    @FXML
    void actionCrearCancion(ActionEvent event) throws ExistingSongException, MalformedURLException {
        if (camposCancionCompletos()) {
            Cancion cancionAux = new Cancion();
            cancionAux.setNombre(txtCancion.getText());
            cancionAux.setAnio(Integer.parseInt(txtAnio.getText()));
            cancionAux.setDuracion(Integer.parseInt(txtDuracion.getText()));
            cancionAux.setAlbum(txtAlbum.getText());
            cancionAux.setCaratula(txtCaratula.getText());
            cancionAux.setUrlYoutube(txtUrl.getText());
            setArtistSong(comboArtista, cancionAux);
            if (comboGenero.getSelectionModel().getSelectedIndex() == 1) {
                cancionAux.setGenero("Rock");
            }
            if (comboGenero.getSelectionModel().getSelectedIndex() == 2) {
                cancionAux.setGenero("Pop");
            }
            if (comboGenero.getSelectionModel().getSelectedIndex() == 3) {
                cancionAux.setGenero("Punk");
            }
            if (comboGenero.getSelectionModel().getSelectedIndex() == 4) {
                cancionAux.setGenero("Reggaeton");
            }
            if (comboGenero.getSelectionModel().getSelectedIndex() == 5) {
                cancionAux.setGenero("Electrónica");
            }
            modelFactoryController.crearCancion(cancionAux);
            showSuccessDialog("Canción creada con éxito");
        } else {
            showErrorDialog("Debe llenar todos los campos");
        }
    }

    /**
     * Método ejecutado cuando se presiona el botón "Regresar".
     * Carga la vista anterior y cierra la ventana actual.
     *
     * @param event El evento de acción.
     * @throws IOException Si ocurre un error al cargar la vista anterior.
     */
    @FXML
    void actionRegresar(ActionEvent event) throws IOException {
        URL url = new File("src/main/java/co/edu/uniquindio/storify/view/gui2.fxml").toURI().toURL();
        Parent root1 = FXMLLoader.load(url);
        Scene scene1 = new Scene(root1, 906, 694);
        Stage stage1 = new Stage();
        URL url1 = new File("src/main/java/co/edu/uniquindio/storify/view/styles/estilos.css").toURI().toURL();
        scene1.getStylesheets().add(url1.toExternalForm());
        stage1.setTitle("Admin");
        stage1.setScene(scene1);
        stage1.show();

        Stage stage = (Stage) btnRegresar.getScene().getWindow();
        stage.close();
    }

    /**
     * Método ejecutado cuando se presiona el botón "Reproducir".
     * Reproduce la canción seleccionada.
     *
     * @param event El evento de acción.
     */
    @FXML
    void actionReproducir(ActionEvent event) {
        // Implementación pendiente
    }

    /**
     * Inicializa el controlador después de que su elemento raíz haya sido completamente procesado.
     */
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        modelFactoryController = ModelFactoryController.getInstance();
        initializeCombo();
        assert btnCrearArtista != null : "fx:id=\"btnCrearArtista\" was not injected: check your FXML file 'creacion.fxml'.";
        assert btnCrearCancion != null : "fx:id=\"btnCrearCancion\" was not injected: check your FXML file 'creacion.fxml'.";
        assert btnRegresar != null : "fx:id=\"btnRegresar\" was not injected: check your FXML file 'creacion.fxml'.";
        assert btnReproducir != null : "fx:id=\"btnReproducir\" was not injected: check your FXML file 'creacion.fxml'.";
        assert comboGenero != null : "fx:id=\"comboGenero\" was not injected: check your FXML file 'creacion.fxml'.";
        assert comboGrupo != null : "fx:id=\"comboGrupo\" was not injected: check your FXML file 'creacion.fxml'.";
        assert comboArtista != null : "fx:id=\"comboArtista\" was not injected: check your FXML file 'creacion.fxml'.";
        assert txtAlbum != null : "fx:id=\"txtAlbum\" was not injected: check your FXML file 'creacion.fxml'.";
        assert txtAnio != null : "fx:id=\"txtAnio\" was not injected: check your FXML file 'creacion.fxml'.";
        assert txtArtista != null : "fx:id=\"txtArtista\" was not injected: check your FXML file 'creacion.fxml'.";
        assert txtCancion != null : "fx:id=\"txtCancion\" was not injected: check your FXML file 'creacion.fxml'.";
        assert txtCaratula != null : "fx:id=\"txtCaratula\" was not injected: check your FXML file 'creacion.fxml'.";
        assert txtCodigo != null : "fx:id=\"txtCodigo\" was not injected: check your FXML file 'creacion.fxml'.";
        assert txtDuracion != null : "fx:id=\"txtDuracion\" was not injected: check your FXML file 'creacion.fxml'.";
        assert txtNacionalidad != null : "fx:id=\"txtNacionalidad\" was not injected: check your FXML file 'creacion.fxml'.";
        assert txtUrl != null : "fx:id=\"txtUrl\" was not injected: check your FXML file 'creacion.fxml'.";


    }

    /**
     * Inicializa los combos de género y grupo con sus opciones.
     */
    public void initializeCombo() {
        comboGenero.getItems().add(" ");
        comboGenero.getItems().add("Rock");
        comboGenero.getItems().add("Pop");
        comboGenero.getItems().add("Punk");
        comboGenero.getItems().add("Reggaeton");
        comboGenero.getItems().add("Electrónica");
        comboGrupo.getItems().add(" ");
        comboGrupo.getItems().add("Si");
        comboGrupo.getItems().add("No");
        artistas();

    }

    /**
     * Carga los artistas disponibles en el combo de artistas.
     */
    public void artistas() {
        comboArtista.getItems().clear();
        modelFactoryController.artistas(comboArtista);
    }

    /**
     * Asigna el código del artista seleccionado a una canción.
     *
     * @param a La lista desplegable de artistas.
     * @param b La canción a la que se le asignará el código del artista.
     */
    public void setArtistSong(ComboBox a, Cancion b) {
        String selectedText = a.getSelectionModel().getSelectedItem().toString();
        ArbolBinario<Artista> arb = modelFactoryController.getArtistas();
        for (Artista artist : arb) {
            if (artist.getNombre().equals(selectedText)) {
                b.setCodigoArtista(artist.getCodigo());
            }
        }
    }

    /**
     * Verifica si todos los campos del formulario de artista están completos.
     *
     * @return true si todos los campos están completos, false de lo contrario.
     */
    private boolean camposArtistaCompletos() {
        return !txtArtista.getText().isEmpty() && !txtCodigo.getText().isEmpty() && !txtNacionalidad.getText().isEmpty() && comboGrupo.getSelectionModel().getSelectedIndex() > 0;
    }

    /**
     * Verifica si todos los campos del formulario de canción están completos.
     *
     * @return true si todos los campos están completos, false de lo contrario.
     */
    private boolean camposCancionCompletos() {
        return !txtCancion.getText().isEmpty() && !txtAnio.getText().isEmpty() && !txtDuracion.getText().isEmpty() && !txtAlbum.getText().isEmpty() && !txtCaratula.getText().isEmpty() && !txtUrl.getText().isEmpty() && comboGenero.getSelectionModel().getSelectedIndex() > 0;
    }

    /**
     * Muestra un diálogo de error con el mensaje especificado.
     *
     * @param message El mensaje de error a mostrar.
     * @throws MalformedURLException Si ocurre un error al cargar el archivo CSS del diálogo de error.
     */
    public void showErrorDialog(String message) throws MalformedURLException {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Aplicar el estilo personalizado al diálogo de error
        URL url = new File("src/main/java/co/edu/uniquindio/storify/view/styles/estilos.css").toURI().toURL();
        alert.getDialogPane().getStylesheets().add(url.toExternalForm());

        alert.showAndWait();
    }

    /**
     * Muestra un diálogo de éxito con el mensaje especificado.
     *
     * @param message El mensaje de éxito a mostrar.
     * @throws MalformedURLException Si ocurre un error al cargar el archivo CSS del diálogo de éxito.
     */
    public void showSuccessDialog(String message) throws MalformedURLException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Éxito");
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Aplicar el estilo personalizado al diálogo de éxito
        URL url = new File("src/main/java/co/edu/uniquindio/storify/view/styles/estilos.css").toURI().toURL();
        alert.getDialogPane().getStylesheets().add(url.toExternalForm());

        alert.showAndWait();
    }

}
