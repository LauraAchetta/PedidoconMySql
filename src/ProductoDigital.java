public class ProductoDigital extends Producto {
    private String urlDescarga;

    public ProductoDigital(int codigo, String nombre, double precio, String urlDescarga) {
        super(codigo, nombre, precio);
        this.urlDescarga = urlDescarga;
    }

    @Override
    public double calcularPrecioFinal() {
        // Los productos digitales no pagan envío
        return getPrecio();
    }

    public String getUrlDescarga() {
        return urlDescarga;
    }
}
