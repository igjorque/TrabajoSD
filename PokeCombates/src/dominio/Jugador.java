package dominio;

public class Jugador {
	
	private String nombre;
	private Equipo equipoPokemon;
	private Pokemon seleccionado;
	
	public Jugador(String nombre, Equipo equipo) {
		this.nombre = nombre;
		this.equipoPokemon = equipo;
		this.seleccionado = equipo.getListaPokemon().get(0);
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
	public Equipo getEquipoPokemon() {
		return this.equipoPokemon;
	}
	
	public void setSeleccionado(Pokemon p) {
		this.seleccionado = p;
	}
	
	public Pokemon getSeleccionado() {
		return this.seleccionado;
	}
	
	
}
