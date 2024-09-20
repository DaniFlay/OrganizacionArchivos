package BBDD;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import registros.Registro;
import sujetos.Cliente;
import sujetos.DetallePedido;
import sujetos.Empleado;
import sujetos.Pedido;
import sujetos.Producto;
import sujetos.Proveedor;
import sujetos.Sucursal;

/**
 * Clase creada para los metodos para realizar la inserccion de todos los datos a la base de datos
 * 
 * @author super
 *
 */
public class Inserccion {
	/**
	 * Recorre la lista de los clientes junto con la base de datos y llama a otros metodos para realizar acciones sobre los objetos
	 * Los duplicados se registran en un archivo txt
	 * @param clientes
	 * @param f
	 */
	public static void busquedaCliente(List<Cliente> clientes,File f)  {
		for(Cliente cliente: clientes) {
			if(existeCliente(cliente,f)) { //Llamo a la funcion para comporbar si el cliente de la lista ya existe en la base de datos
				Cliente c1= buscarCliente(cliente,f); //En el caso de existir aviso al usuario 
				System.out.println("Se ha encontrado un cliente duplicado, que desea hacer ?");
				System.out.println("Cliente en la base de datos:\n"+c1.toString());
				System.out.println("Cliente nuevo:\n"+cliente.toString());
				System.out.println("1. Eliminar el registro antiguo");
				System.out.println("2. Descartar el registro nuevo");
				System.out.println("Escoga una opcion:");
				Scanner sc= new Scanner(System.in);
				int opcion= sc.nextInt();
				if(opcion==1) { // Elimino el registro antiguo e inserto el nuevo
					eliminarCliente(c1,f);
					insertarCliente(cliente,f);
					Registro.registroDuplicados(f, c1.toString());
				}
				else if(opcion==2) { //Descarto el registro nuevo
					Registro.registroDuplicados(f, cliente.toString());
				}
				
			}
			else insertarCliente(cliente,f);
		}
	}
	
