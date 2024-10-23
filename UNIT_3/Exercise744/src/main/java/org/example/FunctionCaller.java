package org.example;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;

public class FunctionCaller {
    static final String URL = "jdbc:postgresql://localhost:5432/JavierGGdb";
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
            CallableStatement statement = connection.prepareCall("{call employees_list_by_name(?)}");
            statement.setString(1, UI.getStringParameter(0));
            UI.showResult(statement);
        }
        catch(SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private static void getEmployeesByDeptNo() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASS)) {
            CallableStatement statement = connection.prepareCall("{call employees_list_by_dept(?)}");
            statement.setInt(1, UI.getDeptNoParameter());
            UI.showResult(statement);
        }
        catch(SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private static void getEmployeesByJob(){
        try (Connection connection = DriverManager.getConnection(URL, USER, PASS)) {
            CallableStatement statement = connection.prepareCall("{call employees_list_by_job(?)}");
            statement.setString(1, UI.getStringParameter(1));
            UI.showResult(statement);
        }
        catch(SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }
}
