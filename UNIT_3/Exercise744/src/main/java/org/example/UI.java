package org.example;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class UI {
    static Scanner scanner = new Scanner(System.in);
    private static final String[] messages = new String[]{
        "Provide a name: ",
        "Provide a job: ",
        "Provide a department name: "
    };

    public static int selectMenuOption() {
        System.out.println("~~FUNCTIONS~~");
        System.out.println("1   Select employees by job");
        System.out.println("2   Select employees by department name");
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

    public static void showResult(ResultSet result) {
        try {
            System.out.println("Number" + "\t" + "Name" + "\t" + "Job" + "\t\t\t" + "Dept Number");
            System.out.println("------------------------------------------------");
            int columnCount = result.getMetaData().getColumnCount();
            while (result.next()) {
                StringBuilder output = new StringBuilder();
                for (int i = 1; i <= columnCount; i++) {
                    if(i == columnCount -1) {
                        output.append(result.getString(i)).append("\t\t ");
                    }
                    else {
                        output.append(result.getString(i)).append("\t ");
                    }
                }

                System.out.println(output);
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
    public static void showMessageError() {
        System.out.println("Wrong input. Please try again.");
    }
}
