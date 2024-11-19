package com.jgg.unit4.practica1javafx_jgg;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Level;

public class LoginController {
    @FXML
    public Button loginButton;
    @FXML
    private AnchorPane loginAPane;
    @FXML
    private PasswordField userText;

    @FXML
    private PasswordField passwdText;

    @FXML
    private Label errorMessage;

    @FXML
    protected void onLoginButtonClick() {
        String user = userText.getText();
        String passwd = passwdText.getText();
        checkLogin(user, passwd);
    }

    public void checkLogin(String user, String passwd) {
        if (user.isEmpty() && passwd.isEmpty()) {
            SellerApplication.LOGGER.log(Level.SEVERE, "User and Password are Empty!");
            errorMessage.setText("User and Password are Empty!");
        } else if (user.isEmpty()) {
            SellerApplication.LOGGER.log(Level.SEVERE, "User is Empty!");
            errorMessage.setText("User is Empty!");
        } else if (passwd.isEmpty()) {
            SellerApplication.LOGGER.log(Level.SEVERE, "Password is Empty!");
            errorMessage.setText("Password is Empty!");
        } else {
            try {
                GenericClassCRUD<Seller> crud = new GenericClassCRUD<>(Seller.class);

                List<Seller> sellers = crud.findAll();
                for (Seller seller : sellers) {

                    if (user.equals(seller.getCif()) && checkPassword(passwd, seller.getPassword())) {
                        SellerApplication.LOGGER.info("Login Successful!");
                        errorMessage.setText("Login Successful!");
                        try {
                            SellerApplication.LOGGER.info("Loading screen...");
                            FXMLLoader loader = new FXMLLoader(SellerApplication.class.getResource("prod_offers-view.fxml"/*"seller_data-view.fxml"*/));
                            Parent root = loader.load();
                            Scene scene = new Scene(root, 600,400);
                            SellerApplication.getAppStage().setScene(scene);

                        } catch (IOException ioException) {
                            SellerApplication.LOGGER.log(Level.SEVERE, "An error occurred while loading the seller_data-view.fxml ", ioException);
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setHeaderText("An error occurred while loading the scene");
                            alert.setContentText(ioException.getMessage());

                            alert.showAndWait();

                            Platform.exit();
                        }
                        return;
                    }
                }
                SellerApplication.LOGGER.log(Level.INFO, "Login Unsuccessful!");
                errorMessage.setText("Login Unsuccessful!");
            }
            catch (Exception exception) {
                SellerApplication.LOGGER.log(Level.SEVERE, "An error occurred while login ", exception);
//                SellerApplication.LOGGER.info("Login Unsuccessful: " + exception.getMessage() );
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("An error occurred while login");
                alert.setContentText(exception.getMessage());
                alert.showAndWait();

            }
        }
    }

    public static boolean checkPassword(String password, String storedHash) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            byte[] hashedBytes = md.digest(password.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes)
            {
                sb.append(String.format("%02x",  b));
            }
            String generatedHash = sb.toString();

            return generatedHash.equalsIgnoreCase(storedHash);
        } catch (NoSuchAlgorithmException exception) {
            SellerApplication.LOGGER.log(Level.SEVERE, "Password Hash Not Built Correctly", exception);
//            SellerApplication.LOGGER.info("Password Hash Not Built Correctly: " + exception.getMessage() );
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Password Hash Not Built Correctly");
            alert.setContentText(exception.getMessage());
            return false;
        }
    }

}