	/**
	 * Comprueba si existe el cliente
	 * @param cliente
	 * @param c
	 * @param f
	 * @return true en el caso de existir y false en el caso contrario
	 */
	public static boolean existeCliente( Cliente cliente,File f ){
		Connection c= ConexionBBDD.conexion(f);
		String query= "select count(*) from clientes where idCliente= ?";
		try {
			PreparedStatement ps= c.prepareStatement(query);
			ps.setInt(1, cliente.getIdCliente());
			ResultSet rs= ps.executeQuery();
			rs.next();
			if(rs.getInt(1)>0) return true;
		} catch (SQLException e) {
			Registro.registroMySQL(f, e);
		}
		return false;
	}
	/**
	 * Busco un cliente con un id en especifico
	 * @param c
	 * @param con
	 * @param f
	 * @return Devuelvo al cliente en forma de objeto Cliente
	 */
	public static Cliente buscarCliente(Cliente c, File f) {
		Connection con= ConexionBBDD.conexion(f);
		Cliente c1= new Cliente();
		String query= "select * from clientes where idCliente= ?";
		try{
			PreparedStatement ps= con.prepareStatement(query);
			ps.setInt(1, c.getIdCliente());
			ResultSet rs= ps.executeQuery();
			rs.next();
			c1.setIdCliente(rs.getInt(1));
			c1.setNombre(rs.getString(2));
			c1.setApellidos(rs.getString(3));
			c1.setTipoCliente(rs.getString(4));
			c1.setnCompras(rs.getInt(5));
		} catch (SQLException e) {
			Registro.registroMySQL(f, e);
		}
		return c1;
	}
	/**
	 * Elimina el cliente con el id especificado
	 * @param c
	 * @param con
	 * @param f
	 */
	public static void eliminarCliente(Cliente c, File f) {
		Connection con= ConexionBBDD.conexion(f);
		String query= "delete from clientes where idCliente=?";
		try{
			PreparedStatement ps= con.prepareStatement(query);
			ps.setInt(1, c.getIdCliente());
			ps.execute();
		} catch (SQLException e) {
			Registro.registroMySQL(f, e);
		}
	}
	/**
	 * Inserta un cliente en la base de datos
	 * @param c
	 * @param con
	 * @param f
	 */
	public static void insertarCliente(Cliente c, File f) {
		String query ="insert into clientes (idCliente,nombre,apellidos,tipoCliente,nCompras) values (?,?,?,?,?)";
		Connection con= ConexionBBDD.conexion(f);
		try{
			PreparedStatement ps= con.prepareStatement(query);
			ps.setInt(1, c.getIdCliente());
			ps.setString(2, c.getNombre());
			ps.setString(3, c.getApellidos());
			ps.setString(4, c.getTipoCliente());
			ps.setInt(5, c.getnCompras());
			ps.execute();
		} catch (SQLException e) {
			Registro.registroMySQL(f, e);
		}
	}
	/**
	 * Recorre la lista de los detalles del pedido para realizar distintasa acciones sobre los objetos
	 * @param detalles
	 * @param f
	 */
	public static void busquedaDetalle(List<DetallePedido> detalles,File f)  {
		for(DetallePedido detalle: detalles) {
			if(existeDetalle(detalle,f)) {
				DetallePedido c1= buscarDetalle(detalle,f);
				System.out.println("Se ha encontrado un detalle de pedido duplicado, que desea hacer ?");
				System.out.println("DetallePedido en la base de datos:\n"+c1.toString());
				System.out.println("DetallePedido nuevo:\n"+detalle.toString());
				System.out.println("1. Eliminar el registro antiguo");
				System.out.println("2. Descartar el registro nuevo");
				System.out.println("Escoga una opcion:");
				Scanner sc= new Scanner(System.in);
				int opcion= sc.nextInt();
				if(opcion==1) {
					eliminarDetalle(c1,f);
					insertarDetalle(detalle,f);
					Registro.registroDuplicados(f, c1.toString());
				}
				else if(opcion==2) {
					Registro.registroDuplicados(f, detalle.toString());
				}
				sc.close();
			}
			else {
				insertarDetalle(detalle,f);
			}
			
		}
	}
	/**
	 * Comprueba si existe el detalle del pedido en la base de datos
	 * @param detalle
	 * @param c
	 * @param f
	 * @return true si existe y false en caso contrario
	 */
	public static boolean existeDetalle( DetallePedido detalle,File f ){
		Connection c= ConexionBBDD.conexion(f);
		String query= "select count(*) from detallesdelospedidos where idPedido= ? and idProducto=? and idProveedorProducto=?";
		try {
			PreparedStatement ps= c.prepareStatement(query);
			ps.setInt(1, detalle.getIdPedido());
			ps.setInt(2, detalle.getIdProducto());
			ps.setInt(3, detalle.getIdProveedorProducto());
			ResultSet rs= ps.executeQuery();
			rs.next();
			if(rs.getInt(1)>0) return true;
		} catch (SQLException e) {
			Registro.registroMySQL(f, e);
		}
		return false;
	}
	/**
	 * Busca un detalle del pedido en la base de datps
	 * @param c
	 * @param con
	 * @param f
	 * @return Lo devuelve en forma de objeto detallePedido
	 */
	public static DetallePedido buscarDetalle(DetallePedido c, File f) {
		Connection con= ConexionBBDD.conexion(f);
		DetallePedido c1= new DetallePedido();
		String query= "select * from detallesdelospedidos where idPedido= ? and idProducto=? and idProveedorProducto=?";
		try{
			PreparedStatement ps= con.prepareStatement(query);
			ps.setInt(1, c.getIdPedido());
			ps.setInt(2, c.getIdProducto());
			ps.setInt(3, c.getIdProveedorProducto());
			ResultSet rs= ps.executeQuery();
			rs.next();
			c1.setIdPedido(rs.getInt(1));
			c1.setIdProducto(rs.getInt(2));
			c1.setIdProveedorProducto(rs.getInt(3));
			c1.setPrecioUnidad(rs.getDouble(4));
			c1.setCantidad(rs.getInt(5));
			c1.setDescuento(rs.getDouble(6));
			c1.setPrecioFinal(rs.getDouble(7));
		} catch (SQLException e) {
			Registro.registroMySQL(f, e);
		}
		return c1;
	}
	/**
	 * Elimina un detalle del pedido de la base de datos
	 * 
	 * @param c
	 * @param con
	 * @param f
	 */
	public static void eliminarDetalle(DetallePedido c, File f) {
		Connection con= ConexionBBDD.conexion(f);
		String query= "delete from detallesdelospedidos where idPedido= ? and idProducto=? and idProveedorProducto=?";
		try(con){
			PreparedStatement ps= con.prepareStatement(query);
			ps.setInt(1, c.getIdPedido());
			ps.setInt(2, c.getIdProducto());
			ps.setInt(3, c.getIdProveedorProducto());
			ps.execute();
		} catch (SQLException e) {
			Registro.registroMySQL(f, e);
		}
	}
	/**
	 * Inserta un detalle del pedido en la base de datos
	 * @param c
	 * @param con
	 * @param f
	 */
	public static void insertarDetalle(DetallePedido c, File f) {
		Connection con= ConexionBBDD.conexion(f);
		String query ="insert into detallesdelospedidos (idDetalle,nombre,apellidos,tipoDetalle,nCompras) values (?,?,?,?,?)";
		try{
			PreparedStatement ps= con.prepareStatement(query);
			ps.setInt(1, c.getIdPedido());
			ps.setInt(2, c.getIdProducto());
			ps.setInt(3, c.getIdProveedorProducto());
			ps.setInt(5, c.getCantidad());
			ps.setDouble(4, c.getPrecioUnidad());
			ps.setDouble(6, c.getDescuento());
			ps.setDouble(7, c.getPrecioFinal());
			ps.execute();
		} catch (SQLException e) {
			Registro.registroMySQL(f, e);
		}
	}
	/**
	 * Recorre la lista de pedidos para hacer acciones sobre los objetos
	 * @param pedidos
	 * @param f
	 */
	public static void busquedaPedido(List<Pedido> pedidos,File f)  {
		for(Pedido pedido: pedidos) {
			if(existePedido(pedido,f)) {
				Pedido c1= buscarPedido(pedido,f);
				System.out.println("Se ha encontrado un pedido duplicado, que desea hacer ?");
				System.out.println("Pedido en la base de datos:\n"+c1.toString());
				System.out.println("Pedido nuevo:\n"+pedido.toString());
				System.out.println("1. Eliminar el registro antiguo");
				System.out.println("2. Descartar el registro nuevo");
				System.out.println("Escoga una opcion:");
				Scanner sc= new Scanner(System.in);
				int opcion= sc.nextInt();
				if(opcion==1) {
					eliminarPedido(c1,f);
					insertarPedido(pedido,f);
					Registro.registroDuplicados(f, c1.toString());
				}
				else if(opcion==2) {
					Registro.registroDuplicados(f, pedido.toString());
				}
				sc.close();
			}
			else {
					insertarPedido(pedido,f);
				}
				
		}
	}
	/**
	 * Comprueba si un pedido existe en la base de datos
	 * @param pedido
	 * @param c
	 * @param f
	 * @return true si existe y false en el caso de no existir
	 */
	public static boolean existePedido( Pedido pedido,File f ){
		Connection c= ConexionBBDD.conexion(f);
		String query= "select count(*) from pedidos where idPedido= ?";
		try {
			PreparedStatement ps= c.prepareStatement(query);
			ps.setInt(1, pedido.getIdPedido());
			ResultSet rs= ps.executeQuery();
			rs.next();
			if(rs.getInt(1)>0) return true;
		} catch (SQLException e) {
			Registro.registroMySQL(f, e);
		}
		return false;
	}
	/**
	 * Busca un pedido y lo devuelve en forma de objeto
	 * @param c
	 * @param con
	 * @param f
	 * @return Pedido en forma de objeto 
	 */
	public static Pedido buscarPedido(Pedido c, File f) {
		Connection con= ConexionBBDD.conexion(f);
		Pedido c1= new Pedido();
		String query= "select * from pedidos where idPedido= ? ";
		try(con){
			PreparedStatement ps= con.prepareStatement(query);
			ps.setInt(1, c.getIdPedido());
			ResultSet rs= ps.executeQuery();
			rs.next();
			c1.setIdPedido(rs.getInt(1));
			c1.setIdCliente(rs.getInt(2));
			c1.setIdEmpleado(rs.getInt(3));
			c1.setFecha(rs.getDate(4));
			c1.setPrecioFinal(rs.getDouble(5));
		} catch (SQLException e) {
			Registro.registroMySQL(f, e);
		}
		return c1;
	}
	/**
	 * Elimina un pedido de la base de datos
	 * 
	 * @param c
	 * @param f
	 */
	public static void eliminarPedido(Pedido c, File f) {
		Connection con= ConexionBBDD.conexion(f);
		String query= "delete from pedidos where idPedido= ?";
		try{
			PreparedStatement ps= con.prepareStatement(query);
			ps.setInt(1, c.getIdPedido());
			ps.execute();
		} catch (SQLException e) {
			Registro.registroMySQL(f, e);
		}
	}
	/**
	 * Inserta un pedido en la base de datos
	 * 
	 * @param c
	 * @param f
	 */
	public static void insertarPedido(Pedido c, File f) {
		Connection con= ConexionBBDD.conexion(f);
		String query ="insert into pedidos (idpedido,idcliente,idempleado,fecha,preciofinal) values (?,?,?,?,?)";
		try{
			java.sql.Date fechaSQL = new java.sql.Date(c.getFecha().getTime());
			PreparedStatement ps= con.prepareStatement(query);
			ps.setInt(1, c.getIdPedido());
			ps.setInt(2, c.getIdCliente());
			ps.setInt(3, c.getIdEmpleado());
			ps.setDate(4, fechaSQL);
			ps.setDouble(5, c.getPrecioFinal());
			ps.execute();
		} catch (SQLException e) {
			Registro.registroMySQL(f, e);
		}
	}
	/**
	 * Recorre la lista de sucursales para realizar acciones sobre los objetos
	 * @param sucursales
	 * @param f
	 */
	public static void busquedaSucursal(List<Sucursal> sucursales,File f)  {
		for(Sucursal sucursal: sucursales) {
			if(existeSucursal(sucursal,f)) {
				Sucursal c1= buscarSucursal(sucursal,f);
				System.out.println("Se ha encontrado una sucursal duplicada, que desea hacer ?");
				System.out.println("Sucursal en la base de datos:\n"+c1.toString());
				System.out.println("Sucursal nuevo:\n"+sucursal.toString());
				System.out.println("1. Eliminar el registro antiguo");
				System.out.println("2. Descartar el registro nuevo");
				System.out.println("Escoga una opcion:");
				Scanner sc= new Scanner(System.in);
				int opcion= sc.nextInt();
				if(opcion==1) {
					eliminarSucursal(c1,f);
					insertarSucursal(sucursal,f);
					Registro.registroDuplicados(f, c1.toString());
				}
				else if(opcion==2) {
					Registro.registroDuplicados(f, sucursal.toString());
				}
				sc.close();
			}
			else {
				insertarSucursal(sucursal,f);
			}
			
		}
	}
	/**
	 * Comrpueba si existe una sucursal en la base de datos
	 * @param sucursal
	 * @param f
	 * @return true en el caso de existir y false en caso contrario
	 */
	public static boolean existeSucursal( Sucursal sucursal,File f ){
		String query= "select count(*) from sucursales where idSucursal= ?";
		Connection c= ConexionBBDD.conexion(f);
		try {
			PreparedStatement ps= c.prepareStatement(query);
			ps.setInt(1, sucursal.getIdSucursal());
			ResultSet rs= ps.executeQuery();
			rs.next();
			if(rs.getInt(1)>0) return true;
		} catch (SQLException e) {
			Registro.registroMySQL(f, e);
		}
		return false;
	}
	/**
	 * Busca una sucursal en la base de datos
	 * @param c
	 * @param f
	 * @return devuelve dicha sucursal en forma de objeto
	 */
	public static Sucursal buscarSucursal(Sucursal c, File f) {
		Sucursal c1= new Sucursal();
		Connection con= ConexionBBDD.conexion(f);
		String query= "select * from sucursales where idSucursal= ? ";
		try{
			PreparedStatement ps= con.prepareStatement(query);
			ps.setInt(1, c.getIdSucursal());
			ResultSet rs= ps.executeQuery();
			rs.next();
			c1.setIdSucursal(rs.getInt(1));
			c1.setProvincia(rs.getString(2));
			c1.setLocalidad(rs.getString(3));
			c1.setDireccion(rs.getString(4));
			c1.setTelefono(rs.getString(5));
			c1.setIdDirectorSucursal(rs.getInt(6));
		} catch (SQLException e) {
			Registro.registroMySQL(f, e);
		}
		return c1;
	}
	 /**
	  * Elimina una sucursal de la base de datos
	  * @param c
	  * @param f
	  */
	public static void eliminarSucursal(Sucursal c, File f) {
		Connection con= ConexionBBDD.conexion(f);
		String query= "delete from sucursales where idSucursal= ?";
		try{
			PreparedStatement ps= con.prepareStatement(query);
			ps.setInt(1, c.getIdSucursal());
			ps.execute();
		} catch (SQLException e) {
			Registro.registroMySQL(f, e);
		}
	}
	/**
	 * Inserta una sucursal en la base de datos
	 * @param c
	 * @param f
	 */
	public static void insertarSucursal(Sucursal c, File f) {
		Connection con= ConexionBBDD.conexion(f);
		String query ="insert into sucursales (idsucursal,provincia,localidad,direccion,telefono,idDirectorSucursal) values (?,?,?,?,?,?)";
		try{
			PreparedStatement ps= con.prepareStatement(query);
			ps.setInt(1, c.getIdSucursal());
			ps.setString(2, c.getProvincia());
			ps.setString(3, c.getLocalidad());
			ps.setString(4, c.getDireccion());
			ps.setString(5, c.getTelefono());
			ps.setInt(6, c.getIdDirectorSucursal());
			ps.execute();
		} catch (SQLException e) {
			Registro.registroMySQL(f, e);
		}
	}
	/**
	 * Recorre la lista de los proveedores para realizar acciones sobre los objetos
	 * 
	 * @param proveedores
	 * @param f
	 */
	public static void busquedaProveedor(List<Proveedor> proveedores,File f)  {
		for(Proveedor proveedor: proveedores) {
			if(existeProveedor(proveedor,f)) {
				Proveedor c1= buscarProveedor(proveedor,f);
				System.out.println("Se ha encontrado un proveedor duplicado, que desea hacer ?");
				System.out.println("Proveedor en la base de datos:\n"+c1.toString());
				System.out.println("Proveedor nuevo:\n"+proveedor.toString());
				System.out.println("1. Eliminar el registro antiguo");
				System.out.println("2. Descartar el registro nuevo");
				System.out.println("Escoga una opcion:");
				Scanner sc= new Scanner(System.in);
				int opcion= sc.nextInt();
				if(opcion==1) {
					eliminarProveedor(c1,f);
					insertarProveedor(proveedor,f);
					Registro.registroDuplicados(f, c1.toString());
				}
				else if(opcion==2) {
					Registro.registroDuplicados(f, proveedor.toString());
				}
				sc.close();
			}
			else {
				insertarProveedor(proveedor,f);
			}
			
		}
	}
	/**
	 * Comprueba si existe un proveedor en la base de datos
	 * @param proveedor
	 * @param f
	 * @return true en el caso de que exista y false en el caso de no existir
	 */
	public static boolean existeProveedor( Proveedor proveedor,File f ){
		Connection c= ConexionBBDD.conexion(f);
		String query= "select count(*) from proveedores where idproveedor= ?";
		try {
			PreparedStatement ps= c.prepareStatement(query);
			ps.setInt(1, proveedor.getIdProveedor());
			ResultSet rs= ps.executeQuery();
			rs.next();
			if(rs.getInt(1)>0) return true;
		} catch (SQLException e) {
			Registro.registroMySQL(f, e);
		}
		return false;
	}
	/**
	 * Busca un proveedor en la base de datos
	 * @param c
	 * @param f
	 * @return devuelve al dicho proveedor en forma de objeto
	 */
	public static Proveedor buscarProveedor(Proveedor c, File f) {
		Proveedor c1= new Proveedor();
		Connection con= ConexionBBDD.conexion(f);
		String query= "select * from proveedores where idproveedor= ? ";
		try{
			PreparedStatement ps= con.prepareStatement(query);
			ps.setInt(1, c.getIdProveedor());
			ResultSet rs= ps.executeQuery();
			rs.next();
			c1.setIdProveedor(rs.getInt(1));
			c1.setDireccion(rs.getString(2));
			c1.setTelefono(rs.getString(3));
			c1.setTipoP(rs.getString(4));
			c1.setIncidencias(rs.getInt(5));
		} catch (SQLException e) {
			Registro.registroMySQL(f, e);
		}
		return c1;
	}
	/**
	 * Elimina a un provveedor de la base de datos
	 * @param c
	 * @param f
	 */
	public static void eliminarProveedor(Proveedor c, File f) {
		Connection con= ConexionBBDD.conexion(f);
		String query= "delete from proveedores where idproveedor= ?";
		try{
			PreparedStatement ps= con.prepareStatement(query);
			ps.setInt(1, c.getIdProveedor());
			ps.execute();
		} catch (SQLException e) {
			Registro.registroMySQL(f, e);
		}
	}
	/**
	 * Inserta a un proveedor en la base de datos
	 * 
	 * @param c
	 * @param f
	 */
	public static void insertarProveedor(Proveedor c, File f) {
		Connection con= ConexionBBDD.conexion(f);
		String query ="insert into proveedores (idproveedor,direccion,telefono,tipop,incidencias) values (?,?,?,?,?)";
		try{
			PreparedStatement ps= con.prepareStatement(query);
			ps.setInt(1, c.getIdProveedor());
			ps.setString(2, c.getDireccion());
			ps.setString(3, c.getTelefono());
			ps.setString(4, c.getTipoP());
			ps.setInt(5, c.getIncidencias());
			ps.execute();
		} catch (SQLException e) {
			Registro.registroMySQL(f, e);
		}
	}
	/**
	 * Recorre la lista de productos para realizar acciones sobre los objetos
	 * @param productos
	 * @param f
	 */
	public static void busquedaProducto(List<Producto> productos,File f)  {
		for(Producto producto: productos) {
			if(existeProducto(producto,f)) {
				Producto c1= buscarProducto(producto,f);
				System.out.println("Se ha encontrado un producto duplicado, que desea hacer ?");
				System.out.println("Producto en la base de datos:\n"+c1.toString());
				System.out.println("Producto nuevo:\n"+producto.toString());
				System.out.println("1. Eliminar el registro antiguo");
				System.out.println("2. Descartar el registro nuevo");
				System.out.println("Escoga una opcion:");
				Scanner sc= new Scanner(System.in);
				int opcion= sc.nextInt();
				if(opcion==1) {
					eliminarProducto(c1,f);
					insertarProducto(producto,f);
					Registro.registroDuplicados(f, c1.toString());
				}
				else if(opcion==2) {
					Registro.registroDuplicados(f, producto.toString());
				}
				sc.close();
			}
			else {
				insertarProducto(producto,f);
			}
			
		}
	}
	/**
	 * Comprueba si existe un producto en la base de datos
	 * @param producto
	 * @param f
	 * @return devuelve true en el caso de existir y false en caso contrario
	 */
	public static boolean existeProducto( Producto producto,File f ){
		Connection c= ConexionBBDD.conexion(f);
		String query= "select count(*) from productos where idproducto= ?";
		try {
			PreparedStatement ps= c.prepareStatement(query);
			ps.setInt(1, producto.getIdProducto());
			ResultSet rs= ps.executeQuery();
			rs.next();
			if(rs.getInt(1)>0) return true;
		} catch (SQLException e) {
			Registro.registroMySQL(f, e);
		}
		return false;
	}
	/**
	 * Busca un producto en la base de datos
	 * @param c
	 * @param f
	 * @return devuelve el produto en forma de objeto 
	 */
	public static Producto buscarProducto(Producto c, File f) {
		Producto c1= new Producto();
		Connection con= ConexionBBDD.conexion(f);
		String query= "select * from productos where idproducto= ? ";
		try{
			PreparedStatement ps= con.prepareStatement(query);
			ps.setInt(1, c.getIdProducto());
			ResultSet rs= ps.executeQuery();
			rs.next();
			c1.setIdProducto(rs.getInt(1));
			c1.setTipoProducto(rs.getString(2));
			c1.setPrecioC(rs.getDouble(3));
			c1.setIdProveedorProducto(rs.getInt(4));
			c1.setPrecioV(rs.getDouble(5));
			c1.setRotacion(rs.getInt(6));
		} catch (SQLException e) {
			Registro.registroMySQL(f, e);
		}
		return c1;
	}
	/**
	 * Elimina un producto de la base de datps
	 * @param c
	 * @param f
	 */
	public static void eliminarProducto(Producto c, File f) {
		Connection con= ConexionBBDD.conexion(f);
		String query= "delete from productos where idproducto= ?";
		try{
			PreparedStatement ps= con.prepareStatement(query);
			ps.setInt(1, c.getIdProducto());
			ps.execute();
		} catch (SQLException e) {
			Registro.registroMySQL(f, e);
		}
	}
	/**
	 * Inserta un producto en la base de datos
	 * @param c
	 * @param f
	 */
	public static void insertarProducto(Producto c, File f) {
		Connection con= ConexionBBDD.conexion(f);
		String query ="insert into productos (idproducto,tipoproducto,precioc,idproveedorproducto,preciov,rotacion) values (?,?,?,?,?,?)";
		try{
			PreparedStatement ps= con.prepareStatement(query);
			ps.setInt(1, c.getIdProducto());
			ps.setString(2, c.getTipoProducto());
			ps.setDouble(3, c.getPrecioC());
			ps.setInt(4, c.getIdProveedorProducto());
			ps.setDouble(5, c.getPrecioV());
			ps.setInt(6, c.getRotacion());
			ps.execute();
			
		} catch (SQLException e) {
			Registro.registroMySQL(f, e);
		}
	}
	/**
	 * Recorre la lista de empleados para realizar acciones sobre los objetos
	 * @param empleados
	 * @param f
	 */
	public static void busquedaEmpleado(List<Empleado> empleados,File f)  {
		for(Empleado empleado: empleados) {
			if(existeEmpleado(empleado,f)) {
				Empleado c1= buscarEmpleado(empleado,f);
				System.out.println("Se ha encontrado un empleado duplicado, que desea hacer ?");
				System.out.println("Empleado en la base de datos:\n"+c1.toString());
				System.out.println("Empleado nuevo:\n"+empleado.toString());
				System.out.println("1. Eliminar el registro antiguo");
				System.out.println("2. Descartar el registro nuevo");
				System.out.println("Escoga una opcion:");
				Scanner sc= new Scanner(System.in);
				int opcion= sc.nextInt();
				if(opcion==1) {
					eliminarEmpleado(c1,f);
					insertarEmpleado(empleado,f);
					Registro.registroDuplicados(f, c1.toString());
				}
				else if(opcion==2) {
					Registro.registroDuplicados(f, empleado.toString());
				}
				sc.close();
			}
			else {
				insertarEmpleado(empleado,f);
			}
			
		}
	}
	/**
	 * Comprueba si existe un empleado en la base de datos
	 * @param empleado
	 * @param c
	 * @param f
	 * @return devuelve true en el caso de existir y false en el caso contrario
	 */
	public static boolean existeEmpleado( Empleado empleado,File f ){
		Connection c= ConexionBBDD.conexion(f);
		String query= "select count(*) from empleados where idempleado= ? and idsucursalempleado=?";
		try {
			PreparedStatement ps= c.prepareStatement(query);
			ps.setInt(1, empleado.getIdEmpleado());
			ps.setInt(2, empleado.getIdSucursalEmpleado());
			ResultSet rs= ps.executeQuery();
			rs.next();
			if(rs.getInt(1)>0) return true;
		} catch (SQLException e) {
			Registro.registroMySQL(f, e);
		}
		return false;
	}
	/**
	 * Busca un empleado en la base de datos
	 * @param c
	 * @param f
	 * @return devuelve dicho empleado en forma de objeto
	 */
	public static Empleado buscarEmpleado(Empleado c, File f) {
		Empleado c1= new Empleado();
		Connection con= ConexionBBDD.conexion(f);
		String query= "select * from empleados where idempleado= ? and idsucursalempleado=?";
		try{
			PreparedStatement ps= con.prepareStatement(query);
			ps.setInt(1, c.getIdEmpleado());
			ResultSet rs= ps.executeQuery();
			rs.next();
			c1.setIdEmpleado(rs.getInt(1));
			c1.setNombre(rs.getString(2));
			c1.setApellidos(rs.getString(3));
			c1.setIdSucursalEmpleado(rs.getInt(4));
			c1.setCategoria(rs.getInt(5));
			c1.setAntiguedad(rs.getInt(6));
			c1.setSalario(rs.getDouble(7));
		} catch (SQLException e) {
			Registro.registroMySQL(f, e);
		}
		return c1;
	}
	/**
	 * Elimina un empleado de la base de datos
	 * @param c
	 * @param f
	 */
	public static void eliminarEmpleado(Empleado c, File f) {
		Connection con= ConexionBBDD.conexion(f);
		String query= "delete from empleados where idempleado= ? and idsucursalempleado=?";
		try{
			PreparedStatement ps= con.prepareStatement(query);
			ps.setInt(1, c.getIdEmpleado());
			ps.setInt(2, c.getIdSucursalEmpleado());
			ps.execute();
		} catch (SQLException e) {
			Registro.registroMySQL(f, e);
		}
	}
	/**
	 * Inserta un empleado en la base de datos
	 * @param c
	 * @param con
	 * @param f
	 */
	public static void insertarEmpleado(Empleado c, File f) {
		Connection con= ConexionBBDD.conexion(f);
		String query ="insert into empleados (idempleado,nombre,apellidos,idsucursalempleado,categoria,antiguedad,salario) values (?,?,?,?,?,?,?)";
		try{
			PreparedStatement ps= con.prepareStatement(query);
			ps.setInt(1, c.getIdEmpleado());
			ps.setString(2, c.getNombre());
			ps.setString(3, c.getApellidos());
			ps.setInt(4, c.getIdSucursalEmpleado());
			ps.setInt(5, c.getCategoria());
			ps.setInt(6, c.getAntiguedad());
			ps.setDouble(7, c.getSalario());
			ps.execute();
			
		} catch (SQLException e) {
			Registro.registroMySQL(f, e);
		}
	}
}
