package Exercise2;

public class Client extends Person{
    //
    private string _telefono;
    private Set<Empresa> _esClienteDe = new Set<Empresa>();

    public string Telefono {
        get => _telefono;
        set {
            string patronTelefono = "^((\+|00)\d{2,3})?\d{9}$";
            if (Regex.IsMatch(value, patronTelefono))
                _telefono = value;
            else
                throw new ArgumentException(TELEFONO_NO_VALIDO);
        }
    }
}
