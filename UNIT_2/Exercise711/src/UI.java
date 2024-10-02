import java.util.Scanner;

public class UI {

    private static final Scanner scanner = new Scanner(System.in);
    public static Contact askForContact() {
        String name, surname, e_mail, phone_number, description;
        System.out.println("Provide the contact's name: ");
        name = scanner.nextLine();
        System.out.println("Provide the contact's surname: ");
        surname =scanner.nextLine();
        System.out.println("Provide the contact's email address: ");
        e_mail =scanner.nextLine();
        System.out.println("Provide the contact's phone number: ");
        phone_number =scanner.nextLine();
        System.out.println("Add a description: ");
        description =scanner.nextLine();
        return new Contact(name, surname,e_mail,phone_number, description);
    }
}
