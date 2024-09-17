package Exercise2;

public class Person {
    //
    private string _nombre;
    private DateTime _fechaNacimiento;

    public string Nombre { get => _nombre; set => _nombre = value; }
    public DateTime FechaNacimiento {
        get => _fechaNacimiento;
        set {
            if (value > DateTime.Today)
                throw new ArgumentException(FECHA_NACIMIENTO_NO_VALIDA);
            _fechaNacimiento= value;
        }
    }

    public int Edad  => (DateTime.Today - FechaNacimiento).Days / 365);
}
}
