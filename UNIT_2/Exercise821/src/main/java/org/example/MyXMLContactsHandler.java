package org.example;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class MyXMLContactsHandler extends DefaultHandler{
    protected String tagContent;
    private String name;
    private String surname;
    private String cellPhone;
    private String workPhone;
    private String homePhone;
    private boolean inPhones;
    // Tag opening found
    //
    @Override
    public void startElement(String uri, String localName,
                             String qName, Attributes attributes) throws SAXException {

        if (qName.equals("contact")) {
            // Reset variables for a new contact
            name = "";
            surname = "";
            cellPhone = "";
            workPhone = "";
            homePhone = "";
        } else if (qName.equals("phones")) {
            inPhones = true;  // Entering the phones section
        }
    }
    // Tag content, usually CDATA
    //
    @Override
    public void characters( char[] ch, int start, int length ) throws SAXException {
        tagContent = new String( ch, start, length );
    }
    // Tag ending
    //
    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        if (qName.equals("name")) {
            name = tagContent;
        } else if (qName.equals("surname")) {
            surname = tagContent;
        } else if (qName.equals("cell") && inPhones) {
            cellPhone = tagContent;  // Capture the cell phone
        } else if (qName.equals("work") && inPhones) {
            workPhone = tagContent;  // Capture the work phone
        } else if (qName.equals("home") && inPhones) {
            homePhone = tagContent;  // Capture the home phone
        } else if (qName.equals("phones")) {
            inPhones = false;  // Exiting the phones section

            // Set the phone number based on the preference order: cell > work > home
            String phoneNumber = !cellPhone.isEmpty() ? cellPhone :
                    !workPhone.isEmpty() ? workPhone : homePhone;

            // Print the contact if we have a name, surname, and a phone
            if (!name.isEmpty() && !surname.isEmpty() && phoneNumber != null) {
                System.out.println("Name: " + name + " " + surname + ", Phone Number: " + phoneNumber);
            }
        }
    }
}
