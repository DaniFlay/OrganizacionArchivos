package sujetos;

import java.io.Serializable;

/**
 * Clase DetallePedido con los getters y setter, los constructores y el metodo toString. Implementa Serializable
 * @author super
 *
 */
public class DetallePedido implements Serializable{
	private int idPedido;
	private int idProducto;
	private int idProveedorProducto;
	private double precioUnidad;
	private int cantidad;
	private double descuento;
	private double precioFinal;
	public int getIdPedido() {
		return idPedido;
	}
	public void setIdPedido(int idPedido) {
		this.idPedido = idPedido;
	}
	public int getIdProducto() {
		return idProducto;
	}
	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}
	public int getIdProveedorProducto() {
		return idProveedorProducto;
	}
	public void setIdProveedorProducto(int idProveedorProducto) {
		this.idProveedorProducto = idProveedorProducto;
	}
	public double getPrecioUnidad() {
		return precioUnidad;
	}
	public void setPrecioUnidad(double precioUnidad) {
		this.precioUnidad = precioUnidad;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public double getDescuento() {
		return descuento;
	}
	public void setDescuento(double descuento) {
		this.descuento = descuento;
	}
	public double getPrecioFinal() {
		return precioFinal;
	}
	public void setPrecioFinal(double precioFInal) {
		this.precioFinal = precioFInal;
	}
	public DetallePedido(int idPedido, int idProducto, int idProveedorProducto, double precioUnidad, int cantidad,
			double descuento, double precioFinal) {
		this.idPedido = idPedido;
		this.idProducto = idProducto;
		this.idProveedorProducto = idProveedorProducto;
		this.precioUnidad = precioUnidad;
		this.cantidad = cantidad;
		this.descuento = descuento;
		this.precioFinal = precioFinal;
	}
	public DetallePedido() {

	}
	@Override
	public String toString() {
		return "DetallePedido: <" + idPedido + ">, <" + idProducto + ">, <"
				+ idProveedorProducto + ">, <"+ precioUnidad + ">, <" + cantidad + ">, <"
				+ descuento + ">, <" + precioFinal + ">\n";
	}
	
}
