package org.example;

import org.jetbrains.annotations.NotNull;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class UI {
    static Scanner scanner = new Scanner(System.in);
    private static final String[] messages = new String[]{
        "Provide a name: ",
        "Provide a job: "
    };

    public static int selectMenuOption() {
        System.out.println("~~FUNCTIONS~~");
        System.out.println("1   Select employees by job");
        System.out.println("2   Select employees by department number");
        System.out.println("3   Select employees by name");
        System.out.println("4   Power off");
        System.out.println("Please select a function:");
        int option = scanner.nextInt();
        scanner.nextLine();
        return option;
    }

    public static void closeScanner() {
        System.out.println("Shutting...");
        scanner.close();
    }

    public static void showResult(@NotNull CallableStatement statement) {
        try {
            ResultSet result = statement.executeQuery();
            System.out.println("Code" + "\t" + "Name" + "\t" + "." + "\t" + ".");
            System.out.println("-----------------------------------------");
            while (result.next()) {
                System.out.println(result.getString(1) + "\t " +
                        result.getString(2) + "\t" + result.getString(3)
                        + "\t" + result.getString(4));
            }
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public static String getStringParameter(int index) throws IndexOutOfBoundsException {
        if(index < 0 || index >= messages.length) {
            throw new IndexOutOfBoundsException(index);
        }
        System.out.println(messages[index]);
        String parameter = scanner.next();
        scanner.nextLine();
        return parameter;
    }

    public static int getDeptNoParameter() {
        System.out.println("Provide a department number:");
        int deptNo = scanner.nextInt();
        scanner.nextLine();
        return deptNo;
    }

    public static void showMessageError() {
        System.out.println("Wrong input. Please try again.");
    }
}
