public class ProductoFisico extends Producto implements Enviable {
    private double pesoKg;

    public ProductoFisico(int codigo, String nombre, double precio, double pesoKg) {
        super(codigo, nombre, precio);
        this.pesoKg = pesoKg;
    }

    @Override
    public double calcularPrecioFinal() {
        // El precio final incluye el costo de envío basado en el peso
        return getPrecio() + calcularCostoEnvio();
    }
    // AGREGAR ESTE MÉTODO:
    public double getPesoKg() {
        return this.pesoKg;
    }
    // Implementación de la Interfaz Enviable
    @Override
    public double calcularCostoEnvio() {
        return pesoKg * 500.0; // Tarifa fija por Kg
    }

    @Override
    public String obtenerInstruccionesEnvio() {
        return "Despachar por correo express (Peso: " + pesoKg + " kg).";
    }
}