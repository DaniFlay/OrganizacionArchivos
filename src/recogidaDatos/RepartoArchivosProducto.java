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
import sujetos.Producto;
/**
 * Clase para los metodos para el reaprto de los archivos de la carpeta productos
 * @author super
 *
 */
public class RepartoArchivosProducto {
	/**
	 * Reparte los archivos segun su extension y va metiendo los objetos en una lista
	 * @param f
	 */
	public static void repartoDeProductos(File f) {
		ArrayList<Producto>productos= new ArrayList<Producto>();
		File[] archivosProductos= f.listFiles();
		for(File archivo: archivosProductos) {
			if(AnalisisArchivos.extension(archivo).endsWith(".dat")) {
				for(Producto c: productoDat(archivo)) {
					productos.add(c);
				}
			}
			else if(AnalisisArchivos.extension(archivo).endsWith(".xml")) {
				for(Producto c: productoXML(archivo)) {
					productos.add(c);
				}
			}
			else if(AnalisisArchivos.extension(archivo).endsWith(".txt")) {
				for(Producto c: productoTxt(archivo)) {
					productos.add(c);
				}
			}
		}
		Inserccion.busquedaProducto(productos, f.getParentFile());
	}
	/**
	 * Recorre los archivos con la extension dat y guarda los datos en una lista
	 * @param f
	 * @return La lista con los objetos de los archivos dat 
	 */
	public static ArrayList<Producto> productoDat(File f) {
		Producto producto;
		int c;
		ArrayList<Producto> productos= new ArrayList<Producto>();
		try {	
			ObjectInputStream is= new ObjectInputStream(new FileInputStream(f));
			while((c=is.read())!=-1) {
				producto =(Producto) is.readObject();
				productos.add(producto);
			}
			is.close();
		} catch (FileNotFoundException e) {
			System.out.println(e.getLocalizedMessage().toString());
		} catch (ClassNotFoundException e) {
			System.out.println(e.getLocalizedMessage().toString());
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage().toString());
		}
		return productos;
	}
	/**
	 * Recorre los archivos txt y va guardando los datos en una lista
	 * @param f
	 * @return lista con los objetos recogidos de los archivos de texto
	 */
	public static ArrayList<Producto> productoTxt(File f){
		String str;
		ArrayList<Producto> productos= new ArrayList<Producto>();
		try (BufferedReader br= new BufferedReader(new FileReader(f))){
			
			while((str= br.readLine())!=null) {
					String[] parts= str.split("; ");
					int id = Integer.parseInt(parts[0].split(": ")[1]);
	                String tipo = parts[1].split(": ")[1];
	                double precioC =Double.parseDouble(parts[2].split(": ")[1]);
	                int idP =Integer.parseInt(parts[3].split(": ")[1]) ;
	                double precioV = Double.parseDouble(parts[4].split(": ")[1]);
	                int rotacion = Integer.parseInt(parts[5].split(": ")[1]);
	                

	                Producto producto = new Producto(id, tipo, precioC, idP, precioV,rotacion);

				productos.add(producto);
			}
			
		} catch (FileNotFoundException e) {
			System.out.println(e.getLocalizedMessage().toString());
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage().toString());
		}
		return productos;
	}
	/**
	 * Recorre los archivos xml y guarda los datos en una lista
	 * @param f
	 * @return la lista son los objetos encontrados en los xml
	 */
	public static List<Producto> productoXML(File f){
		return SAX.crearParseadorProducto(f);

	}
}
