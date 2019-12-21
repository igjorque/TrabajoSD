package net;

import java.io.IOException;
import java.net.Socket;

public class PokeClient {
	private String host;
	private int puerto;
	private int idCombate; 	//Identificador del combate en el que se encuentra actualmente el cliente (?)
							// No se inicializa en el constructor, se establece a posteriori (?)
	
	public PokeClient(String host, int puerto) {
		this.host = host;
		this.puerto = puerto;
	}
	
	public int getIdCombate() {
		return this.idCombate;
	}
	public void setIdCombate(int idCombate) {
		this.idCombate = idCombate;
	}

	public void ejecutar() {
		try(Socket s = new Socket(this.host, this.puerto);
				/*BufferedReader br = new BufferedReader(new InputStreamReader(new DataInputStream(s.getInputStream())));*/){
			
			// Accion para ejecutar cliente
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
