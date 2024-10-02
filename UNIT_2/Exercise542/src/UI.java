import java.util.Scanner;

public class UI {

    private static final Scanner scanner = new Scanner(System.in);
    private static final String FIRST_FILE_NAME = "Enter the first file name: ";
    private static final String SECOND_FILE_NAME = "Enter the second file name: ";

    private static String getFileName (String prompt) {
        System.out.println(prompt);
        return scanner.nextLine();
    }

    public static String getFile1() {
        return getFileName(FIRST_FILE_NAME);
    }

    public static String getFile2() {
        return getFileName(SECOND_FILE_NAME);
    }

    public static void closeScanner() {
        scanner.close();
    }
}
