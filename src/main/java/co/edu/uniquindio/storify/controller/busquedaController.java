package co.edu.uniquindio.storify.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import co.edu.uniquindio.storify.model.*;
import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.engine.EngineOptions;
import com.teamdev.jxbrowser.view.javafx.BrowserView;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import static com.teamdev.jxbrowser.engine.RenderingMode.HARDWARE_ACCELERATED;

public class busquedaController {

    ObservableList<Cancion> observableList = FXCollections.observableArrayList();

    Cancion cancionSeleccion;
    ModelFactoryController modelFactoryController;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnReproducir"
    private Button btnReproducir; // Value injected by FXMLLoader

    @FXML
    private Button btnAgregar;

    @FXML // fx:id="btnArtista"
    private Button btnArtista; // Value injected by FXMLLoader

    @FXML // fx:id="btnRegresar"
    private Button btnRegresar; // Value injected by FXMLLoader

    @FXML // fx:id="btnO"
    private Button btnO; // Value injected by FXMLLoader

    @FXML // fx:id="btnY"
    private Button btnY; // Value injected by FXMLLoader

    @FXML // fx:id="columnAlbum"
    private TableColumn<Cancion, String> columnAlbum; // Value injected by FXMLLoader

    @FXML // fx:id="columnAnio"
    private TableColumn<Cancion, Integer> columnAnio; // Value injected by FXMLLoader

    @FXML // fx:id="columnArtista"
    private TableColumn<Cancion, String> columnArtista; // Value injected by FXMLLoader

    @FXML // fx:id="columnCancion"
    private TableColumn<Cancion, String> columnCancion; // Value injected by FXMLLoader

    @FXML // fx:id="columnDuracion"
    private TableColumn<Cancion, Integer> columnDuracion; // Value injected by FXMLLoader

    @FXML // fx:id="columnGenero"
    private TableColumn<Cancion, String> columnGenero; // Value injected by FXMLLoader

    @FXML // fx:id="tableCanciones"
    private TableView<Cancion> tableCanciones; // Value injected by FXMLLoader

    @FXML // fx:id="txtAlbum"
    private TextField txtAlbum; // Value injected by FXMLLoader

    @FXML // fx:id="txtAnio"
    private TextField txtAnio; // Value injected by FXMLLoader

    @FXML // fx:id="txtArtista"
    private TextField txtArtista; // Value injected by FXMLLoader

    @FXML // fx:id="txtCancion"
    private TextField txtCancion; // Value injected by FXMLLoader

    @FXML // fx:id="txtDuracion"
    private TextField txtDuracion; // Value injected by FXMLLoader

    @FXML // fx:id="txtGenero"
    private TextField txtGenero; // Value injected by FXMLLoader


    /**
     * Método para manejar el evento de búsqueda por artista.
     *
     * @param event El evento de clic en el botón.
     * @throws MalformedURLException Si ocurre un error al cargar una URL.
     */
    @FXML
    void actionBusquedaArtista(ActionEvent event) throws MalformedURLException {
        if (!txtArtista.getText().isEmpty()) {
            ListaDobleEnlazada<Cancion> listaDobleEnlazada = new ListaDobleEnlazada<>();
            listaDobleEnlazada.addAll(modelFactoryController.buscarCancionesArtista(txtArtista.getText()));
            loadNewTable(listaDobleEnlazada);
        } else {
            showErrorDialog("Debe ingresar el nombre del artista");
        }
    }

    /**
     * Método para manejar el evento de búsqueda mediante el operador OR.
     *
     * @param event El evento de clic en el botón.
     * @throws MalformedURLException Si ocurre un error al cargar una URL.
     */
    @FXML
    void actionBusquedaO(ActionEvent event) throws MalformedURLException {
        Cancion cancionAux = new Cancion();
        cancionAux.setNombre(txtCancion.getText());
        cancionAux.setAlbum(txtAlbum.getText());
        cancionAux.setGenero(txtGenero.getText());
        cancionAux.setCodigoArtista(modelFactoryController.buscarArt(txtArtista.getText()));

        String anioText = txtAnio.getText();
        if (!anioText.isEmpty()) {
            cancionAux.setAnio(Integer.parseInt(txtAnio.getText()));
        }

        String duracionText = txtDuracion.getText();
        if (!duracionText.isEmpty()) {
            cancionAux.setDuracion(Integer.parseInt(txtDuracion.getText()));
        }

        boolean tieneTexto = !txtCancion.getText().isEmpty() ||
                !txtAlbum.getText().isEmpty() ||
                !txtGenero.getText().isEmpty() ||
                !txtArtista.getText().isEmpty() ||
                !txtAnio.getText().isEmpty() ||
                !txtDuracion.getText().isEmpty();

        if (tieneTexto) {
            ListaDobleEnlazada<Cancion> listaDobleEnlazada = new ListaDobleEnlazada<>();
            listaDobleEnlazada.addAll(modelFactoryController.buscarCancionOR(cancionAux));
            loadNewTable(listaDobleEnlazada);
        } else {
            showErrorDialog("Debe proporcionar al menos un criterio de búsqueda");
        }
    }

