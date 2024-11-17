package com.jgg.unit4.practica1javafx_jgg;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class SellerApplication extends Application {
    public static final Logger LOGGER = Logger.getLogger(SellerApplication.class.getName());
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SellerApplication.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("SellerApp");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        try {
            // Creates a file handler
            FileHandler fileHandler = new FileHandler("seller_app.log");
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);
            // Registers messages
            LOGGER.info("Program beginning");
            launch();
            fileHandler.close();
        } catch (Exception exception) {
            LOGGER.log(Level.SEVERE, "Error while configuring the logger", exception);
            LOGGER.info(exception.getMessage());
            LOGGER.severe("A critical error occurred during initialization.");
        }
    }
}