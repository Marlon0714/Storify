package co.edu.uniquindio.storify.controller;


import co.edu.uniquindio.storify.model.Artista;
import co.edu.uniquindio.storify.model.Cancion;
import co.edu.uniquindio.storify.model.ListaCircular;
import com.teamdev.jxbrowser.browser.Browser;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.engine.EngineOptions;
import com.teamdev.jxbrowser.view.javafx.BrowserView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.teamdev.jxbrowser.engine.RenderingMode.HARDWARE_ACCELERATED;

public class gui1Controller {

    ObservableList<Cancion> observableList = FXCollections.observableArrayList();

    ModelFactoryController modelFactoryController;

    @FXML // fx:id="brnEliminar"
    private Button brnEliminar; // Value injected by FXMLLoader

    @FXML
    private Button btnConsultar;

    @FXML // fx:id="btnAlbum"
    private Button btnAlbum; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnio"
    private Button btnAnio; // Value injected by FXMLLoader

    @FXML // fx:id="btnArtista"
    private Button btnArtista; // Value injected by FXMLLoader

    @FXML // fx:id="btnBuscar"
    private Button btnBuscar; // Value injected by FXMLLoader

    @FXML // fx:id="btnCancion"
    private Button btnCancion; // Value injected by FXMLLoader

    @FXML // fx:id="btnDuracion"
    private Button btnDuracion; // Value injected by FXMLLoader

    @FXML // fx:id="btnGenero"
    private Button btnGenero; // Value injected by FXMLLoader

    @FXML // fx:id="btnReproducir"
    private Button btnReproducir; // Value injected by FXMLLoader

    @FXML
    private Button btnDeshacer;

    @FXML
    private Button btnRehacer;

    @FXML
    private Label labelAlbum;

    @FXML
    private Label labelAlbum1;
    @FXML
    private Label labelAnio;

    @FXML
    private Label labelAnio1;

    @FXML
    private Label labelDuracion;

    @FXML
    private Label labelDuracion1;

    @FXML
    private Label labelGenero;

    @FXML
    private Label labelGenero1;

    @FXML
    private Label labelTitle;

    @FXML
    private Label labelTitle1;

    @FXML
    private Label imageLabel;

    @FXML // fx:id="columnAlbum"
    private TableColumn<Cancion, String> columnAlbum; // Value injected by FXMLLoader

    @FXML // fx:id="columnArtista"
    private TableColumn<Cancion, String> columnArtista; // Value injected by FXMLLoader

    @FXML // fx:id="columnAño"
    private TableColumn<Cancion, Integer> columnAño; // Value injected by FXMLLoader

    @FXML // fx:id="columnCancion"
    private TableColumn<Cancion, String> columnCancion; // Value injected by FXMLLoader

    @FXML // fx:id="columnDuracion"
    private TableColumn<Cancion, Integer> columnDuracion; // Value injected by FXMLLoader

    @FXML // fx:id="columnGenero"
    private TableColumn<Cancion, String> columnGenero; // Value injected by FXMLLoader


    @FXML // fx:id="tableCanciones"
    private TableView<Cancion> tableCanciones; // Value injected by FXMLLoader

    /**
     * Método que se ejecuta al hacer clic en el botón "Buscar".
     * Abre la vista de búsqueda de canciones.
     *
     * @param event El evento de acción.
     * @throws IOException Si ocurre un error al cargar la vista.
     */
    @FXML
    void actionBuscar(ActionEvent event) throws IOException {
        // Cargar la vista de búsqueda de canciones
        URL url2 = new File("src/main/java/co/edu/uniquindio/storify/view/busqueda.fxml").toURI().toURL();
        Parent root2 = FXMLLoader.load(url2);
        Scene scene2 = new Scene(root2, 906, 694);
        Stage stage2 = new Stage();

        // Establecer el estilo de la vista
        URL url1 = new File("src/main/java/co/edu/uniquindio/storify/view/styles/estilos.css").toURI().toURL();
        scene2.getStylesheets().add(url1.toExternalForm());

        // Configurar la ventana de la vista
        stage2.setTitle("Buscar canciones");
        stage2.setScene(scene2);
        stage2.show();

        // Cerrar la ventana actual
        Stage stage = (Stage) btnBuscar.getScene().getWindow();
        stage.close();
    }

    /**
     * Método que se ejecuta al hacer clic en el botón "Eliminar".
     * Elimina la canción seleccionada de la lista de canciones favoritas.
     *
     * @param event El evento de acción.
     */
    @FXML
    void actionEliminar(ActionEvent event) {
        this.modelFactoryController.eliminarFavorita(cancionSeleccion);
        loadTable();
    }

