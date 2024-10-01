import java.util.Scanner;

public class UI {

    public static final String FIRST_FILE_NAME = "Enter the first file name: ";
    public static final String SECOND_FILE_NAME = "Enter the second file name: ";

    public static String getFile1() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(FIRST_FILE_NAME);
        return scanner.next();
    }

    public static String getFile2() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(SECOND_FILE_NAME);
        return scanner.next();
    }
}
