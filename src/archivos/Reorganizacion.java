package archivos;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Scanner;

import registros.Registro;
/**
 * Esta clase recoge los metodos para la reorganizacion inicial de los archivos
 * 
 * @author super
 *
 */
public class Reorganizacion {
	/**
	 * 
	 * Este metodo se encarga de pedirle al usuario la ruta para la nueva carpeta 
	 * Para la futura reogranizacion de los archivos
	 * 
	 * @return devuelve la ruta en forma de String incidicada por el usuario 
	 */
	public static String carpetaReubicacion() {
        Scanner sc = new Scanner(System.in);
        String ruta;
        do {
            System.out.println("Introduce la ruta para la reubicación de los archivos: ");
            ruta = sc.nextLine();
            if (ruta.isEmpty()) {
                System.out.println("Debes introducir una ruta.");
            }
        } while (ruta.isEmpty());
       // sc.close();
        return ruta;
    }
/**
 * Este metodo se encarga de crear las carpetas en la ruta indicada por el usuario 
 * que recibe como parametro y llama a la funcion analisisCarpeta pasando la ruta 
 * nueva de los archivos y la ruta antigua delos archivos 
 * 
 * @param ruta
 * @param rutaAntigua
 */
    public static void crearCarpetas(String ruta, String rutaAntigua) {
        String[] nombresCarpetas = {"clientes", "empleados", "sucursales", "pedidos", "detalleDeLosPedidos", "productos", "proveedores"};
        for (String nombreCarpeta : nombresCarpetas) {
            File f = new File(ruta + "\\" + nombreCarpeta);
            if (!f.exists()) {
                f.mkdirs();
            }
        }
        
        analisisCarpeta(ruta, rutaAntigua);
    }
/**
 * Esta funcion se encarga de crear una lista de File y analiza la carpeta antigua con los archivos
 * y va metiendo los archivos convenientes en el array, luego llama a la funcion coparArchivos pasando
 * como parameros la ruta nueva de los archivos y la lista con los archivos
 * 
 * @param ruta
 * @param rutaAntigua
 */
    public static void analisisCarpeta(String ruta, String rutaAntigua) {
        ArrayList<File> files = new ArrayList<>();
        File f = new File(rutaAntigua);
        if (f.exists()) {
            File[] archivos = f.listFiles();
            for (File archivo : archivos) {
                if (archivo.isDirectory()) {
                    analisisCarpeta(ruta, archivo.getAbsolutePath()); //Es un metodo rtcursivo en el caso de que se encuentre un directorio se vuelve a llamar a si mismo para analizar dicho directorio
                } else if (archivo.isFile()) {
                    if (AnalisisArchivos.tipoArchivo(archivo)) {
                        files.add(archivo); // en el caso de ser un archivo llama a la funcion tipoArchivo para comporbar si es un archivo con la extension necesaria y si lo es, lo mete en la lista, en caso contrario no hace nada
                    }
                }
            }
            copiaArchivos(files, ruta);
        }
    }

    /**
     * Este metodo se encarga de hacer la copia de los archivos ya guardados en la lista a sus respectivas carpetas
     * 
     * @param files lista con los archivos necesarios 
     * @param ruta la ruta nueva
     */
    public static void copiaArchivos(ArrayList<File> files, String ruta) {
    	
    	// Creo 2 arrays, con los nombres de las carpetas y otro con el nombre que deben contener los archivos para poder copiarlos en las carpetas necesarias
        String[] nombresCarpetas = {"clientes", "empleados", "sucursales", "productos", "proveedores"};
        String[] nombresArchivos = {"cliente", "empleado", "sucursal", "producto", "proveedor"};

        File registroCopiaFicheros = new File(ruta + "\\registroCopiaFicheros.txt"); // creo el archivo para registrar la copia de los ficheros
        registroCopiaFicheros.getParentFile().mkdirs();

        for (File file : files) {
            String fileName = file.getName().toLowerCase();
            String destinationPath = ruta;
            String folder = "";
            // En esta parte del codigo voy comporbando si el archivo contiene la palabra necesaria y dependiendo de esa palabra, creo la nueva ruta para ese archivo dentro de la carpeta necesaria
            for (String nombreArchivo : nombresArchivos) {
                if (fileName.contains(nombreArchivo)) {
                    for (int i = 0; i < nombresCarpetas.length; i++) {
                        if (nombresCarpetas[i].contains(nombreArchivo)) {
                            folder = nombresCarpetas[i];
                        }
                    }
                    destinationPath += "\\" + folder + "\\" + file.getName();
                    break;
                }
            }

            if (fileName.contains("pedido")) {
                destinationPath += fileName.contains("detalle") ? "\\detalleDeLosPedidos" : "\\pedidos"; // en el caso de detalles de los pedidos, lo compurebo aparte, si contiene pedido, busco si tambien contieen detalle y lo añado a la ruta
            }

            File destinationFile = new File(destinationPath); // la ruta nueva del archivo
            Path rutaOrigen = Paths.get(file.getAbsolutePath());

            try {
                Path rutaDestino;
                if (destinationFile.exists()) {
                    String r = cambioDelNombre(destinationFile); // si la ruta existe, es decir, ya existe un archivo con el mismo nombre llamo a la funcion cambioDelNombre, para cambiar el nombre al archivo para poder realizar la copia
                    rutaDestino = Paths.get(r);
                } else {
                    rutaDestino = Paths.get(destinationFile.getAbsolutePath());
                }

                Files.copy(rutaOrigen, rutaDestino, StandardCopyOption.REPLACE_EXISTING); // Realizo finalemte la copia, con la opcion de reemplazar existente, que nunca se va a ejecutar ya que el nombre del archivo fue cambiado 
                Registro.registroCopia(registroCopiaFicheros.getAbsolutePath(), destinationFile, rutaOrigen, rutaDestino); //Llamo a la funcion reistroCopia para registrar la copia del archivo en el txt
            } catch (IOException e) {
               System.out.println(e.getLocalizedMessage().toString());
            }
        }
        Registro.registroBorrado(Paths.get(ruta)); //Llamo a la funcion registro borrado para realizar el borrado de los ficheros con el mismo contenido despues de haberlos copiado todos
    }
    
    /**
     * Esta funcion se encarga de cambiar el nombre al archivo si ya existe, añadiendo un _ y un numero
     * 
     * @param rutaExistente
     * @return Devuelve la ruta con el nombre nuevo no existente
     */
    public static String cambioDelNombre(File rutaExistente) {
        String nombreArchivo = rutaExistente.getName();
        String nombreSinExtension = nombreArchivo.substring(0, nombreArchivo.lastIndexOf('.'));
        String extension = nombreArchivo.substring(nombreArchivo.lastIndexOf('.'));
        int count = 1;
        Path ruta = rutaExistente.toPath();

        while (Files.exists(ruta)) {
            String nombreNuevo = rutaExistente.getParent()+"\\" + nombreSinExtension + '_' + count + extension;
            ruta = Paths.get(nombreNuevo);
            count++;
        }
        return ruta.toString();
    }
}
