package com.jgg.Unit4;

import java.util.Scanner;

public class UI {

    private static final Scanner scanner = new Scanner(System.in);
    private static String[] errorMessages = new String[]{
            "Wrong input. Please try again.\n"
    };
    public static void close() {
        System.out.println("Exiting...");
        scanner.close();
    }

    public static int getOption() {
        System.out.println("~~OPTIONS~~");
        System.out.println("1-  Insert");
        System.out.println("2-  Select");
        System.out.println("3-  Update");
        System.out.println("4-  Delete");
        System.out.println("5-  Exit program");
        int option = scanner.nextInt();
        scanner.nextLine();
        return option;
    }

    public static void getErrorMessage(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= errorMessages.length){
            System.out.println(errorMessages[index]);
        }
        else {
            throw new IndexOutOfBoundsException();
        }
    }

    public static String getColumnOption() {
        String option;
        do{
            System.out.println("~~Select a column~~");
            System.out.println("D-  Department");
            System.out.println("E-  Employee");
            System.out.println("Option: ");
            option = scanner.next();
            scanner.nextLine();
            if(!(option.equalsIgnoreCase("D") ||option.equalsIgnoreCase("E"))){
                getErrorMessage(0);
            }
        }while(!(option.equalsIgnoreCase("D") ||option.equalsIgnoreCase("E")));
        return option;
    }
}
