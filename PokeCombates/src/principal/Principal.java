package principal;

import java.util.Scanner;

import net.PokeClient;

// Ejecuta el cliente.
public class Principal {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
	
		String host = "localhost";
		int puerto = 8080;
		int puertoFich = 8081;
		
		PokeClient pc = new PokeClient(host, puerto, puertoFich);
		pc.ejecutar();
		
		System.out.println("Pulse cualquier tecla para salir.");
		sc.nextLine();
		
		sc.close();
	}

}