    /**
     * Método para manejar el evento de búsqueda mediante el operador AND.
     *
     * @param event El evento de clic en el botón.
     * @throws MalformedURLException Si ocurre un error al cargar una URL.
     */
    @FXML
    void actionBusquedaY(ActionEvent event) throws MalformedURLException {
        if (validarFields()) {
            Cancion cancionAux = new Cancion();
            cancionAux.setNombre(txtCancion.getText());
            cancionAux.setAlbum(txtAlbum.getText());
            cancionAux.setGenero(txtGenero.getText());
            cancionAux.setCodigoArtista(modelFactoryController.buscarArt(txtArtista.getText()));
            cancionAux.setAnio(Integer.parseInt(txtAnio.getText()));
            cancionAux.setDuracion(Integer.parseInt(txtDuracion.getText()));

            ListaDobleEnlazada<Cancion> listaDobleEnlazada = new ListaDobleEnlazada<>();
            listaDobleEnlazada.addAll(modelFactoryController.buscarCancionAND(cancionAux));
            loadNewTable(listaDobleEnlazada);
        } else {
            showErrorDialog("Debe llenar todos los campos");
        }
    }

    /**
     * Método para manejar el evento de regresar.
     *
     * @param event El evento de clic en el botón.
     * @throws IOException Si ocurre un error de entrada o salida.
     */
    @FXML
    void actionRegresar(ActionEvent event) throws IOException {
        modelFactoryController.regresarGui(btnRegresar);
    }

    /**
     * Método para manejar el evento de reproducir una canción.
     *
     * @param event El evento de clic en el botón.
     */
    @FXML
    void actionReproducir(ActionEvent event) {
        // Inicializar Chromium.
        EngineOptions options = EngineOptions.newBuilder(HARDWARE_ACCELERATED)
                .licenseKey("1BNDHFSC1G6ACMC4FPYDA9JCGE2ON6O8O1TLU39NUKF2TT6JPNM2U3U13827LFGQ5LROE8")
                .build();
        Engine engine = Engine.newInstance(options);

        // Crear una instancia de Browser.
        Browser browser = engine.newBrowser();

        // Cargar la página web requerida.
        browser.navigation().loadUrl(cancionSeleccion.getUrlYoutube());

        // Crear y embeber el componente JavaFX BrowserView para mostrar el contenido web.
        BrowserView view = BrowserView.newInstance(browser);

        Scene scene = new Scene(new BorderPane(view), 600, 406);
        Stage primaryStage = new Stage();
        primaryStage.setTitle(cancionSeleccion.getNombre());
        primaryStage.setScene(scene);
        primaryStage.show();

        // Apagar Chromium y liberar los recursos asignados.
        primaryStage.setOnCloseRequest(event1 -> engine.close());
    }

    /**
     * Método para convertir la duración de una canción en un formato legible.
     *
     * @param numero El número de duración de la canción.
     * @return La duración de la canción en formato minutos:segundos.
     */
    public String convertirDuracion(int numero) {
        int segundos = numero % 100;
        int minutos = numero / 100;
        return minutos + ":" + segundos;
    }

    /**
     * Método para manejar el evento de agregar una canción a la lista de favoritas.
     *
     * @param event El evento de clic en el botón.
     */
    @FXML
    void actionAgregar(ActionEvent event) throws MalformedURLException {
        modelFactoryController.agregarFavorita(cancionSeleccion);
        showSuccessDialog("Canción añadida a la lista con éxito");
    }

