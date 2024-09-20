package main;
import java.io.File;

import BBDD.ConexionBBDD;
import BBDD.Consultas;
import archivos.Reorganizacion;
import recogidaDatos.RepartoArchivos;
/**
 * La clase para crear la fucnion main
 * @author super
 *
 */
public class Practica_1ev {
	
	
	/**
	 * Main que ejecuta algunos metodos para la funcionalidad del programa
	 * @param args
	 */
    public static void main(String[] args) {
        String rutaCarpetaCompartida = "C:\\Users\\super\\OneDrive\\Escritorio\\AnimeDownload";
        String rutaNueva=Reorganizacion.carpetaReubicacion();
        Reorganizacion.crearCarpetas(rutaNueva, rutaCarpetaCompartida);
        RepartoArchivos.LeerDirectorios(new File(rutaNueva));
        Consultas.menuConsultas( new File(rutaNueva));
    }  
}