import java.util.Scanner;

public class UI {
    public static final String OVERWRITE_IT = "The file already exists. Do you want to overwrite it?";
    public static final String YES_NO = "Y - Yes         N - No";
    public static final String UNRECOGNIZED_ANSWER = "Unrecognized answer. Please try again.";
    public static final String NAME_OF_THE_FILE = "Specify the name of the file: ";
    public static final String WRITE_A_LINE = "Write a line: ";

    public static Boolean askForOverwrite() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(OVERWRITE_IT);
        System.out.println(YES_NO);
        String answer = scanner.next();
        while(!(answer.equals("Y") || answer.equals("N"))) {
            System.out.println(UNRECOGNIZED_ANSWER);
            answer = scanner.next();
        }
        return answer.equals("N");
    }

    public static String askForFileName() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(NAME_OF_THE_FILE);
        return scanner.next();
    }

    public static String askForLine() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(WRITE_A_LINE);
        return scanner.nextLine();
    }
}
