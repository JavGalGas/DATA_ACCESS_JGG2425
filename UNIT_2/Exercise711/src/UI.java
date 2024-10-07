import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UI {

    private static final Scanner scanner = new Scanner(System.in);
    private static final String[] messages = {
            "Contact added successfully!",
            "Invalid option, please try again."
    };
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

    public static int askForOption() {
        System.out.println("\n--- Contact List Menu ---");
        System.out.println("1. Add New Contact");
        System.out.println("2. Show All Contacts");
        System.out.println("3. Search Contact");
        System.out.println("4. Exit");
        System.out.print("Choose an option: ");

        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }

    public static void showContacts(List<Contact> contacts) {
        System.out.println("\n--- Contact List ---");
        if (contacts.isEmpty()) {
            System.out.println("No contacts available.");
        } else {
            for (Contact contact : contacts) {
                System.out.println(contact);
                System.out.println("---------------------------");
            }
        }
    }

    public static String askForParameter() {
        System.out.print("Enter parameter to search: ");
        return scanner.nextLine();
    }

    public static void showContact(Contact foundContact) {
        if (foundContact != null) {
            System.out.println("\nContact found:");
            System.out.println(foundContact);
        } else {
            System.out.println("Contact not found.");
        }
    }

    public static void closeScanner() {
        System.out.println("Exiting...");
        scanner.close();
    }

    public static void showMessage(int index) throws IndexOutOfBoundsException{
        if (index < 0 || index >= messages.length) {
            throw new IndexOutOfBoundsException();
        }
        System.out.println(messages[index]);
    }
}
