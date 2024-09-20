package sujetos;

import java.io.Serializable;
import java.util.Date;

/**
 * Clase Pedido con los getters y setter, los constructores y el metodo toString. Implementa Serializable
 * @author super
 *
 */
public class Pedido implements Serializable{
	private int idPedido;
	private int idCliente;
	private int idEmpleado;
	private Date fecha;
	private double precioFinal;
	public int getIdPedido() {
		return idPedido;
	}
	public void setIdPedido(int idPedido) {
		this.idPedido = idPedido;
	}
	public int getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}
	public int getIdEmpleado() {
		return idEmpleado;
	}
	public void setIdEmpleado(int idEmpleado) {
		this.idEmpleado = idEmpleado;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public double getPrecioFinal() {
		return precioFinal;
	}
	public void setPrecioFinal(double precioFinal) {
		this.precioFinal = precioFinal;
	}
	public Pedido(int idPedido, int idCliente, int idEmpleado, Date fecha, double precioFinal) {
		this.idPedido = idPedido;
		this.idCliente = idCliente;
		this.idEmpleado = idEmpleado;
		this.fecha = fecha;
		this.precioFinal = precioFinal;
	}
	public Pedido() {

	}
	@Override
	public String toString() {
		return "Pedido: <"+ idPedido + ">, <" + idCliente + ">, <" + idEmpleado + ">, <"
				+ fecha + ">, <" + precioFinal + ">\n";
	}
	
}
