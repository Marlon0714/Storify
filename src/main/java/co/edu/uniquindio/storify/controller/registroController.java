/**
 * Sample Skeleton for 'registro.fxml' Controller Class
 */

package co.edu.uniquindio.storify.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.storify.exceptions.ExistingUserException;
import co.edu.uniquindio.storify.model.Cancion;
import co.edu.uniquindio.storify.model.ListaCircular;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class registroController {

    ModelFactoryController modelFactoryController;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnRegistrarse"
    private Button btnRegistrarse; // Value injected by FXMLLoader

    @FXML // fx:id="logo"
    private Label logo; // Value injected by FXMLLoader

    @FXML // fx:id="txtCorreo"
    private TextField txtCorreo; // Value injected by FXMLLoader

    @FXML // fx:id="txtPasswd"
    private PasswordField txtPasswd; // Value injected by FXMLLoader

    @FXML // fx:id="txtUsername"
    private TextField txtUsername; // Value injected by FXMLLoader

    @FXML
    void actionRegistrar(ActionEvent event) throws IOException {
        ListaCircular<Cancion> cancionesFav = new ListaCircular<>();
        String username = txtUsername.getText();
        String passwd = txtPasswd.getText();
        String email = txtCorreo.getText();
    try {
        modelFactoryController.crearUsuario(username,email,passwd,cancionesFav);
        mostrarLogin();
    }catch (ExistingUserException e){
        showErrorDialog("Usuario ya creado");
    }
    }

    void mostrarLogin() throws IOException {
        URL url = new File("src/main/java/co/edu/uniquindio/storify/view/login.fxml").toURI().toURL();
        Parent root1 = FXMLLoader.load(url);
        Scene scene1 = new Scene(root1, 600 , 406);
        Stage stage1 = new Stage();
        URL url1 = new File("src/main/java/co/edu/uniquindio/storify/view/styles/estilos.css").toURI().toURL();
        scene1.getStylesheets().add(url1.toExternalForm());
        stage1.setTitle(txtUsername.getText());
        stage1.setScene(scene1);
        stage1.show();


        Stage stage = (Stage) btnRegistrarse.getScene().getWindow();
        stage.close();
    }

    public void showErrorDialog(String message) throws MalformedURLException {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error creando usuario");
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Aplicar el estilo personalizado al diálogo de error
        URL url1 = new File("src/main/java/co/edu/uniquindio/storify/view/styles/estilos.css").toURI().toURL();
        alert.getDialogPane().getStylesheets().add((url1.toExternalForm()));
        alert.getDialogPane().getStyleClass().add("dialog-pane");

        alert.showAndWait();
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        modelFactoryController = ModelFactoryController.getInstance();
        assert btnRegistrarse != null : "fx:id=\"btnRegistrarse\" was not injected: check your FXML file 'registro.fxml'.";
        assert logo != null : "fx:id=\"logo\" was not injected: check your FXML file 'registro.fxml'.";
        assert txtCorreo != null : "fx:id=\"txtCorreo\" was not injected: check your FXML file 'registro.fxml'.";
        assert txtPasswd != null : "fx:id=\"txtPasswd\" was not injected: check your FXML file 'registro.fxml'.";
        assert txtUsername != null : "fx:id=\"txtUsername\" was not injected: check your FXML file 'registro.fxml'.";

    }

}
