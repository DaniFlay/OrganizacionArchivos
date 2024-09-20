package BBDD;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import registros.Registro;
/**
 * Clase creada para la tercera parte de la practica
 * Todos los metodos que crean informes los imprimen directaemnte por pantalla
 * @author super
 *
 */
public class Consultas {
	/**
	 * Metodo para llamar al menu de las consultas que a su vez llamará a otros metodos para realizar la consulta
	 * @param c 
	 * @param f La ruta de la carpeta nueva
	 */
	public static void menuConsultas( File f) {
		Connection c= ConexionBBDD.conexion(f);
		ResultSet rs=null;
		Scanner sc= new Scanner(System.in);
		int opcion = 0;
		do{
			System.out.println("Consultas:");
			System.out.println("1. Buscar objetos por las claves primarias");
			System.out.println("2. Imprimir tablas completas");
			System.out.println("3. Imprimir determinados campos de determinadas tablas");
			System.out.println("4. Informe de los pedidos de un empleado");
			System.out.println("5. Informe de los 10 proveedores que tienen una rotacion de producto media menor");
			System.out.println("6. Informe de los mejores clientes");
			System.out.println("7. Informe de los clientes mas habituales entre 2 fechas");
			System.out.println("8. Informe con el número de empleados de cada categoria que hay en cada sucursal");
			System.out.println("9. Informe del numero de sucursales que hay en cada provincia y el numero"
					+ " de empleados de media que tiene cada sucursal de cada provincia");
			System.out.println("10. Informe de los 10 pedidos mas voluminosos");
			System.out.println("11. Informe de los 20 productos mas vendidos juntos con la informacion de "
					+ "los proveedores que lo suministran");
			System.out.println("12. Salir");
			System.out.println("Escoga una opcion: ");
			opcion= sc.nextInt();
		
				switch(opcion) {
				
				case 1:
					rs=buscarObjetosPorClavesPrimarias(f);
					escribirResultSet(rs,f);
					break;
				case 2:
					rs= tablasCompletas(f);
					escribirResultSet(rs,f);
					break;
				case 3:
					rs= buscarPorCampos(f);
					escribirResultSet(rs,f);
					break;
				case 4:
					informePedidosPorEmpleado(f);
					break;
				case 5:
					informeProveedoresMenorRotacion(f);
					break;
				case 6:
					informeMejoresClientes(f);
					break;
				case 7:
					informeClienteHabituales(f);
					break;
				case 8:
					informeEmpleadosCategoriaSucursal(f);
					break;
				case 9:
					informeNumSucursalesXProvinciaNumEmpleadosAvgSucursalProvincia(f);
					break;
				case 10:
					informePedidosMasVoluminosos(f);
					break;
				case 11:
					informeProductosMasVendidosYProveedores(f);
					break;
				case 12:
					System.out.println("Adios!!!");
					break;
				}	
			}while(opcion!=12);
		}

		
	
