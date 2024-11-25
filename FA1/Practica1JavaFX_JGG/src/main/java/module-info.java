module com.jgg.unit4.practica1javafx_jgg {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;


    opens com.jgg.unit4.practica1javafx_jgg to org.hibernate.orm.core, jakarta.persistence, javafx.fxml;
    exports com.jgg.unit4.practica1javafx_jgg;
    exports com.jgg.unit4.practica1javafx_jgg.controllers;
    opens com.jgg.unit4.practica1javafx_jgg.controllers to jakarta.persistence, javafx.fxml, org.hibernate.orm.core;
    exports com.jgg.unit4.practica1javafx_jgg.db;
    opens com.jgg.unit4.practica1javafx_jgg.db to jakarta.persistence, javafx.fxml, org.hibernate.orm.core;
}