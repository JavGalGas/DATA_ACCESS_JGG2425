import java.io.Serializable;

public class Contact implements Serializable {
    private final String name;
    private final String surname;
    private final String email;
    private final String phoneNumber;
    private final String description;

    public Contact (String name, String surname, String e_mail, String phone_number, String description) {
        this.name = name;
        this.surname = surname;
        this.email = e_mail;
        this.phoneNumber = phone_number;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getFullName() {
        return name + " " + surname;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Name: " + name + " " + surname + "\nEmail: " + email +
                "\nPhone: " + phoneNumber + "\nDescription: " + description;
    }
}
