package dominio;

import java.util.ArrayList;
import java.util.List;

public class Equipo {
	
	private List<Pokemon> listaPokemon;
	
	public Equipo() {
		this.listaPokemon = new ArrayList<Pokemon>();
	}

	public List<Pokemon> getListaPokemon() {
		return listaPokemon;
	}
	
	public Pokemon primero() {
		if (!this.listaPokemon.isEmpty()) {
			return this.listaPokemon.get(0);
		}
		else {
			return null;
		}
	}
	
	public boolean equipoDebilitado() {
		boolean b = true;
		int i = 0;
		
		while (b == true && (i<this.listaPokemon.size())) {
			if (this.listaPokemon.get(i).getDebilitado() == true) {
				b = false;
			}
		}
		
		return b;
	}
}
