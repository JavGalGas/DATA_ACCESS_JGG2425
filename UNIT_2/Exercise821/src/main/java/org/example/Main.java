package org.example;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class Main {
    public static void main(String[] args) {
        try {
            SAXParser saxParser = SAXParserFactory.
                    newInstance().newSAXParser();
            saxParser.parse("ContactList.xml", new
                    MyXMLContactsHandler());
        } catch ( Exception e ) {
            e.printStackTrace();
        }

    }
}