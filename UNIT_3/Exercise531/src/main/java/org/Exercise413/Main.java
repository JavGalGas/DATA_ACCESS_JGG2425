package org.Exercise413;

import java.sql.*;

public class Main {
    public static void main(String[] args){
        String URL = "jdbc:postgresql://localhost:5432/JavierGGdb";
        String USER = "postgres";
        String PASS = "postgres";
        try (Connection con = DriverManager.getConnection(URL, USER, PASS)) {
            Statement statement = con.createStatement();
            String SQLsentence = "ALTER TABLE subjects ADD COLUMN Hours INTEGER";
            int rs = statement.executeUpdate(SQLsentence);
            System.out.println(rs);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}