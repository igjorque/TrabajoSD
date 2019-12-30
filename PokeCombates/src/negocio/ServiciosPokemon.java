package negocio;

import java.util.ArrayList;

import dominio.Movimiento;
import dominio.Pokemon;
import dominio.TablaTipos;
import dominio.Tipo;

public class ServiciosPokemon {
	
	public Pokemon clonarPokemon(Pokemon p) {
		ArrayList<Movimiento> listAuxMov = new ArrayList<Movimiento>();
		listAuxMov.add(clonarMovimiento(p.getMovimientos().get(0)));
		listAuxMov.add(clonarMovimiento(p.getMovimientos().get(1)));
		listAuxMov.add(clonarMovimiento(p.getMovimientos().get(2)));
		listAuxMov.add(clonarMovimiento(p.getMovimientos().get(3)));
		
		return new Pokemon(p.getNombre(), p.getTipo(), p.getPs(), p.getAtaque(), p.getDefensa(), p.getVelocidad(), listAuxMov);
	}
	
	public Movimiento clonarMovimiento(Movimiento m) {
		return new Movimiento(m.getNombre(), m.getTipo(), m.getDescripcion(), m.getPP(), m.getPotencia(), m.getPrecision(), m.getPrioridad());
	}
	
	// "Acci�n" de atacar. Ambos pok�mon est�n combatiendo. Solo puede obtener un movimiento que no se haya gastado (que tenga PPs).
	// Devuelve un booleano que indica si el ataque ha tenido exito (TRUE) o no (FALSE).
	public float atacar(Pokemon pokemonPropio, Pokemon pokemonRival, TablaTipos tabla, Movimiento m) {
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
			
			ataque = 0.01f * bonificacion * efectividad * variacion * ((((0.2f * 10 + 1) * 
					pokemonPropio.getAtaque() * m.getPotencia()) / (25 * pokemonRival.getDefensa())) + 2);
			
			recibirAtaque(pokemonRival, ataque);
			
			return ataque;
		}
		
		else {
			return 0;
		}
	}
	
	private void recibirAtaque(Pokemon poke, float danno) {
		poke.setPs(poke.getPs() - (int) danno);
		if (poke.getPs() <= 0) {
			poke.setDebilitado(true);
		}
	}
	
	public String mostrarDatos (Pokemon p) {
		return p.getNombre() + " - Tipo: " + p.getTipo() + "\r\nEstad�sticas:\r\n" +
				"	PS: " + p.getPs() + "\r\n	Ataque: " + p.getAtaque() + "\r\n	Defensa: " +
				p.getDefensa() + "\r\n	Velocidad: " + p.getVelocidad() + "\r\nMovimientos: " +
				p.getMovimientos().toString() + "\r\n";
	}
	
	public String mostrarMovimiento (Movimiento m) {
		return m.getNombre() + " - Tipo: " + m.getTipo() + "\r\nEstad�sticas:\r\n" + 
				"	PP: " + m.getPP() + "\r\n	Potencia: " + m.getPotencia() + "\r\n	Precisi�n: " +
				m.getPrecision() + "\r\n	Prioridad: " + m.getPrioridad() + "\r\n";
	}
}
