import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ContactList {

    private List<Contact> contacts;
    private static final String FILE_NAME = "contacts.obj";

    public ContactList() {
        contacts = loadContacts();
    }

    private List<Contact> loadContacts() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Contact>) ois.readObject();
        } catch (IOException | ClassNotFoundException exception) {
            exception.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void saveContacts() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(contacts);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addContact(Contact contact) {
        contacts.add(contact);
        saveContacts();
    }

    public void showContacts() {
        if (contacts.isEmpty()) {
            System.out.println("No contacts available.");
        } else {
            for (Contact contact : contacts) {
                System.out.println(contact);
                System.out.println("---------------------------");
            }
        }
    }

    public Contact searchContact(String query) {
        for (Contact contact : contacts) {
            if (contact.getFullName().toLowerCase().contains(query.toLowerCase()) ||
                    contact.getPhoneNumber().contains(query) ||
                    contact.getEmail().toLowerCase().contains(query.toLowerCase()) ||
                    contact.getDescription().toLowerCase().contains(query.toLowerCase())) {
                return contact;
            }
        }
        return null;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            UI.showMenuOptions();
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    Contact newContact = UI.askForContact();
                    addContact(newContact);
                    System.out.println("Contact added successfully!");
                    break;

                case 2:
                    System.out.println("\n--- Contact List ---");
                    showContacts();
                    break;

                case 3:
                    System.out.print("Enter parameter to search: ");
                    String searchQuery = scanner.nextLine();
                    Contact foundContact = searchContact(searchQuery);
                    if (foundContact != null) {
                        System.out.println("\nContact found:");
                        System.out.println(foundContact);
                    } else {
                        System.out.println("Contact not found.");
                    }
                    break;

                case 4:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
    }
}
