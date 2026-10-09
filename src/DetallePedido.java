public class DetallePedido {
    private Producto producto;
    private int cantidad;
    private double precio;

    public DetallePedido() {
    }

    public DetallePedido(Producto producto,  int cantidad) {
        this.producto = producto;
        this.precio= producto.getPrecio();
        this.cantidad = cantidad;
    }

    public double calcularSubtotal() {
        return producto.calcularPrecioFinal() * cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    @Override
    public String toString() {
        return cantidad + "x " + producto.getNombre() +
                " | Precio Unit. (c/Envío): $" + producto.calcularPrecioFinal() +
                " | Subtotal: $" + calcularSubtotal();
    }
}