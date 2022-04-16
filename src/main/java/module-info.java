module com.example.rfui {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    opens com.example.rfui;
    exports com.example.rfui;
}