package com.jgg.unit4.practica1javafx_jgg;

import javafx.css.StyleClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class ProdOffersController {
    @FXML
    public HBox tab1;
    @FXML
    public HBox tab2;
    @FXML
    public HBox tab3;
    @FXML
    public HBox tab4;

    @FXML
    public StyleClass custom_tab;
    public ComboBox productCB;
    public DatePicker fromDP;
    public DatePicker toDP;
    public TextField discountTF;

    @FXML
    public void addOffer() {

    }
}
