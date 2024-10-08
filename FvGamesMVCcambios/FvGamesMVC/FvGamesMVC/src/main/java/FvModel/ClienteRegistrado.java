package FvModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ClienteRegistrado extends Cliente {
    private String direccion;
    private String email;
    private String dineroEnCuenta;
    private String metodoPagoPreferido;
    private String contrasena;
    private CarritoDeCompras carrito;
    private int idCliente;

    public ClienteRegistrado(String nombre, String apellidos, String cedula, String direccion, String email, String dineroEnCuenta, String metodoPagoPreferido, String contrasena) {
        super(nombre, apellidos, cedula);
        this.direccion = direccion;
        this.email = email;
        this.dineroEnCuenta = dineroEnCuenta;
        this.metodoPagoPreferido = metodoPagoPreferido;
        this.contrasena = contrasena;
        this.idCliente = guardarClienteEnDB();
        this.carrito = new CarritoDeCompras(idCliente);
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDineroEnCuenta() {
        return dineroEnCuenta;
    }

    public void setDineroEnCuenta(String dineroEnCuenta) {
        this.dineroEnCuenta = dineroEnCuenta;
    }

    public String getMetodoPagoPreferido() {
        return metodoPagoPreferido;
    }

    public void setMetodoPagoPreferido(String metodoPagoPreferido) {
        this.metodoPagoPreferido = metodoPagoPreferido;
    }

    public int getIdCliente() {
        return idCliente;
    }

    @Override
    public void visualizarProductos(Inventario inventario) {
        inventario.mostrarInventario();
    }

    public void agregarProductoAlCarrito(Inventario inventario, String nombreProducto, int cantidad) {
        Producto producto = inventario.buscarProducto(nombreProducto);
        if (producto != null) {
            if (producto.getCantidad() >= cantidad) {
                carrito.agregarProducto(producto, cantidad);
                System.out.println("Producto agregado al carrito.");
            } else {
                System.out.println("Cantidad no disponible. Disponible: " + producto.getCantidad());
            }
        } else {
            System.out.println("Producto no encontrado.");
        }
    }

    public void visualizarCarrito() {
        carrito.mostrarCarrito();
    }

    public void finalizarCompra(Inventario inventario) {
        double total = carrito.calcularTotalConImpuesto();
        if (total <= Double.parseDouble(dineroEnCuenta)) {
            dineroEnCuenta = ""+(Double.parseDouble(dineroEnCuenta) - total);
            List<ElementoCarrito> elementos = carrito.getElementos();
            for (ElementoCarrito elemento : elementos) {
                Producto producto = elemento.getProducto();
                int cantidadComprada = elemento.getCantidad();
                producto.disminuirCantidad(cantidadComprada);
            }
            System.out.println("Compra realizada con éxito.");
            carrito.generarFactura();
            carrito = new CarritoDeCompras(idCliente); // Reiniciar el carrito después de la compra
        } else {
            System.out.println("Fondos insuficientes. Recargue su cuenta.");
        }
    }

    public void recargarCuenta(double monto) {
        dineroEnCuenta = "" + (Double.parseDouble(dineroEnCuenta) + monto);
        System.out.println("Cuenta recargada con éxito.");
    }

    private int guardarClienteEnDB() {
        String sql = "INSERT INTO Cliente (Nombre, Apellidos, Cedula, Direccion, Email, Dinero, MetodoPago, Foto, NumeroTarjeta, NumeroCuentaBanco, Contrasena, TipoCliente) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int id = -1;

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/FVGames", "root", "root");
             PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, getNombre());
            pstmt.setString(2, getApellidos());
            pstmt.setString(3, getCedula());
            pstmt.setString(4, direccion);
            pstmt.setString(5, email);
            pstmt.setString(6, dineroEnCuenta);
            pstmt.setString(7, metodoPagoPreferido);
            pstmt.setString(8, ""); // Foto (dejar vacío o poner valor por defecto si no se tiene)
            pstmt.setString(9, ""); // NumeroTarjeta (dejar vacío o poner valor por defecto si no se tiene)
            pstmt.setString(10, ""); // NumeroCuentaBanco (dejar vacío o poner valor por defecto si no se tiene)
            pstmt.setString(11, contrasena); // Contrasena (dejar vacío o poner valor por defecto si no se tiene)
            pstmt.setString(12, "Cliente"); // TipoCliente por defecto como "Cliente"
            pstmt.executeUpdate();

            // Obtener el ID generado
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    id = rs.getInt(1);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }
}
