package org.example;

import java.util.Scanner;

public class UI {
    static Scanner scanner = new Scanner(System.in);
    static String[] stringParameterMessages = new String[]{
            "Enter an employee name: ",
            "Enter an employee job: ",
            "Enter a department name: ",
            "Enter a department location: ",
    };
    static String[] intParameterMessages = new String[]{
            "Enter a department number: ",
    };

    public static int getOption() {
        System.out.println("~~OPTIONS~~");
        System.out.println("1   Run the transaction.");
        System.out.println("2   Exit the program.");
        System.out.println("Insert an option: ");
        int option = scanner.nextInt();
        scanner.nextLine();
        return option;
    }

    public static void showErrorMessage() {
        System.out.println("Wrong input. Please try again.");
    }

    public static void close(){
        scanner.close();
    }

    public static String getStringParameter(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= stringParameterMessages.length){
            throw new IndexOutOfBoundsException();
        }
        System.out.println(stringParameterMessages[index]);
        String result = scanner.next().toUpperCase();
        scanner.nextLine();
        return result;
    }
    public static int getIntParameter(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= intParameterMessages.length){
            throw new IndexOutOfBoundsException();
        }
        System.out.println(intParameterMessages[index]);
        int result = scanner.nextInt();
        scanner.nextLine();
        return result;
    }

}
