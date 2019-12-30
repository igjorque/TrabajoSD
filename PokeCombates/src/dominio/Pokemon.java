package dominio;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class Pokemon implements Serializable {

	private String nombre;
	private Tipo tipo;
	
	private int ps; //vida
	private int ataque;
	private int defensa;
	private int velocidad;
	
	private boolean debilitado;
	
	private List<Movimiento> movimientos;
	
	// Constructor del objeto Pokemon. Inicia el atributo debilitado como FALSE.
	public Pokemon(String nombre, Tipo lins, int ps, int ataque, int defensa, int velocidad, List<Movimiento> movimientos) {
		this.nombre = nombre;
		this.tipo = lins;
		this.ps = ps;
		this.ataque = ataque;
		this.defensa = defensa;
		this.velocidad = velocidad;
		this.movimientos = movimientos;
		
		this.debilitado = false;
	}
	
	// Obtiene el nombre del pokémon.
	public String getNombre() {
		return nombre;
	}
	
	// Obtiene el tipo del pokémon.
	public Tipo getTipo() {
		return tipo;
	}

	// Obtiene los ps del pokémon.
	public int getPs() {
		return ps;
	}
	
	// Establece los ps del pokémon.
	public void setPs(int ps) {
		this.ps = ps;
	}

	// Obtiene el ataque del pokémon.
	public int getAtaque() {
		return ataque;
	}

	// Obtiene la defensa del pokémon. 
	public int getDefensa() {
		return defensa;
	}

	// Obtiene la velocidad del pokémon.
	public int getVelocidad() {
		return velocidad;
	}
	
	// Obtiene un booleano si el pokemon está debilitado o no.
	public boolean getDebilitado() {
		return this.debilitado;
	}
	
	// Establece el atributo debilitado.
	public void setDebilitado(boolean deb) {
		this.debilitado = true;
	}

	// Obtiene el listado de movimientos del pokémon.
	public List<Movimiento> getMovimientos() {
		return movimientos;
	}

	// Establece el listado de movimientos del pokémon.
	public void setMovimientos(List<Movimiento> movimientos) {
		this.movimientos = movimientos;
	}
	
	@Override
	public String toString() {
		return "Nombre: " + this.nombre + " Tipo: " + this.tipo + 
				"\r\n     PS: " + this.ps + " Ataque: " + this.ataque + " Defensa: " + this.defensa + " Velocidad: " + this.velocidad + "\r\n" +
				"Movimientos:\r\n" + this.movimientos.toString() + "\r\n";
	}
}
