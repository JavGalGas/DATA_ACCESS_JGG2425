package com.jgg.unit4.practica1javafx_jgg;

import javafx.scene.control.Alert;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;

public class UI {

    private static final String USER_INFO_FILE = "user_info.txt";

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
        if (userCif == null || userCif.trim().isEmpty()) {
            SellerApplication.LOGGER.warning("Attempted to save an invalid CIF.");
            return;
        }
        if (!existsUserCif(userCif)) {
            try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(USER_INFO_FILE, true)))) {
                writer.println(userCif);
                SellerApplication.LOGGER.info("User CIF saved successfully: " + userCif);
            } catch (IOException exception) {
                SellerApplication.LOGGER.log(Level.SEVERE, "Error saving user info", exception);
                showErrorAlert("Error", "Error saving user info", exception.getMessage());
            }
        } else {
            SellerApplication.LOGGER.info("User CIF already exists, not saving: " + userCif);
        }
    }

    private static boolean existsUserCif(String userCif) {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_INFO_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (userCif.equals(line)) {
                    return true;
                }
            }
        } catch (IOException exception) {
            SellerApplication.LOGGER.log(Level.SEVERE, "Error while reading " + USER_INFO_FILE, exception);
            showErrorAlert("Error", "Error while reading " + USER_INFO_FILE, exception.getMessage());
        }
        return false;
    }
}
