package recogidaDatos;

import java.io.File;
/**
 * Clase creada para crear un metodo para leer el directorio y mandar dicho directorio a otra funcion para un futuro reparto de datos a las listas y su inserccion a la base de datps
 * @author super
 *
 */
public class RepartoArchivos {
	/**
	 * Lee los directorios de la carpeta principal y dependiendo del nombre lo manda a una funcion u otra
	 * @param f
	 */
	public static void LeerDirectorios(File f) {
		File[] directorios= f.listFiles();
		for(File directorio: directorios) {
			if(directorio.isDirectory()) {
				if(directorio.getName().toLowerCase().contains("cliente")) {
				RepartoArchivosCliente.repartoDeClientes(directorio);
			}
			else if(directorio.getName().toLowerCase().contains("pedido")) {
				RepartoArchivosPedido.repartoDePedidos(directorio);
				
			}
			else if(directorio.getName().toLowerCase().contains("detalle")) {
				RepartoArchivosDetalle.repartoDeDetalles(directorio);
			}
			else if(directorio.getName().toLowerCase().contains("empleado")) {
				RepartoArchivosEmpleado.repartoDeEmpleados(directorio);
			}
			else if(directorio.getName().toLowerCase().contains("producto")) {
				RepartoArchivosProducto.repartoDeProductos(directorio);
			}
			else if(directorio.getName().toLowerCase().contains("proveedor")) {
				RepartoArchivosProveedor.repartoDeProveedores(directorio);
			}
			else if(directorio.getName().toLowerCase().contains("sucursal")) {
				RepartoArchivosSucursal.repartoDeSucursales(directorio);
			}
			}
			
		}
	}

}
