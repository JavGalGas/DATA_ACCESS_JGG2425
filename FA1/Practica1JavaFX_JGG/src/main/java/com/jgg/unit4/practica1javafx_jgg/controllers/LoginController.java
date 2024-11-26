package com.jgg.unit4.practica1javafx_jgg.controllers;

import com.jgg.unit4.practica1javafx_jgg.GenericClassCRUD;
import com.jgg.unit4.practica1javafx_jgg.db.Seller;
import com.jgg.unit4.practica1javafx_jgg.SellerApplication;
import com.jgg.unit4.practica1javafx_jgg.UI;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

public class LoginController {
    @FXML
    public Button loginButton;
    public CheckBox rememberCheckBox;
    @FXML
    private AnchorPane loginAPane;
    @FXML
    private PasswordField userText;

    @FXML
    private PasswordField passwdText;

    @FXML
    private Label errorMessage;

    private static Seller user;

    public static Seller getUser() {
        return user;
    }

    public static void setUser(Seller user) {
        LoginController.user = user;
    }

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

                    if (user.equals(seller.getCif()) && UI.convertToMD5(passwd).equalsIgnoreCase(seller.getPassword())) {
                        SellerApplication.LOGGER.info("Login Successful!");
                        errorMessage.setText("Login Successful!");
                        try {
                            setUser(seller);
                            if(rememberCheckBox.isSelected()) {
                                System.out.println("Saving user...");
                                UI.saveUserCif(user);
                            }
                            SellerApplication.LOGGER.info("Loading screen...");
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/jgg/unit4/practica1javafx_jgg/seller_data-view.fxml"));
                            Parent root = loader.load();

                            Scene scene = new Scene(root);
                            Stage currentStage = (Stage) loginButton.getScene().getWindow();
                            currentStage.setScene(scene);
                            currentStage.show();
                        } catch (IOException ioException) {
                            SellerApplication.LOGGER.log(Level.SEVERE, "An error occurred while loading the seller_data-view.fxml ", ioException);
                            UI.showErrorAlert("Error", "An error occurred while loading the scene", ioException.getMessage());
                            Stage currentStage = (Stage) loginButton.getScene().getWindow();
                            currentStage.close();
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
                UI.showErrorAlert("Error", "An error occurred while login", exception.getMessage());
            }
        }
    }

    public void checkSavedCifs(InputMethodEvent mouseEvent) {
        char[] cif = userText.getText().toCharArray();
        try (BufferedReader reader = new BufferedReader(new FileReader("user_info.txt"))){
            char[] userCif = null;
            String line = reader.readLine();
            while (line != null) {
                userCif = line.toCharArray();
                for (int i = 0; i < cif.length; i++) {
                    if (userCif[i] != cif[i]) {
                        break;
                    } else if (userCif[i] == cif[i] && i == cif.length - 1) {
                        setTextFieldsByCif(line);
                        return;
                    }
                }
                line = reader.readLine();
            }
        } catch (IOException exception) {
            SellerApplication.LOGGER.log(Level.SEVERE, "Error while reading user_info.txt", exception);
            UI.showErrorAlert("Error", "Error while reading user_info.txt", exception.getMessage());
        }
    }

    private void setTextFieldsByCif(String userCif){
        userText.setText(userCif);
        List<Seller> sellers = new GenericClassCRUD<>(Seller.class).findAll();
        for (Seller seller : sellers) {
            if (userCif.equals(seller.getCif())) {
                passwdText.setText(seller.getPassword());
                return;
            }
        }
        passwdText.setText("");
    }
}