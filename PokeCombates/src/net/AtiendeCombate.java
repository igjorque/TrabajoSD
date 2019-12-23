package net;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import dominio.Partida;

public class AtiendeCombate implements Runnable{
	private Socket s;
	private CyclicBarrier barrera;
	
	public AtiendeCombate(Socket conexion, CyclicBarrier barrera) {
		this.s = conexion;
		this.barrera = barrera;
	}
	
	@Override
	public void run() {
		try(BufferedReader br = new BufferedReader (new InputStreamReader (s.getInputStream(), "UTF-8"));
				Writer w = new OutputStreamWriter(s.getOutputStream(), "UTF-8");){
			
			barrera.await(); // Aqui el hilo espera a que dos hilos estén en el mismo punto
			/*
				Gestiona el combate (?). Añadir ¿barrier? para esperar a otro cliente (?). Pensar implementacion. ¿Identificador de combate para no mezclar clientes?
			*/
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		}
		
	}

}
