module com.jgg.unit4.practica1javafx_jgg {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.jgg.unit4.practica1javafx_jgg to javafx.fxml;
    exports com.jgg.unit4.practica1javafx_jgg;
}