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
}
