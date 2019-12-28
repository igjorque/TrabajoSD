package net;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;

public class PokeServer {

	public static void main(String[] args) {
		ServerSocket socket = null;
		
		try {
			socket = new ServerSocket(8080);
			while (true) {
				try {
					final Socket conexion1 = socket.accept();
					final Socket conexion2 = socket.accept();
					
					new Thread(new AtiendeCombate(conexion1, conexion2)).start();
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (socket != null)
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}


	}

}
