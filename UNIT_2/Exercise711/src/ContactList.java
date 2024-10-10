import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ContactList {

    private final List<Contact> contacts;
    private static final String FILE_NAME = "contacts.obj";

    public ContactList() {
        contacts = loadContacts(FILE_NAME);
    }

    public List<Contact> loadContacts(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file))) {
            Object deserializedObject = objectInputStream.readObject();
            return processDeserializedList(deserializedObject);
        } catch (IOException ioException) {
            UI.showError(0, ioException.getMessage());
        } catch (ClassNotFoundException exception) {
            UI.showError(1);
        }
        return new ArrayList<>();
    }

    private List<Contact> processDeserializedList(Object deserializedObject) {
        if (deserializedObject instanceof List<?> tempList) {
            List<Contact> contactList = new ArrayList<>();

            for (Object item : tempList) {
                if (item instanceof Contact) {
                    contactList.add((Contact) item);
                } else {
                    UI.showError(3);
                    return new ArrayList<>();
                }
            }
            return contactList;
        } else {
            UI.showError(4);
            return new ArrayList<>();
        }
    }

    public void saveContacts(String filename, List<Contact> contacts) {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(filename))) {
            objectOutputStream.writeObject(contacts);
        } catch (IOException exception) {
            UI.showError(2, exception.getMessage());
        }
    }

    private void addContact(Contact contact) {
        contacts.add(contact);
        saveContacts(FILE_NAME, contacts);
    }

    public Contact searchContact(String query) {
        for (Contact contact : contacts) { /*equalsIgnoreCase()*/
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
        boolean isRunning = true;
        while (isRunning) {
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
                    isRunning = false;
                    break;

                default:
                    UI.showMessage(1);
            }
        }
    }
}
