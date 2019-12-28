package dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class Equipo implements Serializable {
	
	private List<Pokemon> listaPokemon;
	
	public Equipo() {
		this.listaPokemon = new ArrayList<Pokemon>();
	}

	public List<Pokemon> getListaPokemon() {
		return listaPokemon;
	}
	
	public void addPokemon(Pokemon p) {
		if (!(this.listaPokemon.size() >= 6)) {
			listaPokemon.add(p);
		}
	}
}
