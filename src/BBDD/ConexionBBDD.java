package BBDD;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import registros.Registro;

/**
 * Clase para crear un metodo para realizar la conexion con la base de datos
 * @author super
 *
 */
public class ConexionBBDD{
	/**
	 * Realiza la conexion con la base de datos
	 * 
	 * @param f
	 * @return el elemento Connection
	 */
    public static Connection conexion(File f){
    	Connection c=null;
        String database="practica_acceso_a_datos";
        String host="localhost";
        String port= "3306";
        String urlConnection= "jdbc:mysql://"+host+":"+port+"/"+database;
        String user= "root";
        String pwd= "Ds120621";
        try{
            c= DriverManager.getConnection(urlConnection,user,pwd);
        }catch(SQLException e){
            Registro.registroMySQL(f, e); // Lllama a esta funcion para escribir en un archivo txt de la excepcion sql en el caso de que aparezca
            
        }
        return c;
    }
}