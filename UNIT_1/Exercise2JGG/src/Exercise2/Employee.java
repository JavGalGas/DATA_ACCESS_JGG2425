package Exercise2;

public class Employee extends Person{
    final String NOT_VALID_SALARY = "Salary is not valid.";
    private double grossSalary;

    public double getGrossSalary() {
        return grossSalary;
    }

    public void setGrossSalary(double grossSalary) throws IllegalArgumentException {
        if (grossSalary < 0)
            throw new IllegalArgumentException(NOT_VALID_SALARY);
        this.grossSalary = grossSalary;
    }

}
