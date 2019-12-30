package dominio;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Jugador implements Serializable {
	
	private String nombre;
	private Equipo equipoPokemon;
	private Pokemon seleccionado;
	
	// Constructor del objeto Jugador. Inicia los atributos nombre y equipo, y establece el atributo seleccionado como el primer elemento del equipo.
	public Jugador(String nombre, Equipo equipo) {
		this.nombre = nombre;
		this.equipoPokemon = equipo;
		this.seleccionado = equipo.getListaPokemon().get(0);
	}
	
	// Obtiene el nombre del jugador.
	public String getNombre() {
		return this.nombre;
	}
	
	// Obtiene el equipo del jugador.
	public Equipo getEquipoPokemon() {
		return this.equipoPokemon;
	}
	
	// Establece el pokémon seleccionado del jugador.
	public void setSeleccionado(Pokemon p) {
		this.seleccionado = p;
	}
	
	// Obtiene el pokémon seleccionado del jugador.
	public Pokemon getSeleccionado() {
		return this.seleccionado;
	}
	
	
}
