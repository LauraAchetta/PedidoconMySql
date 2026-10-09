
public class Cliente {
    private String nombre;
    private String apellido;

    private int dni;
    private double saldoMaximo;

    public Cliente() {
    }

    public Cliente(String nombre, String apellido, double saldoMaximo, int dni) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.saldoMaximo = saldoMaximo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public int getDni() {
        return dni;
    }

    public double getSaldoMaximo() {
        return saldoMaximo;
    }

    public void mostrarCliente(){
        System.out.println("El nombre del Cliente es: " + this.getNombre());
    }




}