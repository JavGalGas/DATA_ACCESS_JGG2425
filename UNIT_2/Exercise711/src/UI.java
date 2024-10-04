import java.util.Scanner;

public class UI {

    private static final Scanner scanner = new Scanner(System.in);
    public static Contact askForContact() {
        String name, surname, email, phoneNumber, description;
        System.out.print("Enter name: ");
        name = scanner.nextLine();
        System.out.print("Enter surname: ");
        surname = scanner.nextLine();
        System.out.print("Enter email: ");
        email = scanner.nextLine();
        System.out.print("Enter phone number: ");
        phoneNumber = scanner.nextLine();
        System.out.print("Enter description: ");
        description = scanner.nextLine();
        return new Contact(name, surname,email,phoneNumber, description);
    }

    public static void showMenuOptions() {
        System.out.println("\n--- Contact List Menu ---");
        System.out.println("1. Add New Contact");
        System.out.println("2. Show All Contacts");
        System.out.println("3. Search Contact");
        System.out.println("4. Exit");
        System.out.print("Choose an option: ");
    }
}
