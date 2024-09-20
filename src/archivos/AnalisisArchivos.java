package archivos;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;

/**
 * Esta es la clase para los métodos para realizar distintos análisis de los archvios
 * 
 * @author super
 *
 */
public class AnalisisArchivos {
	/**
	 * El método bytesDelArchivo recibe un archivo f de tipo File, y crea un array de bytes con la longitud del archivo
	 * Luego utilizo el FileInputStream para leer el contenido del archivo en el array de bytes y lo devuelvo 
	 * 
	 * @param f el archivo de tipo File que se pasa como paramentro para leer su cotenido en un array de bytes
	 * @return b el array de bytes con el contenido del archivo 
	 */
	public static byte[] bytesDelArchivo(File f) {
		long length= f.length();
		byte[] b= new byte[(int)length];
		FileInputStream is= null;
		try {
			is= new FileInputStream(f);
			is.read(b);
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage().toString());
		} finally {
			if(is!=null) {
				try {
					is.close();
				} catch (IOException e) {
					System.out.println(e.getLocalizedMessage().toString());
				}
			}
		}
		
		return b;
	}
	
	/**
	 * Esta funcion compara el contenido de 2 archivos File que se pasan como parametros
	 * 
	 * @param f1 
	 * @param f2
	 * @return devuelve false si los contenidos son diferentes y true si son iguales
	 */
	public static boolean comparacionContenido(File f1, File f2) {
		byte[] b1= bytesDelArchivo(f1);
		byte[] b2= bytesDelArchivo(f2);
		
		if(b1.length!=b2.length) return false; //comparo la longitud de los archivos, si es diferente, devuelve false
		
		for(int i=0; i<b1.length;i++) {
			if(b1[i]!=b2[i]) return false; //comparo byte a byte el contenido
		}
		
		return true;
	}
/**
 * Recibe 2 archivos como parametros y compara sus fechas de creacion
 * 
 * @param f1
 * @param f2
 * @return devuelve el archivo mas antiguo 
 */
	public static File compararFechas(File f1, File f2) {
		FileTime t1;
		FileTime t2;
		try {
			t1 = (FileTime) Files.getAttribute(Paths.get(f1.getAbsolutePath()), "creationTime");
			t2 = (FileTime) Files.getAttribute(Paths.get(f2.getAbsolutePath()), "creationTime");
			if(t1.compareTo(t2)<0) return f1;
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage().toString());
		}
		return f2;
	}
	/**
	 * Recibe un archivo file y comprueba si su extension es necesaria para nosotros, es decir
	 * si es un archivo .dat, .xml o .txt
	 * 
	 * @param f
	 * @return Devuelve true si es un archivo con la extension necesaria y false si no lo es
	 */
	public static boolean tipoArchivo(File f) {
		if(f.getName().endsWith("dat") || f.getName().endsWith("xml") || f.getName().endsWith("txt")) return true;
		return false;
	}
	/**
	 * Recibe un archivo y devuelve la extension 
	 * 
	 * @param f
	 * @return devuelve un string con la extension del archivo 
	 */
	public static String extension(File f) {
		return f.getName().toLowerCase().substring(f.getName().lastIndexOf("."));
	}
}
