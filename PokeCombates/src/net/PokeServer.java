package net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PokeServer {

	public static void main(String[] args) {
		ServerSocket socket = null;
		ExecutorService pool = null;
		int i = 1;
		try {
			pool = Executors.newCachedThreadPool();
			socket = new ServerSocket(8080);
			while (true) {
				try {
					final Socket conexion = socket.accept();
					System.out.println("Atiendo al cliente " + i);
					Runnable r = new AtiendeCombate(conexion, i);
					i++;
					pool.execute(r);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			pool.shutdown();
			if (socket != null)
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}


	}

}
