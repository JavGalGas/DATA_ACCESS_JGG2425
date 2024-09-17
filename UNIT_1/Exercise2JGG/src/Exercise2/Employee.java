package Exercise2;

public class Employee extends Person{
    private double grossSalary;

    public double getGrossSalary() {
        return grossSalary;
    }

    public void setGrossSalary(double grossSalary) throws IllegalArgumentException {
        if (grossSalary < 0)
            throw new IllegalArgumentException(SUELDO_NO_VALIDO);
        this.grossSalary = grossSalary;
    }

}
