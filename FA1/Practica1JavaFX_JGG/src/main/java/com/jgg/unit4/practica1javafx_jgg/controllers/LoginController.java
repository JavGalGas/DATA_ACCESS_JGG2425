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
    @FXML
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
        if (validateInputs(user, passwd)) {
            attemptLogin(user, passwd);
        }
    }

    private boolean validateInputs(String user, String passwd) {
        if (user.isEmpty() && passwd.isEmpty()) {
            logAndShowError("User and Password are Empty!");
            return false;
        } else if (user.isEmpty()) {
            logAndShowError("User is Empty!");
            return false;
        } else if (passwd.isEmpty()) {
            logAndShowError("Password is Empty!");
            return false;
        }
        return true;
    }

    private void attemptLogin(String user, String passwd) {
        try {
            GenericClassCRUD<Seller> crud = new GenericClassCRUD<>(Seller.class);
            List<Seller> sellers = crud.findAll();

            for (Seller seller : sellers) {
                if (isValidUser(seller, user, passwd)) {
                    handleSuccessfulLogin(seller, user);
                    return;
                }
            }
            logAndShowError("Login Unsuccessful!");
        } catch (Exception exception) {
            SellerApplication.LOGGER.log(Level.SEVERE, "An error occurred during login", exception);
            UI.showErrorAlert("Error", "An error occurred during login", exception.getMessage());
        }
    }

    private boolean isValidUser(Seller seller, String user, String passwd) {
        return user.equals(seller.getCif()) &&
                UI.convertToMD5(passwd).equalsIgnoreCase(seller.getPassword());
    }

    private void handleSuccessfulLogin(Seller seller, String user) {
        SellerApplication.LOGGER.info("Login Successful!");
        errorMessage.setText("Login Successful!");
        setUser(seller);

        if (rememberCheckBox.isSelected()) {
            saveUser(user);
        }

        loadNextScene();
    }

    private void saveUser(String user) {
        SellerApplication.LOGGER.info("Saving user...");
        UI.saveUserCif(user);
    }

    private void loadNextScene() {
        try {
            SellerApplication.LOGGER.info("Loading screen...");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/jgg/unit4/practica1javafx_jgg/seller_data-view.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage currentStage = (Stage) loginButton.getScene().getWindow();
            currentStage.setScene(scene);
            currentStage.show();
        } catch (IOException ioException) {
            SellerApplication.LOGGER.log(Level.SEVERE, "An error occurred while loading the seller_data-view.fxml", ioException);
            UI.showErrorAlert("Error", "An error occurred while loading the scene", ioException.getMessage());
            Stage currentStage = (Stage) loginButton.getScene().getWindow();
            currentStage.close();
            Platform.exit();
        }
    }

    private void logAndShowError(String message) {
        SellerApplication.LOGGER.log(Level.SEVERE, message);
        errorMessage.setText(message);
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