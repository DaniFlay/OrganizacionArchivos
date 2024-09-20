package registros;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import archivos.AnalisisArchivos;


/**
 * Clase con los metodos para los registros en los txt
 * @author super
 *
 */
public class Registro {
	/**
	 * Esta funcion registra las copias de los archivos que se han copiado de la carpeta antigua a la nueva
	 * @param rutaArchivoRegistro
	 * @param archivo
	 * @param rutaOrigen
	 * @param rutaDestino
	 */
    public static void registroCopia(String rutaArchivoRegistro, File archivo, Path rutaOrigen, Path rutaDestino) {
        try (FileWriter fw = new FileWriter(rutaArchivoRegistro, true)) {
            fw.write(archivo.getName() + " copiado de " + rutaOrigen.toString() + " a " + rutaDestino.toString() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Realiza el registro de los archivos que se borran por tener el mismo contenido
     * @param rutaDestino
     * @return
     */
    public static boolean registroBorrado(Path rutaDestino) {
        String nombreArchivo = "registroBorradoFicherosIguales.txt";
        File f = new File(rutaDestino + "\\" + nombreArchivo);
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                System.out.println("No ha sido posible crear el archivo: " + nombreArchivo);
            }
        }

        try (FileWriter fw = new FileWriter(f, true)) {
            File[] carpetas = rutaDestino.toFile().listFiles(File::isDirectory);
            Set<File> archivosAborrar = new HashSet<>();
            File archivo_a_borrar,dummy;
            for (File carpeta : carpetas) {
                File[] archivos = carpeta.listFiles();
                for (int i = 0; i < archivos.length - 1; i++) {
                	for (int j=i+1;j<archivos.length;j++) {
                    if (AnalisisArchivos.comparacionContenido(archivos[i], archivos[j])) {
                        archivo_a_borrar = AnalisisArchivos.compararFechas(archivos[i], archivos[j]);
                        archivosAborrar.add(archivo_a_borrar);
                    }
                }
            }
               
            }
            Iterator<File> iterator = archivosAborrar.iterator();
            while (iterator.hasNext()) {
                dummy = iterator.next();
                fw.write(dummy.getName() + " eliminado de la ruta " + dummy.getAbsolutePath() + " porque ya existe un archivo con el mismo contenido\n");
                dummy.delete();
            }
        } catch (IOException e) {
            System.out.println("No ha sido posible escribir en el archivo: " + nombreArchivo);
        }
        return true;
    }
    /**
     * Registra todos los descartes del usuario durante la lectura de los xml
     * @param f
     */
    public static void registroDescartes(File f){
        String rutaDoc= f.getParentFile().getParent();
        File doc= new File(rutaDoc+"//descartados.txt");
        if(!doc.exists()){
            try {
                doc.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try(FileWriter fw= new FileWriter(doc,true)){
            fw.write("Un objeto ha sido descartado del archivo "+f.getName()+"\n");
        }catch(IOException e){
            System.out.println("No se ha podido escribir en el archivo descartados.txt");
        }
    }
    /**
     * Realiza el registro de los duplicados descartados a la hora de inserccion en la base de datos
     * @param f
     * @param registro
     */
    public static void registroDuplicados(File f,String registro) {
    	File f1= new File(f.getAbsoluteFile()+"//duplicadosDescartadosBBDD.txt");
    	if(!f1.exists()) {
    		try {
				f1.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	try(FileWriter fw= new FileWriter(f1,true)){
    		fw.write(registro);
    	} catch (IOException e) {
			
			e.printStackTrace();
		}
    }
    /**
     * Realiza el registro de todas las excepciones y errores de SQL
     * @param f
     * @param m
     */
    public static void registroMySQL(File f,SQLException m) {
    	File f1= new File(f.getAbsoluteFile()+"//erroresSQL.txt");
    	if(!f1.exists()) {
    		try {
				f1.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	try(FileWriter fw=new FileWriter(f1,true)){
    		fw.write(m.toString()+"\n");
    	} catch (IOException e) {	
			e.printStackTrace();
		}
    }
}
