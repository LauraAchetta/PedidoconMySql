import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private Cliente cliente;
    private Vendedor vendedor;
    private List<DetallePedido> detalles;


    public Pedido(Cliente cliente, Vendedor vendedor) {
        this.cliente = cliente;
        this.vendedor = vendedor;
        this.detalles = new ArrayList<>();
    }

    public void agregarDetalle(Producto producto, int cantidad) {

        detalles.add(new DetallePedido(producto, cantidad));
    }

    public double calcularTotal() {
        double total = 0;
        for (DetallePedido detalle : detalles) {
            total += detalle.calcularSubtotal();
        }
        return total;
    }

    public void mostrarPedido() {
        System.out.println("==================================================");
        System.out.println("                 RESUMEN DE PEDIDO                ");
        System.out.println("==================================================");
        if (cliente != null) {
            System.out.println("Cliente: " + cliente.getNombre() + " " + cliente.getApellido());
        }
        if (vendedor != null) {
            System.out.println("Vendedor: " + vendedor.getNombre() + " " + vendedor.getApellido());
        }
        System.out.println("--------------------------------------------------");
        System.out.println("DETALLES:");

        for (DetallePedido detalle : detalles) {
            System.out.println("- " + detalle);

            // Verificación para la demostración de Interfaces
            if (detalle.getProducto() instanceof Enviable enviables) {
                System.out.println("  └─ [ENVÍO]: " + enviables.obtenerInstruccionesEnvio() +
                        " | Costo Envío: $" + enviables.calcularCostoEnvio());
            } else {
                System.out.println("  └─ [DIGITAL]: Producto de descarga directa.");
            }
        }

        System.out.println("--------------------------------------------------");
        System.out.println("TOTAL DEL PEDIDO: $" + calcularTotal());
        System.out.println("==================================================");
    }
}