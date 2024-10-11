import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class FileFormatConverter {
//Modify the fuctions
    private static final ContactList contactList = new ContactList();
    private static final String xmlFile = "contacts.xml";
    private static final String objFile = "../Exercise711/contacts.obj";

    public static void convertObjToXml() {
        List<Contact> contacts = contactList.loadContacts(objFile);
        saveContactsToXml(contacts);
    }

    public static void convertXmlToObj() {
        List<Contact> contacts = new ArrayList<>();
        String contactTag = "contact", nameTag = "name", surnameTag = "surname", emailTag = "email",
                phoneTag = "phone", descriptionTag = "description";
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            DefaultHandler handler = new DefaultHandler() {

                String name, surname, email, phoneNumber, description;
                boolean isName, isSurname, isEmail, isPhone, isDescription;

                public void startElement(String uri, String localName, String qName, Attributes attributes) {
                    if (qName.equalsIgnoreCase(nameTag)) {
                        isName = true;
                    } else if (qName.equalsIgnoreCase(surnameTag)) {
                        isSurname = true;
                    } else if (qName.equalsIgnoreCase(emailTag)) {
                        isEmail = true;
                    } else if (qName.equalsIgnoreCase(phoneTag)) {
                        isPhone = true;
                    } else if (qName.equalsIgnoreCase(descriptionTag)) {
                        isDescription = true;
                    }
                }

                public void characters(char[] ch, int start, int length) {
                    String content = new String(ch, start, length);
                    if (isName) {
                        name = content;
                        isName = false;
                    } else if (isSurname) {
                        surname = content;
                        isSurname = false;
                    } else if (isEmail) {
                        email = content;
                        isEmail = false;
                    } else if (isPhone) {
                        phoneNumber = content;
                        isPhone = false;
                    } else if (isDescription) {
                        description = content;
                        isDescription = false;
                    }
                }

                public void endElement(String uri, String localName, String qName) {
                    if (qName.equalsIgnoreCase(contactTag)) {
                        Contact contact = new Contact();
                        contact.setName(name);
                        contact.setSurname(surname);
                        contact.setEmail(email);
                        contact.setPhoneNumber(phoneNumber);
                        contact.setDescription(description);

                        contacts.add(contact);
                    }
                }
            };

            saxParser.parse(xmlFile, handler);

        } catch (Exception e) {
            UI.showError(4);
        }
        contactList.saveContacts(objFile, contacts);
    }

    private static void saveContactsToXml(List<Contact> contacts) {
        try (PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(xmlFile, false)))) {
            printWriter.println(FileFormatConverterUI.getTagAt(0));
            for (Contact contact : contacts) {
                printWriter.println(FileFormatConverterUI.getTagAt(1));
                printWriter.println(FileFormatConverterUI.getTagAt(2) + contact.getName()
                        + FileFormatConverterUI.getTagAt(3));
                printWriter.println(FileFormatConverterUI.getTagAt(4) + contact.getSurname()
                        + FileFormatConverterUI.getTagAt(5));
                printWriter.println(FileFormatConverterUI.getTagAt(6) + contact.getEmail()
                        + FileFormatConverterUI.getTagAt(7));
                printWriter.println(FileFormatConverterUI.getTagAt(8) + contact.getPhoneNumber()
                        + FileFormatConverterUI.getTagAt(9));
                printWriter.println(FileFormatConverterUI.getTagAt(10) + contact.getDescription()
                        + FileFormatConverterUI.getTagAt(11));
                printWriter.println(FileFormatConverterUI.getTagAt(12));
            }
            printWriter.println(FileFormatConverterUI.getTagAt(13));
        }
        catch ( IOException exception) {
            UI.showError(2, exception.getMessage());
        }
    }

    public static void start() {
        boolean isRunning = true;

        while (isRunning) {
            int choice = FileFormatConverterUI.askForConverterOption();

            switch (choice) {
                case 1:
                    convertObjToXml();
                    break;
                case 2:
                    convertXmlToObj();
                    break;
                case 3:
                    FileFormatConverterUI.closeScanner();
                    isRunning = false;
                    break;
                default:
                    UI.showMessage(1);
                    break;
            }
        }
    }
}
