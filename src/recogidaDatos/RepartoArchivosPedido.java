package recogidaDatos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import BBDD.Inserccion;
import archivos.AnalisisArchivos;
import sujetos.Pedido;

/**
 * Clase para repartir los archivos de la carpeta pedido
 * @author super
 *
 */
public class RepartoArchivosPedido {
	/**
	 * Reparte los archivos segun su extension y va metiendo los objetos en una lista
	 * @param f
	 */

	public static void repartoDePedidos(File f) {
		ArrayList<Pedido>pedidos= new ArrayList<Pedido>();
		File[] archivosPedidos= f.listFiles();
		for(File archivo: archivosPedidos) {
			if(AnalisisArchivos.extension(archivo).endsWith(".dat")) {
				for(Pedido c: pedidoDat(archivo)) {
					pedidos.add(c);
				}
			}
			else if(AnalisisArchivos.extension(archivo).endsWith(".xml")) {
				for(Pedido c: pedidoXML(archivo)) {
					pedidos.add(c);
				}
			}
			else if(AnalisisArchivos.extension(archivo).endsWith(".txt")) {
				for(Pedido c: pedidoTxt(archivo)) {
					pedidos.add(c);
				}
			}
		}
		Inserccion.busquedaPedido(pedidos, f.getParentFile());
	}
	
	/**
	 * Recorre los archivos con la extension dat y guarda los datos en una lista
	 * @param f
	 * @return La lista con los objetos de los archivos dat 
	 */
	public static ArrayList<Pedido> pedidoDat(File f) {
		Pedido pedido;
		int c;
		ArrayList<Pedido> pedidos= new ArrayList<Pedido>();
		try {	
			ObjectInputStream is= new ObjectInputStream(new FileInputStream(f));
			while((c=is.read())!=-1) {
				pedido =(Pedido) is.readObject();
				pedidos.add(pedido);
			}
			is.close();
		} catch (FileNotFoundException e) {
			System.out.println(e.getLocalizedMessage().toString());
		} catch (ClassNotFoundException e) {
			System.out.println(e.getLocalizedMessage().toString());
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage().toString());
		}
		return pedidos;
	}
	/**
	 * Recorre los archivos txt y va guardando los datos en una lista
	 * @param f
	 * @return lista con los objetos recogidos de los archivos de texto
	 */
	public static ArrayList<Pedido> pedidoTxt(File f){
		String str;
		ArrayList<Pedido> pedidos= new ArrayList<Pedido>();
		String formatoFecha="yyyy-MM-dd";
		SimpleDateFormat dateFormat= new SimpleDateFormat(formatoFecha);
		try (BufferedReader br= new BufferedReader(new FileReader(f))){
			
			while((str= br.readLine())!=null) {
					String[] parts= str.split("; ");
					int id = Integer.parseInt(parts[0].split(": ")[1]);
					int idC = Integer.parseInt(parts[1].split(": ")[1]);
					int idE = Integer.parseInt(parts[2].split(": ")[1]);
	                Date fecha =dateFormat.parse((parts[3].split(": ")[1]));
	                double precioFinal = Double.parseDouble(parts[4].split(": ")[1]);

	                Pedido pedido = new Pedido(id, idC, idE, fecha, precioFinal);

				pedidos.add(pedido);
			}
			
		} catch (FileNotFoundException e) {
			System.out.println(e.getLocalizedMessage().toString());
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage().toString());
		} catch (ParseException e) {
			System.out.println(e.getLocalizedMessage().toString());
		}
		return pedidos;
	}
	/**
	 * Recorre los archivos xml y guarda los datos en una lista
	 * @param f
	 * @return la lista son los objetos encontrados en los xml
	 */
	public static List<Pedido> pedidoXML(File f){
		return SAX.crearParseadorPedido(f);

	}
}
