package net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

// Servidor encargado de proveer listas de pokémon y movimientos. Inicia un hilo al recibir una petición que se encargará de gestionar la comunicación.

public class PokeServerBD {

	public static void main(String[] args) {
		
		ServerSocket ss = null;
		
		try {
			ss = new ServerSocket(8081); // Escucha en el puerto 8081
			while (true) {
				try {
					final Socket socket = ss.accept();
					
					new Thread(new AtiendePeticionFichero(socket)).start();
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ss != null)
				try {
					ss.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}


	}

}