    /**
     * Método que se ejecuta al hacer clic en el botón "Ordenar por Álbum".
     * Ordena la lista de canciones por el valor de la columna del álbum.
     *
     * @param event El evento de acción.
     */
    @FXML
    void actionOrdenAlbum(ActionEvent event) {
        // Obtener la lista de elementos actualmente mostrados en la tabla
        ObservableList<Cancion> items = tableCanciones.getItems();

        // Ordenar la lista por el valor de la columna del álbum
        FXCollections.sort(items, Comparator.comparing(Cancion::getAlbum));

        // Actualizar la tabla con la lista ordenada
        tableCanciones.setItems(items);
    }

    /**
     * Método que se ejecuta al hacer clic en el botón "Ordenar por Año".
     * Ordena la lista de canciones por el valor de la columna del año.
     *
     * @param event El evento de acción.
     */
    @FXML
    void actionOrdenAnio(ActionEvent event) {
        // Obtener la lista de elementos actualmente mostrados en la tabla
        ObservableList<Cancion> items = tableCanciones.getItems();

        // Ordenar la lista por el valor de la columna del año
        FXCollections.sort(items, Comparator.comparing(Cancion::getAnio));

        // Actualizar la tabla con la lista ordenada
        tableCanciones.setItems(items);
    }

    /**
     * Método que se ejecuta al hacer clic en el botón "Ordenar por Duración".
     * Ordena la lista de canciones por el valor de la columna de la duración.
     *
     * @param event El evento de acción.
     */
    @FXML
    void actionOrdenDuracion(ActionEvent event) {
        // Obtener la lista de elementos actualmente mostrados en la tabla
        ObservableList<Cancion> items = tableCanciones.getItems();

        // Ordenar la lista por el valor de la columna de la duración
        FXCollections.sort(items, Comparator.comparing(Cancion::getDuracion));

        // Actualizar la tabla con la lista ordenada
        tableCanciones.setItems(items);
    }

    /**
     * Método que se ejecuta al hacer clic en el botón "Ordenar por Género".
     * Ordena la lista de canciones por el valor de la columna del género.
     *
     * @param event El evento de acción.
     */
    @FXML
    void actionOrdenGenero(ActionEvent event) {
        // Obtener la lista de elementos actualmente mostrados en la tabla
        ObservableList<Cancion> items = tableCanciones.getItems();

        // Ordenar la lista por el valor de la columna del género
        FXCollections.sort(items, Comparator.comparing(Cancion::getGenero));

        // Actualizar la tabla con la lista ordenada
        tableCanciones.setItems(items);
    }

    /**
     * Método que se ejecuta al hacer clic en el botón "Ordenar por Canción".
     * Ordena la lista de canciones por el valor de la columna del nombre de la canción.
     *
     * @param event El evento de acción.
     */
    @FXML
    void actionOrdenarCancion(ActionEvent event) {
        // Obtener la lista de elementos actualmente mostrados en la tabla
        ObservableList<Cancion> items = tableCanciones.getItems();

        // Ordenar la lista por el valor de la columna del nombre de la canción
        FXCollections.sort(items, Comparator.comparing(Cancion::getNombre));

        // Actualizar la tabla con la lista ordenada
        tableCanciones.setItems(items);
    }

    /**
     * Método que se ejecuta al hacer clic en el botón "Deshacer".
     * Deshace la última acción realizada en la lista de canciones favoritas.
     *
     * @param event El evento de acción.
     */
    @FXML
    void actionDeshacer(ActionEvent event) {
        modelFactoryController.deshacer();
        loadTable();
    }

    /**
     * Método que se ejecuta al hacer clic en el botón "Rehacer".
     * Rehace la última acción deshecha en la lista de canciones favoritas.
     *
     * @param event El evento de acción.
     */
    @FXML
    void actionRehacer(ActionEvent event) {
        modelFactoryController.rehacer();
        loadTable();
    }

    /**
     * Método que se ejecuta al hacer clic en el botón "Reproducir".
     * Reproduce la canción seleccionada.
     *
     * @param event El evento de acción.
     */
    @FXML
    void actionReproducir(ActionEvent event) {
        // Inicializar Chromium
        EngineOptions options = EngineOptions.newBuilder(HARDWARE_ACCELERATED)
                .licenseKey("1BNDHFSC1G6ACMC4FPYDA9JCGE2ON6O8O1TLU39NUKF2TT6JPNM2U3U13827LFGQ5LROE8")
                .build();
        Engine engine = Engine.newInstance(options);

        // Crear una instancia del navegador
        Browser browser = engine.newBrowser();

        // Cargar la página web requerida
        browser.navigation().loadUrl(cancionSeleccion.getUrlYoutube());

        // Crear y embeber el componente JavaFX BrowserView para mostrar el contenido web
        BrowserView view = BrowserView.newInstance(browser);

        Scene scene = new Scene(new BorderPane(view), 600, 406);
        Stage primaryStage = new Stage();
        primaryStage.setTitle(cancionSeleccion.getNombre());
        primaryStage.setScene(scene);
        primaryStage.show();

        // Apagar Chromium y liberar los recursos asignados
        primaryStage.setOnCloseRequest(event1 -> engine.close());
    }

