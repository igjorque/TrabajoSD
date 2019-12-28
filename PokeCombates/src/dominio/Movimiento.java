package dominio;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Movimiento implements Serializable {
	
	private String nombre;
	private Tipo tipo;
	private String descripcion;
	
	private int PP; //Número de usos del movimiento
	private int potencia;
	private int precision;
	private int prioridad;
	
	public Movimiento (String nombre, Tipo tipo, String descripcion, int pp, int potencia, int precision, int prioridad) {
		this.nombre = nombre;
		this.tipo = tipo;
		this.descripcion = descripcion;
		this.PP = pp;
		this.potencia = potencia;
		this.precision = precision;
		this.prioridad = prioridad;
	}
	
	public String getNombre()
	{
		return this.nombre;
	}
	
	public Tipo getTipo() {
		return this.tipo;
	}
	
	public String getDescripcion() {
		return this.descripcion;
	}
	
	public int getPP() {
		return this.PP;
	}
	
	// Controla que solo se pueda gastar un punto, y que no pueda quedar la cantidad de movimientos en negativo.
	public void setPP(int pp) {
		if (pp == this.PP-1 && pp >= 0) {
			this.PP = pp;
		}
	}
	
	public int getPotencia() {
		return this.potencia;
	}
	
	public int getPrecision() {
		return this.precision;
	}
	
	public int getPrioridad() {
		return this.prioridad;
	}
	
	@Override
	public String toString() {
		return "Nombre: " + this.nombre + " Tipo: " + this.tipo + 
				"\r\n     PP: " + this.PP + " Potencia:" + this.potencia + 
				"\r\n     Precisión: " + this.precision + " Prioridad: " + this.prioridad + 
				"\r\n     Descripción: " + this.descripcion + 
				"\r\n";
	}
}
