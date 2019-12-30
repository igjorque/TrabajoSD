package negocio;

import dominio.Equipo;
import dominio.Jugador;
import dominio.Movimiento;
import dominio.Pokemon;

// Clase encargada de gestionar los métodos y servicios que afecten al equipo.
public class ServiciosEquipo {
	
	// Método encargado de comprobar si un equipo está completamente debilitado.
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
	
	// Método encargado de forzar la derrota del jugador (oor rendición). Establece todos los pokémon del jugador como debilitados.
	public void rendirse (Equipo e) {
		for (int i = 0; i < e.getListaPokemon().size(); i++) {
			e.getListaPokemon().get(i).setDebilitado(true);
		}
	}
	
	// Método encargado de cambiar el pokémon seleccionado de un jugador.
	public void cambiarPokemon (Jugador j, Pokemon p) {
		j.setSeleccionado(p);
	}
	
	// Método encargado de elegir un ataque del pokémon seleccionado.
	public Movimiento elegirAtaque(Jugador j, int i) {
		return j.getSeleccionado().getMovimientos().get(i);
	}
}
