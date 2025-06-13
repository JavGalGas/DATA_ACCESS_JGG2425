package com.jgg.catchup.flights_jgg_javafx.controllers;

import com.jgg.catchup.flights_jgg_javafx.models.entities.Ticket;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class TicketSalePointView {
    @FXML
    private TextField passportno;
    @FXML
    private DatePicker flight_date;
    @FXML
    private ComboBox<String> origin;
    @FXML
    private ComboBox<String> destination;
    @FXML
    private ComboBox<String> flight;

    @FXML
    protected void onButtonClick() {
        Ticket ticket = new Ticket();
        ticket.setPassportno(passportno.getText());
        ticket.setDateOfBooking(LocalDate.now());
        ticket.setDateOfTravel(flight_date.getValue());
        ticket.setFlightCode(flight.getSelectionModel().getSelectedItem());

    }
}