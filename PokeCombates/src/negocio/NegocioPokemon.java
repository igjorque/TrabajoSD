package negocio;

public class NegocioPokemon {
	
	private ServiciosEquipo se;
	private ServiciosPokemon sp;
	
	public NegocioPokemon() {
		se = new ServiciosEquipo();
		sp = new ServiciosPokemon();
	}
	
	public ServiciosEquipo getServiciosEquipo() {
		return this.se;
	}
	
	public ServiciosPokemon getServiciosPokemon() {
		return this.sp;
	}
	
}
