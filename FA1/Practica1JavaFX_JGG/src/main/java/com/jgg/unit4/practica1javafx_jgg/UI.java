package com.jgg.unit4.practica1javafx_jgg;

import javafx.scene.control.Alert;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;

public class UI {

    public static void showErrorAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static String convertToMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            byte[] hashedBytes = md.digest(input.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes)
            {
                sb.append(String.format("%02x",  b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException exception) {
            SellerApplication.LOGGER.log(Level.SEVERE, "Password Hash Not Built Correctly", exception);
            UI.showErrorAlert("Error", "Password Hash Not Built Correctly", exception.getMessage());
            return "";
        }
    }

    public static void saveUserCif(String userCif) {
        try (PrintWriter writer = new PrintWriter( new BufferedWriter(new FileWriter("user_info.txt", true)), true)) {

            String user = null;

            try (BufferedReader reader = new BufferedReader(new FileReader("user_info.txt"))){
                while (reader.readLine() != null) {
                    if(userCif.equals(reader.readLine())) {
                        user = userCif;
                        break;
                    }
                }
            } catch (IOException exception) {
                SellerApplication.LOGGER.log(Level.SEVERE, "Error while reading user_info.txt", exception);
                showErrorAlert("Error", "Error while reading user_info.txt", exception.getMessage());
            }
            if (user != null) {
                writer.println(userCif);
            }
        } catch (IOException exception) {
            SellerApplication.LOGGER.log(Level.SEVERE, "Error saving user info", exception);
            showErrorAlert("Error", "Error saving user info", exception.getMessage());
        }
    }
}
