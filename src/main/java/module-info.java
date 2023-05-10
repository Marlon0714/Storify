module co.edu.uniquindio {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.logging;

    opens co.edu.uniquindio to javafx.fxml;
    exports co.edu.uniquindio;
}
