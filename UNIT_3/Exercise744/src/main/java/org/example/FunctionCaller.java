package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.*;

public class FunctionCaller {
    Scanner scanner = new Scanner(System.in);
    final String URL = "jdbc:postgresql://localhost:5432/JavierGGdb";
    final String USER = "postgres";
    final String PASS = "postgres";
    boolean isRunning = true;
    public void start() {
        while (isRunning) {
            System.out.println("~~FUNCTIONS~~");
            System.out.println("1   Select employees by job");
            System.out.println("2   Select employees by department number");
            System.out.println("3   Select employees by name");
            System.out.println("4   Power off");
            System.out.println("Please select a function:");
            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    getEmployeesByJob();
                    break;
                case 2:
                    getEmployeesByDeptNo();
                    break;
                case 3:
                    getEmployeesByName();
                    break;
                case 4:
                    System.out.println("Shutting...");
                    isRunning = false;
                    break;
                default:
                    System.out.println("Wrong input. Please try again.");
                    break;
            }
        }
    }

    private void getEmployeesByName() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASS)) {
            Statement statement = connection.createStatement();
            String SQLsentence = "SELECT code, name, year FROM subjects ORDER BY code";
            ResultSet rs = statement.executeQuery(SQLsentence);
            System.out.println("Code" + "\t" + "Name" + "\t" + "Year");
            System.out.println("-----------------------------------------");
            while (rs.next()) {
                System.out.println(rs.getString(1) + "\t " +
                        rs.getString(2) + "\t" + rs.getString(3));
            }
        }
        catch(SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void getEmployeesByDeptNo() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASS)) {
            Statement statement = connection.createStatement();
            String SQLsentence = "SELECT code, name, year FROM subjects ORDER BY code";
            ResultSet rs = statement.executeQuery(SQLsentence);
            System.out.println("Code" + "\t" + "Name" + "\t" + "Year");
            System.out.println("-----------------------------------------");
            while (rs.next()) {
                System.out.println(rs.getString(1) + "\t " +
                        rs.getString(2) + "\t" + rs.getString(3));
            }
        }
        catch(SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void getEmployeesByJob(){
        try (Connection connection = DriverManager.getConnection(URL, USER, PASS)) {
            Statement statement = connection.createStatement();
            String SQLsentence = "SELECT code, name, year FROM subjects ORDER BY code";
            ResultSet rs = statement.executeQuery(SQLsentence);
            System.out.println("Code" + "\t" + "Name" + "\t" + "Year");
            System.out.println("-----------------------------------------");
            while (rs.next()) {
                System.out.println(rs.getString(1) + "\t " +
                        rs.getString(2) + "\t" + rs.getString(3));
            }
        }
        catch(SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }


}
