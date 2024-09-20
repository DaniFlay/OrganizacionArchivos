package recogidaDatos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import BBDD.Inserccion;
import archivos.AnalisisArchivos;
import sujetos.*;
/**
 * Clase para los metodos para repartir los archivos de los detalles de los pedidos
 * @author super
 *
 */
public class RepartoArchivosDetalle {
	/**
	 * Reparte los archivos segun su extension y va metiendo los objetos en una lista
	 * @param f
	 */
	public static void repartoDeDetalles(File f) {
		ArrayList<DetallePedido> detalles= new ArrayList<DetallePedido>();
		File[] archivosDetalles= f.listFiles();
		for(File archivo: archivosDetalles) {
			if(AnalisisArchivos.extension(archivo).endsWith(".dat")) {
				for(DetallePedido d: detalleDat(archivo)) {
					detalles.add(d);
				}
			}
			else if(AnalisisArchivos.extension(archivo).endsWith(".xml")) {
				for(DetallePedido d: detalleXML(archivo)) {
					detalles.add(d);
				}
			}
			else if(AnalisisArchivos.extension(archivo).endsWith(".txt")) {
				for(DetallePedido d: detalleTxt(archivo)) {
					detalles.add(d);
				}
			}
		}
		Inserccion.busquedaDetalle(detalles, f.getParentFile()); //Manda la lista a la funcion de inserccion
	}
	/**
	 * Recorre los archivos con la extension dat y guarda los datos en una lista
	 * @param f
	 * @return La lista con los objetos de los archivos dat 
	 */
	public static ArrayList<DetallePedido> detalleDat(File f) {
		DetallePedido detalle;
		int c;
		ArrayList<DetallePedido> detalles= new ArrayList<DetallePedido>();
		try {	
			ObjectInputStream is= new ObjectInputStream(new FileInputStream(f));
			while((c=is.read())!=-1) {
				detalle =(DetallePedido) is.readObject();
				detalles.add(detalle);
			}
			is.close();
		} catch (FileNotFoundException e) {
			System.out.println(e.getLocalizedMessage().toString());
		} catch (ClassNotFoundException e) {
			System.out.println(e.getLocalizedMessage().toString());
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage().toString());
		}
		return detalles;
	}
	/**
	 * Recorre los archivos txt y va guardando los datos en una lista
	 * @param f
	 * @return lista con los objetos recogidos de los archivos de texto
	 */
	public static ArrayList<DetallePedido> detalleTxt(File f){
		String str;
		ArrayList<DetallePedido> detalles= new ArrayList<DetallePedido>();
		try (BufferedReader br= new BufferedReader(new FileReader(f))){
			
			while((str= br.readLine())!=null) {
					String[] parts= str.split("; ");
					int idPedido = Integer.parseInt(parts[0].split(": ")[1]);
	                int idProducto = Integer.parseInt(parts[1].split(": ")[1]);
	                int idProveedorProducto =Integer.parseInt(parts[2].split(": ")[1]) ;
	                double precioUnidad =Double.parseDouble(parts[3].split(": ")[1]) ;
	                int cantidad = Integer.parseInt(parts[4].split(": ")[1]);
	                double descuento= Double.parseDouble(parts[5].split(": ")[1]) ;
	                double precioFinal= Double.parseDouble(parts[6].split(": ")[1]) ;

	                DetallePedido detalle = new DetallePedido(idPedido,  idProducto, idProveedorProducto,  precioUnidad, cantidad,
	            			 descuento, precioFinal);

				detalles.add(detalle);
			}
			
		} catch (FileNotFoundException e) {
			System.out.println(e.getLocalizedMessage().toString());
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage().toString());
		}
		return detalles;
	}
	/**
	 * Recorre los archivos xml y guarda los datos en una lista
	 * @param f
	 * @return la lista son los objetos encontrados en los xml
	 */
	public static List<DetallePedido> detalleXML(File f){
		return SAX.crearParseadorDetalles(f);

	}
}
