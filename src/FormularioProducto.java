import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormularioProducto extends JFrame {

    // Componentes de la interfaz
    private JTextField txtCodigo;
    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JRadioButton rbFisico;
    private JRadioButton rbDigital;
    private ButtonGroup bgTipo;
    private JTextField txtPeso;
    private JTextField txtUrl;
    private JButton btnGuardar;
    private JButton btnLimpiar;

    public FormularioProducto() {
        // Configuración de la ventana principal
        setTitle("Gestión de Productos - Sistema de Pedidos");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Panel de Formulario (GridBagLayout para un diseño alineado)
        JPanel panelForm = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Fila 0: Código
        gbc.gridx = 0; gbc.gridy = 0;
        panelForm.add(new JLabel("Código:"), gbc);
        gbc.gridx = 1;
        txtCodigo = new JTextField(15);
        panelForm.add(txtCodigo, gbc);

        // Fila 1: Nombre
        gbc.gridx = 0; gbc.gridy = 1;
        panelForm.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        txtNombre = new JTextField(15);
        panelForm.add(txtNombre, gbc);

        // Fila 2: Precio
        gbc.gridx = 0; gbc.gridy = 2;
        panelForm.add(new JLabel("Precio ($):"), gbc);
        gbc.gridx = 1;
        txtPrecio = new JTextField(15);
        panelForm.add(txtPrecio, gbc);

        // Fila 3: Tipo de Producto (RadioButtons)
        gbc.gridx = 0; gbc.gridy = 3;
        panelForm.add(new JLabel("Tipo:"), gbc);
        gbc.gridx = 1;
        JPanel panelRadio = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        rbFisico = new JRadioButton("Físico", true);
        rbDigital = new JRadioButton("Digital");
        bgTipo = new ButtonGroup();
        bgTipo.add(rbFisico);
        bgTipo.add(rbDigital);
        panelRadio.add(rbFisico);
        panelRadio.add(rbDigital);
        panelForm.add(panelRadio, gbc);

        // Fila 4: Peso en Kg (Para Producto Físico)
        gbc.gridx = 0; gbc.gridy = 4;
        panelForm.add(new JLabel("Peso (Kg):"), gbc);
        gbc.gridx = 1;
        txtPeso = new JTextField(15);
        panelForm.add(txtPeso, gbc);

        // Fila 5: URL de Descarga (Para Producto Digital)
        gbc.gridx = 0; gbc.gridy = 5;
        panelForm.add(new JLabel("URL Descarga:"), gbc);
        gbc.gridx = 1;
        txtUrl = new JTextField(15);
        txtUrl.setEnabled(false); // Deshabilitado por defecto al iniciar en "Físico"
        panelForm.add(txtUrl, gbc);

        add(panelForm, BorderLayout.CENTER);

        // Panel inferior de Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnGuardar = new JButton("Guardar en BD");
        btnLimpiar = new JButton("Limpiar");
        panelBotones.add(btnGuardar);
        panelBotones.add(btnLimpiar);
        add(panelBotones, BorderLayout.SOUTH);

        // --- EVENTOS DE LA INTERFAZ ---

        // Cambiar disponibilidad de campos según el RadioButton seleccionado
        rbFisico.addActionListener(e -> actualizarEstadoCampos());
        rbDigital.addActionListener(e -> actualizarEstadoCampos());

        // Evento del botón Guardar
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarProducto();
            }
        });

        // Evento del botón Limpiar
        btnLimpiar.addActionListener(e -> limpiarCampos());
    }

    // Alternar campos según tipo de producto
    private void actualizarEstadoCampos() {
        if (rbFisico.isSelected()) {
            txtPeso.setEnabled(true);
            txtUrl.setEnabled(false);
            txtUrl.setText("");
        } else {
            txtPeso.setEnabled(false);
            txtPeso.setText("");
            txtUrl.setEnabled(true);
        }
    }

    // Lógica para instanciar el objeto y guardarlo a través de ProductoDAO
    private void guardarProducto() {
        try {
            // Validar campos básicos
            if (txtCodigo.getText().trim().isEmpty() ||
                    txtNombre.getText().trim().isEmpty() ||
                    txtPrecio.getText().trim().isEmpty()) {

                JOptionPane.showMessageDialog(this,
                        "Por favor complete todos los campos obligatorios.",
                        "Campos Incompletos",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            int codigo = Integer.parseInt(txtCodigo.getText().trim());
            String nombre = txtNombre.getText().trim();
            double precio = Double.parseDouble(txtPrecio.getText().trim());

            Producto producto = null;

            // Instanciación polimórfica de la jerarquía Producto
            if (rbFisico.isSelected()) {
                if (txtPeso.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Ingrese el peso del producto físico.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                double peso = Double.parseDouble(txtPeso.getText().trim());
                producto = new ProductoFisico(codigo, nombre, precio, peso);

            } else if (rbDigital.isSelected()) {
                String url = txtUrl.getText().trim();
                if (url.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Ingrese la URL de descarga del producto digital.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                producto = new ProductoDigital(codigo, nombre, precio, url);
            }

            // Guardar en la base de datos usando el DAO
            if (producto != null) {
                boolean exito = ProductoDAO.guardarProducto(producto);

                if (exito) {
                    JOptionPane.showMessageDialog(this,
                            "¡Producto guardado exitosamente en la base de datos!",
                            "Éxito",
                            JOptionPane.INFORMATION_MESSAGE);
                    limpiarCampos();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Ocurrió un error al intentar guardar en la base de datos.",
                            "Error SQL",
                            JOptionPane.ERROR_MESSAGE);
                }
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Verifique que los campos Código, Precio y Peso contengan valores numéricos válidos.",
                    "Error de formato",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Limpia el formulario
    private void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
        txtPeso.setText("");
        txtUrl.setText("");
        rbFisico.setSelected(true);
        actualizarEstadoCampos();
    }

    // Método main para probar la ventana independientemente
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new FormularioProducto().setVisible(true);
        });
    }
}
