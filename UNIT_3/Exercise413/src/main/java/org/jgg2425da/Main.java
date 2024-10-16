package org.jgg2425da;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) throws SQLException {
        String URL = "jdbc:postgresql://localhost:5432/JavierGGdb";
        String USER = "postgres";
        String PASS = "postgres";
        Connection con = null;
        try {
            con = DriverManager.getConnection(URL, USER, PASS);
            Statement statement = con.createStatement();
            String SQLsentence = "ALTER TABLE subjects ADD COLUMN Hours INTEGER";
            int rs = statement.executeUpdate(SQLsentence);
            System.out.println(rs);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        finally {
            if (con != null) {
                con.close();
            }
        }


    }
}