package org.jgg2425da;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        String URL = "jdbc:postgresql://localhost:5432/JavierGGdb";
        String USER = "postgres";
        String PASS = "postgres";
        try (Connection con = DriverManager.getConnection(URL, USER, PASS)) {
            PreparedStatement pstmt = con.prepareStatement("Insert into subjects (Name, Year) values( ?, ? )");
            pstmt.setString( 1, "Markup Languages" );
            pstmt.setInt( 2, 1 );
            pstmt.executeUpdate();
//            Statement statement = con.createStatement();
//            String SQLsentence = "INSERT INTO subjects(name,year) VALUES ('Markup Languages', 1)";
//            int rs = statement.executeUpdate(SQLsentence);
//            System.out.println(rs);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}