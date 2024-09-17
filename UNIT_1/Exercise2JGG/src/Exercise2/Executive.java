package Exercise2;

import java.util.HashSet;

public class Executive extends Employee{
    //
    private String category;

    private final HashSet<Employee> supervises = new HashSet<>();

    public Employee getEmployee(String name) throws IllegalArgumentException{
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
        for (Employee employee : supervises) {
            if (employee.getName().equals(name)) {
                return employee;
            }
        }
        return null;
    }
    public void addEmployee(Employee employee) throws IllegalArgumentException{
        if (employee == null || employee.getName() == null || employee.getName().isEmpty()) {
            throw new IllegalArgumentException("Employee or employee name cannot be null or empty.");
        }
        supervises.add(employee);
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getSubordinateCount(){
        return supervises.size();
    }
}
