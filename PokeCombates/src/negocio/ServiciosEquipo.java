package negocio;

import dominio.Equipo;
import dominio.Pokemon;

public class ServiciosEquipo {
	
	
	public Pokemon primero(Equipo e) {
		if (!e.getListaPokemon().isEmpty()){
			return e.getListaPokemon().get(0);
		}
		else {
			return null;
		}
	}
	
	public boolean equipoDebilitado(Equipo e) {
		boolean b = true;
		int i = 0;
		
		while (b == true && (i<e.getListaPokemon().size())) {
			if (e.getListaPokemon().get(i).getDebilitado() == true) {
				b = false;
			}
		}
		
		return b;
	}
}
