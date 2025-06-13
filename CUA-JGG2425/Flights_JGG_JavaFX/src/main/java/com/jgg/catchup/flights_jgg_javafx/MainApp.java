package com.jgg.catchup.flights_jgg_javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {
    private static Stage appStage;

    public static Stage getAppStage() {
        return appStage;
    }

    @Override
    public void start(Stage stage) throws IOException {
        appStage = stage;

        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("ticket_sale_point-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Ticket Sale Point");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}