package net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PokeServer {

	public static void main(String[] args) {
		ServerSocket socket = null;
		ExecutorService pool = null;
		
		try {
			/*
			final CyclicBarrier barrera = new CyclicBarrier(3);
			pool = Executors.newFixedThreadPool(2);
			*/
			socket = new ServerSocket(8080);
			while (true) {
				try {
					final Socket conexion = socket.accept();
					
					
					/*
					Runnable r = new AtiendeCombate(conexion, barrera); //Pasa la barrera como parámetro para que el hilo pueda esperar. 
					pool.execute(r);
					*/
					
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
