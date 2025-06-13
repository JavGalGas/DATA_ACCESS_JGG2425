module com.jgg.catchup.flights_jgg_javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;


    opens com.jgg.catchup.flights_jgg_javafx to javafx.fxml;
    exports com.jgg.catchup.flights_jgg_javafx;
    exports com.jgg.catchup.flights_jgg_javafx.controllers;
    opens com.jgg.catchup.flights_jgg_javafx.controllers to javafx.fxml;
}