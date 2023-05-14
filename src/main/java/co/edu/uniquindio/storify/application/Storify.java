package co.edu.uniquindio.storify.application;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static javafx.application.Application.launch;

public class Storify extends Application {

        @Override
        public void start(Stage stage) throws IOException {
            // Crear el contenido de la escena
            URL url = new File ("src/main/java/co/edu/uniquindio/storify/view/login.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);
            Scene scene = new Scene(root,600,406 );
            URL url1 = new File("src/main/java/co/edu/uniquindio/storify/view/styles/estilos.css").toURI().toURL();
            scene.getStylesheets().add(url1.toExternalForm());



            // Configurar el escenario principal
            stage.setTitle("Storify");
            stage.setScene(scene);
            stage.show();
        }

        public static void main(String[] args) {
            launch();
        }
    }


