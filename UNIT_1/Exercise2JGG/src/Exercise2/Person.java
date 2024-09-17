package Exercise2;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

public class Person {


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
            throw new IllegalArgumentException(FECHA_NACIMIENTO_NO_VALIDA);
        this.birthDate = birthDate;
    }

    public int getAge(){
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
}
