package persistencia;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import dominio.*;

public class PokemonPers {
	private static String pokemon = "FicheroPokemon.txt";
	
	// M�todo encargado de obtener un listado con todos los objetos de tipo Pok�mon almacenados en la base de datos (fichero).
	public static ArrayList<Pokemon> getPokemonList() {
		
		ArrayList<Pokemon> listaPoke = new ArrayList<Pokemon>();
		
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pokemon)));){
			
			String linea;
			String [] lins; // LineaSplit
			Pokemon poke = null;
			List<Movimiento> listaMov = null;
			
			while((linea = br.readLine()) != null) {
				lins = linea.split(";");
				
				listaMov = new ArrayList<Movimiento>();
				listaMov.add(MovimientoPers.encontrarMovimiento(lins[6]));
				listaMov.add(MovimientoPers.encontrarMovimiento(lins[7]));
				listaMov.add(MovimientoPers.encontrarMovimiento(lins[8]));
				listaMov.add(MovimientoPers.encontrarMovimiento(lins[9]));
				
				poke = new Pokemon(lins[0], MetodosAuxiliares.stringToTipo(lins[1]), Integer.parseInt(lins[2]), Integer.parseInt(lins[3]), 
						Integer.parseInt(lins[4]), Integer.parseInt(lins[5]), listaMov);
				
				listaPoke.add(poke);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return listaPoke;
	}
	
	// M�todo encargado de obtener un Pok�mon concreto de la base de datos (fichero).
	public static Pokemon encontrarPokemon(String s) {

		Pokemon poke = null;

		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pokemon)));) {

			String linea;
			String[] lins; // LineaSplit
			List<Movimiento> listaMov = null;

			while ((linea = br.readLine()) != null) {
				lins = linea.split(";");

				if (lins[0].equals(s)) {
					listaMov = new ArrayList<Movimiento>();
					listaMov.add(MovimientoPers.encontrarMovimiento(lins[6]));
					listaMov.add(MovimientoPers.encontrarMovimiento(lins[7]));
					listaMov.add(MovimientoPers.encontrarMovimiento(lins[8]));
					listaMov.add(MovimientoPers.encontrarMovimiento(lins[9]));
					
					poke = new Pokemon(lins[0], MetodosAuxiliares.stringToTipo(lins[1]), Integer.parseInt(lins[2]),
							Integer.parseInt(lins[3]), Integer.parseInt(lins[4]), Integer.parseInt(lins[5]), listaMov);
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return poke;
	}
	
	
}
