package sujetos;

import java.io.Serializable;
/**
 * Clase Cliente con los getters y setter, los constructores y el metodo toString. Implementa Serializable
 * @author super
 *
 */
public class Cliente implements Serializable{
	private int idCliente;
	private String nombre;
	private String apellidos;
	private String tipoCliente;
	private int nCompras;

	public String getTipoCliente() {
		return tipoCliente;
	}
	public void setTipoCliente(String tipoCliente) {
		this.tipoCliente = tipoCliente;
	}
	public int getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public int getnCompras() {
		return nCompras;
	}
	public void setnCompras(int nCompras) {
		this.nCompras = nCompras;
	}
	public Cliente(int idCliente, String nombre, String apellidos, String tipoCliente, int nCompras) {

		this.idCliente = idCliente;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.tipoCliente = tipoCliente;
		this.nCompras = nCompras;
	}
	public Cliente() {

	}
	@Override
	public String toString() {
		return "Cliente: <"+ idCliente+">, <"+nombre+">, <"+apellidos+">, <"+tipoCliente+">, <"+nCompras+">\n";
	}

}
