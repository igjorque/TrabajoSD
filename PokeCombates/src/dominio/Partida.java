package dominio;

import negocio.NegocioPokemon;

public class Partida {
	
	private Jugador yo;
	private Jugador rival;
	private TablaTipos tabla;
	
	public Partida (Jugador j1, Jugador j2, TablaTipos tabla) {
		this.yo = j1;
		this.rival = j2;
		this.tabla = tabla;
	}
	
	

}