	/**
	 * Metodo que recibe un ResultSet y escribe los valores separados por comas
	 * 
	 * @param rs
	 * @param f
	 */
	public static void escribirResultSet(ResultSet rs, File f) {
			try {
				ResultSetMetaData rsmd= rs.getMetaData();
				int columnas = rsmd.getColumnCount();
				for(int i=1; i<columnas;i++) {
					System.out.print(rsmd.getColumnName(i)+" ");
				}
				System.out.println("\n");
				while(rs.next()) {
					for(int i=1; i<columnas;i++) {
						if(i>1) System.out.print(", ");
						System.out.print(rs.getString(i));
					}
					System.out.println("\n");
				}
			} catch (SQLException e) {
				Registro.registroMySQL(f, e); // registro de la excepcion sql
			}

	
	}
	/**
	 * Este metodo se encarga de comprobar cual es la tabla a la que quiere acceder el usuario
	 * Recibe el string, y lo comprueba, y devuelvo un codigo del 1 al 7, cada umo perteneciente a una tabla
	 * Van en orden alfabetico
	 * 
	 * 
	 * @param input
	 * @return el codigo asignado a la tabla
	 */
	public static int comprobacionDeObjeto(String input) {
		int codigo=0; // En el caso de no existir devuelve 0
		if(input.toLowerCase().contains("cliente")) {
			codigo = 1;
		}
		else if(input.toLowerCase().contains("detalle")) {
			codigo = 2;
		}
		else if(input.toLowerCase().contains("empleado")) {
			codigo = 3;
		}
		else if(input.toLowerCase().contains("pedido")) {
			codigo = 4;
		}
		else if(input.toLowerCase().contains("producto")) {
			codigo = 5;
		}
		else if(input.toLowerCase().contains("proveedor")) {
			codigo = 6;
		}
		else if(input.toLowerCase().contains("sucursal")) {
			codigo = 7;
		}
		return codigo;
	}
	/**
	 * Este metodo se encarga de obtener un objeto por su/sus clave/claves primaria/primarias y devuelve un resultset con el resultado
	 * 
	 * @param f
	 * @return el resultset con el resultado
	 */
	public static ResultSet buscarObjetosPorClavesPrimarias( File f) {
		Connection con= ConexionBBDD.conexion(f);
		int idCliente;
		int idSucursal;
		int idProveedor;
		int idEmpleado;
		int idProducto;
		int idPedido;
		int categoria;
		int codigo;
		String query="select * from ";
		Scanner sc= new Scanner(System.in);
		PreparedStatement ps =null;
		ResultSet rs= null;
		try {
			do {
				System.out.println("Introduce el objeto a buscar: ");
				String objeto= sc.nextLine();
				codigo= comprobacionDeObjeto(objeto); // Obtengo el codigo de la tabla y dependiendo del resultado creo una query u otra, y pido unas claves primarias u otras
				switch(codigo) {
				case 1: 
					query+="cliente where idcliente = ?";
					System.out.println("Introduce el idCliente: ");
					idCliente= sc.nextInt();
					ps= con.prepareStatement(query);
					ps.setInt(1, idCliente);
				case 2:
					query+="detallesdelospedidos where idpedido=? and idproducto =? and idproveedorProducto=?";
					System.out.println("Introduce el idpedido");
					idPedido= sc.nextInt();
					System.out.println("Introduce el idProducto: ");
					idProducto= sc.nextInt();
					System.out.println("Introduce el idProveedorProducto");
					idProveedor= sc.nextInt();
					ps.setInt(1, idPedido);
					ps.setInt(2, idProducto);
					ps.setInt(3, idProveedor);
				case 3:
					query+="empleados where idempleado=? and idsucursalempleado=? and categoria=?";
					System.out.println("Introduce el idEmpleado: ");
					idEmpleado= sc.nextInt();
					System.out.println("Introduce el idSucursalEmpleado");
					idSucursal= sc.nextInt();
					System.out.println("Introduce la categoria del empleado");
					categoria= sc.nextInt();
					ps.setInt(1, idEmpleado);
					ps.setInt(2, idSucursal);
					ps.setInt(3, categoria);
				case 4:
					query+="pedidos where idpedido=? ";
					System.out.println("Introduce el idPedido:");
					idPedido= sc.nextInt();
					ps.setInt(1, idPedido);
				case 5:
					query+="productos where idproducto= ? and idproveedorproducto=?";
					System.out.println("Introduce el idProducto: ");
					idProducto= sc.nextInt();
					System.out.println("Introduce el idProveedorProducto");
					idProveedor= sc.nextInt();
					ps.setInt(1, idProducto);
					ps.setInt(2, idProveedor);
				case 6:
					query+= "proveedores where idproveedor= ?";
					System.out.println("Introduce el idProveedor");
					idProveedor= sc.nextInt();
					ps.setInt(1, idProveedor);
				case 7:
					query+="sucursales where idsucursal=?";
					System.out.println("Introduce el idSucursal");
					idSucursal= sc.nextInt();
					ps.setInt(1, idSucursal);
				case 0: 
					System.out.println("El objeto introducido no existe");
				}
			}while(codigo==0);
			ps= con.prepareStatement(query);
			rs= ps.executeQuery();
		}catch(SQLException e) {
			Registro.registroMySQL(f, e);
		}
		
		sc.close();
		return rs;
	
	}
	/**
	 * Este metodo devuelve un result set con la tabla completa que le pida el usuario 
	 * 
	 * @param f
	 * @return resultset con el resultado
	 */
	public static ResultSet tablasCompletas(File f) {
		Connection con= ConexionBBDD.conexion(f);
		String query="select * from ";
		Scanner sc= new Scanner(System.in);
		ResultSet rs= null;
			System.out.println("Introduce el nombre de la tabla");
			String tabla= sc.nextLine();
			int codigo= comprobacionDeObjeto(tabla);
			switch(codigo) {
			case 1:
				query+="clientes";
				break;
			case 2:
				query+="detallesdelospedidos";
				break;
			case 3:
				query+="empleados";
				break;
			case 4:
				query+="pedidos";
				break;
			case 5:
				query+="productos";
				break;
			case 6:
				query+="proveedores";
				break;
			case 7:
				query+="sucursales";
				break;
			default:
				System.out.println("La tabla no existe");
			}
		try {
			Statement s=con.createStatement();
			rs=s.executeQuery(query);
		} catch (SQLException e) {
			Registro.registroMySQL(f, e);
		}
		return rs;
		
	}
	/**
	 * Busca los campos especificos de la tabla especifica y devuelve un result set con el resultado
	 * 
	 * @param f
	 * @return resultset con el resultado
	 */
	public static ResultSet buscarPorCampos(File f) {
		Connection con= ConexionBBDD.conexion(f);
		ResultSet rs= null;
		Scanner sc= new Scanner(System.in);
		String tabla,columna, query="select ";
		int codigo,num;
		do {
			System.out.println("Introduce el nombre de la tabla: ");
			tabla= sc.nextLine();
			codigo= comprobacionDeObjeto(tabla);
		}while(codigo==0);
			System.out.println("Introduce el numero de columnas a buscar: ");
			num= sc.nextInt();
			sc.nextLine();
			for(int i=0; i<num;i++) {
				System.out.println("Introduce el nombre de la columna: ");
				columna= sc.nextLine();
				if(i!=num-1) {
					query+=columna;
				}
				else {
					query=query+columna+",";
				}
			}
			query= query+"from "+tabla;
			sc.close();
			try {
				Statement s= con.createStatement();
				rs= s.executeQuery(query);
			} catch (SQLException e) {
				Registro.registroMySQL(f, e);
			}
			return rs;
	}
	
