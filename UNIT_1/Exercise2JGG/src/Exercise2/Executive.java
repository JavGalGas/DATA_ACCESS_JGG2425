package Exercise2;

public class Executive extends Employee{
    //
    private string _categoria;
    private Set<Empleado> _supervisa = new Set<Empleado>();

    public string Categoria { get => _categoria; set => _categoria = value; }
    public int Subordinados => supervisa.Count;
}
