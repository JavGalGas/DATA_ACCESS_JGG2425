import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ContactList {

    private List<Contact> agenda = new ArrayList<>();

    public void addContact() {
        Contact contact = UI.askForContact();
    }
}
