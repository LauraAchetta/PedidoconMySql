import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProductoDAO {

    // Datos de conexión a MySQL local
    private static final String URL = "jdbc:mysql://localhost:3306/SISPE?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "root"; // Tu contraseña de root

    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static boolean guardarProducto(Producto producto) {
        String sql = "INSERT INTO producto (codigo, nombre, precio, tipo, peso_kg, url_descarga) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, producto.getCodigo());
            ps.setString(2, producto.getNombre());
            ps.setDouble(3, producto.getPrecio());

            // Determinamos el tipo específico usando polimorfismo / instanceof
            if (producto instanceof ProductoFisico pf) {
                ps.setString(4, "FISICO");
                ps.setDouble(5, pf.getPesoKg()); // Asegúrate de tener el getter en ProductoFisico
                ps.setNull(6, java.sql.Types.VARCHAR);
            } else if (producto instanceof ProductoDigital pd) {
                ps.setString(4, "DIGITAL");
                ps.setNull(5, java.sql.Types.DOUBLE);
                ps.setString(6, pd.getUrlDescarga());
            }

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.err.println("Error al guardar en la base de datos: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}