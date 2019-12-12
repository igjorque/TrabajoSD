package dominio;

import java.util.List;

public class Pokemon {

	private String nombre;
	private Tipo tipo;
	
	private int ps; //vida
	private int ataque;
	private int defensa;
	private int velocidad;
	
	private boolean debilitado;
	
	private List<Movimiento> movimientos;
	
	public Pokemon(String nombre, Tipo lins, int ps, int ataque, int defensa, int velocidad, List<Movimiento> movimientos) {
		this.nombre = nombre;
		this.tipo = lins;
		this.ps = ps;
		this.ataque = ataque;
		this.defensa = defensa;
		this.velocidad = velocidad;
		this.debilitado = false;
		this.movimientos = movimientos;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public Tipo getTipo() {
		return tipo;
	}

	public int getPs() {
		return ps;
	}
	
	public void setPs(int ps) {
		this.ps = ps;
	}

	public int getAtaque() {
		return ataque;
	}

	public int getDefensa() {
		return defensa;
	}

	public int getVelocidad() {
		return velocidad;
	}
	
	public boolean getDebilitado() {
		return this.debilitado;
	}
	
	public void setDebilitado(boolean deb) {
		this.debilitado = true;
	}

	public List<Movimiento> getMovimientos() {
		return movimientos;
	}

	public void setMovimientos(List<Movimiento> movimientos) {
		this.movimientos = movimientos;
	}
}
