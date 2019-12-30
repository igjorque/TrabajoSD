package net;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import dominio.*;
import persistencia.MovimientoPers;
import persistencia.PokemonPers;

// Clase encargada de la gestión de la comunicación entre el cliente y el servidor de ficheros.
// Obtiene las listas de pokémon y movimientos de la capa de persistencia, y se las transmite al cliente.

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
