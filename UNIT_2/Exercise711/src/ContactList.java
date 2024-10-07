import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ContactList {

    private final List<Contact> contacts;
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

    public void saveContacts(){
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(contacts);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addContact(Contact contact) {
        contacts.add(contact);
        saveContacts();
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
        while (true) {
            int choice = UI.askForOption();

            switch (choice) {
                case 1:
                    Contact newContact = UI.askForContact();
                    addContact(newContact);
                    UI.showMessage(0);
                    break;

                case 2:
                    UI.showContacts(contacts);
                    break;

                case 3:
                    String searchQuery = UI.askForParameter();
                    Contact foundContact = searchContact(searchQuery);
                    UI.showContact(foundContact);
                    break;

                case 4:
                    UI.closeScanner();
                    return;

                default:
                    UI.showMessage(1);
            }
        }
    }
}
