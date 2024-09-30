import java.util.Scanner;

public class UI {

    public static Boolean askForOverwrite() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("The file already exists. Do you want to overwrite it?");
        System.out.println("Y - Yes         N - No");
        String answer = scanner.next();
        while(!(answer.equals("Y") || answer.equals("N"))) {
            System.out.println("Unrecognized answer. Please try again.");
            answer = scanner.next();
        }
        return answer.equals("N");
    }

    public static String askForFileName() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Specify the name of the file: ");
        return scanner.next();
    }

    public static String askForLine() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Write a line: ");
        return scanner.nextLine();
    }
}
