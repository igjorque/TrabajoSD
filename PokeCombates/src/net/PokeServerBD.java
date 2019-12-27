package net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PokeServerBD {

	public static void main(String[] args) {
		
		ServerSocket ss = null;
		ExecutorService pool = null;
		
		try {
			pool = Executors.newCachedThreadPool();
			ss = new ServerSocket(8081); // Escucha en el puerto 8081
			while (true) {
				try {
					final Socket socket = ss.accept();
					Runnable r1 = new AtiendePeticionFichero(socket);
					
					pool.execute(r1);
					
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
