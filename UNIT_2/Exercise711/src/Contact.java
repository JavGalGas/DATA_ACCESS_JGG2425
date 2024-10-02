import java.io.Serializable;

public class Contact implements Serializable {
    private String name;
    private String surname;
    private String e_mail;
    private String phone_number;
    private String description;

    public Contact (String name, String surname, String e_mail, String phone_number, String description) {
        this.name = name;
        this.surname = surname;
        this.e_mail = e_mail;
        this.phone_number = phone_number;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getE_mail() {
        return e_mail;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getDescription() {
        return description;
    }
}
