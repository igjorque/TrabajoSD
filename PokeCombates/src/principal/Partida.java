package dominio;

public class Partida {
	
	private Jugador yo;
	private Jugador rival;
	
	private TablaTipos tabla;
	
	public Partida (Jugador j1, Jugador j2, TablaTipos tabla) {
		this.yo = j1;
		this.rival = j2;
		this.tabla = tabla;
	}
	
	public boolean atacar(Movimiento m) {
		m.gastarPP();
		
		Pokemon pokemonPropio = this.yo.getSeleccionado();
		Pokemon pokemonRival = this.rival.getSeleccionado();
		
		int probabilidadAtaque = (int) (Math.random() * 100);
		
		if (m.getPrecision() > probabilidadAtaque) {
			float bonificacion = 1;
			float efectividad;
			int variacion;
			
			float ataque;
			
			if (pokemonPropio.getTipo() == m.getTipo()) {
				bonificacion = 1.5f;
			}
			
			efectividad = tabla.buscarEficacia(m.getTipo(), pokemonRival.getTipo());
			
			variacion = (int) (Math.random() * (100-85)) + 85;
			
			ataque = 0.01f * bonificacion * efectividad * variacion * (((0.2f * 10 + 1) * 
					pokemonPropio.getAtaque() * m.getPotencia() / 25 * pokemonRival.getDefensa()) + 2);
			
			pokemonRival.recibirAtaque(ataque);
			return true;
		}
		else {
			return false;
		}
	}

}