    /**
     * Método para cargar una nueva tabla de canciones.
     *
     * @param list La lista de canciones a cargar.
     */
    private void loadNewTable(ListaDobleEnlazada<Cancion> list) {
        columnCancion.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnArtista.setCellValueFactory(cellData -> {
            Artista artista = modelFactoryController.obtenerAutor(cellData.getValue());
            if (artista != null) {
                return new SimpleStringProperty(artista.getNombre());
            } else {
                return new SimpleStringProperty("");
            }
        });
        columnAlbum.setCellValueFactory(new PropertyValueFactory<>("album"));
        columnGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
        columnAnio.setCellValueFactory(new PropertyValueFactory<>("anio"));
        columnDuracion.setCellValueFactory(new PropertyValueFactory<>("duracion"));
        columnDuracion.setCellFactory(column -> {
            return new TableCell<Cancion, Integer>() {
                @Override
                protected void updateItem(Integer number, boolean empty) {
                    super.updateItem(number, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        setText(convertirDuracion(number));
                    }
                }
            };
        });

        List<Cancion> tempList = new ArrayList<>();

        for (Cancion o : list) {
            tempList.add(o);
        }

        observableList.clear();
        observableList.addAll(tempList);
        tableCanciones.setItems(observableList);
        tableCanciones.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                cancionSeleccion = newSelection;
                btnReproducir.setVisible(true);
                btnAgregar.setVisible(true);
            }
        });
    }

    /**
     * Método de inicialización que se llama cuando se completa la inicialización del controlador.
     */
    @FXML
    // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        modelFactoryController = ModelFactoryController.getInstance();
        //loadTable();
        assert btnArtista != null : "fx:id=\"btnArtista\" was not injected: check your FXML file 'busqueda.fxml'.";
        assert btnO != null : "fx:id=\"btnO\" was not injected: check your FXML file 'busqueda.fxml'.";
        assert btnRegresar != null : "fx:id=\"btnRegresar\" was not injected: check your FXML file 'busqueda.fxml'.";
        assert btnReproducir != null : "fx:id=\"btnReproducir\" was not injected: check your FXML file 'busqueda.fxml'.";
        assert btnY != null : "fx:id=\"btnY\" was not injected: check your FXML file 'busqueda.fxml'.";
        assert columnAlbum != null : "fx:id=\"columnAlbum\" was not injected: check your FXML file 'busqueda.fxml'.";
        assert columnAnio != null : "fx:id=\"columnAnio\" was not injected: check your FXML file 'busqueda.fxml'.";
        assert columnArtista != null : "fx:id=\"columnArtista\" was not injected: check your FXML file 'busqueda.fxml'.";
        assert columnCancion != null : "fx:id=\"columnCancion\" was not injected: check your FXML file 'busqueda.fxml'.";
        assert columnDuracion != null : "fx:id=\"columnDuracion\" was not injected: check your FXML file 'busqueda.fxml'.";
        assert columnGenero != null : "fx:id=\"columnGenero\" was not injected: check your FXML file 'busqueda.fxml'.";
        assert tableCanciones != null : "fx:id=\"tableCanciones\" was not injected: check your FXML file 'busqueda.fxml'.";
        assert txtAlbum != null : "fx:id=\"txtAlbum\" was not injected: check your FXML file 'busqueda.fxml'.";
        assert txtAnio != null : "fx:id=\"txtAnio\" was not injected: check your FXML file 'busqueda.fxml'.";
        assert txtArtista != null : "fx:id=\"txtArtista\" was not injected: check your FXML file 'busqueda.fxml'.";
        assert txtCancion != null : "fx:id=\"txtCancion\" was not injected: check your FXML file 'busqueda.fxml'.";
        assert txtDuracion != null : "fx:id=\"txtDuracion\" was not injected: check your FXML file 'busqueda.fxml'.";
        assert txtGenero != null : "fx:id=\"txtGenero\" was not injected: check your FXML file 'busqueda.fxml'.";

        btnReproducir.setVisible(false);
        btnAgregar.setVisible(false);
    }

    /**
     * Método para mostrar un diálogo de error.
     *
     * @param message El mensaje de error a mostrar.
     */
    private void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        // Aplicar el estilo personalizado al diálogo de éxito
        URL cssFileURL = getClass().getResource("/co/edu/uniquindio/storify/view/styles/estilos.css");
        if (cssFileURL != null) {
            alert.getDialogPane().getStylesheets().add(cssFileURL.toExternalForm());
            alert.getDialogPane().getStyleClass().add("dialog-pane");
        }
        alert.showAndWait();
    }

    /**
     * Método para mostrar un diálogo de éxito.
     *
     * @param message El mensaje de éxito a mostrar.
     */
    private void showSuccessDialog(String message) throws MalformedURLException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Éxito");
        alert.setHeaderText(null);
        alert.setContentText(message);
        // Aplicar el estilo personalizado al diálogo de error
        URL url1 = new File("src/main/java/co/edu/uniquindio/storify/view/styles/estilos.css").toURI().toURL();
        alert.getDialogPane().getStylesheets().add((url1.toExternalForm()));
        alert.getDialogPane().getStyleClass().add("dialog-pane");
        alert.showAndWait();
    }

    /**
     * Método para validar que todos los campos de búsqueda estén llenos.
     *
     * @return true si todos los campos están llenos, false en caso contrario.
     */
    private boolean validarFields() {
        return !txtCancion.getText().isEmpty() &&
                !txtAlbum.getText().isEmpty() &&
                !txtGenero.getText().isEmpty() &&
                !txtArtista.getText().isEmpty() &&
                !txtAnio.getText().isEmpty() &&
                !txtDuracion.getText().isEmpty();
    }
}