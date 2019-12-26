package net;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import dominio.*;
import persistencia.MovimientoPers;
import persistencia.PokemonPers;

public class AtiendePeticionFichero implements Runnable {
	
	private Socket conexion;
	
	public AtiendePeticionFichero (Socket conexion) {
		this.conexion = conexion;
	}
	
	@Override
	public void run() {
		
		ArrayList<Pokemon> listaPoke = new ArrayList<Pokemon>();
		ArrayList<Movimiento> listaMov = new ArrayList<Movimiento>();
		
		try(ObjectOutputStream os = new ObjectOutputStream(conexion.getOutputStream());){
			listaPoke = PokemonPers.getPokemonList();
			listaMov = MovimientoPers.getMovimientoList();
			
			os.writeObject(listaPoke);
			os.writeObject(listaMov);
		} 
		
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
	}
}
