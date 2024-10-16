package org.Exercise411;

import java.sql.*;

public class Main {
    public static void main(String[] args){
        String URL = "jdbc:postgresql://localhost:5432/JavierGGdb";
        String USER = "postgres";
        String PASS = "postgres";
        try (Connection con = DriverManager.getConnection(URL, USER, PASS)) {
            Statement statement = con.createStatement();
            String SQLsentence = "SELECT code, name, year FROM subjects ORDER BY code";
            ResultSet rs = statement.executeQuery(SQLsentence);
            System.out.println("Code" + "\t" + "Name" + "\t" + "Year");
            System.out.println("-----------------------------------------");
            while (rs.next()) {
                System.out.println(rs.getString(1) + "\t " +
                        rs.getString(2) + "\t" + rs.getString(3));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}