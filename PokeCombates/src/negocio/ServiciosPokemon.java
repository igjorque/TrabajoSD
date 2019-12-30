package negocio;

import java.util.ArrayList;

import dominio.Movimiento;
import dominio.Pokemon;
import dominio.TablaTipos;

//Clase encargada de gestionar los m�todos y servicios que afecten al pok�mon.
public class ServiciosPokemon {
	
	// M�todo encargado de clonar un objeto de tipo Pokemon. Devuelve un nuevo objeto con los mismos atributos que el original.
	public Pokemon clonarPokemon(Pokemon p) {
		ArrayList<Movimiento> listAuxMov = new ArrayList<Movimiento>();
		listAuxMov.add(clonarMovimiento(p.getMovimientos().get(0)));
		listAuxMov.add(clonarMovimiento(p.getMovimientos().get(1)));
		listAuxMov.add(clonarMovimiento(p.getMovimientos().get(2)));
		listAuxMov.add(clonarMovimiento(p.getMovimientos().get(3)));
		
		return new Pokemon(p.getNombre(), p.getTipo(), p.getPs(), p.getAtaque(), p.getDefensa(), p.getVelocidad(), listAuxMov);
	}
	
	// M�todo encargado de clonar un objeto de tipo Movimiento. Devuelve un nuevo objeto con los mismos atributos que el original.
	public Movimiento clonarMovimiento(Movimiento m) {
		return new Movimiento(m.getNombre(), m.getTipo(), m.getDescripcion(), m.getPP(), m.getPotencia(), m.getPrecision(), m.getPrioridad());
	}
	
	// M�todo encargado de la "acci�n" de atacar. Ambos pok�mon est�n combatiendo (son el pok�mon seleccionado). S�lo puede obtener un movimiento que no se haya gastado (que tenga PPs).
	// Devuelve un valor en coma flotante. Si este es cero, significa que el ataque ha fallado.
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
	
	// M�todo encargado de actualizar la vida de un pok�mon en funci�n del da�o que ha recibido.
	private void recibirAtaque(Pokemon poke, float danno) {
		poke.setPs(poke.getPs() - (int) danno);
		if (poke.getPs() <= 0) {
			poke.setDebilitado(true);
		}
	}
	
	// M�todo encargado de mostrar los datos de un pok�mon.
	// Nota: este m�todo puede sobrar, pero hemos preferido mantenerlo por si acaso.
	public String mostrarDatos (Pokemon p) {
		return p.getNombre() + " - Tipo: " + p.getTipo() + "\r\nEstad�sticas:\r\n" +
				"	PS: " + p.getPs() + "\r\n	Ataque: " + p.getAtaque() + "\r\n	Defensa: " +
				p.getDefensa() + "\r\n	Velocidad: " + p.getVelocidad() + "\r\nMovimientos: " +
				p.getMovimientos().toString() + "\r\n";
	}
	
	// M�todo encargado de mostrar los datos de un movimiento.
	// Nota: este m�todo puede sobrar, pero hemos preferido mantenerlo por si acaso.
	public String mostrarMovimiento (Movimiento m) {
		return m.getNombre() + " - Tipo: " + m.getTipo() + "\r\nEstad�sticas:\r\n" + 
				"	PP: " + m.getPP() + "\r\n	Potencia: " + m.getPotencia() + "\r\n	Precisi�n: " +
				m.getPrecision() + "\r\n	Prioridad: " + m.getPrioridad() + "\r\n";
	}
}
