package org.example;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;

public class FunctionCaller {
    static final String URL = "jdbc:postgresql://localhost:5432/EmployeeDB";
    static final String USER = "postgres";
    static final String PASS = "postgres";
    static boolean isRunning = true;
    public static void run() {
        while (isRunning) {
            int option = UI.selectMenuOption();
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
                    UI.closeScanner();
                    isRunning = false;
                    break;
                default:
                    UI.showMessageError();
                    break;
            }
        }
    }

    private static void getEmployeesByName() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASS)) {
            CallableStatement statement = connection.prepareCall("{call employee_list_by_name(?)}");
            statement.setString(1, UI.getStringParameter(0));
            ResultSet resultSet = statement.executeQuery();
            UI.showResult(resultSet);
        }
        catch(SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private static void getEmployeesByDeptNo() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASS)) {
            CallableStatement statement = connection.prepareCall("{call employee_list_by_dept(?)}");
            statement.setString(1, UI.getStringParameter(2));
            ResultSet resultSet = statement.executeQuery();
            UI.showResult(resultSet);
        }
        catch(SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private static void getEmployeesByJob(){
        try (Connection connection = DriverManager.getConnection(URL, USER, PASS)) {
            CallableStatement statement = connection.prepareCall("{call employee_list_by_job(?)}");
            statement.setString(1, UI.getStringParameter(1));
            ResultSet resultSet = statement.executeQuery();
            UI.showResult(resultSet);
        }
        catch(SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }
}
