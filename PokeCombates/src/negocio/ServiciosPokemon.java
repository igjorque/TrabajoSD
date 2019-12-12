package negocio;

import dominio.Pokemon;

public class ServiciosPokemon {
	
	public void recibirAtaque(Pokemon poke, float danno) {
		poke.setPs((int) (poke.getPs() - danno));
		if (poke.getPs() <= 0) {
			poke.setDebilitado(true);
		}
	}
}
