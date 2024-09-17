package Exercise2;

public class Employee extends Person{
    private double _sueldoBruto;

    public double SueldoBruto {
        get => _sueldoBruto;
        set {
            if (value < 0)
                throw new ArgumentException(SUELDO_NO_VALIDO);
            sueldoBruto = value;
        }
    }
}
