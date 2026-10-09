public class Vendedor {
    private String nombre;
    private String apellido;
    private int dni;
    private double porcentajeComision;

    public Vendedor() {}

    public Vendedor(String nombre, String apellido, int dni, double pc) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.porcentajeComision = pc;
    }

    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
}