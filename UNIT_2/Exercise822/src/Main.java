import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose an option: ");
        System.out.println("1. Convert OBJ to XML");
        System.out.println("2. Convert XML to OBJ");
        int choice = scanner.nextInt();

        if (choice == 1) {
            FileFormatConverter.convertObjToXml();
        } else if (choice == 2) {
            FileFormatConverter.convertXmlToObj();
        } else {
            System.out.println("Invalid choice");
        }
    }

}