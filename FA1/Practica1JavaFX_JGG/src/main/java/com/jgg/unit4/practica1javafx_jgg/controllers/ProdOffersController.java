package com.jgg.unit4.practica1javafx_jgg.controllers;

import com.jgg.unit4.practica1javafx_jgg.GenericClassCRUD;
import com.jgg.unit4.practica1javafx_jgg.SellerApplication;
import com.jgg.unit4.practica1javafx_jgg.db.Product;
import com.jgg.unit4.practica1javafx_jgg.db.SellerProduct;
import com.jgg.unit4.practica1javafx_jgg.UI;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.util.StringConverter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
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
    @FXML
    public DatePicker fromDP;
    @FXML
    public DatePicker toDP;
    @FXML
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
        else if(from == null || to == null) {
            SellerApplication.LOGGER.log(Level.SEVERE, "From and To are null");
        } else if (from.isBefore(to)) {
            SellerApplication.LOGGER.log(Level.SEVERE, "From is before to");
        } else if (from.isEqual(to)) {
            SellerApplication.LOGGER.log(Level.SEVERE, "From and to cannot be equal");
        } else if (ChronoUnit.DAYS.between(from, to) > 30) {
            SellerApplication.LOGGER.log(Level.SEVERE, "Offer cannot last more than 30 days");
        }
        //discount check
        else{
            try{
                GenericClassCRUD<SellerProduct> sp_crud = new GenericClassCRUD<>(SellerProduct.class);
                List<SellerProduct> products = sp_crud.findAll("sellerProductsFilter", "sellerId", product.getSeller());
                for (SellerProduct sp : products) {
                    if(sp.getOfferStartDate().isBefore(to) && sp.getOfferEndDate().isAfter(from)){
                        return;
                    }
                }
                product.setOfferStartDate(from);
                product.setOfferEndDate(to);
                double convertedDiscount = Double.parseDouble(discount);
                convertedDiscount /= 100;
                BigDecimal percentage = new BigDecimal(convertedDiscount);
                BigDecimal discountedPrice = product.getPrice().multiply(percentage);
                product.setOfferPrice(discountedPrice);

                sp_crud.update(product);

            } catch (NumberFormatException numberFormatException) {
                SellerApplication.LOGGER.log(Level.SEVERE, "An error occurred while parsing the discount", numberFormatException);
                UI.showErrorAlert("Error", "An error occurred while parsing the discount", numberFormatException.getMessage());

            } catch(Exception exception){
                SellerApplication.LOGGER.log(Level.SEVERE, "An error occurred while adding a new offer", exception);
                UI.showErrorAlert("Error", "An error occurred while adding a new offer", exception.getMessage());
            }
        }
    }

    private List<SellerProduct> fetchProducts() {
        return new GenericClassCRUD<>(SellerProduct.class).findAll();
    }

    @FXML
    private void initialize() {
//        productCB.setConverter(converter);
        productCB.getItems().addAll(fetchProducts());
    }

    StringConverter<SellerProduct> converter = new StringConverter<>() {
        @Override
        public String toString(SellerProduct sellerProduct) {
            Product selectedProduct = new GenericClassCRUD<>(Product.class).findById(sellerProduct.getProduct());
            return selectedProduct != null ? selectedProduct.getProductName() : "";
        }

        @Override
        public SellerProduct fromString(String s) {
            return null;
        }
    };

}
