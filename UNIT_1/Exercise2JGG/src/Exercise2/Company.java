package Exercise2;

public class Company {
    //
    private string _nombre;
    private Set<Empleado> _plantilla = new Set<Empleado>();
    private Set<Cliente> _carteraDeClientes = new Set<Cliente>();

    public string Nombre { get => _nombre; set => _nombre = value; }
    public int ClientesTotales => _carteraDeClientes.Count;
    public int EmpleadosTotales => _plantilla.Count;
}
