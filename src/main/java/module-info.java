module co.edu.uniquindio {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.logging;

    opens co.edu.uniquindio to javafx.fxml;
    exports co.edu.uniquindio.storify.controller;
    opens co.edu.uniquindio.storify.controller to javafx.fxml;
    exports co.edu.uniquindio.storify.application;
    opens co.edu.uniquindio.storify.application to javafx.fxml;
    exports co.edu.uniquindio.storify.model;
}


