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
			if (e.getListaPokemon().get(i).getDebilitado() == false) {
				b = false;
			}
			i++;
		}
		
		return b;
	}
	
	public void rendirse (Equipo e) {
		for (int i = 0; i < e.getListaPokemon().size(); i++) {
			e.getListaPokemon().get(i).setDebilitado(true);
		}
	}
	
	public void cambiarPokemon (Jugador j, Pokemon p) {
		j.setSeleccionado(p);
	}
	
	public Movimiento elegirAtaque(Jugador j, int i) {
		return j.getSeleccionado().getMovimientos().get(i);
	}
}
