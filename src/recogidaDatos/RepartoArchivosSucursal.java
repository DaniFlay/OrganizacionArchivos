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
import sujetos.Sucursal;
/**
 * Clase para los metodos para repartir los archivos de la carpeta sucursal
 * @author super
 *
 */
public class RepartoArchivosSucursal {
	/**
	 * Reparte los archivos segun su extension y va metiendo los objetos en una lista
	 * @param f
	 */
	public static void repartoDeSucursales(File f) {
		ArrayList<Sucursal>sucursales= new ArrayList<Sucursal>();
		File[] archivosSucursales= f.listFiles();
		for(File archivo: archivosSucursales) {
			if(AnalisisArchivos.extension(archivo).endsWith(".dat")) {
				for(Sucursal c: sucursalDat(archivo)) {
					sucursales.add(c);
				}
			}
			else if(AnalisisArchivos.extension(archivo).endsWith(".xml")) {
				for(Sucursal c: sucursalXML(archivo)) {
					sucursales.add(c);
				}
			}
			else if(AnalisisArchivos.extension(archivo).endsWith(".txt")) {
				for(Sucursal c: sucursalTxt(archivo)) {
					sucursales.add(c);
				}
			}
		}
		Inserccion.busquedaSucursal(sucursales, f.getParentFile());
	}
	/**
	 * Recorre los archivos con la extension dat y guarda los datos en una lista
	 * @param f
	 * @return La lista con los objetos de los archivos dat 
	 */
	public static ArrayList<Sucursal> sucursalDat(File f) {
		Sucursal sucursal;
		int c;
		ArrayList<Sucursal> sucursales= new ArrayList<Sucursal>();
		try {	
			ObjectInputStream is= new ObjectInputStream(new FileInputStream(f));
			while((c=is.read())!=-1) {
				sucursal =(Sucursal) is.readObject();
				sucursales.add(sucursal);
			}
			is.close();
		} catch (FileNotFoundException e) {
			System.out.println(e.getLocalizedMessage().toString());
		} catch (ClassNotFoundException e) {
			System.out.println(e.getLocalizedMessage().toString());
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage().toString());
		}
		return sucursales;
	}
	/**
	 * Recorre los archivos txt y va guardando los datos en una lista
	 * @param f
	 * @return lista con los objetos recogidos de los archivos de texto
	 */

	public static ArrayList<Sucursal> sucursalTxt(File f){
		String str;
		ArrayList<Sucursal> sucursales= new ArrayList<Sucursal>();
		try (BufferedReader br= new BufferedReader(new FileReader(f));){
			
			while((str= br.readLine())!=null) {
					String[] parts= str.split("; ");
					int id = Integer.parseInt(parts[0].split(": ")[1]);
	                String provincia = parts[1].split(": ")[1];
	                String localidad =parts[2].split(": ")[1];
	                String direccion =parts[3].split(": ")[1];
	                String telefono =parts[4].split(": ")[1];
	                int idDir = Integer.parseInt(parts[5].split(": ")[1]);
	                

	                Sucursal sucursal = new Sucursal(id, provincia, localidad, direccion, telefono,idDir);

				sucursales.add(sucursal);
			}
			
		} catch (FileNotFoundException e) {
			System.out.println(e.getLocalizedMessage().toString());
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage().toString());
		}
		return sucursales;
	}
	/**
	 * Recorre los archivos xml y guarda los datos en una lista
	 * @param f
	 * @return la lista son los objetos encontrados en los xml
	 */
	public static List<Sucursal> sucursalXML(File f){
		return SAX.crearParseadorSucursal(f);

	}
}
