package org.jgg2425da;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String URL = "jdbc:postgresql://localhost:5432/JavierGGdb";
        String USER = "postgres";
        String PASS = "postgres";
        String name;
        int year;
        try (Connection connection = DriverManager.getConnection(URL, USER, PASS)) {
            PreparedStatement pstmt = connection.prepareStatement("Insert into subjects (Name, Year) values( ?, ? )");
            while(true){
                System.out.println("Specify the name: ");
                name = scanner.nextLine();
                System.out.println("Specify the year: ");
                year = scanner.nextInt();
                if (!name.isEmpty()) {
                    pstmt.setString( 1, name);
                    pstmt.setInt( 2, year);
                    pstmt.executeUpdate();
                }
                else {
                    scanner.close();
                    break;
                }
                scanner.nextLine();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}