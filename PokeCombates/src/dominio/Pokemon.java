package dominio;

public class Pokemon {

	private String nombre;
	private Tipo tipo;
	
	private int ps; //vida
	private int ataque;
	private int defensa;
	private int velocidad;
	
	public Pokemon(String nombre, Tipo tipo, int ps, int ataque, int defensa, int velocidad) {
		this.nombre = nombre;
		this.tipo = tipo;
		this.ps = ps;
		this.ataque = ataque;
		this.defensa = defensa;
		this.velocidad = velocidad;
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

	public int getAtaque() {
		return ataque;
	}

	public int getDefensa() {
		return defensa;
	}

	public int getVelocidad() {
		return velocidad;
	}
}
