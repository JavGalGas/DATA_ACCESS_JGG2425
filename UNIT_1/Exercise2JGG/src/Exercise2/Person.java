package Exercise2;

import java.time.LocalDate;
import java.time.Period;

public class Person {

    final String NOT_VALID_BIRTH_DATE = "Birth date is not valid.";
    //
    private String name;
    private LocalDate birthDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) throws IllegalArgumentException {
        if (birthDate.isAfter(LocalDate.now()))
            throw new IllegalArgumentException(NOT_VALID_BIRTH_DATE);
        this.birthDate = birthDate;
    }

    public int getAge(){
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
}
