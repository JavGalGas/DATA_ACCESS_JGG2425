import java.util.Scanner;

public class FileFormatConverterUI extends UI {
    private static final Scanner scanner = new Scanner(System.in);
    private static final String[] tags = new String[] {
            "<contactList>", "    <contact>",
            "        <name>", "</name>",
            "        <surname>", "</surname>",
            "        <email>", "</email>",
            "        <phone>", "</phone>",
            "        <description>", "</description>",
            "    </contact>", "</contactList>",
    };

    public static int askForConverterOption() {
        System.out.println("Choose an option: ");
        System.out.println("1. Convert OBJ to XML");
        System.out.println("2. Convert XML to OBJ");
        System.out.println("3. Exit");
        System.out.print("Choose an option: ");
        int option = scanner.nextInt();
        scanner.nextLine();
        return option;
    }

    public static void closeScanner() {
        UI.closeScanner();
        scanner.close();
    }

    public static String getTagAt(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= tags.length) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + tags.length);
        }
        return tags[index];
    }
}
