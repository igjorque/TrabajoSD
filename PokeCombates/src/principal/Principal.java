package principal;

import net.PokeClient;

public class Principal {

	public static void main(String[] args) {
		
		String host = "localhost";
		int puerto = 8080;
		int puertoFich = 8081;
		
		PokeClient pc = new PokeClient(host, puerto, puertoFich);
		pc.ejecutar();

	}

}
