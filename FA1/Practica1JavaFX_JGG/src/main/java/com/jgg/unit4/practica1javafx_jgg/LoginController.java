package com.jgg.unit4.practica1javafx_jgg;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;

public class LoginController {

    @FXML
    private PasswordField userText;

    @FXML
    private PasswordField passwdText;

    @FXML
    protected void onLoginButtonClick() {
        System.out.println("Login Button Clicked!");
        String user = userText.getText();
        String passwd = passwdText.getText();
//        checkLogin(user, passwd);
        System.out.println("User: " + user);
        System.out.println("Password: " + passwd);
    }

    public void checkLogin(String user, String passwd) {
        try {
            if (checkUser(user, "") &&
                    checkPassword(passwd, "passwd")) {
                SellerAppApplication.LOGGER.log(Level.SEVERE, "Login Successful!");
                /*Go to the next screen*/
            } else {
                SellerAppApplication.LOGGER.log(Level.SEVERE, "Login Unsuccessful: " /*+ errorMessage*/);
                /*Error code*/
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static boolean checkPassword(String password, String storedHash /*, out errorMessage*/) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            byte[] hashedBytes = md.digest(password.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes)
            {
                sb.append(String.format("%02x",  b));
            }
            String generatedHash = sb.toString();

            return generatedHash.equals(storedHash);
        } catch (NoSuchAlgorithmException e) {
            SellerAppApplication.LOGGER.log(Level.SEVERE, "Password Hash Not Built Correctly!");
            System.out.println(e.getMessage());
            /*errorMessage + = "/n " + e.getMessage();*/
            return false;
        }
    }

    public static boolean checkUser(String user, String storedUser) {
        return user.equals(storedUser);
    }

}