	/**
	 * Crea un informe de todos los pedidos de un determinado empleado
	 * 
	 * @param f
	 */
	public static void informePedidosPorEmpleado( File f) {
		Connection con= ConexionBBDD.conexion(f);
		Scanner sc= new Scanner(System.in);
		System.out.println("Introduce el id  del empleado: ");
		int id= sc.nextInt(),linea=1;
		String query= "select * from pedidos where idempleado= ?";
		try(PreparedStatement ps= con.prepareStatement(query)){
			ps.setInt(1, id);
			ResultSet rs= ps.executeQuery();
			while(rs.next()) {
				System.out.println("\nPedido "+linea);
				System.out.println("ID Pedido: "+rs.getInt(1));
				System.out.println("ID Cliente: "+rs.getInt(2));
				System.out.println("ID Empleado: "+rs.getInt(3));
				System.out.println("Fecha: "+rs.getDate(4).toString());
				System.out.println("Precio Final: "+rs.getDouble(5));
			}
			
		} catch (SQLException e) {
			Registro.registroMySQL(f, e);
		}
		sc.close();
		
	}
	/**
	 * Obtiene un informe con los 10 proveedores que tienen un arotacion de producto media menor
	 * 
	 * @param f
	 */
	public static void informeProveedoresMenorRotacion( File f) {
		Connection con= ConexionBBDD.conexion(f);
		String query1= "select idproveedor, avg(rotacion) as rotacion_promedio "
				+ "from productos group by idproveedor order by rotacion_promedio "
				+ "limit 10";
		String query2= "select * from proveedor where idproveedor =? or idproveedor=? "
				+ "or idproveedor=? or idproveedor=? or idproveedor=? or idproveedor=? "
				+ "or idproveedor=? or idproveedor=? or idproveedor=? or idproveedor=? ";
		int[] ids= new int[10];
		int num=0;
		double[] average= new double[10];
		try {
			Statement s= con.createStatement();
			ResultSet rs1= s.executeQuery(query1);
			while(rs1.next()) {
				ids[num]=rs1.getInt(1);
				average[num]=rs1.getDouble(2);
				num++;
			}
			num=0;
			PreparedStatement ps= con.prepareStatement(query2);
			ps.setInt(1, ids[0]);
			ps.setInt(2, ids[1]);
			ps.setInt(3, ids[2]);
			ps.setInt(4, ids[3]);
			ps.setInt(5, ids[4]);
			ps.setInt(6, ids[5]);
			ps.setInt(7, ids[6]);
			ps.setInt(8, ids[7]);
			ps.setInt(9, ids[8]);
			ps.setInt(10, ids[9]);
			ResultSet rs2= ps.executeQuery();
			while(rs2.next()) {
				System.out.println("\nProveedor "+num);
				System.out.println("ID Proveedor: "+rs2.getInt(1));
				System.out.println("Direccion: "+rs2.getString(2));
				System.out.println("Telefono: "+rs2.getString(3));
				System.out.println("Tipo P: "+rs2.getString(4));
				System.out.println("Incidencias: "+rs2.getInt(5));
				System.out.println("La media: "+average[num]);
				num++;
			}
		}catch(SQLException e) {
			Registro.registroMySQL(f, e);
		}
	}
	/**
	 * Crea un informe con los clientes que mas dinero se han gastado, un total de 5 clientes
	 * 
	 * @param f
	 */
	public static void informeMejoresClientes( File f) {
		Connection con= ConexionBBDD.conexion(f);
		String query1="select idcliente, sum(preciofinal) as totalGastado from pedidos group by idcliente order by totalGastado desc limit 5";
		String query2= "select * from clientes where idcliente =? or idcliente =? or idcliente=? or idcliente=? or idcliente=?";
		int[] ids= new int[5];
		double[] gastado= new double[5];
		int num=0;
		try {
			Statement s= con.createStatement();
			ResultSet rs1= s.executeQuery(query1);
			while(rs1.next()) {
				ids[num]= rs1.getInt(1);
				gastado[num]= rs1.getDouble(1);
				num++;
			}
			num=0;
			PreparedStatement ps= con.prepareStatement(query2);
			ps.setInt(1, ids[0]);
			ps.setInt(2, ids[1]);
			ps.setInt(3, ids[2]);
			ps.setInt(4, ids[3]);
			ps.setInt(5, ids[4]);
			ResultSet rs2= ps.executeQuery();
			while(rs2.next()) {
				System.out.println("\nProveedor "+num);
				System.out.println("ID Cliente: "+rs2.getInt(1));
				System.out.println("Nombre: "+rs2.getString(2));
				System.out.println("Apellidos: "+rs2.getString(3));
				System.out.println("Tipo Cliente: "+rs2.getString(4));
				System.out.println("Numero de compras: "+rs2.getInt(5));
				System.out.println("La media: "+gastado[num]);
				num++;
			}
			
		} catch (SQLException e) {
			Registro.registroMySQL(f, e);
		}
		
	}
	/**
	 * Crea un informe con los clientes mas habituales, 5 clientes
	 * 
	 * @param f
	 */
	public static void informeClienteHabituales( File f) {
		Connection c= ConexionBBDD.conexion(f);
		Scanner sc= new Scanner(System.in);
		String query1="select count(idpedido) as pedido, idcliente from pedidos where fecha between ? and ? group by idcliente order "
				+ "by pedido desc limit 5";
		String query2= "select * from clientes where idcliente =? or idcliente=? or id cliente = or idcliente=? or idcliente=?";
		System.out.println("Introduce la primera fecha: ");
		String f1 = sc.nextLine();
		System.out.println("Introduce la segunda fecha: ");
		String f2 = sc.nextLine();
		int[] numPedidos= new int[5];
		int[] ids= new int[5];
		int num=0;
		sc.close();
		try {
			PreparedStatement ps1= c.prepareStatement(query1);
			ps1.setDate(1, java.sql.Date.valueOf(f1));
			ps1.setDate(2, java.sql.Date.valueOf(f2));
			ResultSet rs1= ps1.executeQuery();
			while(rs1.next()) {
				numPedidos[num]= rs1.getInt(1);
				ids[num] = rs1.getInt(2);
				num++;
			}
			num=0;
			PreparedStatement ps2= c.prepareStatement(query2);
			ps2.setInt(1, ids[0]);
			ps2.setInt(2, ids[1]);
			ps2.setInt(3, ids[2]);
			ps2.setInt(4, ids[3]);
			ps2.setInt(5, ids[4]);
			ResultSet rs2= ps2.executeQuery();
			while(rs2.next()) {
				System.out.println("\nCliente "+num);
				System.out.println("ID Cliente: "+rs2.getInt(1));
				System.out.println("Nombre: "+rs2.getString(2));
				System.out.println("Apellidos: "+rs2.getString(3));
				System.out.println("Tipo Cliente: "+rs2.getString(4));
				System.out.println("Numero de Compras: "+rs2.getInt(5));
				System.out.println("Numero de Pedidos entre "+f1+" y "+f2+": "+numPedidos[num]);
				num++;
			}
		} catch (SQLException e) {
			Registro.registroMySQL(f, e);
		}
	}
	/**
	 * Crea un informe con el numero de empleados de cada categoria de cada sucursal
	 * 
	 * @param f
	 */
	public static void informeEmpleadosCategoriaSucursal( File f) {
		Connection con= ConexionBBDD.conexion(f);
		String query= "select idsucursalempleado, categoria, count(*) as numEmpleados"
				+ "from empleados group by idsucursalempleado, categoria";
		try {
			Statement s= con.createStatement();
			ResultSet rs= s.executeQuery(query);
			int count=0;
			while(rs.next()) {
				System.out.println("Sucursal "+count);
				System.out.println("ID Sucursal: "+rs.getInt(1));
				System.out.println("Categoria: "+rs.getInt(2));
				System.out.println("Numero de empleados: "+rs.getInt(3));
				count++;
			}
		} catch (SQLException e) {
			Registro.registroMySQL(f, e);
		}
		
	}
/**
 * Crea el informe con el numero de sucursales que hay en cada provincia 
 * y el numero de empleados de media que tiene cada sucursal
 * 
 * @param f
 */
	public static void informeNumSucursalesXProvinciaNumEmpleadosAvgSucursalProvincia( File f) {
		Connection con= ConexionBBDD.conexion(f);
		String query= "select provincia, count(idsucursal) as numSucursales, avg(numEmpleados) as avgEmpleados "
				+ "from sucursales left join (select idsucursalempleado, count(*) from numEmpleados"
				+ "from empleados group by idsucursalempleado) on idsucursal = idsucursalempleado"
				+ "group by provincia";
		try {
			Statement s= con.createStatement();
			ResultSet rs= s.executeQuery(query);
			int count=0;
			while(rs.next()) {
				System.out.println("\nProvincia "+count);
				System.out.println("Nombre: "+rs.getString(1));
				System.out.println("Numero de Sucursales: "+rs.getInt(2));
				System.out.println("Media de Empleados: "+rs.getDouble(3));
				count++;
			}
		} catch (SQLException e) {
			Registro.registroMySQL(f, e);
		}
				
	}
	
