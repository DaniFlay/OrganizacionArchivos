package sujetos;

import java.io.Serializable;
/**
 * Clase Sucursal con los getters y setter, los constructores y el metodo toString. Implementa Serializable
 * @author super
 *
 */
public class Sucursal implements Serializable{
	private int idSucursal;
	private String provincia;
	private String localidad;
	private String direccion;
	private String telefono;
	private int idDirectorSucursal;
	public int getIdSucursal() {
		return idSucursal;
	}
	public void setIdSucursal(int idSucursal) {
		this.idSucursal = idSucursal;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getLocalidad() {
		return localidad;
	}
	public void setLocalidad(String localidad) {
		this.localidad = localidad;
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
	public int getIdDirectorSucursal() {
		return idDirectorSucursal;
	}
	public void setIdDirectorSucursal(int idDirectorSucursal) {
		this.idDirectorSucursal = idDirectorSucursal;
	}
	public Sucursal(int idSucursal, String provincia, String localidad, String direccion, String telefono,
			int idDirectorSucursal) {
		this.idSucursal = idSucursal;
		this.provincia = provincia;
		this.localidad = localidad;
		this.direccion = direccion;
		this.telefono = telefono;
		this.idDirectorSucursal = idDirectorSucursal;
	}
	public Sucursal() {

	}
	@Override
	public String toString() {
		return "Sucursal: <" + idSucursal + ">, <" + provincia + ">, <" + localidad
				+ ">, <" + direccion + ">, <" + telefono + ">, <" + idDirectorSucursal
				+ ">\n";
	}
	
}
