package dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class Equipo implements Serializable {
	
	private List<Pokemon> listaPokemon;
	
	// Constructor del objeto Equipo. Inicia una lista vac�a. El usuario la rellena a posteriori.
	public Equipo() {
		this.listaPokemon = new ArrayList<Pokemon>();
	}

	// Obtiene la lista de pok�mon.
	public List<Pokemon> getListaPokemon() {
		return listaPokemon;
	}
	
	// A�ade un pok�mon a la lista si y solo si el tama�o de la misma es inferior a 6.
	public void addPokemon(Pokemon p) {
		if (!(this.listaPokemon.size() >= 6)) {
			listaPokemon.add(p);
		}
	}
}
