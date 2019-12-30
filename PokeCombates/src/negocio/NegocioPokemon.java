package negocio;

public class NegocioPokemon {
	
	private ServiciosEquipo se;
	private ServiciosPokemon sp;
	
	// Constructor del "objeto" negocio. Inicializa los servicios de equipo y pokémon.
	public NegocioPokemon() {
		se = new ServiciosEquipo();
		sp = new ServiciosPokemon();
	}
	
	// Obtiene los servicios de equipo.
	public ServiciosEquipo getServiciosEquipo() {
		return this.se;
	}
	
	// Obtiene los servicios de pokémon.
	public ServiciosPokemon getServiciosPokemon() {
		return this.sp;
	}
	
}
