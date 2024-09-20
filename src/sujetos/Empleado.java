package sujetos;

import java.io.Serializable;

/**
 * Clase Empleado con los getters y setter, los constructores y el metodo toString. Implementa Serializable
 * @author super
 *
 */
public class Empleado implements Serializable{
	private int idEmpleado;
	private String nombre;
	private String apellidos;
	private int idSucursalEmpleado;
	private int categoria;
	private int antiguedad;
	private double salario;
	public int getIdEmpleado() {
		return idEmpleado;
	}
	public void setIdEmpleado(int idEmpleado) {
		this.idEmpleado = idEmpleado;
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
	public int getIdSucursalEmpleado() {
		return idSucursalEmpleado;
	}
	public void setIdSucursalEmpleado(int idSucursalEmpleado) {
		this.idSucursalEmpleado = idSucursalEmpleado;
	}
	public int getCategoria() {
		return categoria;
	}
	public void setCategoria(int categoria) {
		this.categoria = categoria;
	}
	public int getAntiguedad() {
		return antiguedad;
	}
	public void setAntiguedad(int antiguedad) {
		this.antiguedad = antiguedad;
	}
	public double getSalario() {
		return salario;
	}
	public void setSalario(double salario) {
		this.salario = salario;
	}
	public Empleado(int idEmpleado, String nombre, String apellidos, int idSucursalEmpleado, int categoria,
			int antiguedad, double salario) {
		this.idEmpleado = idEmpleado;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.idSucursalEmpleado = idSucursalEmpleado;
		this.categoria = categoria;
		this.antiguedad = antiguedad;
		this.salario = salario;
	}
	public Empleado() {

	}
	@Override
	public String toString() {
		return "Empleado: <"+ idEmpleado +">, <"+ nombre + ">, <" + apellidos
				+ ">, <" + idSucursalEmpleado + "v" + categoria + ">, <"
				+ antiguedad + ">, <" + salario + ">\n";
	}
	
	
}
