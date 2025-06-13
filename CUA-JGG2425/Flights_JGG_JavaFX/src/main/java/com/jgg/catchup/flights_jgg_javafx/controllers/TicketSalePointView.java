package com.jgg.catchup.flights_jgg_javafx.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TicketSalePointView {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}