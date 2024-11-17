package com.jgg.unit4.practica1javafx_jgg;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Level;

public class LoginController {

    @FXML
    private PasswordField userText;

    @FXML
    private PasswordField passwdText;

    @FXML
    private Label errorMessage;

    @FXML
    protected void onLoginButtonClick() {
//        System.out.println("Login Button Clicked!");
        String user = userText.getText();
        String passwd = passwdText.getText();
        checkLogin(user, passwd);
//        System.out.println("User: " + user);
//        System.out.println("Password: " + passwd);
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
                        /*Go to the next screen*/
                        return;
                    }
                }
                SellerApplication.LOGGER.log(Level.INFO, "Login Unsuccessful!");
                errorMessage.setText("Login Unsuccessful!");
            }
            catch (Exception e) {
                SellerApplication.LOGGER.log(Level.SEVERE, "An error occurred while login ", e);
                SellerApplication.LOGGER.info("Login Unsuccessful: " + e.getMessage() );
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
        } catch (NoSuchAlgorithmException e) {
            SellerApplication.LOGGER.log(Level.SEVERE, "Password Hash Not Built Correctly!");
            SellerApplication.LOGGER.info("Password Hash Not Built Correctly: " + e.getMessage() );
            return false;
        }
    }

}