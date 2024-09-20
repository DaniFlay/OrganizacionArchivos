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
import archivos.*;
import sujetos.*;
/**
 * Clase para los metodos de raprto de los archivos de los clientes
 * @author super
 *
 */
public class RepartoArchivosCliente {
	/**
	 * Recibe la carpeta clientes y la recorre, comprueba la extension y dependiendo de ello lo manda a otro metodo para sacar los objetos y guardarlos en una lista
	 * @param f
	 */
	public static void repartoDeClientes(File f) {
		ArrayList<Cliente>clientes= new ArrayList<Cliente>();
		File[] archivosClientes= f.listFiles();
		for(File archivo: archivosClientes) {
			if(archivo.getName().endsWith(".dat")) {
				for(Cliente c: clienteDat(archivo)) {
					clientes.add(c);
				}
			}
			else if(archivo.getName().endsWith(".xml")) {
				for(Cliente c: clienteXML(archivo)) {
					clientes.add(c);
				}
			}
			else if(archivo.getName().endsWith(".txt")) {
				for(Cliente c: clienteTxt(archivo)) {
					clientes.add(c);
				}
			}
		}
		Inserccion.busquedaCliente(clientes, f.getParentFile());
	}
	/**
	 * Recorre el documento de los clientes de tipo dat y recoge a todos los clientes en una lista
	 * @param f
	 * @return lista con los clientes del archivo dat
	 */
	public static ArrayList<Cliente> clienteDat(File f) {
		Cliente cliente;
		int c;
		ArrayList<Cliente> clientes= new ArrayList<Cliente>();
		try {	
			ObjectInputStream is= new ObjectInputStream(new FileInputStream(f));
			while((c=is.read())!=-1) {
				cliente =(Cliente) is.readObject();
				clientes.add(cliente);
			}
			is.close();
		} catch (FileNotFoundException e) {
			System.out.println(e.getLocalizedMessage().toString());
		} catch (ClassNotFoundException e) {
			System.out.println(e.getLocalizedMessage().toString());
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage().toString());
		}
		return clientes;
	}
	/**
	 * Recorre el archivo txt de los clientes para guardarlos en una lista
	 * @param f
	 * @return la lista de los clientes
	 */
	public static ArrayList<Cliente> clienteTxt(File f){
		String str;
		ArrayList<Cliente> clientes= new ArrayList<Cliente>();
		try (BufferedReader br= new BufferedReader(new FileReader(f))){
			while((str= br.readLine())!=null) {
					String[] parts= str.split("; ");
					int id = Integer.parseInt(parts[0].split(": ")[1]);
	                String nombre = parts[1].split(": ")[1];
	                String apellidos = parts[2].split(": ")[1];
	                String tipoCliente = parts[3].split(": ")[1];
	                int nCompras = Integer.parseInt(parts[4].split(": ")[1]);

	                Cliente cliente = new Cliente(id, nombre, apellidos, tipoCliente, nCompras);

				clientes.add(cliente);
			}
			
		} catch (FileNotFoundException e) {
			System.out.println(e.getLocalizedMessage().toString());
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage().toString());
		}
		return clientes;
	}
	/**
	 * Recorre el archivo xml de los clientes usando el sax y devuele una lsita con los clientes 
	 * @param f
	 * @return
	 */
	public static List<Cliente> clienteXML(File f){
		return SAX.crearParseadorCliente(f);

	}

}
