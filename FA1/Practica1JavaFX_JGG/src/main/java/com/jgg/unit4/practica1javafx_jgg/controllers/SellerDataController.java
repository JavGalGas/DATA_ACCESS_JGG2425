package com.jgg.unit4.practica1javafx_jgg.controllers;

import com.jgg.unit4.practica1javafx_jgg.GenericClassCRUD;
import com.jgg.unit4.practica1javafx_jgg.SellerApplication;
import com.jgg.unit4.practica1javafx_jgg.UI;
import com.jgg.unit4.practica1javafx_jgg.db.Seller;
import com.jgg.unit4.practica1javafx_jgg.db.User;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.util.Objects;
import java.util.logging.Level;

public class SellerDataController {

    @FXML
    public AnchorPane sDataAPane;
    public Text sellerCif;
    public TextField sellerName;
    public TextField sellerBName;
    public TextField sellerPhone;
    public TextField sellerEmail;
    public TextField sellerPasswd;
    public HBox tab1;
    public HBox tab2;
    public HBox tab3;
    public HBox tab4;

    @FXML
    private void initialize() {
        try{
            Seller seller = LoginController.getUser();
            sellerCif.setText(seller.getCif());
            sellerName.setText(seller.getName());
            sellerBName.setText(seller.getBusinessName());
            sellerPhone.setText(seller.getPhone());
            String email = seller.getEmail();
            if(email != null && !email.isEmpty()){
                sellerEmail.setText(email);
            } else {
                String userEmail = new GenericClassCRUD<>(User.class).findById(seller.getId()).getEmail();
                sellerEmail.setText(userEmail);
            }
            sellerPasswd.setText(seller.getPassword());
        } catch (Exception exception) {
            SellerApplication.LOGGER.log(Level.SEVERE, "An error occurred while initializing", exception);
            UI.showErrorAlert("Error", "An error occurred while initializing", exception.getMessage());
        }
    }


}
