package sujetos;

import java.io.Serializable;

/**
 * Clase Producto con los getters y setter, los constructores y el metodo toString. Implementa Serializable
 * @author super
 *
 */
public class Producto implements Serializable{
	private int idProducto;
	private String tipoProducto;
	private double precioC;
	private int idProveedorProducto;
	private double precioV;
	private int rotacion;
	public int getIdProducto() {
		return idProducto;
	}
	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}
	public String getTipoProducto() {
		return tipoProducto;
	}
	public void setTipoProducto(String tipoProducto) {
		this.tipoProducto = tipoProducto;
	}
	public double getPrecioC() {
		return precioC;
	}
	public void setPrecioC(double precioC) {
		this.precioC = precioC;
	}
	public int getIdProveedorProducto() {
		return idProveedorProducto;
	}
	public void setIdProveedorProducto(int idProveedorProducto) {
		this.idProveedorProducto = idProveedorProducto;
	}
	public double getPrecioV() {
		return precioV;
	}
	public void setPrecioV(double precioV) {
		this.precioV = precioV;
	}
	public int getRotacion() {
		return rotacion;
	}
	public void setRotacion(int rotacion) {
		this.rotacion = rotacion;
	}
	public Producto(int idProducto, String tipoProducto, double precioC, int idProveedorProducto, double precioV,
			int rotacion) {
		this.idProducto = idProducto;
		this.tipoProducto = tipoProducto;
		this.precioC = precioC;
		this.idProveedorProducto = idProveedorProducto;
		this.precioV = precioV;
		this.rotacion = rotacion;
	}
	public Producto() {

	}
	@Override
	public String toString() {
		return "Producto: <" + idProducto + ">, <" + tipoProducto + ">, <" + precioC
				+ ">, <" + idProveedorProducto + ">, <" + precioV + ">, <" + rotacion
				+ ">\n";
	}
	
}
