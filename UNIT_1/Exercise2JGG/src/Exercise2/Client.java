package Exercise2;

import java.util.HashSet;
import java.util.regex.Pattern;

public class Client extends Person{
    //
    final String NOT_VALID_PHONE = "Phone is not valid";
    private String phone;
    private HashSet<Company> isClientOf = new HashSet<>();

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) throws IllegalArgumentException {
        String phonePattern = "^((\\+|00)\\d{2,3})?\\d{9}$";
        if (Pattern.matches(phonePattern, phone)) {
            this.phone = phone;
        } else {
            throw new IllegalArgumentException(NOT_VALID_PHONE);
        }
    }

    public Company getCompany(String name) throws IllegalArgumentException{
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
        for (Company company : isClientOf) {
            if (company.getName().equals(name)) {
                return company;
            }
        }
        return null;
    }

    public void addCompany(Company company) throws IllegalArgumentException{
        if (company == null || company.getName() == null || company.getName().isEmpty()) {
            throw new IllegalArgumentException("Employee or employee name cannot be null or empty.");
        }
        isClientOf.add(company);
    }

}
