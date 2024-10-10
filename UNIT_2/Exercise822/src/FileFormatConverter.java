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
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            DefaultHandler handler = new DefaultHandler() {

                String name, surname, email, phoneNumber, description;
                boolean isName, isSurname, isEmail, isPhone, isDescription;

                public void startElement(String uri, String localName, String qName, Attributes attributes) {
                    if (qName.equalsIgnoreCase("name")) {
                        isName = true;
                    } else if (qName.equalsIgnoreCase("surname")) {
                        isSurname = true;
                    } else if (qName.equalsIgnoreCase("email")) {
                        isEmail = true;
                    } else if (qName.equalsIgnoreCase("phone")) {
                        isPhone = true;
                    } else if (qName.equalsIgnoreCase("description")) {
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
                    if (qName.equalsIgnoreCase("contact")) {
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
            printWriter.println("<contactList>");
            for (Contact contact : contacts) {
                printWriter.println("    <contact>");
                printWriter.println("        <name>" + contact.getName() + "</name>");
                printWriter.println("        <surname>" + contact.getSurname() + "</surname>");
                printWriter.println("        <email>" + contact.getEmail() + "</email>");
                printWriter.println("        <phone>" + contact.getPhoneNumber() + "</phone>");
                printWriter.println("        <description>" + contact.getDescription() + "</description>");
                printWriter.println("    </contact>");
            }
            printWriter.println("</contactList>");
        }
        catch ( IOException exception) {
            UI.showError(2, exception.getMessage());
        }
    }
}
