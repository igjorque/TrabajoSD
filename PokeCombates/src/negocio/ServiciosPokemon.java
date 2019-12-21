package negocio;

import dominio.Movimiento;
import dominio.Pokemon;
import dominio.TablaTipos;

public class ServiciosPokemon {
	
	// "Acción" de atacar. Ambos pokémon están combatiendo. Solo puede obtener un movimiento que no se haya gastado (que tenga PPs).
	// Devuelve un booleano que indica si el ataque ha tenido exito (TRUE) o no (FALSE).
	public boolean atacar(Pokemon pokemonPropio, Pokemon pokemonRival, TablaTipos tabla, Movimiento m) {
		m.setPP(m.getPP()-1);
		
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
			
			recibirAtaque(pokemonRival, ataque);
			
			return true;
		}
		else {
			return false;
		}
	}
	
	private void recibirAtaque(Pokemon poke, float danno) {
		poke.setPs((int) (poke.getPs() - danno));
		if (poke.getPs() <= 0) {
			poke.setDebilitado(true);
		}
	}
	
	public String mostrarDatos (Pokemon p) {
		return p.getNombre() + " - Tipo: " + p.getTipo() + "\r\nEstadísticas:\r\n" +
				"	PS: " + p.getPs() + "\r\n	Ataque: " + p.getAtaque() + "\r\n	Defensa: " +
				p.getDefensa() + "\r\n	Velocidad: " + p.getVelocidad() + "\r\nMovimientos: " +
				p.getMovimientos().toString();
	}
	
	public String mostrarMovimiento (Movimiento m) {
		return m.getNombre() + " - Tipo: " + m.getTipo() + "\r\nEstadísticas:\r\n" + 
				"	PP: " + m.getPP() + "\r\n	Potencia: " + m.getPotencia() + "\r\n	Precisión: " +
				m.getPrecision() + "\r\n	Prioridad: " + m.getPrioridad();
	}
}
