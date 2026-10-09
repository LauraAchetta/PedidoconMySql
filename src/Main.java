import javax.swing.SwingUtilities;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Lista donde se irán guardando los productos ingresados desde el formulario
        List<Producto> productosIngresados = new ArrayList<>();

        // Abrir el formulario desde el Main
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                FormularioProducto formulario = new FormularioProducto();
                formulario.setVisible(true);
            }
        });
    }
}