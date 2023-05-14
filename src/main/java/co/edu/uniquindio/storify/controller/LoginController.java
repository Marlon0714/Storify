package co.edu.uniquindio.storify.controller;

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


    public void actionLogin(javafx.event.ActionEvent event) throws IOException {
        if(txtUsername.getText().equals("admin") && pswdField.getText().equals("$aDmiN")){
            mostrarAdminView();
        }else {
            this.modelFactoryController.iniciarSesion(txtUsername.getText(), pswdField.getText());
            mostrarUserGui();
        }
    }

    public void actionRegistro(ActionEvent event) {
    }

    void mostrarAdminView() throws IOException {
        URL url = new File("src/main/java/co/edu/uniquindio/storify/view/gui1.fxml").toURI().toURL();
        Parent root1 = FXMLLoader.load(url);
        Scene scene1 = new Scene(root1, 906 , 694);
        Stage stage1 = new Stage();
        URL url1 = new File("src/main/java/co/edu/uniquindio/storify/view/styles/estilos.css").toURI().toURL();
        scene1.getStylesheets().add(url1.toExternalForm());
        stage1.setTitle(txtUsername.getText());
        stage1.setScene(scene1);
        stage1.show();


        Stage stage = (Stage) btnLogin.getScene().getWindow();
        stage.close();
    }







    void mostrarUserGui() throws IOException {
        URL url2 = new File("src/main/java/co/edu/uniquindio/storify/view/gui1.fxml").toURI().toURL();
        Parent root2 = FXMLLoader.load(url2);
        Scene scene2 = new Scene(root2, 906 , 694);
        Stage stage2 = new Stage();
        URL url1 = new File("src/main/java/co/edu/uniquindio/storify/view/styles/estilos.css").toURI().toURL();
        scene2.getStylesheets().add(url1.toExternalForm());
        stage2.setTitle(txtUsername.getText());
        stage2.setScene(scene2);
        stage2.show();

        Stage stage = (Stage) btnLogin.getScene().getWindow();
        stage.close();
    }
    @FXML
    void initialize() {
        assert pswdField != null : "fx:id=\"pswdField\" was not injected: check your FXML file 'login.fxml'.";
        assert btnLogin != null : "fx:id=\"btnLogin\" was not injected: check your FXML file 'login.fxml'.";
        assert txtUsername != null : "fx:id=\"txtUsername\" was not injected: check your FXML file 'login.fxml'.";
        modelFactoryController = ModelFactoryController.getInstance();
    }
}