	/**
	 * Obtiene el informe de los 10 pedidos mas voluminosos
	 * 
	 * @param f
	 */
	public static void informePedidosMasVoluminosos( File f) {
		Connection con= ConexionBBDD.conexion(f);
		String query="select idpedido, count(idproducto) as numProductos from detallesdelospedidos"
				+ "group by idpedido order by numProductos desc limit 10";
		String query2= "select * from pedido where idpedido=? and idPedido=? and idPedido=? and idPedido=? "
				+ "and idPedido=? and idPedido=? and idPedido=? and idPedido=? and idPedido=? and idPedido=?";
		int[] ids=new int[10];
		int[] numProductos= new int[10];
		int count=0;
		try {
			Statement s= con.createStatement();
			ResultSet rs1= s.executeQuery(query);
			while(rs1.next()) {
				ids[count]= rs1.getInt(1);
				numProductos[count]= rs1.getInt(2);
				count++;
			}
			count=0;
			PreparedStatement ps= con.prepareStatement(query2);
			ps.setInt(1, ids[0]);
			ps.setInt(2, ids[1]);
			ps.setInt(3, ids[2]);
			ps.setInt(4, ids[3]);
			ps.setInt(5, ids[4]);
			ps.setInt(6, ids[5]);
			ps.setInt(7, ids[6]);
			ps.setInt(8, ids[7]);
			ps.setInt(9, ids[8]);
			ps.setInt(10, ids[9]);
			ResultSet rs2= ps.executeQuery();
			while(rs2.next()) {
				System.out.println("Pedido "+count);
				System.out.println("ID Pedido: "+rs2.getInt(1));
				System.out.println("ID Cliente: "+rs2.getInt(2));
				System.out.println("ID Empleado: "+rs2.getInt(3));
				System.out.println("Fecha: " +rs2.getDate(4).toString());
				System.out.println("Precio Final: "+rs2.getDouble(5));
				System.out.println("Numero de Productos: "+numProductos[count]);
				count++;
			}
		} catch (SQLException e) {
			Registro.registroMySQL(f, e);
		}
		
				
	}
	/**
	 * Obtiene el informe de los 20 productos mas vendidos
	 * @param f
	 */
	public static void informeProductosMasVendidosYProveedores( File f) {
		Connection con= ConexionBBDD.conexion(f);
		String query1= "select idproducto, idproveedorproducto, sum(cantidad) as suma "
				+ "from detallesdelospedidos group by idproducto, idproveedorproducto "
				+ "order by suma desc limit 20";
		String query2= "select * from producto where idproducto=? or idproducto=? or idproducto=? or idproducto=? "
				+ "idproducto=? or idproducto=? or idproducto=? or idproducto=? or idproducto=? or idproducto=? "
				+ "idproducto=? or idproducto=? or idproducto=? or idproducto=? or idproducto=? or idproducto=? "
				+ "idproducto=? or idproducto=? or idproducto=? or idproducto=? ";
		String query3= "select * from proveedor where idproveedor=? or idproveedor=? or idproveedor=? or idproveedor=? "
				+ " or idproveedor=? or idproveedor=? or idproveedor=? or idproveedor=? or idproveedor=? or idproveedor=? "
				+ "or idproveedor=? or idproveedor=? or idproveedor=? or idproveedor=? or idproveedor=? or idproveedor=?"
				+ "or idproveedor=? or idproveedor=? or idproveedor=? or idproveedor=?";
		
		int[] ids1= new int[20];
		int[] ids2= new int[20];
		int[] suma= new int[20];
		int count =0;
		try {
			Statement s= con.createStatement();
			ResultSet rs1= s.executeQuery(query1);
			while(rs1.next()) {
				ids1[count]= rs1.getInt(1);
				ids2[count]= rs1.getInt(2);
				suma[count]= rs1.getInt(3);
				count++;
			}
			count=0;
			PreparedStatement ps= con.prepareStatement(query2);
			ps.setInt(1, ids1[0]);
			ps.setInt(2, ids1[1]);
			ps.setInt(3, ids1[2]);
			ps.setInt(4, ids1[3]);
			ps.setInt(5, ids1[4]);
			ps.setInt(6, ids1[5]);
			ps.setInt(7, ids1[6]);
			ps.setInt(8, ids1[7]);
			ps.setInt(9, ids1[8]);
			ps.setInt(10, ids1[9]);
			ps.setInt(11, ids1[10]);
			ps.setInt(12, ids1[11]);
			ps.setInt(13, ids1[12]);
			ps.setInt(14, ids1[13]);
			ps.setInt(15, ids1[14]);
			ps.setInt(16, ids1[15]);
			ps.setInt(17, ids1[16]);
			ps.setInt(18, ids1[17]);
			ps.setInt(19, ids1[18]);
			ps.setInt(20, ids1[19]);
			ResultSet rs2= ps.executeQuery();
			PreparedStatement ps1= con.prepareStatement(query3);
			ps1.setInt(1, ids2[0]);
			ps1.setInt(2, ids2[1]);
			ps1.setInt(3, ids2[2]);
			ps1.setInt(4, ids2[3]);
			ps1.setInt(5, ids2[4]);
			ps1.setInt(6, ids2[5]);
			ps1.setInt(7, ids2[6]);
			ps1.setInt(8, ids2[7]);
			ps1.setInt(9, ids2[8]);
			ps1.setInt(10, ids2[9]);
			ps1.setInt(11, ids2[10]);
			ps1.setInt(12, ids2[11]);
			ps1.setInt(13, ids2[12]);
			ps1.setInt(14, ids2[13]);
			ps1.setInt(15, ids2[14]);
			ps1.setInt(16, ids2[15]);
			ps1.setInt(17, ids2[16]);
			ps1.setInt(18, ids2[17]);
			ps1.setInt(19, ids2[18]);
			ps1.setInt(20, ids2[19]);
			ResultSet rs3= ps.executeQuery();
			while(rs2.next() && rs3.next()) {
				System.out.println("Producto: "+count);
				System.out.println("ID Producto "+rs2.getInt(1));
				System.out.println("Tipo Producto: "+rs2.getString(2));
				System.out.println("Precio C: "+rs2.getDouble(3));
				System.out.println("Precio V: "+rs2.getDouble(5));
				System.out.println("Rotacion: "+rs2.getInt(6));
				System.out.println("ID Proveedor: "+rs3.getInt(1));
				System.out.println("Direccion: "+rs3.getString(2));
				System.out.println("Telefono: "+rs3.getString(3));
				System.out.println("Tipo P: "+rs3.getString(4));
				System.out.println("Incidencias: "+rs3.getInt(5));
				System.out.println("Cantidad vendida de producto: "+suma[count]);
				count++;
			}
		} catch (SQLException e) {
			Registro.registroMySQL(f, e);
		}
	}
	
}
