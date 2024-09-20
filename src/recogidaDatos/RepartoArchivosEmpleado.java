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
import sujetos.Empleado;
/**
 * Clase para repartir los archivos de la carpeta empleados
 * @author super
 *
 */
public class RepartoArchivosEmpleado {
	/**
	 * Reparte los archivos segun su extension y va metiendo los objetos en una lista
	 * @param f
	 */
	public static void repartoDeEmpleados(File f) {
		ArrayList<Empleado>empleados= new ArrayList<Empleado>();
		File[] archivosEmpleados= f.listFiles();
		for(File archivo: archivosEmpleados) {
			if(AnalisisArchivos.extension(archivo).endsWith(".dat")) {
				for(Empleado c: empleadoDat(archivo)) {
					empleados.add(c);
				}
			}
			else if(AnalisisArchivos.extension(archivo).endsWith(".xml")) {
				for(Empleado c: empleadoXML(archivo)) {
					empleados.add(c);
				}
			}
			else if(AnalisisArchivos.extension(archivo).endsWith(".txt")) {
				for(Empleado c: empleadoTxt(archivo)) {
					empleados.add(c);
				}
			}
		}
		Inserccion.busquedaEmpleado(empleados, f.getParentFile());
	}
	/**
	 * Recorre los archivos con la extension dat y guarda los datos en una lista
	 * @param f
	 * @return La lista con los objetos de los archivos dat 
	 */
	public static ArrayList<Empleado> empleadoDat(File f) {
		Empleado empleado;
		int c;
		ArrayList<Empleado> empleados= new ArrayList<Empleado>();
		try {	
			ObjectInputStream is= new ObjectInputStream(new FileInputStream(f));
			while((c=is.read())!=-1) {
				empleado =(Empleado) is.readObject();
				empleados.add(empleado);
			}
			is.close();
		} catch (FileNotFoundException e) {
			System.out.println(e.getLocalizedMessage().toString());
		} catch (ClassNotFoundException e) {
			System.out.println(e.getLocalizedMessage().toString());
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage().toString());
		}
		return empleados;
	}
	/**
	 * Recoree los archivos txt y va guardando los datos en una lista
	 * @param f
	 * @return lista con los objetos recogidos de los archivos de texto
	 */
	public static ArrayList<Empleado> empleadoTxt(File f){
		String str;
		ArrayList<Empleado> empleados= new ArrayList<Empleado>();
		try (BufferedReader br= new BufferedReader(new FileReader(f));){
			while((str= br.readLine())!=null) {
					String[] parts= str.split("; ");
					int id = Integer.parseInt(parts[0].split(": ")[1]);
	                String nombre = parts[1].split(": ")[1];
	                String apellidos = parts[2].split(": ")[1];
	                int idSucursal =Integer.parseInt(parts[3].split(": ")[1]) ;
	                int categoria = Integer.parseInt(parts[4].split(": ")[1]);
	                int antiguedad = Integer.parseInt(parts[5].split(": ")[1]);
	                double salario = Double.parseDouble(parts[6].split(": ")[1]);

	                Empleado empleado = new Empleado(id, nombre, apellidos, idSucursal, categoria,antiguedad,salario);

				empleados.add(empleado);
			}
			
		} catch (FileNotFoundException e) {
			System.out.println(e.getLocalizedMessage().toString());
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage().toString());
		}
		return empleados;
	}
	/**
	 * Recorre los archivos xml y guarda los datos en una lista
	 * @param f
	 * @return la lista son los objetos encontrados en los xml
	 */
	public static List<Empleado> empleadoXML(File f){
		return SAX.crearParseadorEmpleado(f);

	}
}
