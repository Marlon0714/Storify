package co.edu.uniquindio.storify.controller;

import co.edu.uniquindio.storify.model.Administrador;
import co.edu.uniquindio.storify.model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginController {
    ModelFactoryController modelFactoryController;
    @FXML // fx:id="btnLogin"
    private Button btnLogin; // Value injected by FXMLLoader

    @FXML // fx:id="btnRegistro"
    private Button btnRegistro; // Value injected by FXMLLoader

    @FXML // fx:id="logo"
    private Label logo; // Value injected by FXMLLoader

    @FXML // fx:id="pswdField"
    private PasswordField pswdField; // Value injected by FXMLLoader

    @FXML // fx:id="txtUsername"
    private TextField txtUsername; // Value injected by FXMLLoader

    /**
     * Acción de inicio de sesión.
     *
     * @param event Evento de acción.
     * @throws IOException Si ocurre un error al cargar la siguiente vista.
     */
    public void actionLogin(ActionEvent event) throws IOException {
        modelFactoryController.iniciarSesion(txtUsername.getText(), pswdField.getText());

        if (modelFactoryController.getUsuariologgeado() instanceof Administrador) {
            mostrarAdminView();
        } else {
            this.modelFactoryController.iniciarSesion(txtUsername.getText(), pswdField.getText());
            mostrarUserGui();
        }
    }

    /**
     * Acción de registro.
     *
     * @param event Evento de acción.
     * @throws IOException Si ocurre un error al cargar la vista de registro.
     */
    public void actionRegistro(ActionEvent event) throws IOException {
        URL url = new File("src/main/java/co/edu/uniquindio/storify/view/registro.fxml").toURI().toURL();
        Parent root1 = FXMLLoader.load(url);
        Scene scene1 = new Scene(root1, 600, 406);
        Stage stage1 = new Stage();
        URL url1 = new File("src/main/java/co/edu/uniquindio/storify/view/styles/estilos.css").toURI().toURL();
        scene1.getStylesheets().add(url1.toExternalForm());
        stage1.setTitle(txtUsername.getText());
        stage1.setScene(scene1);
        stage1.show();

        Stage stage = (Stage) btnLogin.getScene().getWindow();
        stage.close();
    }

    /**
     * Muestra la vista de administrador.
     *
     * @throws IOException Si ocurre un error al cargar la vista de administrador.
     */
    void mostrarAdminView() throws IOException {
        URL url = new File("src/main/java/co/edu/uniquindio/storify/view/gui2.fxml").toURI().toURL();
        Parent root1 = FXMLLoader.load(url);
        Scene scene1 = new Scene(root1, 906, 694);
        Stage stage1 = new Stage();
        URL url1 = new File("src/main/java/co/edu/uniquindio/storify/view/styles/estilos.css").toURI().toURL();
        scene1.getStylesheets().add(url1.toExternalForm());
        stage1.setTitle(txtUsername.getText());
        stage1.setScene(scene1);
        stage1.show();

        Stage stage = (Stage) btnLogin.getScene().getWindow();
        stage.close();
    }

    /**
     * Muestra la vista de usuario.
     *
     * @throws IOException Si ocurre un error al cargar la vista de usuario.
     */
    void mostrarUserGui() throws IOException {
        URL url2 = new File("src/main/java/co/edu/uniquindio/storify/view/gui1.fxml").toURI().toURL();
        Parent root2 = FXMLLoader.load(url2);
        Scene scene2 = new Scene(root2, 906, 694);
        Stage stage2 = new Stage();
        URL url1 = new File("src/main/java/co/edu/uniquindio/storify/view/styles/estilos.css").toURI().toURL();
        scene2.getStylesheets().add(url1.toExternalForm());
        stage2.setTitle(txtUsername.getText());
        stage2.setScene(scene2);
        stage2.show();

        Stage stage = (Stage) btnLogin.getScene().getWindow();
        stage.close();
    }

    /**
     * Método de inicialización del controlador.
     * Verifica la inyección de elementos FXML y obtiene una instancia del controlador de modelos.
     */
    @FXML
    void initialize() {
        assert pswdField != null : "fx:id=\"pswdField\" was not injected: check your FXML file 'login.fxml'.";
        assert btnLogin != null : "fx:id=\"btnLogin\" was not injected: check your FXML file 'login.fxml'.";
        assert txtUsername != null : "fx:id=\"txtUsername\" was not injected: check your FXML file 'login.fxml'.";
        modelFactoryController = ModelFactoryController.getInstance();
    }
}