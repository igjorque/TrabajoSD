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
import presentacion.presConsola;

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
				
				Ocurren cosas y manda mensaje
				protocoloEnviarDatos(w, dan1, dan2, deb1, deb2);
			*/
			
		} 
		
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
		
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		} 
		
		catch (BrokenBarrierException e) 
		{
			e.printStackTrace();
		}
		
	}
	
	private void protocoloEnviarDatos(Writer w, boolean danno1, boolean danno2, boolean deb1, boolean deb2) {
		try {
			w.write("MensajeInicio");
			
			w.write("DannoJ1");
			if (danno1) {
				// w.write(nomJugador, danno);
			} else {
				w.write("nulo");
			}

			w.write("DannoJ2");
			if (danno2) {
				// w.write(nomJugador, danno);
			} else {
				w.write("nulo");
			}

			w.write("DebilitadoJ1");
			if (deb1) {
				// w.write(nomJugador);
			} else {
				w.write("nulo");
			}

			w.write("DebilitadoJ2");
			if (deb2) {
				// w.write(nomJugador);
			} else {
				w.write("nulo");
			}

			w.write("MensajeFin");
		} 
		
		catch (IOException e) {
			e.printStackTrace();
		}
	}

}
