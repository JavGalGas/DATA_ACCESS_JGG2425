package com.jgg.unit4.practica1javafx_jgg;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.logging.Level;

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
    public ComboBox<SellerProduct> productCB;
    public DatePicker fromDP;
    public DatePicker toDP;
    public TextField discountTF;

    @FXML
    public void onAddButtonClick() {
        SellerProduct product = productCB.getSelectionModel().getSelectedItem();
        LocalDate from = fromDP.getValue();
        LocalDate to = toDP.getValue();
        String discount = discountTF.getText();
        addOffer(product, from, to, discount);
    }

    private void addOffer(SellerProduct product, LocalDate from, LocalDate to, String discount) {
        //Product checks
        if(product == null) {
            SellerApplication.LOGGER.log(Level.SEVERE, "Product is null");
        }
        //From/To checks
        //discount check
        else{
            try{
                product.setOfferStartDate(from);
                product.setOfferEndDate(to);
                double convertedDiscount = Double.parseDouble(discount);
                convertedDiscount /= 100;
                BigDecimal percentage = new BigDecimal(convertedDiscount);
                BigDecimal discountedPrice = product.getPrice().multiply(percentage);
                product.setOfferPrice(discountedPrice);
                GenericClassCRUD<SellerProduct> sp_crud = new GenericClassCRUD<>(SellerProduct.class);
                sp_crud.update(product);

            } catch (NumberFormatException numberFormatException) {
                SellerApplication.LOGGER.log(Level.SEVERE, "An error occurred while parsing the discount", numberFormatException);

            } catch(Exception exception){
                SellerApplication.LOGGER.log(Level.SEVERE, "An error occurred while adding a new offer", exception);

            }
        }
    }



    @FXML
    private void initialize() {
        // Get the current scene
        Scene scene = SellerApplication.getAppStage().getScene();
        // Add the CSS file
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("custom_tab.css")).toExternalForm());
    }
}
