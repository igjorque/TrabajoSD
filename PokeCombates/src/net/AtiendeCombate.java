package net;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class AtiendeCombate implements Runnable{
	private Socket conexion;
	private int numPeticion;
	
	
	public AtiendeCombate(Socket conexion, int i) {
		this.conexion = conexion;
		this.numPeticion = i;
	}
	
	@Override
	public void run() {
		byte buff[] = new byte[1024*4];
		int leido;
		String doc = "Doc1.txt";
		try(OutputStream os = conexion.getOutputStream();
				InputStream is = new FileInputStream(doc)){
			leido = is.read(buff);
			System.out.println("Peticion " + this.numPeticion + " - Comienza");
			
			/*
				Gestiona el combate (?). Añadir ¿barrier? para esperar a otro cliente (?). Pensar implementacion. ¿Identificador de combate para no mezclar clientes?
			*/
			
			System.out.println("Peticion " + this.numPeticion + " - Termina");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
