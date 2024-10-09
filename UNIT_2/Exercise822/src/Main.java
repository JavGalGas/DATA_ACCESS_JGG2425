import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose an option: ");
        System.out.println("1. Convert OBJ to XML");
        System.out.println("2. Convert XML to OBJ");
        int choice = scanner.nextInt();

        if (choice == 1) {
            FileFormatConverter.convertObjToXml("contacts.obj", "contacts.xml");
        } else if (choice == 2) {
            FileFormatConverter.convertXmlToObj("contacts.xml", "contacts.obj");
        } else {
            System.out.println("Invalid choice");
        }
    }

}