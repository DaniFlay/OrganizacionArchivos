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
import sujetos.Proveedor;

/**
 * Clase para los metodos para el reparto de los archivos de la carpeta proveedor
 * @author super
 *
 */
public class RepartoArchivosProveedor {
	/**
	 * Reparte los archivos segun su extension y va metiendo los objetos en una lista
	 * @param f
	 */
	public static void repartoDeProveedores(File f) {
		ArrayList<Proveedor>proveedores= new ArrayList<Proveedor>();
		File[] archivosProveedores= f.listFiles();
		for(File archivo: archivosProveedores) {
			if(AnalisisArchivos.extension(archivo).endsWith(".dat")) {
				for(Proveedor c: proveedorDat(archivo)) {
					proveedores.add(c);
				}
			}
			else if(AnalisisArchivos.extension(archivo).endsWith(".xml")) {
				for(Proveedor c: proveedorXML(archivo)) {
					proveedores.add(c);
				}
			}
			else if(AnalisisArchivos.extension(archivo).endsWith(".txt")) {
				for(Proveedor c: proveedorTxt(archivo)) {
					proveedores.add(c);
				}
			}
		}
		Inserccion.busquedaProveedor(proveedores, f.getParentFile());
	}
	/**
	 * Recorre los archivos con la extension dat y guarda los datos en una lista
	 * @param f
	 * @return La lista con los objetos de los archivos dat 
	 */
	public static ArrayList<Proveedor> proveedorDat(File f) {
		Proveedor proveedor;
		int c;
		ArrayList<Proveedor> proveedores= new ArrayList<Proveedor>();
		try {	
			ObjectInputStream is= new ObjectInputStream(new FileInputStream(f));
			while((c=is.read())!=-1) {
				proveedor =(Proveedor) is.readObject();
				proveedores.add(proveedor);
			}
			is.close();
		} catch (FileNotFoundException e) {
			System.out.println(e.getLocalizedMessage().toString());
		} catch (ClassNotFoundException e) {
			System.out.println(e.getLocalizedMessage().toString());
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage().toString());
		}
		return proveedores;
	}
	/**
	 * Recorre los archivos txt y va guardando los datos en una lista
	 * @param f
	 * @return lista con los objetos recogidos de los archivos de texto
	 */
	public static ArrayList<Proveedor> proveedorTxt(File f){
		String str;
		ArrayList<Proveedor> proveedores= new ArrayList<Proveedor>();
		try (BufferedReader br= new BufferedReader(new FileReader(f));){
			
			while((str= br.readLine())!=null) {
					String[] parts= str.split("; ");
					int id = Integer.parseInt(parts[0].split(": ")[1]);
	                String direccion = parts[1].split(": ")[1];
	                String telefono =parts[2].split(": ")[1];
	                String tipoP =parts[3].split(": ")[1];
	                int incidencias = Integer.parseInt(parts[4].split(": ")[1]);
	                

	                Proveedor proveedor = new Proveedor(id, direccion, telefono, tipoP, incidencias);

				proveedores.add(proveedor);
			}
			
		} catch (FileNotFoundException e) {
			System.out.println(e.getLocalizedMessage().toString());
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage().toString());
		}
		return proveedores;
	}
	/**
	 * Recorre los archivos xml y guarda los datos en una lista
	 * @param f
	 * @return la lista son los objetos encontrados en los xml
	 */
	public static List<Proveedor> proveedorXML(File f){
		return SAX.crearParseadorProveedor(f);

	}
}
