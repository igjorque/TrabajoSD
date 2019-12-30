package negocio;

import dominio.Equipo;
import dominio.Jugador;
import dominio.Movimiento;
import dominio.Pokemon;

// Clase encargada de gestionar los m�todos y servicios que afecten al equipo.
public class ServiciosEquipo {
	
	// M�todo encargado de comprobar si un equipo est� completamente debilitado.
	public boolean equipoDebilitado (Equipo e) {
		boolean b = true;
		int i = 0;
		
		while (b == true && (i<e.getListaPokemon().size())) {
			if (e.getListaPokemon().get(i).getDebilitado() == false) {
				b = false;
			}
			i++;
		}
		
		return b;
	}
	
	// M�todo encargado de forzar la derrota del jugador (oor rendici�n). Establece todos los pok�mon del jugador como debilitados.
	public void rendirse (Equipo e) {
		for (int i = 0; i < e.getListaPokemon().size(); i++) {
			e.getListaPokemon().get(i).setDebilitado(true);
		}
	}
	
	// M�todo encargado de cambiar el pok�mon seleccionado de un jugador.
	public void cambiarPokemon (Jugador j, Pokemon p) {
		j.setSeleccionado(p);
	}
	
	// M�todo encargado de elegir un ataque del pok�mon seleccionado.
	public Movimiento elegirAtaque(Jugador j, int i) {
		return j.getSeleccionado().getMovimientos().get(i);
	}
}
