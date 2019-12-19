package negocio;

import dominio.Equipo;
import dominio.Jugador;
import dominio.Movimiento;
import dominio.Pokemon;

public class ServiciosEquipo {
	
	public boolean equipoDebilitado (Equipo e) {
		boolean b = true;
		int i = 0;
		
		while (b == true && (i<e.getListaPokemon().size())) {
			if (e.getListaPokemon().get(i).getDebilitado() == true) {
				b = false;
			}
		}
		
		return b;
	}
	
	public void cambiarPokemon (Jugador j, Pokemon p) {
		j.setSeleccionado(p);
	}
	
	public Movimiento elegirAtaque(Jugador j, int i) {
		return j.getSeleccionado().getMovimientos().get(i);
	}
}