    /**
     * Método que se ejecuta al hacer clic en el botón "Consultar".
     * Muestra información sobre el género con más canciones y el artista más popular.
     *
     * @param event El evento de acción.
     */
    @FXML
    void actionConsultar(ActionEvent event) {
        String genero = modelFactoryController.generoPopular();
        Artista artista = modelFactoryController.artistaPopular();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Top");
        alert.setHeaderText(null);
        alert.setContentText("El género con más canciones es: " + genero + "\n El artista más popular es: " + artista.getNombre());

        // Aplicar el estilo personalizado al diálogo de éxito
        URL cssFileURL = getClass().getResource("/co/edu/uniquindio/storify/view/styles/estilos.css");
        if (cssFileURL != null) {
            alert.getDialogPane().getStylesheets().add(cssFileURL.toExternalForm());
            alert.getDialogPane().getStyleClass().add("dialog-pane");
        }

        alert.showAndWait();

    }

    /**
     * Método que muestra la información de la canción seleccionada en los elementos de la interfaz.
     */
    private void mostrarInfo() {
        if (cancionSeleccion != null) {
            labelTitle.setText(cancionSeleccion.getNombre());
            labelAlbum.setText(cancionSeleccion.getAlbum());
            labelAnio.setText(String.valueOf(cancionSeleccion.getAnio()));
            labelDuracion.setText(convertirDuracion(cancionSeleccion.getDuracion()));
            labelGenero.setText(cancionSeleccion.getGenero());

            labelTitle.setVisible(true);
            labelAlbum.setVisible(true);
            labelGenero.setVisible(true);
            labelDuracion.setVisible(true);
            labelAnio.setVisible(true);

            labelTitle1.setVisible(true);
            labelDuracion1.setVisible(true);
            labelAlbum1.setVisible(true);
            labelGenero1.setVisible(true);
            labelAnio1.setVisible(true);
            String imagePath = cancionSeleccion.getCaratula();

            // Establecer la imagen de fondo del Label
            imageLabel.setStyle("-fx-background-image: url('file:" + imagePath + "');");

        }
    }

    /**
     * Convierte un número entero en una cadena de formato "minutos:segundos".
     *
     * @param numero El número entero a convertir.
     * @return La cadena de formato "minutos:segundos".
     */
    public String convertirDuracion(int numero) {
        int segundos = numero % 100;
        int minutos = numero / 100;
        return minutos + ":" + segundos;
    }

    /**
     * Método de inicialización que se ejecuta al cargar la interfaz.
     */
    @FXML
    void initialize() {
        modelFactoryController = ModelFactoryController.getInstance();
        loadTable();
        labelAlbum.setVisible(false);
        labelTitle.setVisible(false);
        labelGenero.setVisible(false);
        labelDuracion.setVisible(false);
        labelAnio.setVisible(false);
        labelTitle1.setVisible(false);
        labelDuracion1.setVisible(false);
        labelAlbum1.setVisible(false);
        labelGenero1.setVisible(false);
        labelAnio1.setVisible(false);
    }
    Cancion cancionSeleccion;


    private void loadTable() {
        columnCancion.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnArtista.setCellValueFactory(cellData -> {
            Artista artista = modelFactoryController.obtenerAutor(cellData.getValue());
            if (artista != null) {
                return new SimpleStringProperty(artista.getNombre());
            }else{
                return new SimpleStringProperty("");
            }
        });
        columnAlbum.setCellValueFactory(new PropertyValueFactory<>("album"));
        columnGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
        columnAño.setCellValueFactory(new PropertyValueFactory<>("anio"));
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
        ListaCircular<Cancion> list = modelFactoryController.tomarListaCancionesFavoritas();

        if (!list.isEmpty()){
            for (Cancion o: list) {
                tempList.add(o);

            }
            observableList.clear();
            observableList.addAll(tempList);
            tableCanciones.setItems(observableList);
            tableCanciones.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    cancionSeleccion = newSelection;
                    mostrarInfo();
                }
            });
        }else{
            observableList.clear();
        }
    }

}


