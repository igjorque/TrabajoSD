package dominio;

public class Movimiento {
	
	private String nombre;
	private Tipo tipo;
	
	private int PP; //Número de usos del movimiento
	private int potencia;
	private int precision;
	private int prioridad;
	
	public Movimiento (String nombre, Tipo tipo, int pp, int potencia, int precision, int prioridad) {
		this.nombre = nombre;
		this.tipo = tipo;
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
	
	public int getPP() {
		return this.PP;
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
}
