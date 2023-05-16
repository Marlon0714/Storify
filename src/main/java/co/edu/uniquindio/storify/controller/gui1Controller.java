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
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.teamdev.jxbrowser.engine.RenderingMode.HARDWARE_ACCELERATED;

public class gui1Controller {

    ObservableList<Cancion> observableList = FXCollections.observableArrayList();

    ModelFactoryController modelFactoryController;

    @FXML // fx:id="brnEliminar"
    private Button brnEliminar; // Value injected by FXMLLoader

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

    @FXML
    void actionBuscar(ActionEvent event) {

    }

    @FXML
    void actionEliminar(ActionEvent event) {
        this.modelFactoryController.eliminarFavorita(cancionSeleccion);
        loadTable();

    }

    @FXML
    void actionOrdenAlbum(ActionEvent event) {
        // Obtener la lista de elementos actualmente mostrados en la tabla
        ObservableList<Cancion> items = tableCanciones.getItems();

        // Ordenar la lista por el valor de la columna del álbum
        FXCollections.sort(items, Comparator.comparing(Cancion::getAlbum));

        // Actualizar la tabla con la lista ordenada
        tableCanciones.setItems(items);
    }

    @FXML
    void actionOrdenAnio(ActionEvent event) {
        // Obtener la lista de elementos actualmente mostrados en la tabla
        ObservableList<Cancion> items = tableCanciones.getItems();

        // Ordenar la lista por el valor de la columna del año
        FXCollections.sort(items, Comparator.comparing(Cancion::getAnio));

        // Actualizar la tabla con la lista ordenada
        tableCanciones.setItems(items);
    }

    @FXML
    void actionOrdenDuracion(ActionEvent event) {
        // Obtener la lista de elementos actualmente mostrados en la tabla
        ObservableList<Cancion> items = tableCanciones.getItems();

        // Ordenar la lista por el valor de la columna de la duración
        FXCollections.sort(items, Comparator.comparing(Cancion::getDuracion));

        // Actualizar la tabla con la lista ordenada
        tableCanciones.setItems(items);
    }

    @FXML
    void actionOrdenGenero(ActionEvent event) {
        // Obtener la lista de elementos actualmente mostrados en la tabla
        ObservableList<Cancion> items = tableCanciones.getItems();

        // Ordenar la lista por el valor de la columna del género
        FXCollections.sort(items, Comparator.comparing(Cancion::getGenero));

        // Actualizar la tabla con la lista ordenada
        tableCanciones.setItems(items);
    }

    @FXML
    void actionOrdenarCancion(ActionEvent event) {
        // Obtener la lista de elementos actualmente mostrados en la tabla
        ObservableList<Cancion> items = tableCanciones.getItems();

        // Ordenar la lista por el valor de la columna del nombre de la canción
        FXCollections.sort(items, Comparator.comparing(Cancion::getNombre));

        // Actualizar la tabla con la lista ordenada
        tableCanciones.setItems(items);
    }

    @FXML
    void actionReproducir(ActionEvent event){
        // Initialize Chromium.
        EngineOptions options = EngineOptions.newBuilder(HARDWARE_ACCELERATED)
                .licenseKey("1BNDHFSC1G6ACMC4FPYDA9JCGE2ON6O8O1TLU39NUKF2TT6JPNM2U3U13827LFGQ5LROE8")
                .build();
        Engine engine = Engine.newInstance(options);

// Create a Browser instance.
        Browser browser = engine.newBrowser();

// Load the required web page.
        browser.navigation().loadUrl(cancionSeleccion.getUrlYoutube());

// Create and embed JavaFX BrowserView component to display web content.
        BrowserView view = BrowserView.newInstance(browser);

        Scene scene = new Scene(new BorderPane(view), 600, 406);
        Stage primaryStage = new Stage();
        primaryStage.setTitle(cancionSeleccion.getNombre());
        primaryStage.setScene(scene);
        primaryStage.show();

// Shutdown Chromium and release allocated resources.
        primaryStage.setOnCloseRequest(event1 -> engine.close());
    }

    /***@FXML
    void actionReproducir(ActionEvent event) {
        NativeInterface.open();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("YouTube Viewer");
                frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
                frame.getContentPane().add(getBrowserPanel(cancionSeleccion), BorderLayout.CENTER);
                frame.setSize(800, 600);
                frame.setLocationByPlatform(true);
                frame.setVisible(true);
            }
        });
        NativeInterface.runEventPump();
        // don't forget to properly close native components
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            //@Override
            public void run() {
                NativeInterface.close();
            }
        }));
    }/**/

    /**public static JPanel getBrowserPanel(Cancion cancionSeleccion) {
        JPanel webBrowserPanel = new JPanel(new BorderLayout());
        JWebBrowser webBrowser = new JWebBrowser(new chrriis.dj.nativeswing.NSOption[]{});
        webBrowserPanel.add(webBrowser, BorderLayout.CENTER);
        webBrowser.setBarsVisible(false);
        webBrowser.navigate(cancionSeleccion.getUrlYoutube());
        return webBrowserPanel;
    }**/


    @FXML
    void actionOrdenArtista(ActionEvent event) {

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
        for (Cancion o: list) {
            tempList.add(o);
            //Artista a =modelFactoryController.obtenerAutor(o);
            //columnArtista.setCellValueFactory(new PropertyValueFactory<>(a.getNombre()));
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
    }

    private void mostrarInfo() {
        if(cancionSeleccion != null){
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
    public String convertirDuracion(int numero) {
        int segundos = numero % 100;
        int minutos = numero / 100;
        return minutos + ":" + segundos;
    }


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

}


