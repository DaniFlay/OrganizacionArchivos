package sujetos;

import java.io.Serializable;

/**
 * Clase Proveedor con los getters y setter, los constructores y el metodo toString. Implementa Serializable
 * @author super
 *
 */
public class Proveedor implements Serializable{
	private int idProveedor;
	private String direccion;
	private String telefono;
	private String tipoP;
	private int incidencias;
	public int getIdProveedor() {
		return idProveedor;
	}
	public void setIdProveedor(int idProveedor) {
		this.idProveedor = idProveedor;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getTipoP() {
		return tipoP;
	}
	public void setTipoP(String tipoP) {
		this.tipoP = tipoP;
	}

	
	public int getIncidencias() {
		return incidencias;
	}
	public void setIncidencias(int incidencias) {
		this.incidencias = incidencias;
	}
	public Proveedor(int idProveedor, String direccion, String telefono, String tipoP, int incidencias) {
		this.idProveedor = idProveedor;
		this.direccion = direccion;
		this.telefono = telefono;
		this.tipoP = tipoP;
		this.incidencias = incidencias;
	}
	public Proveedor() {

	}
	@Override
	public String toString() {
		return "Proveedor: <" + idProveedor + ">, <" + direccion + "v" + telefono
				+ ">, <" + tipoP + ">, <" + incidencias + ">\n";
	}
	
}
